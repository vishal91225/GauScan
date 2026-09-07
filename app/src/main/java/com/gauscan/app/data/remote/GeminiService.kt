package com.gauscan.app.data.remote

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.util.Log
import com.gauscan.app.BuildConfig
import com.gauscan.app.data.model.BreedResult
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeminiService @Inject constructor() {

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val gson = Gson()
    private val API_KEY = BuildConfig.GEMINI_API_KEY

    // gemini-2.5-flash confirmed in account
    private val BASE_URL =
        "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent"

    // Use AtomicBoolean for thread-safe flag — always resets properly
    private val isCallInProgress = AtomicBoolean(false)

    suspend fun identifyBreed(context: Context, imageUri: Uri): Result<BreedResult> {
        // Try to set flag — if already true, reject
        if (!isCallInProgress.compareAndSet(false, true)) {
            return Result.failure(Exception("Already processing. Please wait."))
        }

        return withContext(Dispatchers.IO) {
            try {
                if (API_KEY.isBlank() || API_KEY == "\"\"") {
                    return@withContext Result.failure(
                        Exception("Gemini API key missing!\nAdd GEMINI_API_KEY in local.properties")
                    )
                }

                val base64Image = uriToBase64(context, imageUri)
                    ?: return@withContext Result.failure(Exception("Could not read image. Please try again."))

                val prompt = """
You are an expert livestock and veterinary specialist. Analyze this image.

Is this a cattle (cow/bull/calf/ox) or buffalo of ANY breed from anywhere in the world?

Respond with ONLY a raw JSON object. No markdown. No backticks. No explanation. No thinking text. Just the JSON.

If YES (is cattle or buffalo):
{"isAnimal":true,"breedName":"exact breed name","confidence":0.85,"category":"Cattle","origin":"country or region","description":"2-3 sentences about this breed","milkYield":"X-Y L/day or N/A","characteristics":["trait1","trait2","trait3","trait4"],"uses":["use1","use2","use3"],"conservationStatus":"Stable","temperament":"Docile","bodyWeight":"X-Y kg","lifespan":"X-Y years","dietaryNeeds":"brief diet info","climateAdaptability":"climate type","economicValue":"economic note","diseaseDetected":false,"diseaseName":"","diseaseSymptoms":[],"diseaseTreatment":[],"diseaseSeverity":"","vetAdvice":""}

If disease visible: set diseaseDetected to true, fill disease fields.

Accept ALL breeds: Indian (Gir/Sahiwal/Murrah etc), European (Holstein/Simmental/Hereford etc), African (Ankole/Boran etc), American (Brahman/Angus etc), Asian (Wagyu/Hanwoo/Yak etc).

If NOT cattle or buffalo:
{"isAnimal":false,"message":"reason"}

OUTPUT ONLY JSON. NOTHING ELSE.
                """.trimIndent()

                val requestJson = gson.toJson(
                    GeminiRequest(
                        contents = listOf(
                            Content(
                                parts = listOf(
                                    Part(text = prompt),
                                    Part(inlineData = InlineData(mimeType = "image/jpeg", data = base64Image))
                                )
                            )
                        ),
                        generationConfig = GenerationConfig(temperature = 0.1f, maxOutputTokens = 1000)
                    )
                )

                val request = Request.Builder()
                    .url("$BASE_URL?key=$API_KEY")
                    .post(requestJson.toRequestBody("application/json".toMediaType()))
                    .build()

                val response = client.newCall(request).execute()
                val responseBody = response.body?.string()

                if (!response.isSuccessful || responseBody == null) {
                    val errorMsg = when (response.code) {
                        400 -> "Invalid request. Try a clearer image."
                        401, 403 -> "Invalid API key."
                        404 -> "Model unavailable."
                        429 -> "Too many requests. Wait 60 sec and retry."
                        500, 503 -> "Server error. Try again."
                        else -> "Error ${response.code}. Check internet."
                    }
                    return@withContext Result.failure(Exception(errorMsg))
                }

                // ── Extract JSON from response (handles thinking model) ──
                val jsonText = extractJsonFromResponse(responseBody)
                    ?: return@withContext Result.failure(
                        Exception("Could not read AI response. Please try again.")
                    )

                Log.d("GAUSCAN", "Extracted JSON: ${jsonText.take(200)}")

                val result = try {
                    gson.fromJson(jsonText, GeminiBreedResponse::class.java)
                } catch (e: Exception) {
                    Log.e("GAUSCAN", "Parse error: ${e.message}, JSON was: $jsonText")
                    return@withContext Result.failure(
                        Exception("AI response parse error. Please try again.")
                    )
                }

                if (result.isAnimal != true) {
                    return@withContext Result.failure(
                        Exception(
                            result.message
                                ?: "No cattle or buffalo detected.\nPlease use a clear photo."
                        )
                    )
                }

                val originValue = result.origin ?: result.originState ?: "Unknown"

                Result.success(
                    BreedResult(
                        breedName = result.breedName ?: "Unknown Breed",
                        confidence = result.confidence ?: 0.7f,
                        category = result.category ?: "Cattle",
                        description = result.description ?: "",
                        originState = originValue,
                        milkYield = result.milkYield ?: "N/A",
                        characteristics = result.characteristics ?: emptyList(),
                        uses = result.uses ?: emptyList(),
                        conservationStatus = result.conservationStatus ?: "Stable",
                        temperament = result.temperament ?: "",
                        bodyWeight = result.bodyWeight ?: "",
                        lifespan = result.lifespan ?: "",
                        dietaryNeeds = result.dietaryNeeds ?: "",
                        climateAdaptability = result.climateAdaptability ?: "",
                        economicValue = result.economicValue ?: "",
                        diseaseDetected = result.diseaseDetected ?: false,
                        diseaseName = result.diseaseName ?: "",
                        diseaseSymptoms = result.diseaseSymptoms ?: emptyList(),
                        diseaseTreatment = result.diseaseTreatment ?: emptyList(),
                        diseaseSeverity = result.diseaseSeverity ?: "",
                        vetAdvice = result.vetAdvice ?: ""
                    )
                )

            } catch (e: Exception) {
                val msg = when {
                    e.message?.contains("timeout", true) == true ->
                        "Request timed out. Check internet and try again."
                    e.message?.contains("Unable to resolve", true) == true ->
                        "No internet connection."
                    else -> "Error: ${e.message ?: "Unknown error"}"
                }
                Result.failure(Exception(msg))
            } finally {
                // ── ALWAYS reset flag, no matter what happens ──
                isCallInProgress.set(false)
            }
        }
    }

    /**
     * Extracts valid JSON from Gemini response.
     * Handles:
     * 1. Normal response: direct JSON text
     * 2. Thinking model (gemini-2.5): has thought parts + text parts
     * 3. Markdown wrapped: ```json ... ```
     */
    private fun extractJsonFromResponse(responseBody: String): String? {
        return try {
            val jsonResponse = JsonParser.parseString(responseBody).asJsonObject
            val candidates = jsonResponse.getAsJsonArray("candidates")
            val content = candidates?.get(0)?.asJsonObject?.getAsJsonObject("content")
            val parts = content?.getAsJsonArray("parts")

            if (parts == null || parts.size() == 0) return null

            // Collect all text parts
            val textParts = mutableListOf<String>()
            for (i in 0 until parts.size()) {
                val part = parts[i].asJsonObject
                val text = part.get("text")?.asString
                if (!text.isNullOrBlank()) {
                    textParts.add(text.trim())
                }
            }

            if (textParts.isEmpty()) return null

            // Try each part to find valid JSON
            // gemini-2.5 thinking: first part = thoughts, last part = actual answer
            for (text in textParts.reversed()) {
                val cleaned = cleanJson(text)
                if (cleaned != null && isValidJson(cleaned)) {
                    return cleaned
                }
            }

            // Fallback: try all parts forward
            for (text in textParts) {
                val cleaned = cleanJson(text)
                if (cleaned != null && isValidJson(cleaned)) {
                    return cleaned
                }
            }

            null
        } catch (e: Exception) {
            Log.e("GAUSCAN", "Response extraction error: ${e.message}")
            null
        }
    }

    private fun cleanJson(text: String): String? {
        var cleaned = text.trim()


        cleaned = cleaned
            .removePrefix("```json")
            .removePrefix("```JSON")
            .removePrefix("```")
            .removeSuffix("```")
            .trim()

        // Find JSON object boundaries
        val start = cleaned.indexOf('{')
        val end = cleaned.lastIndexOf('}')

        if (start == -1 || end == -1 || end <= start) return null

        return cleaned.substring(start, end + 1).trim()
    }

    private fun isValidJson(text: String): Boolean {
        return try {
            val parsed = JsonParser.parseString(text)
            parsed.isJsonObject
        } catch (e: Exception) {
            false
        }
    }

    private fun uriToBase64(context: Context, uri: Uri): String? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream.close()
            val resized = resizeBitmap(bitmap, 512)
            val out = ByteArrayOutputStream()
            resized.compress(Bitmap.CompressFormat.JPEG, 70, out)
            Base64.encodeToString(out.toByteArray(), Base64.NO_WRAP)
        } catch (e: Exception) {
            null
        }
    }

    private fun resizeBitmap(bitmap: Bitmap, maxSize: Int): Bitmap {
        val ratio = minOf(maxSize.toFloat() / bitmap.width, maxSize.toFloat() / bitmap.height)
        return if (ratio < 1f) {
            Bitmap.createScaledBitmap(
                bitmap,
                (bitmap.width * ratio).toInt(),
                (bitmap.height * ratio).toInt(),
                true
            )
        } else bitmap
    }
}

// ── Data Classes ──────────────────────────────────────────────
data class GeminiRequest(
    val contents: List<Content>,
    @SerializedName("generation_config") val generationConfig: GenerationConfig
)

data class Content(val parts: List<Part>)

data class Part(
    val text: String? = null,
    @SerializedName("inline_data") val inlineData: InlineData? = null
)

data class InlineData(
    @SerializedName("mime_type") val mimeType: String,
    val data: String
)

data class GenerationConfig(
    val temperature: Float,
    @SerializedName("max_output_tokens") val maxOutputTokens: Int
)

data class GeminiResponse(val candidates: List<Candidate>?)
data class Candidate(val content: Content?)

data class GeminiBreedResponse(
    @SerializedName("isAnimal") val isAnimal: Boolean?,
    @SerializedName("breedName") val breedName: String?,
    @SerializedName("confidence") val confidence: Float?,
    @SerializedName("category") val category: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("origin") val origin: String?,
    @SerializedName("originState") val originState: String?,
    @SerializedName("milkYield") val milkYield: String?,
    @SerializedName("characteristics") val characteristics: List<String>?,
    @SerializedName("uses") val uses: List<String>?,
    @SerializedName("conservationStatus") val conservationStatus: String?,
    @SerializedName("temperament") val temperament: String?,
    @SerializedName("bodyWeight") val bodyWeight: String?,
    @SerializedName("lifespan") val lifespan: String?,
    @SerializedName("dietaryNeeds") val dietaryNeeds: String?,
    @SerializedName("climateAdaptability") val climateAdaptability: String?,
    @SerializedName("economicValue") val economicValue: String?,
    @SerializedName("diseaseDetected") val diseaseDetected: Boolean?,
    @SerializedName("diseaseName") val diseaseName: String?,
    @SerializedName("diseaseSymptoms") val diseaseSymptoms: List<String>?,
    @SerializedName("diseaseTreatment") val diseaseTreatment: List<String>?,
    @SerializedName("diseaseSeverity") val diseaseSeverity: String?,
    @SerializedName("vetAdvice") val vetAdvice: String?,
    @SerializedName("message") val message: String?
)
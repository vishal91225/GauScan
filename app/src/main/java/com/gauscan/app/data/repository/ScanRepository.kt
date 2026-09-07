package com.gauscan.app.data.repository

import android.content.Context
import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.gauscan.app.data.model.ScanRecord
import com.gauscan.app.data.remote.GeminiService
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScanRepository @Inject constructor(
    private val geminiService: GeminiService,
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage,
    private val auth: FirebaseAuth
) {
    suspend fun identifyBreed(context: Context, imageUri: Uri): Result<String> {
        return try {
            // 1. Call Gemini AI
            val breedResult = geminiService.identifyBreed(context, imageUri).getOrThrow()

            // 2. Upload image to Firebase Storage
            val userId = auth.currentUser?.uid ?: "anonymous"
            val imageId = UUID.randomUUID().toString()
            val imageRef = storage.reference.child("scans/$userId/$imageId.jpg")

            val imageUrl = try {
                val inputStream = context.contentResolver.openInputStream(imageUri)!!
                imageRef.putStream(inputStream).await()
                inputStream.close()
                imageRef.downloadUrl.await().toString()
            } catch (e: Exception) {
                imageUri.toString()
            }

            // 3. Save to Firestore with ALL fields including disease
            val scanId = UUID.randomUUID().toString()
            val scanRecord = ScanRecord(
                id = scanId,
                userId = userId,
                imageUrl = imageUrl,
                localImagePath = imageUri.toString(),
                breedName = breedResult.breedName,
                confidence = breedResult.confidence,
                category = breedResult.category,
                originState = breedResult.originState,
                milkYield = breedResult.milkYield,
                description = breedResult.description,
                characteristics = breedResult.characteristics,
                uses = breedResult.uses,
                conservationStatus = breedResult.conservationStatus,
                temperament = breedResult.temperament,
                bodyWeight = breedResult.bodyWeight,
                lifespan = breedResult.lifespan,
                dietaryNeeds = breedResult.dietaryNeeds,
                climateAdaptability = breedResult.climateAdaptability,
                economicValue = breedResult.economicValue,
                diseaseDetected = breedResult.diseaseDetected,
                diseaseName = breedResult.diseaseName,
                diseaseSymptoms = breedResult.diseaseSymptoms,
                diseaseTreatment = breedResult.diseaseTreatment,
                diseaseSeverity = breedResult.diseaseSeverity,
                vetAdvice = breedResult.vetAdvice
            )

            firestore.collection("scans").document(scanId).set(scanRecord).await()

            // 4. Increment user scan count
            try {
                firestore.collection("users").document(userId)
                    .update("totalScans", com.google.firebase.firestore.FieldValue.increment(1))
                    .await()
            } catch (e: Exception) { /* ignore */ }

            Result.success(scanId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getScanById(scanId: String): Result<ScanRecord> {
        return try {
            val doc = firestore.collection("scans").document(scanId).get().await()
            val scan = doc.toObject(ScanRecord::class.java)
                ?: return Result.failure(Exception("Scan not found"))
            Result.success(scan.copy(id = doc.id))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUserScans(userId: String): Result<List<ScanRecord>> {
        return try {
            val snapshot = firestore.collection("scans")
                .whereEqualTo("userId", userId)
                .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .limit(50)
                .get()
                .await()
            val scans = snapshot.documents.mapNotNull { doc ->
                doc.toObject(ScanRecord::class.java)?.copy(id = doc.id)
            }
            Result.success(scans)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteScan(scanId: String): Result<Unit> {
        return try {
            firestore.collection("scans").document(scanId).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
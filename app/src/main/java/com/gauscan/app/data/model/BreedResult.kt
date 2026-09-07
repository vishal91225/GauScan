package com.gauscan.app.data.model

data class BreedResult(
    val breedName: String = "",
    val confidence: Float = 0f,
    val category: String = "",
    val description: String = "",
    val originState: String = "",
    val milkYield: String = "",
    val characteristics: List<String> = emptyList(),
    val uses: List<String> = emptyList(),
    val conservationStatus: String = "",
    val temperament: String = "",
    val bodyWeight: String = "",
    val lifespan: String = "",
    val dietaryNeeds: String = "",
    val climateAdaptability: String = "",
    val economicValue: String = "",
    // Disease Detection Fields
    val diseaseDetected: Boolean = false,
    val diseaseName: String = "",
    val diseaseSymptoms: List<String> = emptyList(),
    val diseaseTreatment: List<String> = emptyList(),
    val diseaseSeverity: String = "",  // "Low", "Medium", "High"
    val vetAdvice: String = ""
)
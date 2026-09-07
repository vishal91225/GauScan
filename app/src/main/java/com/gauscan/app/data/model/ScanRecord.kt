package com.gauscan.app.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.PropertyName

data class ScanRecord(
    val id: String = "",
    val userId: String = "",
    val imageUrl: String = "",
    val localImagePath: String = "",
    val breedName: String = "",
    val confidence: Float = 0f,
    val category: String = "",
    val originState: String = "",
    val milkYield: String = "",
    val description: String = "",
    val characteristics: List<String> = emptyList(),
    val uses: List<String> = emptyList(),
    val conservationStatus: String = "",
    val temperament: String = "",
    val bodyWeight: String = "",
    val lifespan: String = "",
    val dietaryNeeds: String = "",
    val climateAdaptability: String = "",
    val economicValue: String = "",
    // Disease Detection
    val diseaseDetected: Boolean = false,
    val diseaseName: String = "",
    val diseaseSymptoms: List<String> = emptyList(),
    val diseaseTreatment: List<String> = emptyList(),
    val diseaseSeverity: String = "",
    val vetAdvice: String = "",
    val timestamp: Timestamp = Timestamp.now(),
    @get:PropertyName("isSaved")
    @set:PropertyName("isSaved")
    var isSaved: Boolean = true) {
    constructor() : this("")
}
package com.gauscan.app.utils

object Constants {
    // Firestore Collections
    const val COLLECTION_USERS = "users"
    const val COLLECTION_SCANS = "scans"

    // Storage Paths
    const val STORAGE_SCANS = "scans"

    // Preferences
    const val PREF_ONBOARDING_DONE = "onboarding_done"
    const val PREF_THEME_MODE = "theme_mode"

    // Scan limits (Firebase free tier)
    const val MAX_SCAN_HISTORY = 50
    const val IMAGE_MAX_SIZE_PX = 800
    const val IMAGE_QUALITY = 80

    // Breed categories
    const val CATEGORY_CATTLE = "Cattle"
    const val CATEGORY_BUFFALO = "Buffalo"

    // Confidence thresholds
    const val CONFIDENCE_HIGH = 0.80f
    const val CONFIDENCE_MEDIUM = 0.50f

    // Breed of the day (cycles through list)
    val BREEDS_ROTATION = listOf(
        "Gir", "Sahiwal", "Murrah", "Ongole",
        "Tharparkar", "Jaffarabadi", "Red Sindhi",
        "Nili-Ravi", "Kankrej", "Bhadawari"
    )
}
package com.gauscan.app.data.model

import com.google.firebase.Timestamp

data class UserProfile(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val photoUrl: String = "",
    val profession: String = "Farmer",  // Farmer, Veterinarian, Researcher, Student
    val location: String = "",
    val totalScans: Int = 0,
    val joinedDate: Timestamp = Timestamp.now()
) {
    constructor() : this("")
}
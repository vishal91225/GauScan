package com.gauscan.app.presentation.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.gauscan.app.data.model.UserProfile
import com.gauscan.app.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

data class ProfileUiState(
    val isLoading: Boolean = false,
    val userProfile: UserProfile? = null,
    val totalScans: Int = 0,
    val cattleScans: Int = 0,
    val buffaloScans: Int = 0,
    val isSignedOut: Boolean = false,
    val errorMessage: String? = null,
    val showSignOutDialog: Boolean = false
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadProfile()
    }

    private fun loadProfile() {
        val userId = auth.currentUser?.uid ?: return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                // Load user profile from Firestore
                val userDoc = firestore.collection("users")
                    .document(userId).get().await()
                val profile = userDoc.toObject(UserProfile::class.java)

                // Load scan stats
                val scans = firestore.collection("scans")
                    .whereEqualTo("userId", userId)
                    .get().await()
                val scanList = scans.documents
                val cattleCount = scanList.count { it.getString("category") == "Cattle" }
                val buffaloCount = scanList.count { it.getString("category") == "Buffalo" }

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    userProfile = profile ?: UserProfile(
                        uid = userId,
                        name = auth.currentUser?.displayName ?: "",
                        email = auth.currentUser?.email ?: "",
                        photoUrl = auth.currentUser?.photoUrl?.toString() ?: ""
                    ),
                    totalScans = scanList.size,
                    cattleScans = cattleCount,
                    buffaloScans = buffaloCount
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message
                )
            }
        }
    }

    fun showSignOutDialog() {
        _uiState.value = _uiState.value.copy(showSignOutDialog = true)
    }

    fun hideSignOutDialog() {
        _uiState.value = _uiState.value.copy(showSignOutDialog = false)
    }

    fun signOut() {
        authRepository.signOut()
        _uiState.value = _uiState.value.copy(isSignedOut = true)
    }

    fun updateProfession(profession: String) {
        val userId = auth.currentUser?.uid ?: return
        viewModelScope.launch {
            try {
                firestore.collection("users").document(userId)
                    .update("profession", profession).await()
                _uiState.value = _uiState.value.copy(
                    userProfile = _uiState.value.userProfile?.copy(profession = profession)
                )
            } catch (e: Exception) { /* Handle error */ }
        }
    }
}
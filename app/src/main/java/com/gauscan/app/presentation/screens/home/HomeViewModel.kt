package com.gauscan.app.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.gauscan.app.data.model.ScanRecord
import com.gauscan.app.data.repository.AuthRepository
import com.gauscan.app.data.repository.ScanRepository
import com.gauscan.app.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject
import com.gauscan.app.data.model.UserProfile
data class HomeUiState(
    val isLoading: Boolean = false,
    val userName: String = "",
    val userPhoto: String = "",
    val totalScans: Int = 0,
    val cattleScans: Int = 0,
    val buffaloScans: Int = 0,
    val recentScans: List<ScanRecord> = emptyList(),
    val breedOfTheDay: String = "Gir",
    val errorMessage: String? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val scanRepository: ScanRepository,
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadHomeData()
        setBreedOfTheDay()
    }

    private fun loadHomeData() {
        val userId = auth.currentUser?.uid ?: return
        _uiState.value = _uiState.value.copy(
            isLoading = true
        )

        firestore.collection("users")
            .document(userId)
            .get()
            .addOnSuccessListener { document ->

                val profile = document.toObject(UserProfile::class.java)

                _uiState.value = _uiState.value.copy(
                    userName = profile?.name ?: "Friend",
                    userPhoto = profile?.photoUrl
                        ?: auth.currentUser?.photoUrl?.toString()
                        ?: ""
                )
            }

        viewModelScope.launch {
            // Load user scans
            val result = scanRepository.getUserScans(userId)
            if (result.isSuccess) {
                val scans = result.getOrNull() ?: emptyList()
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    totalScans = scans.size,
                    cattleScans = scans.count { it.category == Constants.CATEGORY_CATTLE },
                    buffaloScans = scans.count { it.category == Constants.CATEGORY_BUFFALO },
                    recentScans = scans.take(5)
                )
            } else {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Failed to load data"
                )
            }
        }
    }

    private fun setBreedOfTheDay() {
        // Rotate breed based on day of year
        val dayOfYear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
        val index = dayOfYear % Constants.BREEDS_ROTATION.size
        _uiState.value = _uiState.value.copy(
            breedOfTheDay = Constants.BREEDS_ROTATION[index]
        )
    }

    fun refresh() = loadHomeData()
}
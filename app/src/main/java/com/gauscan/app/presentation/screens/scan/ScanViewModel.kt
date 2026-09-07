package com.gauscan.app.presentation.screens.scan

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gauscan.app.data.repository.ScanRepository
import com.gauscan.app.utils.ImageUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ScanUiState(
    val selectedImageUri: Uri? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val scanId: String? = null,
    val cameraImageUri: Uri? = null
)

@HiltViewModel
class ScanViewModel @Inject constructor(
    private val scanRepository: ScanRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ScanUiState())
    val uiState: StateFlow<ScanUiState> = _uiState.asStateFlow()

    // ── Guard: ek hi request ek baar ──────────────────────────
    private var identifyJob: Job? = null

    fun setSelectedImage(uri: Uri) {
        _uiState.value = _uiState.value.copy(
            selectedImageUri = uri,
            errorMessage = null
        )
    }

    fun createImageUri(context: Context): Uri {
        val uri = ImageUtils.createTempImageUri(context)
        _uiState.value = _uiState.value.copy(cameraImageUri = uri)
        return uri
    }

    fun onCameraImageCaptured() {
        _uiState.value.cameraImageUri?.let { uri ->
            _uiState.value = _uiState.value.copy(
                selectedImageUri = uri,
                errorMessage = null
            )
        }
    }

    fun clearImage() {
        identifyJob?.cancel()
        _uiState.value = _uiState.value.copy(
            selectedImageUri = null,
            errorMessage = null,
            isLoading = false
        )
    }

    fun clearState() {
        _uiState.value = _uiState.value.copy(scanId = null)
    }

    fun identifyBreed(context: Context) {
        // ── Already running? Block karo ───────────────────────
        if (_uiState.value.isLoading) return
        val imageUri = _uiState.value.selectedImageUri ?: return

        // ── Previous job cancel karke naya start karo ─────────
        identifyJob?.cancel()
        identifyJob = viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )
            val result = scanRepository.identifyBreed(context, imageUri)
            _uiState.value = if (result.isSuccess) {
                _uiState.value.copy(
                    isLoading = false,
                    scanId = result.getOrNull()
                )
            } else {
                _uiState.value.copy(
                    isLoading = false,
                    errorMessage = result.exceptionOrNull()?.message
                        ?: "Could not identify breed. Please try again."
                )
            }
        }
    }
}
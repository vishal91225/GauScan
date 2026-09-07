package com.gauscan.app.presentation.screens.result

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gauscan.app.data.model.ScanRecord
import com.gauscan.app.data.repository.ScanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ResultUiState(
    val isLoading: Boolean = false,
    val scan: ScanRecord? = null,
    val errorMessage: String? = null
)

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val scanRepository: ScanRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ResultUiState())
    val uiState: StateFlow<ResultUiState> = _uiState.asStateFlow()

    fun loadResult(scanId: String) {
        if (scanId.isBlank()) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val result = scanRepository.getScanById(scanId)
            _uiState.value = if (result.isSuccess) {
                _uiState.value.copy(
                    isLoading = false,
                    scan = result.getOrNull()
                )
            } else {
                _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Could not load result: ${result.exceptionOrNull()?.message}"
                )
            }
        }
    }

    fun shareResult(context: Context, uiState: ResultUiState) {
        val scan = uiState.scan ?: return
        val confidence = (scan.confidence * 100).toInt()
        val shareText = """
🐄 GauScan Result

Breed: ${scan.breedName}
Category: ${scan.category}
Confidence: $confidence%
Origin: ${scan.originState}
Milk Yield: ${scan.milkYield}

${scan.description}

Identified using GauScan – AI-powered Indian Cattle & Buffalo Breed Recognition
        """.trimIndent()

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
            putExtra(Intent.EXTRA_SUBJECT, "GauScan: ${scan.breedName} Breed Identified")
        }
        context.startActivity(Intent.createChooser(intent, "Share via"))
    }
}
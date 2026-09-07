package com.gauscan.app.presentation.screens.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.gauscan.app.data.model.ScanRecord
import com.gauscan.app.data.repository.ScanRepository
import com.gauscan.app.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HistoryUiState(
    val isLoading: Boolean = false,
    val scans: List<ScanRecord> = emptyList(),
    val filteredScans: List<ScanRecord> = emptyList(),
    val filterCategory: String = "All",
    val searchQuery: String = "",
    val errorMessage: String? = null
)

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val scanRepository: ScanRepository,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _uiState = MutableStateFlow(HistoryUiState())
    val uiState: StateFlow<HistoryUiState> = _uiState.asStateFlow()

    init {
        loadScans()
    }

    private fun loadScans() {
        val userId = auth.currentUser?.uid ?: return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val result = scanRepository.getUserScans(userId)
            if (result.isSuccess) {
                val scans = result.getOrNull() ?: emptyList()
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    scans = scans,
                    filteredScans = scans
                )
            } else {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Failed to load history"
                )
            }
        }
    }

    fun setFilter(category: String) {
        _uiState.value = _uiState.value.copy(filterCategory = category)
        applyFilters()
    }

    fun setSearchQuery(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
        applyFilters()
    }

    private fun applyFilters() {
        val state = _uiState.value
        val filtered = state.scans.filter { scan ->
            val categoryMatch = state.filterCategory == "All" ||
                    scan.category == state.filterCategory
            val searchMatch = state.searchQuery.isEmpty() ||
                    scan.breedName.contains(state.searchQuery, ignoreCase = true) ||
                    scan.originState.contains(state.searchQuery, ignoreCase = true)
            categoryMatch && searchMatch
        }
        _uiState.value = _uiState.value.copy(filteredScans = filtered)
    }

    fun deleteScan(scanId: String) {
        viewModelScope.launch {
            scanRepository.deleteScan(scanId)
            val updated = _uiState.value.scans.filter { it.id != scanId }
            _uiState.value = _uiState.value.copy(scans = updated)
            applyFilters()
        }
    }

    fun refresh() = loadScans()
}
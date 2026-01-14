package com.ucahealth.app.ui.sports


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucahealth.app.data.model.Sport
import com.ucahealth.app.data.repository.SportsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SportsViewModel(
    private val repository: SportsRepository = SportsRepository()
) : ViewModel() {

    private val _sports = MutableStateFlow<List<Sport>>(emptyList())
    val sports: StateFlow<List<Sport>> = _sports

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadSports() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val result = repository.getSports()
                _sports.value = result
            } catch (e: Exception) {
                _error.value = e.message ?: "Error desconocido"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
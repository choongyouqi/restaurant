package com.setel.restaurant.ui.operatinghours

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.setel.restaurant.data.OperatingHoursRepository
import com.setel.restaurant.ui.operatinghours.OperatingHoursUiState.Error
import com.setel.restaurant.ui.operatinghours.OperatingHoursUiState.Loading
import com.setel.restaurant.ui.operatinghours.OperatingHoursUiState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OperatingHoursViewModel @Inject constructor(
    private val operatingHoursRepository: OperatingHoursRepository
) : ViewModel() {

    val uiState: StateFlow<OperatingHoursUiState> = operatingHoursRepository
        .operatingHourss.map(::Success)
        .catch { Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Loading)

    fun addOperatingHours(name: String) {
        viewModelScope.launch {
            operatingHoursRepository.add(name)
        }
    }
}

sealed interface OperatingHoursUiState {
    object Loading : OperatingHoursUiState
    data class Error(val throwable: Throwable) : OperatingHoursUiState
    data class Success(val data: List<String>) : OperatingHoursUiState
}

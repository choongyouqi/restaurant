/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.setel.restaurant.ui.operatinghours

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.setel.restaurant.data.OperatingHoursRepository
import com.setel.restaurant.ui.operatinghours.OperatingHoursUiState.Error
import com.setel.restaurant.ui.operatinghours.OperatingHoursUiState.Loading
import com.setel.restaurant.ui.operatinghours.OperatingHoursUiState.Success
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

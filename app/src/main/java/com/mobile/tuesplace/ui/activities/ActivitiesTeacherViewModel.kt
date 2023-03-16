package com.mobile.tuesplace.ui.activities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.tuesplace.data.AgendaResponseData
import com.mobile.tuesplace.data.ProfileData
import com.mobile.tuesplace.data.ProfileResponseData
import com.mobile.tuesplace.services.ActivitiesService
import com.mobile.tuesplace.services.ProfileService
import com.mobile.tuesplace.ui.states.GetActivitiesUiState
import com.mobile.tuesplace.ui.states.GetProfileByIdUiState
import com.mobile.tuesplace.usecase.GetActivitiesUseCase
import com.mobile.tuesplace.usecase.GetMyActivitiesUseCase
import com.mobile.tuesplace.usecase.GetProfileByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ActivitiesTeacherViewModel(
    private val getActivitiesUseCase: GetActivitiesUseCase,
    private val getMyActivitiesUseCase: GetMyActivitiesUseCase
    ): ViewModel() {
    private val _getActivitiesStateFlow = MutableStateFlow<GetActivitiesUiState>(
        GetActivitiesUiState.Empty)
    val getActivitiesStateFlow: StateFlow<GetActivitiesUiState> = _getActivitiesStateFlow

    fun getActivities() {
        viewModelScope.launch {
            getActivitiesUseCase.invoke(object :
                ActivitiesService.ActivitiesCallback<List<AgendaResponseData>> {
                override fun onSuccess(data: List<AgendaResponseData>) {
                    viewModelScope.launch {
                        _getActivitiesStateFlow.emit(GetActivitiesUiState.Success(data))
                    }
                }

                override fun onError(error: String) {
                    viewModelScope.launch {
                        _getActivitiesStateFlow.emit(GetActivitiesUiState.Error(error))
                    }
                }
            })
        }
    }

    fun getMyActivities(){
        viewModelScope.launch {
            getMyActivitiesUseCase.invoke(
                object : ActivitiesService.ActivitiesCallback<List<AgendaResponseData>> {
                    override fun onSuccess(data: List<AgendaResponseData>) {
                        viewModelScope.launch {
                            _getActivitiesStateFlow.emit(GetActivitiesUiState.Success(data))
                        }
                    }

                    override fun onError(error: String) {
                        viewModelScope.launch {
                            _getActivitiesStateFlow.emit(GetActivitiesUiState.Error(error))
                        }
                    }

                }
            )
        }
    }
}
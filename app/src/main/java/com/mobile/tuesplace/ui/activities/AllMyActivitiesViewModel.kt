package com.mobile.tuesplace.ui.activities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.tuesplace.data.AgendaResponseData
import com.mobile.tuesplace.services.ActivitiesService
import com.mobile.tuesplace.ui.states.GetMyActivitiesUiState
import com.mobile.tuesplace.usecase.GetMyActivitiesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AllMyActivitiesViewModel(private val getMyActivitiesUseCase: GetMyActivitiesUseCase): ViewModel() {

    private val _getMyActivitiesStateFlow =
        MutableStateFlow<GetMyActivitiesUiState>(GetMyActivitiesUiState.Empty)
    val getMyActivitiesStateFlow: StateFlow<GetMyActivitiesUiState> = _getMyActivitiesStateFlow

    fun getMyActivities() {
        viewModelScope.launch {
            getMyActivitiesUseCase.invoke(object :
                ActivitiesService.ActivitiesCallback<List<AgendaResponseData>> {
                override fun onSuccess(data: List<AgendaResponseData>) {
                    viewModelScope.launch {
                        _getMyActivitiesStateFlow.emit(GetMyActivitiesUiState.Success(data))
                    }
                }

                override fun onError(error: String) {
                    viewModelScope.launch {
                        _getMyActivitiesStateFlow.emit(GetMyActivitiesUiState.Error(error))
                    }
                }
            })
        }
    }
}
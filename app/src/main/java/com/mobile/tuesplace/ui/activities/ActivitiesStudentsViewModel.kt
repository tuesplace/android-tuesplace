package com.mobile.tuesplace.ui.activities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.tuesplace.data.AgendaResponseData
import com.mobile.tuesplace.services.ActivitiesService
import com.mobile.tuesplace.ui.states.GetActivitiesUiState
import com.mobile.tuesplace.usecase.GetActivitiesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ActivitiesStudentsViewModel(private val getActivitiesUseCase: GetActivitiesUseCase) :
    ViewModel() {

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

    private val _setVisibilityStateFlow = MutableStateFlow<SetVisibility>(SetVisibility.Loaded(arrayListOf(1,0,0,0)))
    val setVisibilityStateFlow: StateFlow<SetVisibility> = _setVisibilityStateFlow

    fun setVisibilities(index: Int) {
        if (_setVisibilityStateFlow.value is SetVisibility.Loaded) {
            val items = (_setVisibilityStateFlow.value as SetVisibility.Loaded).items
            for (i in 0 until items.size) {
                if (i != index) {
                    items[i] = 0
                } else {
                    items[i] = 1
                }
            }
            viewModelScope.launch {
                _setVisibilityStateFlow.emit(SetVisibility.Loaded(items))
            }
        }
    }

    sealed class SetVisibility {
        class Loaded(var items: java.util.ArrayList<Int>) : SetVisibility()
        object None : SetVisibility()
    }

}
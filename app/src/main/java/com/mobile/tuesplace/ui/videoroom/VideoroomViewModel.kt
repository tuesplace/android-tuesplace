package com.mobile.tuesplace.ui.videoroom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.tuesplace.data.GroupResponseData
import com.mobile.tuesplace.services.GroupService
import com.mobile.tuesplace.ui.states.GetMyGroupsUiState
import com.mobile.tuesplace.usecase.GetMyGroupsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VideoroomViewModel(private val groupsUseCase: GetMyGroupsUseCase): ViewModel() {

    private val _getGroupStateFlow = MutableStateFlow<GetMyGroupsUiState>(GetMyGroupsUiState.Empty)
    val getGroupStateFlow: StateFlow<GetMyGroupsUiState> = _getGroupStateFlow

    fun getGroups() {
        viewModelScope.launch {
            groupsUseCase.invoke(object : GroupService.GroupCallback<List<GroupResponseData>> {
                override fun onSuccess(groupGeneric: List<GroupResponseData>) {
                    viewModelScope.launch {
                        _getGroupStateFlow.emit(GetMyGroupsUiState.Success(groupGeneric))
                    }
                }

                override fun onError(error: String) {
                    viewModelScope.launch {
                        _getGroupStateFlow.emit(GetMyGroupsUiState.Error(error))
                    }
                }

            })
        }
    }
}
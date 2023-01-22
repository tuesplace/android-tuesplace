package com.mobile.tuesplace.ui.groups

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.tuesplace.data.GroupResponseData
import com.mobile.tuesplace.services.GroupService
import com.mobile.tuesplace.ui.states.GetGroupsUiState
import com.mobile.tuesplace.usecase.GetGroupsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AllGroupsViewModel(private val getGroupsUseCase: GetGroupsUseCase): ViewModel() {

    private val _getGroupStateFlow = MutableStateFlow<GetGroupsUiState>(GetGroupsUiState.Empty)

    fun getGroups() {
        viewModelScope.launch {
            getGroupsUseCase.invoke(object : GroupService.GroupCallback<List<GroupResponseData>> {
                override fun onSuccess(groupGeneric: List<GroupResponseData>) {
                    viewModelScope.launch {
                        _getGroupStateFlow.emit(GetGroupsUiState.Success)
                    }
                }

                override fun onError(error: String) {
                    viewModelScope.launch {
                        _getGroupStateFlow.emit(GetGroupsUiState.Error(error))
                    }
                }

            })
        }
    }

}
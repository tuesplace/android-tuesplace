package com.mobile.tuesplace.ui.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.tuesplace.data.GroupData
import com.mobile.tuesplace.services.GroupService
import com.mobile.tuesplace.ui.states.CreateGroupUiState
import com.mobile.tuesplace.ui.states.GetGroupsUiState
import com.mobile.tuesplace.usecase.CreateGroupUseCase
import com.mobile.tuesplace.usecase.GetGroupsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class WelcomeViewModel(
    private val createGroupUseCase: CreateGroupUseCase,
    private val getGroupsUseCase: GetGroupsUseCase,
) : ViewModel() {

    private val _uiStateFlow = MutableStateFlow<CreateGroupUiState>(CreateGroupUiState.Empty)

    fun createGroup(groupData: GroupData) {
        viewModelScope.launch {
            createGroupUseCase.invoke(groupData, object : GroupService.GroupCallback<GroupData> {
                override fun onError(error: String) {
                    viewModelScope.launch {
                        _uiStateFlow.emit(CreateGroupUiState.Error(error))
                    }
                }

                override fun onSuccess(groupGeneric: GroupData) {
                    viewModelScope.launch {
                        _uiStateFlow.emit(CreateGroupUiState.Success)
                    }
                }
            })
        }
    }

    private val _getGroupstateFlow = MutableStateFlow<GetGroupsUiState>(GetGroupsUiState.Empty)

    fun getGroups() {
        viewModelScope.launch {
            getGroupsUseCase.invoke(object : GroupService.GroupCallback<List<GroupData>> {
                override fun onSuccess(groupGeneric: List<GroupData>) {
                    viewModelScope.launch {
                        _getGroupstateFlow.emit(GetGroupsUiState.Success)
                    }
                }

                override fun onError(error: String) {
                    viewModelScope.launch {
                        _getGroupstateFlow.emit(GetGroupsUiState.Error(error))
                    }
                }

            })
        }
    }

}
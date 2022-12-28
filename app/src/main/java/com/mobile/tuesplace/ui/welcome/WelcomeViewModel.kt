package com.mobile.tuesplace.ui.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.tuesplace.data.CreateGroupResponse
import com.mobile.tuesplace.data.GroupData
import com.mobile.tuesplace.services.GroupService
import com.mobile.tuesplace.ui.states.CreateGroupUiState
import com.mobile.tuesplace.usecase.CreateGroupUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class WelcomeViewModel(private val createGroupUseCase: CreateGroupUseCase): ViewModel() {

    private val _uiStateFlow = MutableStateFlow<CreateGroupUiState>(CreateGroupUiState.Empty)

    fun createGroup(groupData: GroupData){
        viewModelScope.launch {
            createGroupUseCase.invoke(groupData, object : GroupService.CreateGroupCallback {
                override fun onError(error: String) {
                    viewModelScope.launch {
                        _uiStateFlow.emit(CreateGroupUiState.Error(error))
                    }
                }

                override fun onSuccess(
                    createGroupResponse: CreateGroupResponse,
                    createGroupData: GroupData
                ) {
                    viewModelScope.launch {
                        _uiStateFlow.emit(CreateGroupUiState.Success)
                    }
                }
            })
        }
    }

}
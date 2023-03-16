package com.mobile.tuesplace.ui.chats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.tuesplace.data.GroupData
import com.mobile.tuesplace.data.GroupResponseData
import com.mobile.tuesplace.services.GroupService
import com.mobile.tuesplace.ui.states.GetGroupUiState
import com.mobile.tuesplace.usecase.GetGroupUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChatroomViewModel(private val getGroupUseCase: GetGroupUseCase): ViewModel() {
    private val _getGroupStateFlow = MutableStateFlow<GetGroupUiState>(GetGroupUiState.Empty)
    val groupStateFlow: StateFlow<GetGroupUiState> = _getGroupStateFlow

    fun getGroup(groupId: String){
        viewModelScope.launch {
            getGroupUseCase.invoke(object : GroupService.GroupCallback<GroupResponseData>{
                override fun onSuccess(groupGeneric: GroupResponseData) {
                    viewModelScope.launch {
                        _getGroupStateFlow.emit(GetGroupUiState.Success(groupGeneric))
                    }
                }

                override fun onError(error: String) {
                    viewModelScope.launch {
                        _getGroupStateFlow.emit(GetGroupUiState.Error(error))
                    }
                }
            }, groupId)
        }
    }
}
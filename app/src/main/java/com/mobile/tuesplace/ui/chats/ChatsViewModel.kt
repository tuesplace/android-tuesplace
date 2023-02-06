package com.mobile.tuesplace.ui.chats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.tuesplace.data.GroupResponseData
import com.mobile.tuesplace.services.GroupService
import com.mobile.tuesplace.ui.states.GetMyGroupsUiState
import com.mobile.tuesplace.usecase.GetMyGroupsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChatsViewModel(private val getMyGroupsUseCase: GetMyGroupsUseCase): ViewModel() {
    private val _getMyGroupsStateFlow = MutableStateFlow<GetMyGroupsUiState>(
        GetMyGroupsUiState.Empty)
    val getMyGroupsStateFlow: StateFlow<GetMyGroupsUiState> = _getMyGroupsStateFlow

    //getting all groups with type 'chat'
    fun getMyGroups(){
        viewModelScope.launch {
            getMyGroupsUseCase.invoke(
                object : GroupService.GroupCallback<List<GroupResponseData>> {
                    override fun onSuccess(groupGeneric: List<GroupResponseData>) {
                        viewModelScope.launch{
                            _getMyGroupsStateFlow.emit(GetMyGroupsUiState.Success(groupGeneric.filter {
                                it.type == "chat"
                            }))
                        }
                    }

                    override fun onError(error: String) {
                        viewModelScope.launch {
                            _getMyGroupsStateFlow.emit(GetMyGroupsUiState.Error(error))
                        }
                    }

                }
            )
        }
    }
}
package com.mobile.tuesplace.ui.groups

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.tuesplace.data.EditGroupData
import com.mobile.tuesplace.data.GroupData
import com.mobile.tuesplace.data.GroupResponseData
import com.mobile.tuesplace.services.GroupService
import com.mobile.tuesplace.ui.states.DeleteGroupUiState
import com.mobile.tuesplace.ui.states.GetGroupUiState
import com.mobile.tuesplace.usecase.DeleteGroupUseCase
import com.mobile.tuesplace.usecase.EditGroupUseCase
import com.mobile.tuesplace.usecase.GetGroupUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EditGroupViewModel(
    private val groupUseCase: GetGroupUseCase,
    private val deleteGroupUseCase: DeleteGroupUseCase,
    private val editGroupUseCase: EditGroupUseCase,
) : ViewModel() {
    private val _groupName =
        MutableStateFlow("")
    val groupName: StateFlow<String> = _groupName

    fun groupName(nameInput: String) {
        _groupName.value = nameInput
    }

    private val _groupType =
        MutableStateFlow("")
    val groupType: StateFlow<String> = _groupType

    fun groupType(typeInput: String) {
        _groupType.value = typeInput
    }

    private val _classes =
        MutableStateFlow("")
    val classes: StateFlow<String> = _classes

    fun classes(classesInput: String) {
        _classes.value = classesInput
    }

    private val _getGroupStateFlow = MutableStateFlow<GetGroupUiState>(GetGroupUiState.Empty)
    val groupStateFlow: StateFlow<GetGroupUiState> = _getGroupStateFlow

    fun getGroup(groupId: String) {
        viewModelScope.launch {
            groupUseCase.invoke(object : GroupService.GroupCallback<GroupResponseData> {
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

    private val _deleteGroupUiStateFlow =
        MutableStateFlow<DeleteGroupUiState>(DeleteGroupUiState.Empty)
    val deleteGroupUiStateFlow: StateFlow<DeleteGroupUiState> = _deleteGroupUiStateFlow

    fun deleteGroup(groupId: String) {
        viewModelScope.launch {
            deleteGroupUseCase.invoke(object : GroupService.GroupCallback<Unit> {
                override fun onSuccess(groupGeneric: Unit) {
                    viewModelScope.launch {
                        _deleteGroupUiStateFlow.emit(DeleteGroupUiState.Success)
                    }
                }

                override fun onError(error: String) {
                    viewModelScope.launch {
                        _deleteGroupUiStateFlow.emit(DeleteGroupUiState.Error(error))
                    }
                }
            }, groupId)
        }
    }

    private val _editGroupUiStateFlow =
        MutableStateFlow<DeleteGroupUiState>(DeleteGroupUiState.Empty)
    val editGroupUiStateFlow: StateFlow<DeleteGroupUiState> = _editGroupUiStateFlow

    fun editGroup(groupId: String, editGroupData: EditGroupData) {
        viewModelScope.launch {
            editGroupUseCase.invoke(
                object : GroupService.GroupCallback<Unit> {
                    override fun onSuccess(groupGeneric: Unit) {
                        viewModelScope.launch {
                            _editGroupUiStateFlow.emit(DeleteGroupUiState.Success)
                        }
                    }

                    override fun onError(error: String) {
                        viewModelScope.launch {
                            _editGroupUiStateFlow.emit(DeleteGroupUiState.Error(error))
                        }
                    }

                }, groupId = groupId, editGroupData = editGroupData
            )
        }
    }

    fun resetEditState(){
        viewModelScope.launch{
            _editGroupUiStateFlow.emit(DeleteGroupUiState.Empty)
        }
    }

    fun resetDeleteState(){
        viewModelScope.launch{
            _deleteGroupUiStateFlow.emit(DeleteGroupUiState.Empty)
        }
    }
}
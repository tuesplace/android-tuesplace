package com.mobile.tuesplace.ui.groups

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.tuesplace.data.GroupData
import com.mobile.tuesplace.services.GroupService
import com.mobile.tuesplace.ui.states.GetGroupUiState
import com.mobile.tuesplace.usecase.GetGroupUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EditGroupViewModel(private val groupUseCase: GetGroupUseCase): ViewModel() {
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

    fun getGroup(groupId: String){
        viewModelScope.launch {
            groupUseCase.invoke(object : GroupService.GroupCallback<GroupData>{
                override fun onSuccess(groupGeneric: GroupData) {
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
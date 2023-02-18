package com.mobile.tuesplace.ui.groups

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.tuesplace.data.GroupData
import com.mobile.tuesplace.services.GroupService
import com.mobile.tuesplace.ui.states.CreateGroupUiState
import com.mobile.tuesplace.usecase.CreateGroupUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CreateGroupViewModel(private val createGroupUseCase: CreateGroupUseCase): ViewModel() {

    private val _groupName =
        MutableStateFlow("")
    val groupName: StateFlow<String> = _groupName

    fun groupName(nameInput: String) {
        _groupName.value = nameInput
    }

    private val _teachers =
        MutableStateFlow("")
    val teachers: StateFlow<String> = _teachers

    fun teachers(teacher: String) {
        _teachers.value = teacher
    }

    private val _classes =
        MutableStateFlow("")
    val classes: StateFlow<String> = _classes

    fun classes(classesInput: String) {
        _classes.value = classesInput
    }

    private val _groupsTypeStateFlow = MutableStateFlow(false)
    val groupsTypeStateFlow: StateFlow<Boolean> = _groupsTypeStateFlow

    fun groupsType(groupsType: Boolean) {
        _groupsTypeStateFlow.value = !groupsType
    }

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
}
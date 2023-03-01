package com.mobile.tuesplace.ui.classes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.tuesplace.data.GroupResponseData
import com.mobile.tuesplace.services.GroupService
import com.mobile.tuesplace.ui.states.GetMyGroupsUiState
import com.mobile.tuesplace.usecase.GetMyGroupsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ClassesViewModel(private val getMyGroupsUseCase: GetMyGroupsUseCase): ViewModel() {

    private val _getMyGroupsStateFlow = MutableStateFlow<GetMyGroupsUiState>(
        GetMyGroupsUiState.Empty)
    val getMyGroupsStateFlow: StateFlow<GetMyGroupsUiState> = _getMyGroupsStateFlow

    //getting all groups with type 'class'
    fun getMyGroups(){
        viewModelScope.launch {
            getMyGroupsUseCase.invoke(
                object : GroupService.GroupCallback<List<GroupResponseData>> {
                    override fun onSuccess(groupGeneric: List<GroupResponseData>) {
                        viewModelScope.launch{
                            val classes = groupGeneric.filter { it.type == "subject" }
                            _getMyGroupsStateFlow.emit(GetMyGroupsUiState.Success(classes))
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
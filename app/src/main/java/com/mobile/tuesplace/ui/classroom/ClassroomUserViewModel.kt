package com.mobile.tuesplace.ui.classroom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.tuesplace.data.*
import com.mobile.tuesplace.services.GroupService
import com.mobile.tuesplace.services.PostService
import com.mobile.tuesplace.ui.states.GetGroupUiState
import com.mobile.tuesplace.ui.states.GetPostsUiState
import com.mobile.tuesplace.usecase.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ClassroomUserViewModel(
    private val getGroupUseCase: GetGroupUseCase,
    private val getPostsUseCase: GetPostsUseCase,
) : ViewModel() {

    private val _getGroupStateFlow = MutableStateFlow<GetGroupUiState>(GetGroupUiState.Empty)
    val getGroupStateFlow: StateFlow<GetGroupUiState> = _getGroupStateFlow

    fun getGroup(groupId: String) {
        viewModelScope.launch {
            getGroupUseCase.invoke(object : GroupService.GroupCallback<GroupData> {
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

    private val _getPostsStateFlow = MutableStateFlow<GetPostsUiState>(GetPostsUiState.Empty)
    val getPostsStateFlow: StateFlow<GetPostsUiState> = _getPostsStateFlow

    fun getPosts(groupId: String) {
        viewModelScope.launch {
            getPostsUseCase.invoke(groupId,
                object : PostService.PostCallback<List<PostResponseData>> {
                    override fun onSuccess(generic: List<PostResponseData>) {
                        viewModelScope.launch {
                            _getPostsStateFlow.emit(GetPostsUiState.Success(generic))
                        }
                    }

                    override fun onError(error: String) {
                        viewModelScope.launch {
                            _getPostsStateFlow.emit(GetPostsUiState.Error(error))
                        }
                    }
                })
        }
    }
}
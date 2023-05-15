package com.mobile.tuesplace.ui.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.tuesplace.PostRequestData
import com.mobile.tuesplace.services.PostService
import com.mobile.tuesplace.ui.states.CreatePostUiState
import com.mobile.tuesplace.usecase.CreatePostUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CreatePostViewModel(private val createPostUseCase: CreatePostUseCase) : ViewModel() {

    private val _postName =
        MutableStateFlow("")
    val postName: StateFlow<String> = _postName

    fun postName(nameInput: String) {
        _postName.value = nameInput
    }

    private val _postDescription =
        MutableStateFlow("")
    val postDescription: StateFlow<String> = _postDescription

    fun postDescription(descriptionInput: String) {
        _postDescription.value = descriptionInput
    }

    private val _postDeadline =
        MutableStateFlow("")
    val postDeadline: StateFlow<String> = _postDeadline

    fun postDeadline(deadlineInput: String) {
        _postDeadline.value = deadlineInput
    }

    private val _postType = MutableStateFlow(false)
    val postType: StateFlow<Boolean> = _postType

    fun postType(postInput: Boolean) {
        _postType.value = !postInput
    }

    private val _createPostStateFlow =
        MutableStateFlow<CreatePostUiState>(CreatePostUiState.Empty)
    val createPostStateFlow: StateFlow<CreatePostUiState> = _createPostStateFlow

    fun createPost(groupId: String, post: PostRequestData) {
        viewModelScope.launch {
            createPostUseCase.invoke(object : PostService.PostCallback<Unit> {
                override fun onSuccess(generic: Unit) {
                    viewModelScope.launch {
                        _createPostStateFlow.emit(CreatePostUiState.Success)
                    }
                }

                override fun onError(error: String) {
                    viewModelScope.launch {
                        _createPostStateFlow.emit(CreatePostUiState.Error(error))
                    }
                }

            }, groupId = groupId, post = post)
        }
    }

    fun resetState(){
        viewModelScope.launch {
            _createPostStateFlow.emit(CreatePostUiState.Empty)
        }
    }
}
package com.mobile.tuesplace.ui.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.tuesplace.PostRequestData
import com.mobile.tuesplace.services.PostService
import com.mobile.tuesplace.ui.states.DeletePostUiState
import com.mobile.tuesplace.ui.states.EditPostUiState
import com.mobile.tuesplace.usecase.DeletePostUseCase
import com.mobile.tuesplace.usecase.EditPostUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EditPostViewModel(
    private val editPostUseCase: EditPostUseCase,
    private val deletePostUseCase: DeletePostUseCase,
) : ViewModel() {

    private val _postTitle =
        MutableStateFlow("")
    val postTitle: StateFlow<String> = _postTitle

    fun postTitle(titleInput: String) {
        _postTitle.value = titleInput
    }

    private val _postDescription =
        MutableStateFlow("")
    val postDescription: StateFlow<String> = _postDescription

    fun postDescription(descriptionInput: String) {
        _postDescription.value = descriptionInput
    }

    private val _editPostUiState = MutableStateFlow<EditPostUiState>(EditPostUiState.Empty)
    val editPostUiState: StateFlow<EditPostUiState> = _editPostUiState

    fun editPost(post: PostRequestData, groupId: String, postId: String) {
        viewModelScope.launch {
            editPostUseCase.invoke(object : PostService.PostCallback<Unit> {
                override fun onSuccess(generic: Unit) {
                    viewModelScope.launch {
                        _editPostUiState.emit(EditPostUiState.Success)
                    }
                }

                override fun onError(error: String) {
                    viewModelScope.launch {
                        _editPostUiState.emit(EditPostUiState.Error(error))
                    }
                }

            }, groupId = groupId, postId = postId, post = post)
        }
    }

    fun resetEditPostUiState(){
        viewModelScope.launch {
            _editPostUiState.emit(EditPostUiState.Empty)
        }
    }

    fun editPostBody(body: PostRequestData): PostRequestData{
        if (body.body?.isEmpty() == true) {
            body.body = null
        } else if (body.title?.isEmpty() == true) {
            body.title = null
        }
        return  body
    }

    private val _deletePostUiState = MutableStateFlow<DeletePostUiState>(DeletePostUiState.Empty)
    val deletePostUiState: StateFlow<DeletePostUiState> = _deletePostUiState

    fun deletePost(groupId: String, postId: String) {
        viewModelScope.launch {
            deletePostUseCase.invoke(
                object : PostService.PostCallback<Unit> {
                    override fun onSuccess(generic: Unit) {
                        viewModelScope.launch {
                            _deletePostUiState.emit(DeletePostUiState.Success)
                        }
                    }

                    override fun onError(error: String) {
                        viewModelScope.launch {
                            _deletePostUiState.emit(DeletePostUiState.Error(error))
                        }
                    }

                }, postId = postId, groupId = groupId
            )
        }
    }

    fun resetDeletePostUiState(){
        viewModelScope.launch {
            _deletePostUiState.emit(DeletePostUiState.Empty)
        }
    }
}
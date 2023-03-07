package com.mobile.tuesplace.ui.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.tuesplace.data.CommentData
import com.mobile.tuesplace.data.CommentRequestData
import com.mobile.tuesplace.data.PostResponseData
import com.mobile.tuesplace.services.CommentService
import com.mobile.tuesplace.services.PostService
import com.mobile.tuesplace.ui.states.CreateCommentUiState
import com.mobile.tuesplace.ui.states.GetPostCommentsUiState
import com.mobile.tuesplace.ui.states.GetPostUiState
import com.mobile.tuesplace.usecase.CreateCommentUseCase
import com.mobile.tuesplace.usecase.GetPostCommentsUseCase
import com.mobile.tuesplace.usecase.GetPostUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PostViewModel(
    private val createCommentUseCase: CreateCommentUseCase,
    private val getPostUseCase: GetPostUseCase,
    private val getPostCommentsUseCase: GetPostCommentsUseCase,
) : ViewModel() {

    private val _enabled = MutableStateFlow(false)
    val enabled: StateFlow<Boolean> = _enabled

    fun enabled(enabledValue: Boolean) {
        _enabled.value = enabledValue
    }

    private val _comment =
        MutableStateFlow("")
    val comment: StateFlow<String> = _comment

    fun comment(commentInput: String) {
        _comment.value = commentInput
    }

    private val _commentMenuIndex =
        MutableStateFlow(-1)
    val commentMenuIndex: StateFlow<Int> = _commentMenuIndex

    private val _commentList =
        MutableStateFlow<List<CommentData>>(arrayListOf())
    val commentList: StateFlow<List<CommentData>> = _commentList

    fun setCommentMenuIndex(index: Int){
        _commentMenuIndex.value = index
    }

    private val _createCommentStateFlow =
        MutableStateFlow<CreateCommentUiState>(CreateCommentUiState.Empty)
    val createCommentStateFlow: StateFlow<CreateCommentUiState> = _createCommentStateFlow

    fun createComment(groupId: String, postId: String, comment: CommentRequestData) {
        viewModelScope.launch {
            createCommentUseCase.invoke(
                object : CommentService.CommentCallback<CommentRequestData> {
                    override fun onSuccess(generic: CommentRequestData) {
                        viewModelScope.launch {
                            _createCommentStateFlow.emit(CreateCommentUiState.Success)
                        }
                    }

                    override fun onError(error: String) {
                        viewModelScope.launch {
                            _createCommentStateFlow.emit(CreateCommentUiState.Error(error))
                        }
                    }

                }, groupId, postId, comment
            )
        }
    }

    private val _getPostStateFlow =
        MutableStateFlow<GetPostUiState>(GetPostUiState.Empty)
    val getPostStateFlow: StateFlow<GetPostUiState> = _getPostStateFlow

    fun getPost(groupId: String, postId: String) {
        viewModelScope.launch {
            getPostUseCase.invoke(object : PostService.PostCallback<PostResponseData> {
                override fun onSuccess(generic: PostResponseData) {
                    viewModelScope.launch {
                        _getPostStateFlow.emit(GetPostUiState.Success(generic))
                    }
                }

                override fun onError(error: String) {
                    viewModelScope.launch {
                        _getPostStateFlow.emit(GetPostUiState.Error(error))
                    }
                }
            }, groupId, postId)
        }
    }

    private val _getPostCommentsStateFlow =
        MutableStateFlow<GetPostCommentsUiState>(GetPostCommentsUiState.Empty)
    val getPostCommentsStateFlow: StateFlow<GetPostCommentsUiState> = _getPostCommentsStateFlow

    fun getPostComments(groupId: String, postId: String) {
        viewModelScope.launch {
            getPostCommentsUseCase.invoke(object :
                CommentService.CommentCallback<List<CommentData>> {
                override fun onSuccess(generic: List<CommentData>) {
                    viewModelScope.launch {
                        _commentList.value = generic
                        _getPostCommentsStateFlow.emit(GetPostCommentsUiState.Success(generic))
                    }
                }

                override fun onError(error: String) {
                    viewModelScope.launch {
                        _getPostCommentsStateFlow.emit(GetPostCommentsUiState.Error(error))
                    }
                }

            }, groupId, postId)
        }
    }

    fun editComment(commentInput: String, index: Int) {
        _commentList.value.get(index = index).body = commentInput
    }
}
package com.mobile.tuesplace.ui.post

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.tuesplace.data.*
import com.mobile.tuesplace.services.CommentService
import com.mobile.tuesplace.services.PostService
import com.mobile.tuesplace.services.ProfileService
import com.mobile.tuesplace.services.SubmissionsService
import com.mobile.tuesplace.ui.states.*
import com.mobile.tuesplace.usecase.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class PostViewModel(
    private val createCommentUseCase: CreateCommentUseCase,
    private val getPostUseCase: GetPostUseCase,
    private val getPostCommentsUseCase: GetPostCommentsUseCase,
    private val editCommentsUseCase: EditCommentUseCase,
    private val deleteCommentsUseCase: DeleteCommentUseCase,
    private val createSubmissionUseCase: CreateSubmissionUseCase,
    private val profileUseCase: GetProfileUseCase
) : ViewModel() {

    private val _enabled = MutableStateFlow(-1)
    val enabled: StateFlow<Int> = _enabled

    fun enabled(enabledValue: Int) {
        _enabled.value = enabledValue
    }

    private val _comment =
        MutableStateFlow("")
    val comment: StateFlow<String> = _comment

    fun comment(commentInput: String) {
        _comment.value = commentInput
    }

    var postData: PostResponseData? = null

    private val _commentMenuIndex =
        MutableStateFlow(-1)
    val commentMenuIndex: StateFlow<Int> = _commentMenuIndex

    private val _commentList =
        MutableStateFlow<SnapshotStateList<CommentData>>(mutableStateListOf())
    val commentList: StateFlow<SnapshotStateList<CommentData>> = _commentList.asStateFlow()

    fun setCommentMenuIndex(index: Int) {
        _commentMenuIndex.value = index
    }

    private val _createCommentStateFlow =
        MutableStateFlow<CreateCommentUiState>(CreateCommentUiState.Empty)
    val createCommentStateFlow: StateFlow<CreateCommentUiState> = _createCommentStateFlow

    fun createComment(groupId: String, postId: String, comment: CommentRequestData) {
        viewModelScope.launch {
            createCommentUseCase.invoke(
                object : CommentService.CommentCallback<Unit> {
                    override fun onSuccess(generic: Unit) {
                        viewModelScope.launch {
                            getPostComments(groupId, postId)
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

    fun resetCreateComment() {
        viewModelScope.launch {
            _createCommentStateFlow.emit(CreateCommentUiState.Empty)
        }
    }

    fun resetEditComment() {
        viewModelScope.launch {
            _editPostCommentsStateFlow.emit(EditCommentsUiState.Empty)
        }
    }

    fun resetDeleteComment() {
        viewModelScope.launch {
            _deletePostCommentsStateFlow.emit(DeleteCommentUiState.Empty)
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
                        postData = generic
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
                CommentService.CommentCallback<SnapshotStateList<CommentData>> {
                override fun onSuccess(generic: SnapshotStateList<CommentData>) {
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

    fun setPostStateAsLoaded() {
        viewModelScope.launch {
            postData?.let { GetPostUiState.Loaded(it) }?.let { _getPostStateFlow.emit(it) }
        }
    }

    fun editComment(commentInput: String, index: Int) {
        _commentList.value.get(index = index).body = commentInput
        val newItems = mutableStateListOf<CommentData>()
        newItems.addAll(_commentList.value)
        _commentList.value = newItems
    }

    private val _editPostCommentsStateFlow =
        MutableStateFlow<EditCommentsUiState>(EditCommentsUiState.Empty)
    val editPostCommentsStateFlow: StateFlow<EditCommentsUiState> = _editPostCommentsStateFlow

    fun editComment(
        commentRequestData: CommentRequestData,
        groupId: String,
        postId: String,
        commentId: String,
    ) {
        viewModelScope.launch {
            editCommentsUseCase.invoke(
                object : CommentService.CommentCallback<Unit> {
                    override fun onSuccess(generic: Unit) {
                        viewModelScope.launch {
                            _editPostCommentsStateFlow.emit(EditCommentsUiState.Success)
                        }
                    }

                    override fun onError(error: String) {
                        viewModelScope.launch {
                            _editPostCommentsStateFlow.emit(EditCommentsUiState.Error(error))
                        }
                    }

                },
                groupId = groupId,
                postId = postId,
                commentId = commentId,
                comment = commentRequestData
            )
        }
    }

    private val _deletePostCommentsStateFlow =
        MutableStateFlow<DeleteCommentUiState>(DeleteCommentUiState.Empty)
    val deletePostCommentsStateFlow: StateFlow<DeleteCommentUiState> = _deletePostCommentsStateFlow

    fun deleteComment(commentId: String, groupId: String, postId: String) {
        viewModelScope.launch {
            deleteCommentsUseCase.invoke(
                object : CommentService.CommentCallback<Unit> {
                    override fun onSuccess(generic: Unit) {
                        viewModelScope.launch {
                            getPostComments(groupId, postId)
                            _deletePostCommentsStateFlow.emit(DeleteCommentUiState.Success)
                        }
                    }

                    override fun onError(error: String) {
                        viewModelScope.launch {
                            _deletePostCommentsStateFlow.emit(DeleteCommentUiState.Error(error))
                        }
                    }
                }, groupId = groupId, postId = postId, commentId = commentId
            )
        }
    }

    private val _dialogVisibility = MutableStateFlow(false)
    val dialogVisibility: StateFlow<Boolean> = _dialogVisibility

    fun dialogVisibility(dialogVisibility: Boolean) {
        _dialogVisibility.value = dialogVisibility
    }

    private val _createSubmissionStateFlow =
        MutableStateFlow<CreateSubmissionUiState>(CreateSubmissionUiState.Empty)
    val createSubmissionStateFlow: StateFlow<CreateSubmissionUiState> = _createSubmissionStateFlow

    fun createSubmission(assets: MultipartBody.Part, groupId: String, postId: String) {
        viewModelScope.launch {
            createSubmissionUseCase.invoke(groupId = groupId,
                postId = postId,
                assets = assets,
                submissionCallback = object :
                    SubmissionsService.SubmissionCallback<Unit> {
                    override fun onSuccess(data: Unit) {
                        viewModelScope.launch {
                            _createSubmissionStateFlow.emit(CreateSubmissionUiState.Success)
                        }
                    }

                    override fun onError(error: String) {
                        viewModelScope.launch {
                            _createSubmissionStateFlow.emit(CreateSubmissionUiState.Error(error))
                        }
                    }

                })
        }
    }

    fun resetSubmissionState(){
        viewModelScope.launch {
            _createSubmissionStateFlow.emit(CreateSubmissionUiState.Empty)
        }
    }

    var profile: ProfileResponseData? = null

    private val _getProfileStateFlow = MutableStateFlow<GetProfileUiState>(GetProfileUiState.Empty)
    val getProfileStateFlow: StateFlow<GetProfileUiState> = _getProfileStateFlow

    fun getProfile() {
        viewModelScope.launch {
            profileUseCase.invoke(object : ProfileService.GetProfileCallback<ProfileResponseData> {
                override fun onSuccess(profileGeneric: ProfileResponseData) {
                    viewModelScope.launch {
                        profile = profileGeneric
                        _getProfileStateFlow.emit(GetProfileUiState.Success(profileGeneric))
                    }
                }

                override fun onError(error: String) {
                    viewModelScope.launch {
                        _getProfileStateFlow.emit(GetProfileUiState.Error(error))
                    }
                }

            })
        }
    }
}
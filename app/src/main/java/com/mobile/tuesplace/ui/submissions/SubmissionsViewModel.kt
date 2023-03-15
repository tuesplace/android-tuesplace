package com.mobile.tuesplace.ui.submissions

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.tuesplace.data.MarkRequestData
import com.mobile.tuesplace.data.PostResponseData
import com.mobile.tuesplace.data.SubmissionData
import com.mobile.tuesplace.services.MarkService
import com.mobile.tuesplace.services.PostService
import com.mobile.tuesplace.services.SubmissionsService
import com.mobile.tuesplace.ui.states.CreateSubmissionMarkUiState
import com.mobile.tuesplace.ui.states.GetPostSubmissionsUiState
import com.mobile.tuesplace.ui.states.GetPostUiState
import com.mobile.tuesplace.usecase.CreateSubmissionMarkUseCase
import com.mobile.tuesplace.usecase.GetPostSubmissionsUseCase
import com.mobile.tuesplace.usecase.GetPostUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SubmissionsViewModel(
    private val getPostSubmissionsUseCase: GetPostSubmissionsUseCase,
    private val getPostUseCase: GetPostUseCase,
    private val createSubmissionMarkUseCase: CreateSubmissionMarkUseCase
): ViewModel() {

    private val _getPostSubmissionsUiState =
        MutableStateFlow<GetPostSubmissionsUiState>(GetPostSubmissionsUiState.Empty)
    val getPostSubmissionsUiState: StateFlow<GetPostSubmissionsUiState> = _getPostSubmissionsUiState

    fun getPostSubmissions(groupId: String, postId: String){
        viewModelScope.launch {
            getPostSubmissionsUseCase.invoke(
                groupId = groupId,
                postId = postId,
                submissionCallback = object : SubmissionsService.SubmissionCallback<SnapshotStateList<SubmissionData>> {
                    override fun onSuccess(data: SnapshotStateList<SubmissionData>) {
                        viewModelScope.launch {
                            _submissionsList.value = data
                            _getPostSubmissionsUiState.emit(GetPostSubmissionsUiState.Success(data))
                        }
                    }

                    override fun onError(error: String) {
                        viewModelScope.launch {
                            _getPostSubmissionsUiState.emit(GetPostSubmissionsUiState.Error(error))
                        }
                    }

                }
            )
        }
    }

    var postData: PostResponseData? = null

    private val _getPostUiState =
        MutableStateFlow<GetPostUiState>(GetPostUiState.Empty)
    val getPostUiState: StateFlow<GetPostUiState> = _getPostUiState

    fun getPost(groupId: String, postId: String) {
        viewModelScope.launch {
            getPostUseCase.invoke(object : PostService.PostCallback<PostResponseData> {
                override fun onSuccess(generic: PostResponseData) {
                    viewModelScope.launch {
                        postData = generic
                        _getPostUiState.emit(GetPostUiState.Success(generic))
                    }
                }

                override fun onError(error: String) {
                    viewModelScope.launch {
                        _getPostUiState.emit(GetPostUiState.Error(error))
                    }
                }
            }, groupId, postId)
        }
    }

    fun setPostStateAsLoaded(){
        viewModelScope.launch {
            postData?.let { GetPostUiState.Loaded(it) }?.let { _getPostUiState.emit(it) }
        }
    }

    private val _submissionIndex =
        MutableStateFlow(-1)
    val submissionIndex: StateFlow<Int> = _submissionIndex

    private val _submissionsList =
        MutableStateFlow<SnapshotStateList<SubmissionData>>(mutableStateListOf())
    val submissionsList: StateFlow<SnapshotStateList<SubmissionData>> = _submissionsList.asStateFlow()

    fun setSubmissionIndex(index: Int){
        _submissionIndex.value = index
    }

    private val _dialogVisibility = MutableStateFlow(false)
    val dialogVisibility: StateFlow<Boolean> = _dialogVisibility

    fun dialogVisibility(dialogVisibility: Boolean) {
        _dialogVisibility.value = dialogVisibility
    }
    private val _markValue =
        MutableStateFlow("")
    val markValue: StateFlow<String> = _markValue.asStateFlow()

    fun markValue(markInput: String) {
        _markValue.value = markInput
    }

    private val _createSubmissionMarkUiState =
        MutableStateFlow<CreateSubmissionMarkUiState>(CreateSubmissionMarkUiState.Empty)
    val createSubmissionMarkUiState: StateFlow<CreateSubmissionMarkUiState> = _createSubmissionMarkUiState

    fun createSubmissionMark(groupId: String, postId: String, submissionId: String, mark: MarkRequestData){
        viewModelScope.launch {
            createSubmissionMarkUseCase.invoke(
                object : MarkService.MarkCallback<Unit> {
                    override fun onSuccess(generic: Unit) {
                        viewModelScope.launch {
                            _createSubmissionMarkUiState.emit(CreateSubmissionMarkUiState.Success)
                        }
                    }

                    override fun onError(error: String) {
                        viewModelScope.launch {
                            _createSubmissionMarkUiState.emit(CreateSubmissionMarkUiState.Error(error))
                        }
                    }

                }, groupId, postId, submissionId, mark
            )
        }
    }
}
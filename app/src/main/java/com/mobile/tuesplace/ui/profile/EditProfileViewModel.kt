package com.mobile.tuesplace.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.tuesplace.data.EditProfileData
import com.mobile.tuesplace.data.ProfileResponseData
import com.mobile.tuesplace.services.ProfileService
import com.mobile.tuesplace.ui.states.*
import com.mobile.tuesplace.usecase.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class EditProfileViewModel(
    private val profileUseCase: GetProfileByIdUseCase,
    private val myProfileUseCase: GetProfileUseCase,
    private val editProfileUseCase: EditProfileUseCase,
    private val putProfileAssetsUseCase: PutProfileAssetsUseCase,
    private val putMyProfileAssetsUseCase: PutMyProfileAssetsUseCase,
    private val editMyProfileUseCase: EditMyProfileUseCase,
    private val deleteProfileUseCase: DeleteProfileUseCase
    ): ViewModel() {
    private val _getProfileStateFlow = MutableStateFlow<GetProfileByIdUiState>(GetProfileByIdUiState.Empty)
    val getProfileStateFlow: StateFlow<GetProfileByIdUiState> = _getProfileStateFlow

    fun getProfile(profileId: String) {
        viewModelScope.launch {
            profileUseCase.invoke(object : ProfileService.GetProfileCallback<ProfileResponseData> {
                override fun onSuccess(profileGeneric: ProfileResponseData) {
                    viewModelScope.launch {
                        _getProfileStateFlow.emit(GetProfileByIdUiState.Success(profileGeneric))
                    }
                }

                override fun onError(error: String) {
                    viewModelScope.launch {
                        _getProfileStateFlow.emit(GetProfileByIdUiState.Error(error))
                    }
                }

            }, profileId = profileId)
        }
    }

    private val _getMyProfileStateFlow = MutableStateFlow<GetProfileUiState>(GetProfileUiState.Empty)
    val getMyProfileStateFlow: StateFlow<GetProfileUiState> = _getMyProfileStateFlow

    var myProfile: ProfileResponseData? = null

    fun getMyProfile(){
        viewModelScope.launch {
            myProfileUseCase.invoke(
                object : ProfileService.GetProfileCallback<ProfileResponseData> {
                    override fun onSuccess(profileGeneric: ProfileResponseData) {
                        viewModelScope.launch {
                            myProfile = profileGeneric
                            _getMyProfileStateFlow.emit(GetProfileUiState.Success(profileGeneric))
                        }
                    }

                    override fun onError(error: String) {
                        viewModelScope.launch {
                            _getMyProfileStateFlow.emit(GetProfileUiState.Error(error))
                        }
                    }

                }
            )
        }
    }
    private val _editProfileStateFlow = MutableStateFlow<EditProfileUiState>(EditProfileUiState.Empty)
    val editProfileStateFlow: StateFlow<EditProfileUiState> = _editProfileStateFlow

    fun editProfile(editProfileData: EditProfileData, profileId: String){
        viewModelScope.launch {
            editProfileUseCase.invoke(object : ProfileService.GetProfileCallback<Unit> {
                override fun onSuccess(profileGeneric: Unit) {
                    viewModelScope.launch {
                        _editProfileStateFlow.emit(EditProfileUiState.Success)
                    }
                }

                override fun onError(error: String) {
                    viewModelScope.launch {
                        _editProfileStateFlow.emit(EditProfileUiState.Error(error))
                    }
                }

            }, editProfileData = editProfileData, profileId = profileId)
        }
    }

    fun editMyProfile(editProfileData: EditProfileData){
        viewModelScope.launch {
            editMyProfileUseCase.invoke(object : ProfileService.GetProfileCallback<Unit> {
                override fun onSuccess(profileGeneric: Unit) {
                    viewModelScope.launch {
                        _editProfileStateFlow.emit(EditProfileUiState.Success)
                    }
                }

                override fun onError(error: String) {
                    viewModelScope.launch {
                        _editProfileStateFlow.emit(EditProfileUiState.Error(error))
                    }
                }

            }, editProfileData = editProfileData)
        }
    }

    private val _changeName =
        MutableStateFlow("")
    val changeName: StateFlow<String> = _changeName

    fun changeName(nameInput: String) {
        _changeName.value = nameInput
    }

    private val _changeEmail =
        MutableStateFlow("")
    val changeEmail: StateFlow<String> = _changeEmail

    fun changeEmail(emailInput: String) {
        _changeEmail.value = emailInput
    }

    private val _changeClass =
        MutableStateFlow("")
    val changeClass: StateFlow<String> = _changeClass

    fun changeClass(classInput: String) {
        _changeClass.value = classInput
    }

    private val _imageUpload = MutableStateFlow<MultipartBody.Part?>(null)
    val imageUpload: MutableStateFlow<MultipartBody.Part?> = _imageUpload

    fun imageUpload(image: MultipartBody.Part) {
        _imageUpload.value = image
    }

    private val _putProfileAssetsStateFlow = MutableStateFlow<PutMyProfileAssetsUiState>(PutMyProfileAssetsUiState.Empty)
    val putProfileAssetsStateFlow: StateFlow<PutMyProfileAssetsUiState> = _putProfileAssetsStateFlow

    fun putProfileAssets(profilePic: MultipartBody.Part, profileId: String) {
        viewModelScope.launch {
            putProfileAssetsUseCase.invoke(
                object : ProfileService.GetProfileCallback<Unit> {
                    override fun onSuccess(profileGeneric: Unit) {
                        viewModelScope.launch {
                            _putProfileAssetsStateFlow.emit(PutMyProfileAssetsUiState.Success)
                        }
                    }

                    override fun onError(error: String) {
                        viewModelScope.launch {
                            _putProfileAssetsStateFlow.emit(PutMyProfileAssetsUiState.Error(error))
                        }
                    }

                }, profilePic, profileId = profileId
            )
        }
    }

    fun putMyProfileAssets(profilePic: MultipartBody.Part){
        viewModelScope.launch {
            putMyProfileAssetsUseCase.invoke(
                object : ProfileService.GetProfileCallback<Unit> {
                    override fun onSuccess(profileGeneric: Unit) {
                        viewModelScope.launch {
                            _putProfileAssetsStateFlow.emit(PutMyProfileAssetsUiState.Success)
                        }
                    }

                    override fun onError(error: String) {
                        viewModelScope.launch {
                            _putProfileAssetsStateFlow.emit(PutMyProfileAssetsUiState.Error(error))
                        }
                    }

                }, profilePic
            )
        }
    }

    fun resetEditStates(){
        viewModelScope.launch {
            _putProfileAssetsStateFlow.emit(PutMyProfileAssetsUiState.Empty)
            _getMyProfileStateFlow.emit(GetProfileUiState.Empty)
        }
    }

    private val _dialogVisibility = MutableStateFlow(false)
    val dialogVisibility: StateFlow<Boolean> = _dialogVisibility

    fun dialogVisibility(dialogVisibility: Boolean) {
        _dialogVisibility.value = dialogVisibility
    }

    private val _deleteProfileUiState = MutableStateFlow<DeleteProfileUiState>(DeleteProfileUiState.Empty)
    val deleteProfileUiState: StateFlow<DeleteProfileUiState> = _deleteProfileUiState

    fun deleteProfile(profileId: String) {
        viewModelScope.launch {
            deleteProfileUseCase.invoke(
                object : ProfileService.GetProfileCallback<Unit> {
                    override fun onSuccess(profileGeneric: Unit) {
                        viewModelScope.launch {
                            _deleteProfileUiState.emit(DeleteProfileUiState.Success)
                        }
                    }

                    override fun onError(error: String) {
                        viewModelScope.launch {
                            _deleteProfileUiState.emit(DeleteProfileUiState.Error(error))
                        }
                    }

                }, profileId
            )
        }
    }

    fun resetDeleteState() {
        viewModelScope.launch {
            _deleteProfileUiState.emit(DeleteProfileUiState.Empty)
        }
    }

}
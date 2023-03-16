package com.mobile.tuesplace.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.tuesplace.data.ProfileData
import com.mobile.tuesplace.services.ProfileService
import com.mobile.tuesplace.ui.states.CreateProfileUiState
import com.mobile.tuesplace.usecase.CreateProfileUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class CreateProfileViewModel(private val createProfileUseCase: CreateProfileUseCase): ViewModel() {
    private val _name =
        MutableStateFlow("")
    val name: StateFlow<String> = _name

    fun name(nameInput: String) {
        _name.value = nameInput
    }

    private val _email =
        MutableStateFlow("")
    val email: StateFlow<String> = _email

    fun email(emailInput: String) {
        _email.value = emailInput
    }

    private val _role =
        MutableStateFlow("")
    val role: StateFlow<String> = _role

    fun role(roleInput: String) {
        _role.value = roleInput
    }

    private val _classString =
        MutableStateFlow("")
    val classString: StateFlow<String> = _classString

    fun changeClass(classInput: String) {
        _classString.value = classInput
    }

    private val _imageUpload = MutableStateFlow<MultipartBody.Part?>(null)
    val imageUpload: MutableStateFlow<MultipartBody.Part?> = _imageUpload

    fun imageUpload(image: MultipartBody.Part) {
        _imageUpload.value = image
    }

    private val _createProfileStateFlow = MutableStateFlow<CreateProfileUiState>(CreateProfileUiState.Empty)
    val createProfileStateFlow: StateFlow<CreateProfileUiState> = _createProfileStateFlow

    fun createProfile(profileData: ProfileData){
        viewModelScope.launch {
            createProfileUseCase.invoke(
                object : ProfileService.GetProfileCallback<Unit> {
                    override fun onSuccess(profileGeneric: Unit) {
                        viewModelScope.launch {
                            _createProfileStateFlow.emit(CreateProfileUiState.Success)
                        }
                    }

                    override fun onError(error: String) {
                        viewModelScope.launch { _createProfileStateFlow.emit(CreateProfileUiState.Error(error)) }
                    }

                }, profileData
            )
        }
    }

    fun resetState() {
        viewModelScope.launch {
            _createProfileStateFlow.emit(CreateProfileUiState.Empty)
        }
    }

}
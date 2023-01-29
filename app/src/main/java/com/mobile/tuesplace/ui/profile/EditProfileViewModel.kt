package com.mobile.tuesplace.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.tuesplace.data.EditProfileData
import com.mobile.tuesplace.data.ProfileData
import com.mobile.tuesplace.services.ProfileService
import com.mobile.tuesplace.ui.states.EditProfileUiState
import com.mobile.tuesplace.ui.states.GetProfileUiState
import com.mobile.tuesplace.usecase.EditProfileUseCase
import com.mobile.tuesplace.usecase.GetProfileUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EditProfileViewModel(private val profileUseCase: GetProfileUseCase, private val editProfileUseCase: EditProfileUseCase): ViewModel() {
    private val _getProfileStateFlow = MutableStateFlow<GetProfileUiState>(GetProfileUiState.Empty)
    val getProfileStateFlow: StateFlow<GetProfileUiState> = _getProfileStateFlow

    fun getProfile() {
        viewModelScope.launch {
            profileUseCase.invoke(object : ProfileService.GetProfileCallback<ProfileData> {
                override fun onSuccess(profileGeneric: ProfileData) {
                    viewModelScope.launch {
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

}
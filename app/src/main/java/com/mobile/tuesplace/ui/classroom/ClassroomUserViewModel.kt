package com.mobile.tuesplace.ui.classroom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.tuesplace.data.ProfileData
import com.mobile.tuesplace.data.ProfileResponseData
import com.mobile.tuesplace.services.ProfileService
import com.mobile.tuesplace.ui.states.GetProfileByIdUiState
import com.mobile.tuesplace.usecase.GetProfileByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ClassroomUserViewModel(private val profileByIdUseCase: GetProfileByIdUseCase): ViewModel() {

    private val _profileId =
        MutableStateFlow("")
    val profileId: StateFlow<String> = _profileId

    private val _getProfileByIdStateFlow = MutableStateFlow<GetProfileByIdUiState>(GetProfileByIdUiState.Empty)
    val getProfileByIdStateFlow: StateFlow<GetProfileByIdUiState> = _getProfileByIdStateFlow

    fun getProfileById(profileId: String) {
        viewModelScope.launch {
            profileByIdUseCase.invoke(object : ProfileService.GetProfileCallback<ProfileResponseData> {
                override fun onSuccess(profileGeneric: ProfileResponseData) {
                    viewModelScope.launch {
                        _getProfileByIdStateFlow.emit(GetProfileByIdUiState.Success(profileGeneric))
                    }
                }

                override fun onError(error: String) {
                    viewModelScope.launch {
                        _getProfileByIdStateFlow.emit(GetProfileByIdUiState.Error(error))
                    }
                }

            }, profileId)
        }
    }
}
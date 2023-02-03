package com.mobile.tuesplace.ui.classroom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.tuesplace.data.ProfileData
import com.mobile.tuesplace.services.ProfileService
import com.mobile.tuesplace.ui.states.GetGroupsUiState
import com.mobile.tuesplace.ui.states.GetProfileByIdUiState
import com.mobile.tuesplace.usecase.GetProfileByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.Interceptor.Companion.invoke

class ClassroomStudentViewModel(private val profileByIdUseCase: GetProfileByIdUseCase): ViewModel() {

    private val _getProfileByIdStateFlow = MutableStateFlow<GetProfileByIdUiState>(GetProfileByIdUiState.Empty)
    val getProfileByIdStateFlow: StateFlow<GetProfileByIdUiState> = _getProfileByIdStateFlow

    fun getProfileById(profileId: String) {
        viewModelScope.launch {
            profileByIdUseCase.invoke(object : ProfileService.GetProfileCallback<ProfileData> {
                override fun onSuccess(profileGeneric: ProfileData) {
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
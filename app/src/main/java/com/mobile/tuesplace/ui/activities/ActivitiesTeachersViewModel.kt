package com.mobile.tuesplace.ui.activities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.tuesplace.data.ProfileResponseData
import com.mobile.tuesplace.services.ProfileService
import com.mobile.tuesplace.ui.states.GetAllProfilesUiState
import com.mobile.tuesplace.usecase.GetAllProfilesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ActivitiesTeachersViewModel(private val allProfilesUseCase: GetAllProfilesUseCase): ViewModel() {
    private val _getAllProfilesStateFlow = MutableStateFlow<GetAllProfilesUiState>(
        GetAllProfilesUiState.Empty)
    val getAllProfilesStateFlow: StateFlow<GetAllProfilesUiState> = _getAllProfilesStateFlow

    fun getAllProfiles(){
        viewModelScope.launch {
            allProfilesUseCase.invoke(object : ProfileService.GetProfileCallback<List<ProfileResponseData>>{
                override fun onSuccess(profileGeneric: List<ProfileResponseData>) {
                    viewModelScope.launch {
                        _getAllProfilesStateFlow.emit(GetAllProfilesUiState.Success(profileGeneric))
                    }
                }

                override fun onError(error: String) {
                    viewModelScope.launch {
                        _getAllProfilesStateFlow.emit(GetAllProfilesUiState.Error(error))
                    }
                }

            })
        }
    }
}
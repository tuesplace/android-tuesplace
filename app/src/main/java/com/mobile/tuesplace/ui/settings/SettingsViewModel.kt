package com.mobile.tuesplace.ui.settings

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.tuesplace.EMPTY_STRING
import com.mobile.tuesplace.data.ProfileResponseData
import com.mobile.tuesplace.dataStore
import com.mobile.tuesplace.services.ProfileService
import com.mobile.tuesplace.session.SessionManager
import com.mobile.tuesplace.ui.states.GetProfileUiState
import com.mobile.tuesplace.usecase.CreateSubmissionUseCase
import com.mobile.tuesplace.usecase.GetProfileUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val context: Context,
    private val profileUseCase: GetProfileUseCase,
    ) : ViewModel() {

    private val sessionManager = SessionManager.getInstance(dataStore = context.dataStore)
    fun deleteTokensData() {
        viewModelScope.launch {
            deleteSavedTokens()
            sessionManager.setTokens()
        }
    }

    private fun deleteSavedTokens() {
        viewModelScope
            .launch {
                sessionManager.setAuthEntities(EMPTY_STRING, EMPTY_STRING)
            }
    }

    private val _getProfileStateFlow = MutableStateFlow<GetProfileUiState>(GetProfileUiState.Empty)
    val getProfileStateFlow: StateFlow<GetProfileUiState> = _getProfileStateFlow

    fun getProfile() {
        viewModelScope.launch {
            profileUseCase.invoke(object : ProfileService.GetProfileCallback<ProfileResponseData> {
                override fun onSuccess(profileGeneric: ProfileResponseData) {
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
}
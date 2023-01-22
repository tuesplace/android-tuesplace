package com.mobile.tuesplace.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.tuesplace.data.SignInData
import com.mobile.tuesplace.services.AuthService
import com.mobile.tuesplace.ui.states.SignInUiState
import com.mobile.tuesplace.usecase.SignInUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class LoginViewModel(
    private val signInUseCase: SignInUseCase,
) : ViewModel() {

    private val _email =
        MutableStateFlow("")
    val email: StateFlow<String> = _email

    fun email(emailInput: String) {
        _email.value = emailInput
    }

    private val _password =
        MutableStateFlow("")
    val password: StateFlow<String> = _password

    fun password(passwordInput: String) {
        _password.value = passwordInput
    }

    private val _passwordVisibility = MutableStateFlow(false)
    val passwordVisibility: StateFlow<Boolean> = _passwordVisibility

    fun passwordVisibility(passwordVisibility: Boolean) {
        _passwordVisibility.value = !passwordVisibility
    }

    private val _uiStateFlow = MutableStateFlow<SignInUiState>(SignInUiState.Empty)
    val uiStateFlow: StateFlow<SignInUiState> = _uiStateFlow

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            signInUseCase.invoke(email, password, object : AuthService.AuthCallback {
                override fun onSuccess(signInResponse: SignInData) {
                    viewModelScope.launch {
                        _uiStateFlow.emit(SignInUiState.Success)
                    }
                }

                override fun onError(error: String) {
                    viewModelScope.launch {
                        _uiStateFlow.emit(SignInUiState.Error(error))
                    }
                }
            })
        }
    }

    fun resetState() {
        viewModelScope.launch {
            _uiStateFlow.emit(SignInUiState.Empty)
        }
    }

}
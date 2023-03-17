package com.mobile.tuesplace.ui.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.tuesplace.data.ProfileData
import com.mobile.tuesplace.data.ProfileResponseData
import com.mobile.tuesplace.data.SignInData
import com.mobile.tuesplace.dataStore
import com.mobile.tuesplace.services.AuthService
import com.mobile.tuesplace.services.ProfileService
import com.mobile.tuesplace.session.SessionManager
import com.mobile.tuesplace.ui.states.GetProfileUiState
import com.mobile.tuesplace.ui.states.SignInUiState
import com.mobile.tuesplace.usecase.GetProfileUseCase
import com.mobile.tuesplace.usecase.SignInUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class LoginViewModel(
    private val signInUseCase: SignInUseCase,
    private val profileUseCase: GetProfileUseCase,
    private val context: Context
) : ViewModel() {

    private val sessionManager = SessionManager.getInstance(dataStore = context.dataStore)

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

    private val _uiStateFlow = MutableStateFlow<SignInUiState>(SignInUiState.Empty)
    val uiStateFlow: StateFlow<SignInUiState> = _uiStateFlow

    fun passwordVisibility(passwordVisibility: Boolean) {
        _passwordVisibility.value = !passwordVisibility
    }

    init {
        checkIfUserIsLoggedIn()
    }

    private fun checkIfUserIsLoggedIn() {
        viewModelScope.launch {
            val token = sessionManager.fetchAuthToken()
            Log.d("savedToken", token)
            if (token.isNotEmpty()) {
                sessionManager.setTokens()
                _uiStateFlow.emit(SignInUiState.Success)
                getProfile()
            }
        }
    }

    private val _isCorrectPassword = MutableStateFlow(false)
    val isCorrectPassword: StateFlow<Boolean> = _isCorrectPassword

    private val _isCorrectEmail = MutableStateFlow(false)
    val isCorrectEmail: StateFlow<Boolean> = _isCorrectEmail

    fun signIn(email: String, password: String) {
        if (ValidateFields.isValidEmail(email)) {
            if (ValidateFields.validatePassword(password)) {
                viewModelScope.launch {
                    signInUseCase.invoke(email, password, object : AuthService.AuthCallback {
                        override fun onSuccess(signInResponse: SignInData) {
                            viewModelScope.launch {
                                continueAfterLogin(signInResponse.accessToken, signInResponse.refreshToken)
                                sessionManager.setTokens()
                                _uiStateFlow.emit(SignInUiState.Success)
                                _isCorrectPassword.value = false
                            }
                        }

                        override fun onError(error: String) {
                            viewModelScope.launch {
                                _uiStateFlow.emit(SignInUiState.Error(error))
                                _isCorrectPassword.value = true
                            }
                        }
                    })
                }
            } else {
                _isCorrectPassword.value = true
            }
        } else {
            _isCorrectEmail.value = true
        }
    }

    fun resetState() {
        viewModelScope.launch {
            _uiStateFlow.emit(SignInUiState.Empty)
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

    fun resetProfileState(){
        viewModelScope.launch {
            _getProfileStateFlow.emit(GetProfileUiState.Empty)
        }
    }

    fun continueAfterLogin(token: String?, refreshToken: String?) {
        viewModelScope
            .launch {
                if (token != null && refreshToken != null) {
                    Log.d("testToken", "continue after login $token")
                    sessionManager.setAuthEntities(token, refreshToken)
                    getProfile()
                }
            }
    }
}
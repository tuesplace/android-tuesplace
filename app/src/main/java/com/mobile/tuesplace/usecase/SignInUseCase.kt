package com.mobile.tuesplace.usecase

import com.mobile.tuesplace.data.AuthData
import com.mobile.tuesplace.services.AuthService

class SignInUseCase(private val authService: AuthService) {
    suspend fun invoke(email: String, password: String, authCallback: AuthService.AuthCallback){
        authService.signIn(AuthData(email, password), authCallback)
    }
}
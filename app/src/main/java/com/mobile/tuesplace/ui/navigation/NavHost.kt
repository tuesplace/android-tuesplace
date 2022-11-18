package com.mobile.tuesplace.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.mobile.tuesplace.ui.forgottenpassword.ForgottenPasswordScreen
import com.mobile.tuesplace.ui.login.LoginScreen
import com.mobile.tuesplace.ui.login.LoginViewModel
import com.mobile.tuesplace.ui.welcome.WelcomeScreen
import org.koin.androidx.compose.getViewModel

@Composable
fun NavHost(navController: NavHostController){
    androidx.navigation.compose.NavHost(navController = navController, startDestination = LOGIN_SCREEN) {
        composable(LOGIN_SCREEN) {
            val viewModel = getViewModel<LoginViewModel>()
            val email by viewModel.email.collectAsState()
            val password by viewModel.password.collectAsState()
            val passwordVisibility by viewModel.passwordVisibility.collectAsState()

            LoginScreen(
                onLoginClick = { navController.navigate(WELCOME_SCREEN) },
                onForgottenPasswordClick = {  },
                email = email,
                setEmail = { viewModel.email(it) },
                password = password,
                setPassword = { viewModel.password(it) },
                passwordVisibility = passwordVisibility,
                setPasswordVisibility = {viewModel.passwordVisibility(it) },
            )
        }
        composable(WELCOME_SCREEN) {
            WelcomeScreen()
        }
        composable(FORGOTTEN_PASSWORD){
            ForgottenPasswordScreen()
        }
    }
}
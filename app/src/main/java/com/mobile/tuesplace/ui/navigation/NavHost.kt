package com.mobile.tuesplace.ui.navigation

import android.accounts.AccountManager
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.mobile.tuesplace.data.GroupData
import com.mobile.tuesplace.ui.classes.ClassesScreen
import com.mobile.tuesplace.ui.classroom.ClassroomScreen
import com.mobile.tuesplace.ui.forgottenpassword.ForgottenPasswordScreen
import com.mobile.tuesplace.ui.login.LoginScreen
import com.mobile.tuesplace.ui.login.LoginViewModel
import com.mobile.tuesplace.ui.messages.MessagesScreen
import com.mobile.tuesplace.ui.welcome.WelcomeScreen
import com.mobile.tuesplace.ui.welcome.WelcomeViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun NavHost(navController: NavHostController){
    androidx.navigation.compose.NavHost(navController = navController, startDestination = LOGIN_SCREEN) {
        composable(LOGIN_SCREEN) {
            val viewModel = getViewModel<LoginViewModel>()
            val email by viewModel.email.collectAsState()
            val password by viewModel.password.collectAsState()
            val passwordVisibility by viewModel.passwordVisibility.collectAsState()
            val accountManager = AccountManager.get(LocalContext.current)
            LoginScreen(
                onLoginClick = {
                    viewModel.signIn("kalina.valevaa@gmail.com", "12345678Ll2022\$\$&&")
                    navController.navigate(WELCOME_SCREEN)
                               },
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
            val context = LocalContext.current
            val viewModel = getViewModel<WelcomeViewModel>()
            WelcomeScreen(
                onMessageClick = {
                    //navController.navigate(MESSAGES_SCREEN)
                    viewModel.createGroup(GroupData(
                        name = "12 Б",
                        //teachers = arrayListOf("Дора Цветанова"),
                        type = "chat",
                        classes = arrayListOf("12Б")
                    ))
                    },
                onEnterClassClick = { navController.navigate(CLASSES_SCREEN) },
                onEnterClassroomClick = { navController.navigate(CLASSROOM_SCREEN) },
                onLinkClick = { ContextCompat.startActivity(context, Intent(Intent.ACTION_VIEW, Uri.parse("https://tues.bg")), null)}
            )
        }
        composable(FORGOTTEN_PASSWORD_SCREEN){
            ForgottenPasswordScreen()
        }
        composable(MESSAGES_SCREEN){
            MessagesScreen()
        }
        composable(CLASSES_SCREEN){
            ClassesScreen()
        }
        composable(CLASSROOM_SCREEN){
            ClassroomScreen()
        }
    }
}
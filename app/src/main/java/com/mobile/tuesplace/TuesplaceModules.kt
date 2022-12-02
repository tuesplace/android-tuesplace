package com.mobile.tuesplace

import com.mobile.tuesplace.services.AuthService
import com.mobile.tuesplace.services.AuthServiceImpl
import com.mobile.tuesplace.ui.login.LoginViewModel
import com.mobile.tuesplace.usecase.SignInUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val TuesplaceModules = module {
    viewModel { LoginViewModel(get()) }

    factory { SignInUseCase(get()) }
    factory<AuthService> { AuthServiceImpl() }
}
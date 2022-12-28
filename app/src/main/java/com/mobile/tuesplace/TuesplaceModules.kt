package com.mobile.tuesplace

import com.mobile.tuesplace.services.AuthService
import com.mobile.tuesplace.services.AuthServiceImpl
import com.mobile.tuesplace.services.GroupService
import com.mobile.tuesplace.services.GroupServiceImpl
import com.mobile.tuesplace.ui.login.LoginViewModel
import com.mobile.tuesplace.ui.welcome.WelcomeViewModel
import com.mobile.tuesplace.usecase.CreateGroupUseCase
import com.mobile.tuesplace.usecase.SignInUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val TuesplaceModules = module {
    viewModel { LoginViewModel(get()) }
    viewModel { WelcomeViewModel(get()) }

    factory { SignInUseCase(get()) }
    factory { CreateGroupUseCase(get()) }

    factory<GroupService> { GroupServiceImpl() }
    factory<AuthService> { AuthServiceImpl() }
}
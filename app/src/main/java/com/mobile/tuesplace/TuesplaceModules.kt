package com.mobile.tuesplace

import com.mobile.tuesplace.services.*
import com.mobile.tuesplace.ui.login.LoginViewModel
import com.mobile.tuesplace.ui.welcome.WelcomeViewModel
import com.mobile.tuesplace.usecase.CreateGroupUseCase
import com.mobile.tuesplace.usecase.GetGroupsUseCase
import com.mobile.tuesplace.usecase.GetProfileUseCase
import com.mobile.tuesplace.usecase.SignInUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val TuesplaceModules = module {
    viewModel { LoginViewModel(get(), get()) }
    viewModel { WelcomeViewModel(get(), get()) }

    factory { SignInUseCase(get()) }
    factory { CreateGroupUseCase(get()) }
    factory { GetGroupsUseCase(get()) }
    factory { GetProfileUseCase(get()) }

    factory<GroupService> { GroupServiceImpl() }
    factory<AuthService> { AuthServiceImpl() }
    factory<ProfileService> { ProfileServiceImpl() }
}
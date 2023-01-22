package com.mobile.tuesplace

import com.mobile.tuesplace.services.*
import com.mobile.tuesplace.ui.groups.AllGroupsViewModel
import com.mobile.tuesplace.ui.groups.CreateGroupViewModel
import com.mobile.tuesplace.ui.groups.EditGroupViewModel
import com.mobile.tuesplace.ui.login.LoginViewModel
import com.mobile.tuesplace.ui.welcome.WelcomeAdminViewModel
import com.mobile.tuesplace.ui.welcome.WelcomeViewModel
import com.mobile.tuesplace.usecase.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val TuesplaceModules = module {
    viewModel { LoginViewModel(get()) }
    viewModel { WelcomeViewModel(get()) }
    viewModel { CreateGroupViewModel(get()) }
    viewModel { WelcomeAdminViewModel(get()) }
    viewModel { EditGroupViewModel(get()) }
    viewModel { AllGroupsViewModel(get()) }

    factory { SignInUseCase(get()) }
    factory { CreateGroupUseCase(get()) }
    factory { GetGroupsUseCase(get()) }
    factory { GetProfileUseCase(get()) }
    factory { GetGroupUseCase(get()) }

    factory<GroupService> { GroupServiceImpl() }
    factory<AuthService> { AuthServiceImpl() }
    factory<ProfileService> { ProfileServiceImpl() }
}
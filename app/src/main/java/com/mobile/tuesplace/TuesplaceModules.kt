package com.mobile.tuesplace

import android.content.Intent
import com.mobile.tuesplace.services.*
import com.mobile.tuesplace.ui.chats.ChatroomViewModel
import com.mobile.tuesplace.ui.chats.ChatsScreen
import com.mobile.tuesplace.ui.chats.ChatsViewModel
import com.mobile.tuesplace.ui.classes.ClassesViewModel
import com.mobile.tuesplace.ui.classroom.ClassroomUserViewModel
import com.mobile.tuesplace.ui.videoroom.VideoroomViewModel
import com.mobile.tuesplace.ui.groups.AllGroupsViewModel
import com.mobile.tuesplace.ui.groups.CreateGroupViewModel
import com.mobile.tuesplace.ui.groups.EditGroupViewModel
import com.mobile.tuesplace.ui.login.LoginViewModel
import com.mobile.tuesplace.ui.profile.EditProfileViewModel
import com.mobile.tuesplace.ui.profile.ProfileViewModel
import com.mobile.tuesplace.ui.welcome.WelcomeAdminViewModel
import com.mobile.tuesplace.ui.welcome.WelcomeViewModel
import com.mobile.tuesplace.usecase.*
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val TuesplaceModules = module {
    viewModel { LoginViewModel(get(), get()) }
    viewModel { WelcomeViewModel(get()) }
    viewModel { CreateGroupViewModel(get()) }
    viewModel { WelcomeAdminViewModel() }
    viewModel { EditGroupViewModel(get(), get()) }
    viewModel { AllGroupsViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { EditProfileViewModel(get(), get()) }
    viewModel { VideoroomViewModel(get()) }
    viewModel { ClassroomUserViewModel(get()) }
    viewModel { ClassesViewModel(get()) }
    viewModel { ChatsViewModel(get()) }
    viewModel { ChatroomViewModel(get()) }

    factory { SignInUseCase(get()) }
    factory { CreateGroupUseCase(get()) }
    factory { GetGroupsUseCase(get()) }
    factory { GetProfileUseCase(get()) }
    factory { GetGroupUseCase(get()) }
    factory { DeleteGroupUseCase(get()) }
    factory { EditProfileUseCase(get()) }
    factory { GetMyGroupsUseCase(get()) }
    factory { GetProfileByIdUseCase(get()) }

    factory { AuthenticationManager(androidContext(), Intent()) }

    factory<GroupService> { GroupServiceImpl() }
    factory<AuthService> { AuthServiceImpl(get()) }
    factory<ProfileService> { ProfileServiceImpl() }

//    single<>
}
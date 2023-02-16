package com.mobile.tuesplace.ui.navigation

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mobile.tuesplace.ROLE
import com.mobile.tuesplace.data.GroupData
import com.mobile.tuesplace.ui.chats.ChatroomScreen
import com.mobile.tuesplace.ui.chats.ChatroomViewModel
import com.mobile.tuesplace.ui.classes.ClassesScreen
import com.mobile.tuesplace.ui.classes.ClassesViewModel
import com.mobile.tuesplace.ui.classroom.ClassroomUserViewModel
import com.mobile.tuesplace.ui.classroom.ClassroomUserScreen
import com.mobile.tuesplace.ui.videoroom.VideoroomViewModel
import com.mobile.tuesplace.ui.forgottenpassword.ForgottenPasswordScreen
import com.mobile.tuesplace.ui.groups.*
import com.mobile.tuesplace.ui.login.LoginScreen
import com.mobile.tuesplace.ui.login.LoginViewModel
import com.mobile.tuesplace.ui.chats.ChatsScreen
import com.mobile.tuesplace.ui.chats.ChatsViewModel
import com.mobile.tuesplace.ui.post.CreatePostScreen
import com.mobile.tuesplace.ui.profile.EditProfileScreen
import com.mobile.tuesplace.ui.profile.EditProfileViewModel
import com.mobile.tuesplace.ui.profile.ProfileScreen
import com.mobile.tuesplace.ui.settings.SettingsScreen
import com.mobile.tuesplace.ui.states.EditProfileUiState
import com.mobile.tuesplace.ui.states.GetGroupUiState
import com.mobile.tuesplace.ui.states.GetProfileUiState
import com.mobile.tuesplace.ui.students.AllStudentsScreen
import com.mobile.tuesplace.ui.students.AllStudentsViewModel
import com.mobile.tuesplace.ui.videoroom.VideoroomScreen
import com.mobile.tuesplace.ui.welcome.WelcomeAdminScreen
import com.mobile.tuesplace.ui.welcome.WelcomeScreen
import com.mobile.tuesplace.ui.welcome.WelcomeViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun NavHost(navController: NavHostController) {
    androidx.navigation.compose.NavHost(navController = navController,
        startDestination = LOGIN_SCREEN) {
        composable(LOGIN_SCREEN) {
            val viewModel = getViewModel<LoginViewModel>()
            val email by viewModel.email.collectAsState()
            val password by viewModel.password.collectAsState()
            val passwordVisibility by viewModel.passwordVisibility.collectAsState()
            val uiState by viewModel.uiStateFlow.collectAsState()
            val profileUiState by viewModel.getProfileStateFlow.collectAsState()
            val isCorrectPasswordState by viewModel.isCorrectPassword.collectAsState()
            val isCorrectEmailState by viewModel.isCorrectEmail.collectAsState()
            //val accountManager = AccountManager.get(LocalContext.current)
            LoginScreen(
                onLoginClick = {
                    viewModel.signIn(email, password)
                },
                onForgottenPasswordClick = { },
                email = email,
                setEmail = { viewModel.email(it) },
                password = password,
                setPassword = { viewModel.password(it) },
                passwordVisibility = passwordVisibility,
                setPasswordVisibility = { viewModel.passwordVisibility(it) },
                uiState = uiState,
                onSuccess = {
                    viewModel.resetState()
                    viewModel.getProfile()
                },
                isCorrectPassword = isCorrectPasswordState,
                isCorrectEmail = isCorrectEmailState
            )
            when (profileUiState) {
                GetProfileUiState.Empty -> {}
                is GetProfileUiState.Error -> {}
                GetProfileUiState.Loading -> {}
                is GetProfileUiState.Success -> {
                    ROLE = (profileUiState as GetProfileUiState.Success).profile.role
                    when (ROLE) {
                        "admin" -> navController.navigate(WELCOME_ADMIN_SCREEN)
                        "student" -> navController.navigate(WELCOME_SCREEN)
                        "teacher" -> navController.navigate(WELCOME_SCREEN)
                    }
                    viewModel.resetProfileState()
                }
                EditProfileUiState.Loading -> {}
            }
        }
        composable(WELCOME_SCREEN) {
            val context = LocalContext.current
            val viewModel = getViewModel<WelcomeViewModel>()
            LaunchedEffect(null) {
                viewModel.getProfile()
            }
            WelcomeScreen(
                onMessageClick = {
                    navController.navigate(CHATS_SCREEN)
                },
                onEnterClassClick = {
                    navController.navigate(CLASSES_SCREEN)
                },
                onEnterVideoroomClick = { navController.navigate(VIDEOROOM_SCREEN) },
                onLinkClick = {
                    ContextCompat.startActivity(context,
                        Intent(Intent.ACTION_VIEW, Uri.parse("https://tues.bg")),
                        null)
                },
                onAgendaClick = { }
            )
        }
        composable(FORGOTTEN_PASSWORD_SCREEN) {
            ForgottenPasswordScreen()
        }
        composable(CHATS_SCREEN) {
            val viewModel = getViewModel<ChatsViewModel>()
            val getMyGroupsUiState by viewModel.getMyGroupsStateFlow.collectAsState()
            viewModel.getMyGroups()
            ChatsScreen(
                getMyGroupsUiState = getMyGroupsUiState,
                onChatClick = { navController.navigate("$CHATROOM_SCREEN/$it") }
            )
        }
        composable(CLASSES_SCREEN) {
            val viewModel = getViewModel<ClassesViewModel>()
            val getMyGroupsUiState by viewModel.getMyGroupsStateFlow.collectAsState()
            viewModel.getMyGroups()
            ClassesScreen(
                onClassClick = { navController.navigate(CLASSROOM_USER_SCREEN) },
                getMyGroupsUiState = getMyGroupsUiState
            )
        }
        composable(VIDEOROOM_SCREEN) {
            val viewModel = getViewModel<VideoroomViewModel>()
            val groupsUiState by viewModel.getGroupStateFlow.collectAsState()
            viewModel.getGroups()
            VideoroomScreen(
                groupsUiState = groupsUiState,
                onBackPressed = { navController.navigateUp() }
            )
        }
        composable(WELCOME_ADMIN_SCREEN) {
            val context = LocalContext.current
            WelcomeAdminScreen(
                onAgendaClick = { },
                onGroupsClick = { navController.navigate(ALL_GROUPS_SCREEN) },
                onStudentsClick = { navController.navigate(ALL_STUDENTS_SCREEN) },
                onTeachersClick = { },
                onLinkClick = {
                    ContextCompat.startActivity(context,
                        Intent(Intent.ACTION_VIEW, Uri.parse("https://tues.bg")),
                        null)
                }
            )
        }
        composable(CREATE_GROUP_SCREEN) {
            val viewModel = getViewModel<CreateGroupViewModel>()
            val groupName by viewModel.groupName.collectAsState()
            val groupType by viewModel.groupType.collectAsState()
            val classes by viewModel.classes.collectAsState()
            CreateGroupScreen(
                groupName = groupName,
                setGroupName = { viewModel.groupName(it) },
                groupType = groupType,
                setGroupType = { viewModel.groupType(it) },
                classes = classes,
                setClasses = { viewModel.classes(it) },
                onCreateGroupClick = {
                    viewModel.createGroup(GroupData(
                        name = groupName,
                        type = groupType,
                        classes = arrayListOf(classes)
                    ))
                }
            )
        }
        composable(ALL_GROUPS_SCREEN) {
            val viewModel = getViewModel<AllGroupsViewModel>()
            val allGroupsUiStateFlow by viewModel.getGroupStateFlow.collectAsState()
            viewModel.getGroups()
            AllGroupsScreen(
                allGroupsUiStateFlow,
                onGroupClick = { id ->
                    navController.navigate("edit_group_screen/$id")
                },
                onAddClick = {
                    navController.navigate(
                        CREATE_GROUP_SCREEN)
                })
        }
        composable("edit_group_screen/{groupId}",
            arguments = listOf(navArgument("groupId") {
                type = NavType.StringType
            })) { backStackEntry ->
            val viewModel = getViewModel<EditGroupViewModel>()
            val groupName by viewModel.groupName.collectAsState()
            val groupType by viewModel.groupType.collectAsState()
            val classes by viewModel.classes.collectAsState()
            val groupUiStateFlow by viewModel.groupStateFlow.collectAsState()
            val deleteGroupUiStateFlow by viewModel.deleteGroupUiStateFlow.collectAsState()

            LaunchedEffect(null) {
                backStackEntry.arguments?.getString("groupId")?.let { groupId ->
                    viewModel.getGroup(groupId)
                }
            }
            EditGroupScreen(
                groupUiStateFlow = groupUiStateFlow,
                groupName = groupName,
                setGroupName = { viewModel.groupName(it) },
                groupType = groupType,
                setGroupType = { viewModel.groupType(it) },
                classes = classes,
                setClasses = { viewModel.classes(it) },
                onEditClick = { },
                onDeleteClick = { viewModel.deleteGroup(it) },
                deleteUiState = deleteGroupUiStateFlow,
                onBackPressed = { navController.navigateUp() })
        }
        composable(PROFILE_SCREEN) {
            val viewModel = getViewModel<EditProfileViewModel>()
            val profileUiState by viewModel.getProfileStateFlow.collectAsState()
            LaunchedEffect(null) {
                viewModel.getProfile()
            }
            ProfileScreen(profileUiState = profileUiState)
        }
        composable(EDIT_PROFILE_SCREEN) {
            val viewModel = getViewModel<EditProfileViewModel>()
            val profileUiState by viewModel.getProfileStateFlow.collectAsState()
            val enabled: Boolean = ROLE == "admin"
            LaunchedEffect(null) {
                viewModel.getProfile()
            }
            EditProfileScreen(profileUiState = profileUiState,
                enabled = enabled,
                onSaveChanges = {})
        }
        composable(SETTINGS_SCREEN) {
            SettingsScreen(
                onEditClick = { navController.navigate(PROFILE_SCREEN) },
                onSignOutClick = { navController.navigate(LOGIN_SCREEN) }
            )
        }
        composable(CLASSROOM_USER_SCREEN) {
            val viewModel = getViewModel<ClassroomUserViewModel>()
            val getProfileByIdUiState by viewModel.getProfileByIdStateFlow.collectAsState()

            ClassroomUserScreen(
                setProfile = {},
                getGroupUiState = GetGroupUiState.Empty,
                getProfileByIdUiState = getProfileByIdUiState,
                onCreatePostClick = { navController.navigate(CREATE_POST) },
                onEditPostClick = { }
            )
        }
        composable(CREATE_POST) {
            CreatePostScreen()
        }
        composable("$CHATROOM_SCREEN/{groupId}",
            arguments = listOf(navArgument("groupId") {
                type = NavType.StringType
            })) { backStackEntry ->
            val viewModel = getViewModel<ChatroomViewModel>()
            val getGroupUiState by viewModel.groupStateFlow.collectAsState()
            LaunchedEffect(null) {
                backStackEntry.arguments?.getString("groupId")?.let { groupId ->
                    viewModel.getGroup(groupId)
                }
            }
            ChatroomScreen(getGroupUiState = getGroupUiState)
        }
        composable(ALL_STUDENTS_SCREEN) {
            val viewModel = getViewModel<AllStudentsViewModel>()
            val getAllProfilesStateFlow by viewModel.getAllProfilesStateFlow.collectAsState()
            viewModel.getAllProfiles()
            AllStudentsScreen(getAllProfilesUiState = getAllProfilesStateFlow, onStudentClick = {})
        }
    }
}
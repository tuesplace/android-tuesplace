package com.mobile.tuesplace.ui.navigation

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mobile.tuesplace.R
import com.mobile.tuesplace.ROLE
import com.mobile.tuesplace.data.GroupData
import com.mobile.tuesplace.ui.classes.ClassesScreen
import com.mobile.tuesplace.ui.classroom.ClassroomScreen
import com.mobile.tuesplace.ui.forgottenpassword.ForgottenPasswordScreen
import com.mobile.tuesplace.ui.groups.*
import com.mobile.tuesplace.ui.login.LoginScreen
import com.mobile.tuesplace.ui.login.LoginViewModel
import com.mobile.tuesplace.ui.messages.MessagesScreen
import com.mobile.tuesplace.ui.profile.EditProfileScreen
import com.mobile.tuesplace.ui.profile.EditProfileViewModel
import com.mobile.tuesplace.ui.profile.ProfileScreen
import com.mobile.tuesplace.ui.states.GetProfileUiState
import com.mobile.tuesplace.ui.welcome.WelcomeAdminScreen
import com.mobile.tuesplace.ui.welcome.WelcomeAdminViewModel
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
            //val accountManager = AccountManager.get(LocalContext.current)
            LoginScreen(
                onLoginClick = {
                    viewModel.signIn("student1@gmail.com", "Student1!")
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
            )
            when(profileUiState) {
                GetProfileUiState.Empty -> {}
                is GetProfileUiState.Error -> {}
                GetProfileUiState.Loading -> {}
                is GetProfileUiState.Success -> {
                    ROLE = (profileUiState as GetProfileUiState.Success).profile.role
                    when(ROLE) {
                        "admin" -> navController.navigate(WELCOME_ADMIN_SCREEN)
                        "student" -> navController.navigate(WELCOME_SCREEN)
                        "teacher" -> navController.navigate(WELCOME_SCREEN)
                    }
                    viewModel.resetProfileState()
                }
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
                    navController.navigate(MESSAGES_SCREEN)
                },
                onEnterClassClick = {
                    navController.navigate(CLASSES_SCREEN)
                },
                onEnterClassroomClick = { navController.navigate(CLASSROOM_SCREEN) },
                onLinkClick = {
                    ContextCompat.startActivity(context,
                        Intent(Intent.ACTION_VIEW, Uri.parse("https://tues.bg")),
                        null)
                }
            )
        }
        composable(FORGOTTEN_PASSWORD_SCREEN) {
            ForgottenPasswordScreen()
        }
        composable(MESSAGES_SCREEN) {
            MessagesScreen()
        }
        composable(CLASSES_SCREEN) {
            ClassesScreen()
        }
        composable(CLASSROOM_SCREEN) {
            ClassroomScreen()
        }
        composable(WELCOME_ADMIN_SCREEN) {
            val allGroups = stringResource(id = R.string.all_groups)
            val createStudent = stringResource(id = R.string.add_student)
            val removeStudent = stringResource(id = R.string.remove_student)

            WelcomeAdminScreen(onClick = { string ->
                when (string) {
                    allGroups -> {
                        navController.navigate(ALL_GROUPS_SCREEN)
                    }
                    createStudent -> {

                    }
                    removeStudent -> {

                    }
                }
            })
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
                onGroupClick = {
                    navController.navigate("edit_group_screen/63ce4efd8bd8e60aefbc5839")
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
        composable(PROFILE_SCREEN){
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
            EditProfileScreen(profileUiState = profileUiState, enabled = enabled, onSaveChanges = {})
        }
    }
}
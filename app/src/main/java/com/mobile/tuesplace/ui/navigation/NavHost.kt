package com.mobile.tuesplace.ui.navigation

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mobile.tuesplace.*
import com.mobile.tuesplace.data.*
import com.mobile.tuesplace.session.SessionManager
import com.mobile.tuesplace.ui.Loading
import com.mobile.tuesplace.ui.activities.*
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
import com.mobile.tuesplace.ui.navigate
import com.mobile.tuesplace.ui.post.*
import com.mobile.tuesplace.ui.profile.*
import com.mobile.tuesplace.ui.settings.SettingsScreen
import com.mobile.tuesplace.ui.settings.SettingsViewModel
import com.mobile.tuesplace.ui.states.GetProfileUiState
import com.mobile.tuesplace.ui.students.AllStudentsScreen
import com.mobile.tuesplace.ui.students.AllStudentsViewModel
import com.mobile.tuesplace.ui.submissions.SubmissionsScreen
import com.mobile.tuesplace.ui.submissions.SubmissionsViewModel
import com.mobile.tuesplace.ui.teachers.AllTeachersScreen
import com.mobile.tuesplace.ui.videoroom.VideoroomScreen
import com.mobile.tuesplace.ui.welcome.WelcomeAdminScreen
import com.mobile.tuesplace.ui.welcome.WelcomeScreen
import org.koin.androidx.compose.getViewModel

@RequiresApi(Build.VERSION_CODES.O)
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
                },
                isCorrectPassword = isCorrectPasswordState,
                isCorrectEmail = isCorrectEmailState
            )
            when (profileUiState) {
                GetProfileUiState.Empty -> {}
                is GetProfileUiState.Error -> {}
                GetProfileUiState.Loading -> {
                    Loading()
                }
                is GetProfileUiState.Success -> {
                    when (((profileUiState as GetProfileUiState.Success).profile.role)) {
                        ADMIN_ROLE -> navController.navigate(WELCOME_ADMIN_SCREEN,
                            navOptions = NavOptions.Builder()
                                .setPopUpTo(LOGIN_SCREEN,
                                    true).build())
                        STUDENT_ROLE -> navController.navigate(WELCOME_SCREEN,
                            bundleOf(ROLE to STUDENT_ROLE,
                                BLOCKED to (profileUiState as GetProfileUiState.Success).profile.blocked),
                            navOptions = NavOptions.Builder()
                                .setPopUpTo(LOGIN_SCREEN,
                                    true).build())
                        TEACHER_ROLE -> navController.navigate(WELCOME_SCREEN,
                            bundleOf(ROLE to TEACHER_ROLE,
                                BLOCKED to (profileUiState as GetProfileUiState.Success).profile.blocked),
                            navOptions = NavOptions.Builder()
                                .setPopUpTo(LOGIN_SCREEN,
                                    true).build())
                        else -> {}
                    }
                    viewModel.resetProfileState()
                }
            }
        }
        composable(WELCOME_SCREEN) { backStackEntry ->
            val context = LocalContext.current

            backStackEntry.arguments?.getBoolean(BLOCKED)?.let {
                WelcomeScreen(
                    onMessageClick = {
                        navController.navigate(CHATS_SCREEN)
                    },
                    onEnterClassClick = {
                        navController.navigate(CLASSES_SCREEN,
                            bundleOf(ROLE to backStackEntry.arguments?.getString(ROLE)))
                    },
                    onEnterVideoroomClick = { navController.navigate(MY_ACTIVITIES_SCREEN) },
                    onLinkClick = {
                        ContextCompat.startActivity(context,
                            Intent(Intent.ACTION_VIEW, Uri.parse(WEB_VIEW_LINK)),
                            null)
                    },
                    onAgendaClick = {
                        navController.navigate(ALL_MY_ACTIVITIES_SCREEN)
                    },
                    blocked = it
                )
            }
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
        composable(CLASSES_SCREEN) { backStackEntry ->
            val viewModel = getViewModel<ClassesViewModel>()
            val getMyGroupsUiState by viewModel.getMyGroupsStateFlow.collectAsState()
            viewModel.getMyGroups()
            ClassesScreen(
                onClassClick = {
                    navController.navigate(CLASSROOM_USER_SCREEN,
                        bundleOf(GROUP_ID to it,
                            ROLE to backStackEntry.arguments?.getString(ROLE)))
                },
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
                onAgendaClick = { navController.navigate(ACTIVITIES_OPTION_MENU_SCREEN) },
                onGroupsClick = { navController.navigate(ALL_GROUPS_SCREEN) },
                onStudentsClick = { navController.navigate(ALL_STUDENTS_SCREEN) },
                onTeachersClick = { navController.navigate(ALL_TEACHERS_SCREEN) },
                onLinkClick = {
                    ContextCompat.startActivity(context,
                        Intent(Intent.ACTION_VIEW, Uri.parse(WEB_VIEW_LINK)),
                        null)
                }
            )
        }
        composable(CREATE_GROUP_SCREEN) {
            val viewModel = getViewModel<CreateGroupViewModel>()
            val groupName by viewModel.groupName.collectAsState()
            val teachers by viewModel.teachers.collectAsState()
            val classes by viewModel.classes.collectAsState()
            val groupType by viewModel.groupsTypeStateFlow.collectAsState()
            val classListVisibility by viewModel.classListVisibility.collectAsState()
            val teachersListVisibility by viewModel.teacherListVisibility.collectAsState()
            val createGroupUiState by viewModel.uiState.collectAsState()
            val getAllProfilesUiState by viewModel.getAllProfilesStateFlow.collectAsState()
            LaunchedEffect(null) {
                viewModel.getAllProfiles()
            }
            CreateGroupScreen(
                groupName = groupName,
                setGroupName = { viewModel.groupName(it) },
                classes = classes,
                setClasses = { viewModel.classes(it) },
                onCreateGroupClick = {
                    viewModel.createGroup(GroupData(
                        name = groupName,
                        type = if (groupType) CHAT_TYPE else SUBJECT_TYPE,
                        classes = viewModel.classesList
                    ))
                },
                teacher = teachers,
                setTeacher = { viewModel.teachers(it) },
                groupsType = groupType,
                setGroupsType = { viewModel.groupsType(it) },
                onTeacherClick = {
                    viewModel.addTeacher(it)
                    viewModel.teachers(EMPTY_STRING)
                                 },
                classListVisibility = classListVisibility,
                teacherListVisibility = teachersListVisibility,
                setClassVisibility = { viewModel.classListVisibility(it) },
                setTeacherListVisibility = { viewModel.teacherListVisibility(it) },
                onClassClick = { viewModel.addClass(it) },
                teachers = viewModel.profiles,
                createGroupUiState = createGroupUiState,
                onCreateGroupSuccess = {
                    navController.navigate(ALL_GROUPS_SCREEN)
                    viewModel.resetState()
                },
                getAllProfilesUiState = getAllProfilesUiState,
                selectedTeachers = viewModel.teachersList
            )
        }
        composable(ALL_GROUPS_SCREEN) {
            val viewModel = getViewModel<AllGroupsViewModel>()
            val allGroupsUiStateFlow by viewModel.getGroupStateFlow.collectAsState()
            val groupTypeStateFlow by viewModel.groupsTypeStateFlow.collectAsState()
            viewModel.getGroups()
            AllGroupsScreen(
                allGroupsUiStateFlow,
                onGroupClick = { id ->
                    navController.navigate(EDIT_GROUP_SCREEN, bundleOf(GROUP_ID to id))
                },
                onAddClick = {
                    navController.navigate(
                        CREATE_GROUP_SCREEN)
                },
                groupsType = groupTypeStateFlow,
                setGroupsType = { viewModel.groupsType(it) }
            )
        }
        composable(EDIT_GROUP_SCREEN) { backStackEntry ->
            val viewModel = getViewModel<EditGroupViewModel>()
            val groupName by viewModel.groupName.collectAsState()
            val groupType by viewModel.groupType.collectAsState()
            val classes by viewModel.classes.collectAsState()
            val groupUiStateFlow by viewModel.groupStateFlow.collectAsState()
            val deleteGroupUiStateFlow by viewModel.deleteGroupUiStateFlow.collectAsState()
            val editGroupUiState by viewModel.editGroupUiStateFlow.collectAsState()
            LaunchedEffect(null) {
                backStackEntry.arguments?.getString(GROUP_ID)?.let { groupId ->
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
                onEditClick = {
                    backStackEntry.arguments?.getString(GROUP_ID)?.let { groupId ->
                        viewModel.editGroup(groupId = groupId,
                            editGroupData = EditGroupData(
                                groupName.ifEmpty { null },
                                groupType.ifEmpty { null },
                                if (classes.isEmpty()) {
                                    null
                                } else {
                                    arrayListOf(classes)
                                }))
                    }
                },
                onDeleteClick = { viewModel.deleteGroup(it) },
                deleteUiState = deleteGroupUiStateFlow,
                onBackPressed = { navController.navigateUp() },
                onDeleteSuccess = {
                    navController.navigateUp()
                    viewModel.resetDeleteState()
                },
                onEditSuccess = {
                    navController.navigateUp()
                    viewModel.resetEditState()
                },
                editGroupUiState = editGroupUiState
            )
        }
        composable(PROFILE_SCREEN) { backStackEntry ->
            val viewModel = getViewModel<EditProfileViewModel>()
            val profileUiState by viewModel.getProfileStateFlow.collectAsState()
            LaunchedEffect(null) {
                if (SessionManager.getUser()?.role == ADMIN_ROLE) {
                    if (SessionManager.getUser()?._id != backStackEntry.arguments?.getString(ID_STRING)) {
                        backStackEntry.arguments?.getString(ID_STRING)
                            ?.let { viewModel.getProfile(it) }
                    }
                }
            }
            ProfileScreen(
                profileUiState = profileUiState,
                onEditClick = {
                    navController.navigate(EDIT_PROFILE_SCREEN,
                        bundleOf(ID_STRING to it,
                            IS_ADMIN to (backStackEntry.arguments?.getString(ID_STRING) == EMPTY_STRING)))
                },
                isAdmin = SessionManager.getUser()?.role == ADMIN_ROLE && SessionManager.getUser()?._id != backStackEntry.arguments?.getString(ID_STRING)
            )
        }
        composable(EDIT_PROFILE_SCREEN) { backStackEntry ->
            val viewModel = getViewModel<EditProfileViewModel>()
            val profileUiState by viewModel.getProfileStateFlow.collectAsState()
            val changeName by viewModel.changeName.collectAsState()
            val changeEmail by viewModel.changeEmail.collectAsState()
            val changeClass by viewModel.changeClass.collectAsState()
            val editProfileStateFlow by viewModel.editProfileStateFlow.collectAsState()
            val imageUpload by viewModel.imageUpload.collectAsState()
            val getMyProfileUiState by viewModel.getMyProfileStateFlow.collectAsState()
            val profileAssetsUiState by viewModel.putProfileAssetsStateFlow.collectAsState()
            val dialogVisibility by viewModel.dialogVisibility.collectAsState()
            val deleteProfileUiState by viewModel.deleteProfileUiState.collectAsState()
            LaunchedEffect(null) {
                if (backStackEntry.arguments?.getBoolean(IS_ADMIN) == true) {
                    viewModel.getMyProfile()
                } else {
                    backStackEntry.arguments?.getString(ID_STRING)
                        ?.let { viewModel.getProfile(it) }
                }
            }
            EditProfileScreen(
                profileUiState = profileUiState,
                onSaveChanges = {
                    if (backStackEntry.arguments?.getBoolean(IS_ADMIN) == true) {
                        viewModel.editMyProfile(EditProfileData(
                            fullName = changeName.ifEmpty { null },
                            email = changeEmail.ifEmpty { null },
                            password = null,
                            role = null,
                            className = changeClass.ifEmpty { null }
                        ))
                        imageUpload?.let {
                            viewModel.putMyProfileAssets(it)
                        }
                    } else {
                        backStackEntry.arguments?.getString(ID_STRING)?.let {
                            viewModel.editProfile(EditProfileData(
                                fullName = changeName.ifEmpty { null },
                                email = changeEmail.ifEmpty { null },
                                password = null,
                                role = null,
                                className = changeClass.ifEmpty { null }
                            ), it)
                        }
                        imageUpload?.let {
                            backStackEntry.arguments?.getString(ID_STRING)
                                ?.let { it1 -> viewModel.putProfileAssets(it, it1) }
                        }
                    }
                },
                onAddPhotoClick = {},
                changeName = changeName,
                setChangedName = { viewModel.changeName(it) },
                changeEmail = changeEmail,
                setChangedEmail = { viewModel.changeEmail(it) },
                changeClass = changeClass,
                setChangedClass = { viewModel.changeClass(it) },
                editProfileStateFlow = editProfileStateFlow,
                onImageUpload = { viewModel.imageUpload(it) },
                onEditProfileSuccess = {
                    navController.navigateUp()
                    viewModel.resetEditStates()
                },
                getProfileUiState = getMyProfileUiState,
                profileAssetsUiState = profileAssetsUiState,
                onDeleteClick = { viewModel.deleteProfile(it) },
                dialogVisibility = dialogVisibility,
                setDialogVisibility = { viewModel.dialogVisibility(it) },
                deleteProfileUiState = deleteProfileUiState,
                onDeleteSuccess = {
                    viewModel.resetDeleteState()
                    navController.navigateUp()
                },
            )
        }
        composable(SETTINGS_SCREEN) {
            val viewModel = getViewModel<SettingsViewModel>()
            val getProfileUiState by viewModel.getProfileStateFlow.collectAsState()
            viewModel.getProfile()
            val emptyTokensState by viewModel.emptyTokensStateFlow.collectAsState()
            SettingsScreen(
                onEditClick = {
                    navController.navigate(PROFILE_SCREEN,
                        bundleOf(ID_STRING to EMPTY_STRING))
                },
                onSignOutClick = {
                    viewModel.deleteTokensData()
                },
                onForgottenPasswordClick = {},
                getProfileUiState = getProfileUiState,
                onEmptyTokens = {
                    navController.navigate(LOGIN_SCREEN, navOptions = NavOptions.Builder()
                        .setPopUpTo(LOGIN_SCREEN,
                            true).build())
                    viewModel.setEmptyTokenFalse()
                },
                emptyTokensState = emptyTokensState
            )
        }
        composable(CLASSROOM_USER_SCREEN) { backStackEntry ->
            val viewModel = getViewModel<ClassroomUserViewModel>()
            val getPostsUiState by viewModel.getPostsStateFlow.collectAsState()
            val getGroupUiState by viewModel.getGroupStateFlow.collectAsState()
            LaunchedEffect(null) {
                backStackEntry.arguments?.getString(GROUP_ID)
                    ?.let { viewModel.getGroup(it) }
            }
            ClassroomUserScreen(
                getGroupUiState = getGroupUiState,
                onGroupSuccess = {
                    backStackEntry.arguments?.getString(GROUP_ID)
                        ?.let { viewModel.getPosts(it) }
                    viewModel.setGroupStateAsLoaded()
                },
                getPostsUiState = getPostsUiState,
                onCreatePostClick = {
                    navController.navigate(CREATE_POST,
                        bundleOf(GROUP_ID to backStackEntry.arguments?.getString(GROUP_ID),
                            ROLE to backStackEntry.arguments?.getString(ROLE)))
                },
                onEditPostClick = { },
                onPostClick = {
                    if (backStackEntry.arguments?.getString(ROLE) == TEACHER_ROLE) {
                        navController.navigate(SUBMISSIONS_SCREEN,
                            bundleOf(GROUP_ID to backStackEntry.arguments?.getString(
                                GROUP_ID),
                                POST_ID to it.first))
                    } else {
                        navController.navigate(POST_SCREEN,
                            bundleOf(GROUP_ID to backStackEntry.arguments?.getString(
                                GROUP_ID),
                                POST_ID to it.first))
                    }
                },
                group = viewModel.group
            )
        }
        composable(CREATE_POST) { backStackEntry ->

            val viewModel = getViewModel<CreatePostViewModel>()
            val postName by viewModel.postName.collectAsState()
            val postDescription by viewModel.postDescription.collectAsState()
            val postDeadline by viewModel.postDeadline.collectAsState()
            val postType by viewModel.postType.collectAsState()
            val createPostUiState by viewModel.createPostStateFlow.collectAsState()

            backStackEntry.arguments?.getString(ROLE)?.let { role ->
                CreatePostScreen(
                    role = role,
                    postName = postName,
                    setPostName = { viewModel.postName(it) },
                    postDescription = postDescription,
                    setPostDescription = { viewModel.postDescription(it) },
                    deadline = postDeadline,
                    setDeadline = { viewModel.postDeadline(it) },
                    postType = postType,
                    setPostType = { viewModel.postType(it) },
                    onCreateClick = {
                        backStackEntry.arguments?.getString(GROUP_ID)
                            ?.let { it1 -> viewModel.createPost(it1, it) }
                    },
                    createPostUiState = createPostUiState,
                    onCreatePostSuccess = {
                        navController.navigateUp()
                        viewModel.resetState()
                    }
                )
            }
        }
        composable("$CHATROOM_SCREEN/{groupId}",
            arguments = listOf(navArgument(GROUP_ID) {
                type = NavType.StringType
            })) { backStackEntry ->
            val viewModel = getViewModel<ChatroomViewModel>()
            val getGroupUiState by viewModel.groupStateFlow.collectAsState()
            val comment by viewModel.comment.collectAsState()
            LaunchedEffect(null) {
                backStackEntry.arguments?.getString(GROUP_ID)?.let { groupId ->
                    viewModel.getGroup(groupId)
                }
            }
            ChatroomScreen(
                getGroupUiState = getGroupUiState,
                onCommentChange = { viewModel.comment(it) },
                commentInput = comment,
                onSendClick = {}
            )
        }
        composable(ALL_STUDENTS_SCREEN) {
            val viewModel = getViewModel<AllStudentsViewModel>()
            val getAllProfilesStateFlow by viewModel.getAllProfilesStateFlow.collectAsState()
            viewModel.getAllProfiles()
            AllStudentsScreen(
                getAllProfilesUiState = getAllProfilesStateFlow,
                onStudentClick = { id ->
                    navController.navigate(PROFILE_SCREEN, bundleOf(ID_STRING to id))
                },
                onCreateNewClick = { navController.navigate(CREATE_PROFILE) }
            )
        }
        composable(ALL_TEACHERS_SCREEN) {
            val viewModel = getViewModel<AllStudentsViewModel>()
            val getAllProfilesStateFlow by viewModel.getAllProfilesStateFlow.collectAsState()
            viewModel.getAllProfiles()
            AllTeachersScreen(
                getAllProfilesUiState = getAllProfilesStateFlow,
                onTeacherClick = {
                    navController.navigate(PROFILE_SCREEN,
                        bundleOf(ID_STRING to it))
                },
                onCreateNewClick = { navController.navigate(CREATE_PROFILE) }
            )
        }
        composable(MY_ACTIVITIES_SCREEN) {
            val viewModel = getViewModel<MyActivitiesViewModel>()
            val getActivitiesStateFlow by viewModel.getMyActivitiesStateFlow.collectAsState()
            viewModel.getMyActivities()
            MyActivitiesScreen(
                getMyActivitiesUiState = getActivitiesStateFlow,
                onFullAgendaClick = { navController.navigate(ALL_MY_ACTIVITIES_SCREEN) }
            )
        }
        composable(ALL_MY_ACTIVITIES_SCREEN) {
            val viewModel = getViewModel<AllMyActivitiesViewModel>()
            val getMyActivitiesUiState by viewModel.getMyActivitiesStateFlow.collectAsState()
            viewModel.getMyActivities()
            AllMyActivitiesScreen(getMyActivitiesUiState = getMyActivitiesUiState)
        }
        composable(ACTIVITIES_OPTION_MENU_SCREEN) {
            ActivitiesOptionMenuScreen(
                onStudentsClick = { navController.navigate(ACTIVITIES_STUDENTS_CLASS_MENU) },
                onTeachersClick = { navController.navigate(ACTIVITIES_TEACHERS_SCREEN) },
                onChangeAgendaClick = { navController.navigate(UPLOAD_ACTIVITIES_SCREEN) }
            )
        }
        composable(ACTIVITIES_STUDENTS_CLASS_MENU) {
            ActivitiesStudentsClassMenuScreen(
                onEightGradeClick = {
                    navController.navigate(ACTIVITIES_STUDENTS_SCREEN,
                        bundleOf("grade" to "8"))
                },
                onNinthGradeClick = {
                    navController.navigate(ACTIVITIES_STUDENTS_SCREEN,
                        bundleOf("grade" to "9"))
                },
                onTenthGradeClick = {
                    navController.navigate(ACTIVITIES_STUDENTS_SCREEN,
                        bundleOf("grade" to "10"))
                },
                onEleventhGradeClick = {
                    navController.navigate(ACTIVITIES_STUDENTS_SCREEN,
                        bundleOf("grade" to "11"))
                },
                onTwelfthGradClick = {
                    navController.navigate(ACTIVITIES_STUDENTS_SCREEN,
                        bundleOf("grade" to "12"))
                }
            )
        }
        composable(ACTIVITIES_STUDENTS_SCREEN) { backStackEntry ->
            val viewModel = getViewModel<ActivitiesStudentsViewModel>()
            val getActivitiesUiState by viewModel.getActivitiesStateFlow.collectAsState()
            val setVisibilityStateFlow by viewModel.setVisibilityStateFlow.collectAsState()

            viewModel.getActivities()
            backStackEntry.arguments?.getString("grade")?.let { grade ->
                ActivitiesStudentsScreen(
                    grade = grade,
                    getActivitiesUiState = getActivitiesUiState,
                    setVisibilityStateFlow = setVisibilityStateFlow,
                    setVisibility = { viewModel.setVisibilities(it) }
                )
            }
        }
        composable(ACTIVITIES_TEACHERS_SCREEN) {
            val viewModel = getViewModel<ActivitiesTeachersViewModel>()
            val getAllProfilesStateFlow by viewModel.getAllProfilesStateFlow.collectAsState()
            viewModel.getAllProfiles()
            ActivitiesTeachersScreen(
                getAllProfileUiState = getAllProfilesStateFlow,
                onTeacherClick = {
                    navController.navigate(ACTIVITIES_TEACHER_SCREEN,
                        bundleOf("profileId" to it))
                }
            )
        }
        composable(ACTIVITIES_TEACHER_SCREEN) { backStackEntry ->
            val viewModel = getViewModel<ActivitiesTeacherViewModel>()
            val getActivitiesUiState by viewModel.getActivitiesStateFlow.collectAsState()
            viewModel.getActivities()
            backStackEntry.arguments?.getString("profileId")?.let {
                ActivitiesTeacherScreen(getActivitiesUiState = getActivitiesUiState,
                    profileId = it)
            }
        }
        composable(UPLOAD_ACTIVITIES_SCREEN) {
            val viewModel = getViewModel<UploadActivityViewModel>()
            val getSpecificationStateFlow by viewModel.getSpecificationStateFlow.collectAsState()
            val editSpecificationAssetsStateFlow by viewModel.editSpecificationAssetsStateFlow.collectAsState()
            var idString = ""
            LaunchedEffect(null) {
                viewModel.getSpecification()
            }
            UploadActivityScreen(
                onUploadClick = { url ->
                    if (idString.isNotEmpty()) {
                        viewModel.editSpecificationAssets(idString, url)
                    }
                },
                onGetSpecificationSuccess = { id ->
                    idString = id
                },
                specificationUiState = getSpecificationStateFlow,
                editSpecificationUiState = editSpecificationAssetsStateFlow,
                onEditSuccess = { navController.navigateUp() }
            )
        }
        composable(POST_SCREEN) { backStackEntry ->

            val viewModel = getViewModel<PostViewModel>()
            val comment by viewModel.comment.collectAsState()
            val getPostUiState by viewModel.getPostStateFlow.collectAsState()
            val getPostCommentsUiState by viewModel.getPostCommentsStateFlow.collectAsState()
            val commentMenuIndex by viewModel.commentMenuIndex.collectAsState()
            val enabled by viewModel.enabled.collectAsState()
            val commentsData by viewModel.commentList.collectAsState()
            val editCommentUiState by viewModel.editPostCommentsStateFlow.collectAsState()
            val deleteCommentUiState by viewModel.deletePostCommentsStateFlow.collectAsState()
            val createCommentUiState by viewModel.createCommentStateFlow.collectAsState()
            val dialogVisibility by viewModel.dialogVisibility.collectAsState()
            val createSubmissionUiState by viewModel.createSubmissionStateFlow.collectAsState()
            val getProfileUiState by viewModel.getProfileStateFlow.collectAsState()

            LaunchedEffect(null) {
                backStackEntry.arguments?.getString(GROUP_ID)?.let {
                    backStackEntry.arguments?.getString(POST_ID)
                        ?.let { it1 -> viewModel.getPost(it, it1) }
                }
            }
            PostScreen(
                commentInput = comment,
                onCommentChange = { viewModel.comment(it) },
                getPostUiState = getPostUiState,
                onSendClick = {
                    backStackEntry.arguments?.getString(GROUP_ID)
                        ?.let { it1 ->
                            viewModel.createComment(it1,
                                it.postId,
                                it.commentRequestData)
                        }
                },
                getPostCommentsUiState = getPostCommentsUiState,
                onPostSuccess = {
                    viewModel.getProfile()
                },
                onEditClick = { postInfo ->
                    navController.navigate(EDIT_POST_SCREEN,
                        bundleOf(GROUP_ID to backStackEntry.arguments?.getString(GROUP_ID),
                            POST_ID to backStackEntry.arguments?.getString(POST_ID),
                            "titleString" to postInfo.title,
                            "bodyString" to postInfo.body))
                },
                commentMenuIndex = commentMenuIndex,
                setCommentMenuIndex = { viewModel.setCommentMenuIndex(it) },
                onDeleteClick = {
                    backStackEntry.arguments?.getString(GROUP_ID)
                        ?.let { groupId ->
                            backStackEntry.arguments?.getString(POST_ID)?.let { postId ->
                                viewModel.deleteComment(
                                    groupId = groupId,
                                    postId = postId,
                                    commentId = it
                                )
                            }
                        }
                },
                onEditCommentClick = {
                    backStackEntry.arguments?.getString(GROUP_ID)
                        ?.let { groupId ->
                            backStackEntry.arguments?.getString(POST_ID)?.let { postId ->
                                viewModel.editComment(CommentRequestData(body = it.second,
                                    isPrivate = null),
                                    commentId = it.first,
                                    groupId = groupId,
                                    postId = postId)
                            }
                        }
                },
                enabled = enabled,
                setEnabled = { viewModel.enabled(it) },
                setEditCommentBody = { viewModel.editComment(it.first, it.second) },
                commentData = commentsData,
                post = viewModel.postData,
                dialogVisibility = dialogVisibility,
                setDialogVisibility = { viewModel.dialogVisibility(it) },
                editCommentUiState = editCommentUiState,
                onEditCommentSuccess = { viewModel.resetEditComment() },
                deleteCommentUiState = deleteCommentUiState,
                onDeleteCommentSuccess = { viewModel.resetDeleteComment() },
                createCommentUiState = createCommentUiState,
                onCreateCommentSuccess = { viewModel.resetCreateComment() },
                onUploadAssignmentClick = {
                    backStackEntry.arguments?.getString(GROUP_ID)
                        ?.let { groupId ->
                            backStackEntry.arguments?.getString(POST_ID)?.let { postId ->
                                viewModel.createSubmission(it,
                                    groupId = groupId,
                                    postId = postId)
                            }
                        }
                },
                createSubmissionUiState = createSubmissionUiState,
                onSubmissionSuccess = { viewModel.resetSubmissionState() },
                getProfileUiState = getProfileUiState,
                onGetProfileSuccess = {
                    backStackEntry.arguments?.getString(GROUP_ID)
                        ?.let {
                            backStackEntry.arguments?.getString(POST_ID)
                                ?.let { it1 -> viewModel.getPostComments(it, it1) }
                        }
                    viewModel.setPostStateAsLoaded()
                },
                myProfile = viewModel.profile
            )
        }
        composable(EDIT_POST_SCREEN) { backStackEntry ->

            val viewModel = getViewModel<EditPostViewModel>()
            val title by viewModel.postTitle.collectAsState()
            val description by viewModel.postDescription.collectAsState()
            val editPostUiState by viewModel.editPostUiState.collectAsState()
            val deletePostUiState by viewModel.deletePostUiState.collectAsState()
            LaunchedEffect(null) {
                backStackEntry.arguments?.getString("titleString")
                    ?.let { viewModel.postTitle(it) }
                backStackEntry.arguments?.getString("bodyString")
                    ?.let { viewModel.postDescription(it) }
            }
            EditPostScreen(
                title = title,
                onTitleChange = { viewModel.postTitle(it) },
                description = description,
                onDescriptionChange = { viewModel.postDescription(it) },
                onEditClick = { post ->
                    val postBody = viewModel.editPostBody(post)
                    backStackEntry.arguments?.getString(GROUP_ID)
                        ?.let { groupId ->
                            backStackEntry.arguments?.getString(POST_ID)?.let { postId ->
                                viewModel.editPost(groupId = groupId,
                                    postId = postId,
                                    post = postBody)
                            }
                        }
                },
                onDeleteClick = {
                    backStackEntry.arguments?.getString(GROUP_ID)
                        ?.let { groupId ->
                            backStackEntry.arguments?.getString(POST_ID)
                                ?.let { postId -> viewModel.deletePost(groupId, postId) }
                        }
                },
                deletePostUiState = deletePostUiState,
                onDeleteSuccess = {
                    viewModel.resetDeletePostUiState()
                    navController.navigate(CLASSROOM_USER_SCREEN,
                        bundleOf(GROUP_ID to backStackEntry.arguments?.getString(GROUP_ID)))
                },
                editPostUiState = editPostUiState,
                onEditSuccess = {
                    viewModel.resetEditPostUiState()
                    navController.navigate(CLASSROOM_USER_SCREEN,
                        bundleOf(GROUP_ID to backStackEntry.arguments?.getString(GROUP_ID)))
                }
            )
        }
        composable(SUBMISSIONS_SCREEN) { backStackEntry ->
            val viewModel = getViewModel<SubmissionsViewModel>()
            val getPostUiState by viewModel.getPostUiState.collectAsState()
            val getPostSubmissionsUiState by viewModel.getPostSubmissionsUiState.collectAsState()
            val dialogVisibility by viewModel.dialogVisibility.collectAsState()
            val markValue by viewModel.markValue.collectAsState()
            val submissionIndex by viewModel.submissionIndex.collectAsState()
            val submissions by viewModel.submissionsList.collectAsState()
            val createSubmissionMarkUiState by viewModel.createSubmissionMarkUiState.collectAsState()
            LaunchedEffect(null) {
                backStackEntry.arguments?.getString(GROUP_ID)?.let {
                    backStackEntry.arguments?.getString(POST_ID)
                        ?.let { it1 -> viewModel.getPost(it, it1) }
                }
            }
            SubmissionsScreen(
                getPostUiState = getPostUiState,
                getPostSubmissionsUiState = getPostSubmissionsUiState,
                onPostSuccess = {
                    backStackEntry.arguments?.getString(GROUP_ID)?.let {
                        backStackEntry.arguments?.getString(POST_ID)
                            ?.let { it1 -> viewModel.getPostSubmissions(it, it1) }
                    }
                    viewModel.setPostStateAsLoaded()
                },
                postResponseData = viewModel.postData,
                dialogVisibility = dialogVisibility,
                setDialogVisibility = { viewModel.dialogVisibility(it) },
                markValue = markValue,
                onMarkChange = { viewModel.markValue(it) },
                onDeleteClick = { },
                onSaveClick = { pair ->
                    if (submissions[submissionIndex].marks.isEmpty()) {
                        backStackEntry.arguments?.getString(GROUP_ID)?.let {
                            backStackEntry.arguments?.getString(POST_ID)
                                ?.let { it1 ->
                                    viewModel.createSubmissionMark(it,
                                        it1,
                                        pair.first,
                                        MarkRequestData(pair.second.toFloat()))
                                }
                        }
                    }
                },
                submissions = submissions,
                submissionIndex = submissionIndex,
                setSubmissionIndex = { viewModel.setSubmissionIndex(it) },
                createSubmissionMarkUiState = createSubmissionMarkUiState,
                onSaveEditClick = { studentId, markId, mark ->
                    backStackEntry.arguments?.getString(GROUP_ID)?.let {
                        viewModel.editMark(it, studentId, markId, mark)
                    }
                }
            )
        }
        composable(CREATE_PROFILE) {
            val viewModel = getViewModel<CreateProfileViewModel>()
            val name by viewModel.name.collectAsState()
            val email by viewModel.email.collectAsState()
            val role by viewModel.role.collectAsState()
            val grade by viewModel.classString.collectAsState()
            val createProfileUiState by viewModel.createProfileStateFlow.collectAsState()
            CreateProfileScreen(
                name = name,
                setName = { viewModel.name(it) },
                email = email,
                setEmail = { viewModel.email(it) },
                role = role,
                setRole = { viewModel.role(it) },
                grade = grade,
                setGrade = { viewModel.changeClass(it) },
                onCreateClick = {
                    viewModel.createProfile(
                        ProfileData(
                            fullName = name,
                            email = email,
                            role = role,
                            className = grade.ifEmpty { null }
                        )
                    )
                },
                onImageUpload = { viewModel.imageUpload(it) },
                onAddPhotoClick = { },
                createProfileUiState = createProfileUiState,
                onCreateSuccess = {
                    navController.navigateUp()
                }
            )
        }
    }
}
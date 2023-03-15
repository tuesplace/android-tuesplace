package com.mobile.tuesplace

import com.google.gson.Gson
import com.mobile.tuesplace.services.*
import com.mobile.tuesplace.ui.activities.*
import com.mobile.tuesplace.ui.chats.ChatroomViewModel
import com.mobile.tuesplace.ui.chats.ChatsViewModel
import com.mobile.tuesplace.ui.classes.ClassesViewModel
import com.mobile.tuesplace.ui.classroom.ClassroomUserViewModel
import com.mobile.tuesplace.ui.videoroom.VideoroomViewModel
import com.mobile.tuesplace.ui.groups.AllGroupsViewModel
import com.mobile.tuesplace.ui.groups.CreateGroupViewModel
import com.mobile.tuesplace.ui.groups.EditGroupViewModel
import com.mobile.tuesplace.ui.login.LoginViewModel
import com.mobile.tuesplace.ui.post.CreatePostViewModel
import com.mobile.tuesplace.ui.post.EditPostViewModel
import com.mobile.tuesplace.ui.post.PostViewModel
import com.mobile.tuesplace.ui.profile.EditProfileViewModel
import com.mobile.tuesplace.ui.profile.ProfileViewModel
import com.mobile.tuesplace.ui.settings.SettingsViewModel
import com.mobile.tuesplace.ui.students.AllStudentsViewModel
import com.mobile.tuesplace.ui.submissions.SubmissionsViewModel
import com.mobile.tuesplace.ui.welcome.WelcomeAdminViewModel
import com.mobile.tuesplace.ui.welcome.WelcomeViewModel
import com.mobile.tuesplace.usecase.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val TuesplaceModules = module {

    viewModel { LoginViewModel(get(), get(), androidContext()) }
    viewModel { WelcomeViewModel(get()) }
    viewModel { CreateGroupViewModel(get(), get()) }
    viewModel { WelcomeAdminViewModel() }
    viewModel { EditGroupViewModel(get(), get()) }
    viewModel { AllGroupsViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { EditProfileViewModel(get(), get(), get()) }
    viewModel { VideoroomViewModel(get()) }
    viewModel { ClassroomUserViewModel(get(), get()) }
    viewModel { ClassesViewModel(get()) }
    viewModel { ChatsViewModel(get()) }
    viewModel { ChatroomViewModel(get()) }
    viewModel { AllStudentsViewModel(get()) }
    viewModel { MyActivitiesViewModel(get()) }
    viewModel { ActivitiesStudentsViewModel(get()) }
    viewModel { ActivitiesTeachersViewModel(get()) }
    viewModel { ActivitiesTeacherViewModel(get(), get()) }
    viewModel { AllMyActivitiesViewModel(get()) }
    viewModel { UploadActivityViewModel(get(), get()) }
    viewModel { CreatePostViewModel(get()) }
    viewModel { PostViewModel(get(), get(), get(), get(), get(), get(), get()) }
    viewModel { EditPostViewModel(get(), get()) }
    viewModel { SubmissionsViewModel(get(), get(), get()) }
    viewModel { SettingsViewModel(androidContext(), get()) }

    single<ApiServices> { get<Retrofit>().create(ApiServices::class.java) }

    factory { SignInUseCase(get()) }
    factory { CreateGroupUseCase(get()) }
    factory { GetGroupsUseCase(get()) }
    factory { GetProfileUseCase(get()) }
    factory { GetGroupUseCase(get()) }
    factory { DeleteGroupUseCase(get()) }
    factory { EditProfileUseCase(get()) }
    factory { GetMyGroupsUseCase(get()) }
    factory { GetProfileByIdUseCase(get()) }
    factory { GetAllProfilesUseCase(get()) }
    factory { GetActivitiesUseCase(get()) }
    factory { GetMyActivitiesUseCase(get()) }
    factory { SpecificationUseCase(get()) }
    factory { EditSpecificationAssetsUseCase(get()) }
    factory { CreatePostUseCase(get()) }
    factory { GetPostsUseCase(get()) }
    factory { CreateCommentUseCase(get()) }
    factory { GetPostUseCase(get()) }
    factory { GetPostCommentsUseCase(get()) }
    factory { EditPostUseCase(get()) }
    factory { DeletePostUseCase(get()) }
    factory { EditCommentUseCase(get()) }
    factory { DeleteCommentUseCase(get()) }
    factory { GetPostSubmissionsUseCase(get()) }
    factory { PutMyProfileAssetsUseCase(get()) }
    factory { CreateSubmissionMarkUseCase(get()) }
    factory { CreateSubmissionUseCase(get()) }

    factory<GroupService> { GroupServiceImpl(retrofit = get()) }
    factory<AuthService> { AuthServiceImpl(get()) }
    factory<ProfileService> { ProfileServiceImpl(get()) }
    factory<CommentService> { CommentServiceImpl(get()) }
    factory<MarkService> { MarkServiceImpl(get()) }
    factory<PostService> { PostServiceImpl(get()) }
    factory<ActivitiesService> { ActivitiesImpl(get()) }
    factory<SpecificationService> { SpecificationImpl(get()) }
    factory<SubmissionsService> { SubmissionsServiceImpl(get()) }

    factory { TuesAuthenticator() }
    factory { TuesInterceptor(androidContext()) }
    single {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        OkHttpClient().newBuilder()
           // .authenticator(get<TuesAuthenticator>())
            .addInterceptor(get<TuesInterceptor>())
            .addInterceptor(interceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }
    single {
        Gson()
    }
    single {
        val okHttpClient = get<OkHttpClient>()

        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }
//    single<>
}
package com.mobile.tuesplace.services

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.mobile.tuesplace.*
import com.mobile.tuesplace.data.*
import okhttp3.MultipartBody

import retrofit2.Call
import retrofit2.http.*

interface ApiServices {

    @POST(SIGN_IN)
    fun signIn(@Body authData: AuthData): Call<BaseResponse<SignInData>>

    @POST(GENERATE_TOKEN_PAIR)
    fun generateTokenPair(@Body refreshToken: String): Call<BaseResponse<SignInData>>

    @POST(CREATE_GROUP)
    fun createGroup(@Body createGroupData: GroupData): Call<BaseResponse<GroupData>>

    @GET(CREATE_GROUP)
    fun getGroups(): Call<BaseResponse<List<GroupResponseData>>>

    @GET(GET_GROUP)
    fun getGroup(@Path("groupId") groupId: String): Call<BaseResponse<GroupData>>

    @GET(GET_MY_GROUPS)
    fun getMyGroups(): Call<BaseResponse<List<GroupResponseData>>>

    @PUT(GET_GROUP)
    fun editGroup(@Path("groupId") groupId: String, groupData: EditGroupData): Call<BaseResponse<Unit>>

    @DELETE(GET_GROUP)
    fun deleteGroup(@Path("groupId") groupId: String): Call<BaseResponse<Unit>>

    @GET(PROFILE_ME)
    fun getProfile(): Call<BaseResponse<ProfileResponseData>>

    @GET(ALL_PROFILES)
    fun getAllProfiles(): Call<BaseResponse<List<ProfileResponseData>>>

    @POST(ALL_PROFILES)
    fun createProfile(@Body profileData: ProfileData): Call<BaseResponse<Unit>>

    @GET(PROFILE)
    fun getProfiles(@Path("profileId") profileId: String): Call<BaseResponse<ProfileResponseData>>

    @PUT(PROFILE)
    fun putProfile(@Path("profileId") profileId: String, @Body editProfileData: EditProfileData): Call<BaseResponse<Unit>>

    @Multipart
    @PUT(PROFILE_ASSETS)
    fun putProfileAssets(@Path("profileId") profileId: String, @Part profilePic: MultipartBody.Part): Call<BaseResponse<Unit>>

    @PUT(PROFILE_ME)
    fun editProfile(@Body editProfileData: EditProfileData): Call<BaseResponse<Unit>>

    @DELETE(PROFILE)
    fun deleteProfile(@Path("profileId") profileId: String): Call<BaseResponse<Unit>>

    @Multipart
    @PUT(MY_PROFILE_ASSETS)
    fun putMyProfileAssets(@Part profilePic: MultipartBody.Part): Call<Unit>


    @GET(GET_POSTS)
    fun getPosts(@Path("groupId") groupId: String): Call<BaseResponse<List<PostResponseData>>>

    @POST(GET_POSTS)
    fun createPost(@Path("groupId") groupId: String, @Body post: PostRequestData): Call<BaseResponse<PostRequestData>>

    @PUT(EDIT_POST)
    fun editPost(@Path("groupId") groupId: String, @Path("postId") postId: String, @Body post: PostRequestData): Call<BaseResponse<Unit>>

    @GET(EDIT_POST)
    fun getPost(@Path("groupId") groupId: String, @Path("postId") postId: String): Call<BaseResponse<PostResponseData>>

    @DELETE(EDIT_POST)
    fun deletePost(@Path("groupId") groupId: String, @Path("postId") postId: String): Call<BaseResponse<Unit>>

    @GET(GET_MARKS)
    fun getGroupMarks(@Path("groupId") groupId: String): Call<BaseResponse<List<SubmissionMarkData>>>

    @GET(STUDENT_MARKS)
    fun getStudentMarks(@Path("groupId") groupId: String, @Path("studentId") studentId: String): Call<BaseResponse<List<SubmissionMarkData>>>

    @POST(STUDENT_MARKS)
    fun addStudentMark(@Path("groupId") groupId: String, @Path("studentId") studentId: String, @Body mark: Double): Call<BaseResponse<SubmissionMarkData>>

    @PUT(STUDENT_MARK)
    fun editStudentMark(@Path("groupId") groupId: String, @Path("studentId") studentId: String, @Path("markId") markId: String, @Body mark: Double): Call<BaseResponse<SubmissionMarkData>>

    @DELETE(STUDENT_MARK)
    fun deleteStudentMark(@Path("groupId") groupId: String, @Path("studentId") studentId: String, @Path("markId") markId: String): Call<BaseResponse<Unit>>

    @GET(POST_COMMENTS)
    fun getPostComments(@Path("groupId") groupId: String, @Path("postId") postId: String): Call<BaseResponse<SnapshotStateList<CommentData>>>

    @POST(POST_COMMENTS)
    fun addPostComment(@Path("groupId") groupId: String, @Path("postId") postId: String, @Body comment: CommentRequestData): Call<BaseResponse<Unit>>

    @PUT(POST_COMMENT)
    fun editPostComment(@Path("groupId") groupId: String, @Path("postId") postId: String, @Path("commentId") commentId: String, @Body commentBody: CommentRequestData): Call<BaseResponse<Unit>>

    @PATCH(POST_COMMENT)
    fun addCommentReaction(@Path("groupId") groupId: String, @Path("postId") postId: String, @Path("commentId") commentId: String, emoji: String): Call<BaseResponse<Unit>>

    @DELETE(POST_COMMENT)
    fun deletePostComment(@Path("groupId") groupId: String, @Path("postId") postId: String, @Path("commentId") commentId: String): Call<BaseResponse<Unit>>

    @GET(ACTIVITIES)
    fun getActivities(): Call<BaseResponse<List<AgendaResponseData>>>

    @GET(MY_ACTIVITIES)
    fun getMyActivities(): Call<BaseResponse<List<AgendaResponseData>>>

    @GET(SPECIFICATION)
    fun getSpecification(): Call<BaseResponse<ArrayList<Specification>>>

    @Multipart
    @PUT(SPECIFICATION_ASSETS)
    fun editSpecificationAssets(@Path("specificationId") specificationId: String, @Part specification: MultipartBody.Part): Call<Unit>

    @GET(SUBMISSIONS)
    fun getPostSubmissions(@Path("groupId") groupId: String, @Path("postId") postId: String): Call<BaseResponse<SnapshotStateList<SubmissionData>>>

    @Multipart
    @POST(SUBMISSIONS)
    fun createSubmission(@Path("groupId") groupId: String, @Path("postId") postId: String,  @Part assets: MultipartBody.Part): Call<BaseResponse<SubmissionData>>

    @POST(SUBMISSIONS_MARKS)
    fun createSubmissionMark(@Path("groupId") groupId: String, @Path("postId") postId: String, @Path("submissionId") submissionId: String, @Body markRequestData: MarkRequestData): Call<BaseResponse<Unit>>
    //@Query("responseBehavior") responseBehavior: String
}
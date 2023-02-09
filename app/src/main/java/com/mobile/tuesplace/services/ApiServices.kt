package com.mobile.tuesplace.services

import com.mobile.tuesplace.*
import com.mobile.tuesplace.data.*

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

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
    fun editGroup(@Path("groupId") groupId: String, groupData: EditGroupData): Call<BaseResponse<EditGroupData>>

    @DELETE(GET_GROUP)
    fun deleteGroup(@Path("groupId") groupId: String): Call<BaseResponse<Unit>>

    @GET(PROFILE_ME)
    fun getProfile(): Call<BaseResponse<ProfileData>>

    @GET(ALL_PROFILES)
    fun getAllProfiles(): Call<BaseResponse<List<ProfileResponseData>>>

    @GET(PROFILE)
    fun getProfiles(@Path("profileId") profileId: String): Call<BaseResponse<ProfileData>>

    @PUT(PROFILE)
    fun editProfile(@Path("profileId") profileId: String, editProfileData: EditProfileData): Call<BaseResponse<Unit>>

    @DELETE(PROFILE)
    fun deleteProfile(@Path("profileId") profileId: String): Call<BaseResponse<Unit>>

    @GET(GET_POSTS)
    fun getPosts(@Path("groupId") groupId: String): Call<BaseResponse<List<PostData>>>

    @POST(GET_POSTS)
    fun createPost(@Path("groupId") groupId: String, @Body post: String): Call<BaseResponse<PostData>>

    @PUT(EDIT_POST)
    fun editPost(@Path("groupId") groupId: String, @Path("postId") postId: String, @Body post: String): Call<BaseResponse<PostData>>

    @DELETE(EDIT_POST)
    fun deletePost(@Path("groupId") groupId: String, @Path("postId") postId: String): Call<BaseResponse<Unit>>

    @GET(GET_MARKS)
    fun getGroupMarks(@Path("groupId") groupId: String): Call<BaseResponse<List<MarkData>>>

    @GET(STUDENT_MARKS)
    fun getStudentMarks(@Path("groupId") groupId: String, @Path("studentId") studentId: String): Call<BaseResponse<List<MarkData>>>

    @POST(STUDENT_MARKS)
    fun addStudentMark(@Path("groupId") groupId: String, @Path("studentId") studentId: String, @Body mark: Double): Call<BaseResponse<MarkData>>

    @PUT(STUDENT_MARK)
    fun editStudentMark(@Path("groupId") groupId: String, @Path("studentId") studentId: String, @Path("markId") markId: String, @Body mark: Double): Call<BaseResponse<MarkData>>

    @DELETE(STUDENT_MARK)
    fun deleteStudentMark(@Path("groupId") groupId: String, @Path("studentId") studentId: String, @Path("markId") markId: String): Call<BaseResponse<Unit>>

    @GET(POST_COMMENTS)
    fun getPostComments(@Path("groupId") groupId: String, @Path("postId") postId: String): Call<BaseResponse<List<CommentData>>>

    @POST(POST_COMMENTS)
    fun addPostComment(@Path("groupId") groupId: String, @Path("postId") postId: String, @Body commentBody: String): Call<BaseResponse<CommentData>>

    @PUT(POST_COMMENT)
    fun editPostComment(@Path("groupId") groupId: String, @Path("postId") postId: String, @Path("commentId") commentId: String, @Body commentBody: String): Call<BaseResponse<CommentData>>

    @PATCH(POST_COMMENT)
    fun addCommentReaction(@Path("groupId") groupId: String, @Path("postId") postId: String, @Path("commentId") commentId: String, emoji: String): Call<BaseResponse<Unit>>

    @DELETE(POST_COMMENT)
    fun deletePostComment(@Path("groupId") groupId: String, @Path("postId") postId: String, @Path("commentId") commentId: String): Call<BaseResponse<Unit>>

}
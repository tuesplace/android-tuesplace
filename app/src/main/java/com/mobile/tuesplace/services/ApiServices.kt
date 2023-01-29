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
    fun createGroup(@Header("Authorization") token: String, @Body createGroupData: GroupData): Call<BaseResponse<GroupData>>

    @GET(CREATE_GROUP)
    fun getGroups(@Header("Authorization") token: String): Call<BaseResponse<List<GroupResponseData>>>

    @GET(GET_GROUP)
    fun getGroup(@Header("Authorization") token: String, @Path("groupId") groupId: String): Call<BaseResponse<GroupData>>

    @PUT(GET_GROUP)
    fun editGroup(@Header("Authorization") token: String, @Path("groupId") groupId: String, groupData: EditGroupData): Call<BaseResponse<EditGroupData>>

    @DELETE(GET_GROUP)
    fun deleteGroup(@Header("Authorization") token: String, @Path("groupId") groupId: String): Call<BaseResponse<Unit>>

    @GET(PROFILE_ME)
    fun getProfile(@Header("Authorization") token: String): Call<BaseResponse<ProfileData>>

    @GET(PROFILE)
    fun getProfiles(@Header("Authorization") token: String): Call<BaseResponse<List<ProfileData>>>

    @PUT(PROFILE)
    fun editProfile(@Header("Authorization") token: String, @Path("profileId") profileId: String, editProfileData: EditProfileData): Call<BaseResponse<Unit>>

    @DELETE(PROFILE)
    fun deleteProfile(@Header("Authorization") token: String, @Path("profileId") profileId: String): Call<BaseResponse<Unit>>

    @GET(GET_POSTS)
    fun getPosts(@Header("Authorization") token: String, @Path("groupId") groupId: String): Call<BaseResponse<List<PostData>>>

    @POST(GET_POSTS)
    fun createPost(@Header("Authorization") token: String, @Path("groupId") groupId: String, @Body post: String): Call<BaseResponse<PostData>>

    @PUT(EDIT_POST)
    fun editPost(@Header("Authorization") token: String, @Path("groupId") groupId: String, @Path("postId") postId: String, @Body post: String): Call<BaseResponse<PostData>>

    @DELETE(EDIT_POST)
    fun deletePost(@Header("Authorization") token: String, @Path("groupId") groupId: String, @Path("postId") postId: String): Call<BaseResponse<Unit>>

    @GET(GET_MARKS)
    fun getGroupMarks(@Header("Authorization") token: String, @Path("groupId") groupId: String): Call<BaseResponse<List<MarkData>>>

    @GET(STUDENT_MARKS)
    fun getStudentMarks(@Header("Authorization") token: String, @Path("groupId") groupId: String, @Path("studentId") studentId: String): Call<BaseResponse<List<MarkData>>>

    @POST(STUDENT_MARKS)
    fun addStudentMark(@Header("Authorization") token: String, @Path("groupId") groupId: String, @Path("studentId") studentId: String, @Body mark: Double): Call<BaseResponse<MarkData>>

    @PUT(STUDENT_MARK)
    fun editStudentMark(@Header("Authorization") token: String, @Path("groupId") groupId: String, @Path("studentId") studentId: String, @Path("markId") markId: String, @Body mark: Double): Call<BaseResponse<MarkData>>

    @DELETE(STUDENT_MARK)
    fun deleteStudentMark(@Header("Authorization") token: String, @Path("groupId") groupId: String, @Path("studentId") studentId: String, @Path("markId") markId: String): Call<BaseResponse<Unit>>

    @GET(POST_COMMENTS)
    fun getPostComments(@Header("Authorization") token: String, @Path("groupId") groupId: String, @Path("postId") postId: String): Call<BaseResponse<List<CommentData>>>

    @POST(POST_COMMENTS)
    fun addPostComment(@Header("Authorization") token: String, @Path("groupId") groupId: String, @Path("postId") postId: String, @Body commentBody: String): Call<BaseResponse<CommentData>>

    @PUT(POST_COMMENT)
    fun editPostComment(@Header("Authorization") token: String, @Path("groupId") groupId: String, @Path("postId") postId: String, @Path("commentId") commentId: String, @Body commentBody: String): Call<BaseResponse<CommentData>>

    @PATCH(POST_COMMENT)
    fun addCommentReaction(@Header("Authorization") token: String, @Path("groupId") groupId: String, @Path("postId") postId: String, @Path("commentId") commentId: String, emoji: String): Call<BaseResponse<Unit>>

    @DELETE(POST_COMMENT)
    fun deletePostComment(@Header("Authorization") token: String, @Path("groupId") groupId: String, @Path("postId") postId: String, @Path("commentId") commentId: String): Call<BaseResponse<Unit>>

}
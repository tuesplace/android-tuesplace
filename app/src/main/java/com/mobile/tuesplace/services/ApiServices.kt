package com.mobile.tuesplace.services

import com.mobile.tuesplace.*
import com.mobile.tuesplace.data.*

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiServices {

    @POST(SIGN_IN)
    fun signIn(@Body authData: AuthData): Call<SignInResponse>

    @POST(GENERATE_TOKEN_PAIR)
    fun generateTokenPair(@Body refreshToken: String): Call<SignInResponse>

    @POST(CREATE_GROUP)
    fun createGroup(@Header("Authorization") token: String, @Body createGroupData: GroupData): Call<CreateGroupResponse>

    @GET(CREATE_GROUP)
    fun getGroups(@Header("Authorization") token: String): Call<List<GroupData>>

    @GET(GET_GROUP)
    fun getGroup(@Header("Authorization") token: String, @Path("groupId") groupId: String): Call<GroupData>

    @PUT(GET_GROUP)
    fun editGroup(@Header("Authorization") token: String, @Path("groupId") groupId: String, groupData: EditGroupData): Call<EditGroupData>

    @DELETE(GET_GROUP)
    fun deleteGroup(@Header("Authorization") token: String, @Path("groupId") groupId: String): Call<DeleteGroupResponse>

    @GET(PROFILE_ME)
    fun getProfile(@Header("Authorization") token: String): Call<ProfileData>

    @GET(PROFILE)
    fun getProfiles(@Header("Authorization") token: String): Call<List<ProfileData>>

    @PUT(PROFILE)
    fun editProfile(@Header("Authorization") token: String, @Path("profileId") profileId: String, editProfileData: EditProfileData): Call<EditProfileData>

    @DELETE(PROFILE)
    fun deleteProfile(@Header("Authorization") token: String, @Path("profileId") profileId: String): Call<DeleteProfileResponse>

    @GET(GET_POSTS)
    fun getPosts(@Header("Authorization") token: String, @Path("groupId") groupId: String): Call<PostsResponse>

    @POST(GET_POSTS)
    fun createPost(@Header("Authorization") token: String, @Path("groupId") groupId: String): Call<PostResponse>

    @PUT(EDIT_POST)
    fun editPost(@Header("Authorization") token: String, @Path("groupId") groupId: String, @Path("postId") postId: String): Call<PostResponse>

    @DELETE(EDIT_POST)
    fun deletePost(@Header("Authorization") token: String, @Path("groupId") groupId: String, @Path("postId") postId: String): Call<BaseResponse<String>>

    @GET(GET_MARKS)
    fun getGroupMarks(@Header("Authorization") token: String, @Path("groupId") groupId: String): Call<BaseResponse<List<MarkData>>>

    @GET(STUDENT_MARKS)
    fun getStudentMarks(@Header("Authorization") token: String, @Path("groupId") groupId: String, @Path("studentId") studentId: String): Call<BaseResponse<List<MarkData>>>

    @POST(STUDENT_MARKS)
    fun addStudentMark(@Header("Authorization") token: String, @Path("groupId") groupId: String, @Path("studentId") studentId: String): Call<BaseResponse<MarkData>>

    @PUT(STUDENT_MARK)
    fun editStudentMark(@Header("Authorization") token: String, @Path("groupId") groupId: String, @Path("studentId") studentId: String, @Path("markId") markId: String): Call<BaseResponse<MarkData>>

    @DELETE(STUDENT_MARK)
    fun deleteStudentMark(@Header("Authorization") token: String, @Path("groupId") groupId: String, @Path("studentId") studentId: String, @Path("markId") markId: String): Call<DeleteMarkResponse>
}
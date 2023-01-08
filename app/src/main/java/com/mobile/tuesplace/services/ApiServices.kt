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
}
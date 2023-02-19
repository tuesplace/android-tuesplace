package com.mobile.tuesplace.data

import com.google.gson.annotations.SerializedName

data class EditProfileData(
    @SerializedName("fullName")
    var fullName: String?,
    @SerializedName("email")
    var email: String?,
    @SerializedName("password")
    var password: String?,
    @SerializedName("role")
    var role: String?,
    @SerializedName("class")
    var className: String?
)
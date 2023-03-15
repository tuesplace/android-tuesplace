package com.mobile.tuesplace.data

import com.google.gson.annotations.SerializedName

data class ProfileResponseData(
    var _id: String,
    var fullName: String,
    var email: String,
    var role: String,
    @SerializedName("class")
    var className: String?,
    var assets: ProfileAssets?
)
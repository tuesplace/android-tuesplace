package com.mobile.tuesplace.data

import com.google.gson.annotations.SerializedName

data class EditGroupData(
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("type")
    var type: String? = null,
    @SerializedName("classes")
    var classes: ArrayList<String>? = null,
)
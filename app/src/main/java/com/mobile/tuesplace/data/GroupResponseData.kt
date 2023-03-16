package com.mobile.tuesplace.data

data class GroupResponseData(
    var _id: String,
    var name: String,
    var type: String,
    var classes: ArrayList<String>? = null,
    var teachers: ArrayList<ProfileResponseData>?
)

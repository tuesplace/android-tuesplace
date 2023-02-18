package com.mobile.tuesplace.data

data class GroupData(
    var name: String,
    var teachers: ArrayList<ProfileData>? = null,
    var type: String,
    var classes: ArrayList<String>? = null
)



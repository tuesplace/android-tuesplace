package com.mobile.tuesplace.data

data class GroupResponseData(
    var id: String,
    var name: String,
    var type: String,
    var classes: ArrayList<String>? = null
)

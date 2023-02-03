package com.mobile.tuesplace.data

data class GroupData(
    var name: String,
    var teachers: ArrayList<String>? = null,
    var type: String,
    var classes: ArrayList<String>? = null
)

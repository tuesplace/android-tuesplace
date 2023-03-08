package com.mobile.tuesplace.data

data class GroupData(
    var name: String,
    var owners: ArrayList<ResolvedAssociation<ProfileData>>? = null,
    var type: String,
    var classes: ArrayList<String>? = null
)



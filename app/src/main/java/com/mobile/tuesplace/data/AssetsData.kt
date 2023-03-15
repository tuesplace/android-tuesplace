package com.mobile.tuesplace.data

data class AssetData(
    var _id: String,
    var owner: ResolvedAssociation<ProfileData>,
    var key: String,
    var mimetype: String,
    var src: String,
)

data class ProfileAssets(
    var profilePic: ArrayList<ResolvedAssociation<AssetData>>,
)

data class SubmissionAssets(
    var assets: ArrayList<ResolvedAssociation<AssetData>>,
)
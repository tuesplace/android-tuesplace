package com.mobile.tuesplace.data

import kotlinx.serialization.Serializable

@Serializable
data class AssetData(
    var _id: String,
    var owner: ResolvedAssociation<ProfileData>,
    var key: String,
    var mimetype: String,
    var src: String,
)

@Serializable
data class ProfileAssets(
    var profilePic: ArrayList<ResolvedAssociation<AssetData>>,
)

data class SubmissionAssets(
    var assets: ArrayList<ResolvedAssociation<AssetData>>,
)
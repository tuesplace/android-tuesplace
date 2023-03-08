package com.mobile.tuesplace.data

data class PostResponseData(
    var _id: String,
    var owner: ResolvedAssociation<ProfileData>,
    var title: String,
    var body: String,
    var isPrivate: String,
    var associations: GroupAssociations,
    var reactions: ArrayList<ReactionsData>,
    var createdAt: String,
    var updatedAt: String,
)

data class GroupAssociations(
    var group: ShouldResolveData
)
package com.mobile.tuesplace.data

data class CommentData(
    var _id: String,
    var owner: ResolvedAssociation<ProfileData>,
    var body: String,
    var isPrivate: Boolean,
    var associations: GroupAssociations,
    var post: ShouldResolveData,
    var reactions: List<ReactionsData>,
    var createdAt: String,
    var updatedAt: String
)
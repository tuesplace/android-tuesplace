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
    var assignmentInfo: AssignmentInfo
)

data class GroupAssociations(
    var group: ShouldResolveData
)

data class AssignmentInfo(
    var isAssignment: Boolean,
    var deadline: Number
)

data class ReactionsData(
    var authorId: String,
    var emoji: String
)
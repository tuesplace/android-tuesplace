package com.mobile.tuesplace.data

data class SubmissionMarkData(
    var owner: ResolvedAssociation<ProfileResponseData>,
    var mark: Number
)

data class MarkData(
    var owner: ResolvedAssociation<ProfileResponseData>,
    var mark: Number,
    var associations: MarkAssociations,
)

data class MarkAssociations(
    var student: ResolvedAssociation<ProfileResponseData>
)
package com.mobile.tuesplace.data

data class SubmissionMarkData(
    var _id: String,
    var owner: ResolvedAssociation<ProfileResponseData>,
    var mark: Number
)

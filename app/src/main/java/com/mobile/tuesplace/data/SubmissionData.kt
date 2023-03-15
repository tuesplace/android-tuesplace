package com.mobile.tuesplace.data

data class SubmissionData(
    var _id: String,
    var owner: ResolvedAssociation<ProfileData>,
    var assets: SubmissionAssets,
    var associations: SubmissionAssociations,
    var marks: ArrayList<SubmissionMarkData>
)

data class SubmissionAssociations(
    var group: ShouldResolveData,
    var post: ShouldResolveData,
    var student: ShouldResolveData
)

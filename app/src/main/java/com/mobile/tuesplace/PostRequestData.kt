package com.mobile.tuesplace

data class PostRequestData(
    var title: String?,
    var body: String?,
    var assignmentInfo: AssignmentInfo?,
)

data class AssignmentInfo(
    var isAssignment: Boolean,
    var deadline: Number,
)
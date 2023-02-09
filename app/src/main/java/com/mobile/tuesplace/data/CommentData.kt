package com.mobile.tuesplace.data

data class CommentData(
    var authorId: String,
    var body: String,
    var reactions: List<String>,
    var createdAt: String,
    var updatedAt: String,
)
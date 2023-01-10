package com.mobile.tuesplace.data

data class PostData(
    var authorId: String,
    var reactions: List<ReactionsData>? = listOf(),
    var createdAt: String,
    var updatedAt: String,
    var body: String
)

data class ReactionsData(
    var authorId: String,
    var emoji: String
)
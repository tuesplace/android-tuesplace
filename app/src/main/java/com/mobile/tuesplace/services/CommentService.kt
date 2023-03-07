package com.mobile.tuesplace.services

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.mobile.tuesplace.data.CommentData
import com.mobile.tuesplace.data.CommentRequestData

interface CommentService {

    suspend fun getPostComments(commentCallback: CommentCallback<SnapshotStateList<CommentData>>, groupId: String, postId: String)
    suspend fun addPostComment(commentCallback: CommentCallback<Unit>, groupId: String, postId: String, comment: CommentRequestData)
    suspend fun editPostComment(commentCallback: CommentCallback<Unit>, groupId: String, postId: String, commentId: String, comment: CommentRequestData)
    fun addCommentReaction(commentCallback: CommentCallback<Unit>, groupId: String, postId: String, commentId: String, emoji: String)
    suspend fun deletePostComment(commentCallback: CommentCallback<Unit>, groupId: String, postId: String, commentId: String)

    interface CommentCallback<Generic>{
        fun onSuccess(generic: Generic)
        fun onError(error: String)
    }
}
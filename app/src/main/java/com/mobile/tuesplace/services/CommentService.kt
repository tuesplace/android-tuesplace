package com.mobile.tuesplace.services

import com.mobile.tuesplace.data.CommentData
import com.mobile.tuesplace.data.DeleteCommentResponse

interface CommentService {

    fun getPostComments(commentCallback: CommentCallback<List<CommentData>>, groupId: String, postId: String)
    fun addPostComment(commentCallback: CommentCallback<CommentData>, groupId: String, postId: String, comment: String)
    fun editPostComment(commentCallback: CommentCallback<CommentData>, groupId: String, postId: String, commentId: String, comment: String)
    fun addCommentReaction(commentCallback: CommentCallback<DeleteCommentResponse>, groupId: String, postId: String, commentId: String, emoji: String)
    fun deletePostComment(commentCallback: CommentCallback<DeleteCommentResponse>, groupId: String, postId: String, commentId: String)

    interface CommentCallback<Generic>{
        fun onSuccess(generic: Generic)
        fun onError(error: String)
    }
}
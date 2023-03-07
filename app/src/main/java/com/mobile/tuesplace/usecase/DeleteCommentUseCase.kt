package com.mobile.tuesplace.usecase

import com.mobile.tuesplace.services.CommentService

class DeleteCommentUseCase(private val commentService: CommentService) {
    suspend operator fun invoke(commentCallback: CommentService.CommentCallback<Unit>, groupId: String, postId: String, commentId: String){
        commentService.deletePostComment(commentCallback, groupId, postId, commentId)
    }
}
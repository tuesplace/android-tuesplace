package com.mobile.tuesplace.usecase

import com.mobile.tuesplace.data.CommentRequestData
import com.mobile.tuesplace.services.CommentService

class EditCommentUseCase(private val commentService: CommentService) {
    suspend operator fun invoke(commentCallback: CommentService.CommentCallback<Unit>, groupId: String, postId: String, commentId: String, comment: CommentRequestData){
        commentService.editPostComment(commentCallback, groupId, postId, commentId, comment)
    }
}
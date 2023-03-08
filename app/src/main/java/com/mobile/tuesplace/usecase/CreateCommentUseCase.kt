package com.mobile.tuesplace.usecase

import com.mobile.tuesplace.data.CommentRequestData
import com.mobile.tuesplace.services.CommentService

class CreateCommentUseCase(private val commentService: CommentService) {
    suspend operator fun invoke(commentCallback: CommentService.CommentCallback<Unit>, groupId: String, postId: String, comment: CommentRequestData){
        commentService.addPostComment(commentCallback, groupId, postId, comment)
    }
}
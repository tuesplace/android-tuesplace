package com.mobile.tuesplace.usecase

import com.mobile.tuesplace.data.CommentData
import com.mobile.tuesplace.services.CommentService

class GetPostCommentsUseCase(private val commentService: CommentService) {
    suspend operator fun invoke(commentCallback: CommentService.CommentCallback<List<CommentData>>, groupId: String, postId: String){
        commentService.getPostComments(commentCallback, groupId, postId)
    }
}
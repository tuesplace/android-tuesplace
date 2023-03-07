package com.mobile.tuesplace.usecase

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.mobile.tuesplace.data.CommentData
import com.mobile.tuesplace.services.CommentService

class GetPostCommentsUseCase(private val commentService: CommentService) {
    suspend operator fun invoke(commentCallback: CommentService.CommentCallback<SnapshotStateList<CommentData>>, groupId: String, postId: String){
        commentService.getPostComments(commentCallback, groupId, postId)
    }
}
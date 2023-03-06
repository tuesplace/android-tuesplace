package com.mobile.tuesplace.usecase

import com.mobile.tuesplace.PostRequestData
import com.mobile.tuesplace.services.PostService

class EditPostUseCase(private val postService: PostService) {
    suspend operator fun invoke(postCallback: PostService.PostCallback<Unit>, postId: String, groupId: String, post: PostRequestData){
        postService.editPost(postCallback, postId, groupId, post)
    }
}
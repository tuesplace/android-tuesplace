package com.mobile.tuesplace.usecase

import com.mobile.tuesplace.services.PostService

class DeletePostUseCase(private val postService: PostService) {
    suspend operator fun invoke(postCallback: PostService.PostCallback<Unit>, postId: String, groupId: String){
        postService.deletePost(postCallback, postId, groupId)
    }
}
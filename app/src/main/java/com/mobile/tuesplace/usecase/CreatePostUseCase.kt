package com.mobile.tuesplace.usecase

import com.mobile.tuesplace.PostRequestData
import com.mobile.tuesplace.services.PostService

class CreatePostUseCase(private val postService: PostService) {
    suspend operator fun invoke(postCallback: PostService.PostCallback<PostRequestData>, groupId: String, post: PostRequestData) {
        postService.createPost(postCallback, groupId, post)
    }
}
package com.mobile.tuesplace.usecase

import com.mobile.tuesplace.data.PostResponseData
import com.mobile.tuesplace.services.PostService

class GetPostUseCase(private val postService: PostService) {
    suspend operator fun invoke(postCallback: PostService.PostCallback<PostResponseData>, groupId: String, postId: String){
        postService.getPost(postCallback, groupId, postId)
    }
}
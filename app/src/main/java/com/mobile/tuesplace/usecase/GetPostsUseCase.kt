package com.mobile.tuesplace.usecase

import com.mobile.tuesplace.data.PostResponseData
import com.mobile.tuesplace.services.PostService

class GetPostsUseCase(private val postService: PostService) {
    suspend operator fun invoke(groupId: String, postCallback: PostService.PostCallback<List<PostResponseData>>){
        postService.getPosts(groupId = groupId, postCallback = postCallback)
    }
}
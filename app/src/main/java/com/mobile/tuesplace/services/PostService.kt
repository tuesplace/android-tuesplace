package com.mobile.tuesplace.services

import com.mobile.tuesplace.data.BaseResponse
import com.mobile.tuesplace.data.PostData
import com.mobile.tuesplace.data.PostResponse
import com.mobile.tuesplace.data.PostsResponse

interface PostService {

    fun getPosts(postCallback: PostCallback<PostsResponse>, groupId: String)
    fun createPost(postCallback: PostCallback<PostResponse>, postData: PostData, groupId: String)
    fun editPost(postCallback: PostCallback<PostResponse>, postId: String, groupId: String)
    fun deletePost(postCallback: PostCallback<BaseResponse<String>>, postId: String, groupId: String)

    interface PostCallback<Generic>{
        fun onSuccess(generic: Generic)
        fun onError(error: String)
    }
}
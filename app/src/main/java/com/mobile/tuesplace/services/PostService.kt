package com.mobile.tuesplace.services

import com.mobile.tuesplace.PostRequestData
import com.mobile.tuesplace.data.PostData
import com.mobile.tuesplace.data.PostResponseData

interface PostService {

    suspend fun getPosts(postCallback: PostCallback<List<PostResponseData>>, groupId: String)
    suspend fun createPost(postCallback: PostCallback<PostRequestData>, groupId: String, post: PostRequestData)
    suspend fun getPost(postCallback: PostCallback<PostResponseData>, groupId: String, postId: String)
    fun editPost(postCallback: PostCallback<PostData>, postId: String, groupId: String, post: String)
    fun deletePost(postCallback: PostCallback<Unit>, postId: String, groupId: String)

    interface PostCallback<Generic>{
        fun onSuccess(generic: Generic)
        fun onError(error: String)
    }
}
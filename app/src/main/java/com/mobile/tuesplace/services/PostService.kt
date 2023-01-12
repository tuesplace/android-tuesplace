package com.mobile.tuesplace.services

import com.mobile.tuesplace.data.PostData

interface PostService {

    fun getPosts(postCallback: PostCallback<List<PostData>>, groupId: String)
    fun createPost(postCallback: PostCallback<PostData>, groupId: String, post: String)
    fun editPost(postCallback: PostCallback<PostData>, postId: String, groupId: String, post: String)
    fun deletePost(postCallback: PostCallback<Unit>, postId: String, groupId: String)

    interface PostCallback<Generic>{
        fun onSuccess(generic: Generic)
        fun onError(error: String)
    }
}
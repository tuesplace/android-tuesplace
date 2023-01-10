package com.mobile.tuesplace.services

import com.mobile.tuesplace.ACCESS_TOKEN
import com.mobile.tuesplace.data.BaseResponse
import com.mobile.tuesplace.data.PostData
import com.mobile.tuesplace.data.PostResponse
import com.mobile.tuesplace.data.PostsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostServiceImpl : PostService {

    private val retrofit = RetrofitHelper.getInstance().create(ApiServices::class.java)

    override fun getPosts(postCallback: PostService.PostCallback<PostsResponse>, groupId: String) {
        retrofit.getPosts("Bearer $ACCESS_TOKEN", groupId = groupId).enqueue(
            object : Callback<PostsResponse> {
                override fun onResponse(
                    call: Call<PostsResponse>,
                    response: Response<PostsResponse>,
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { postCallback.onSuccess(it) }
                    } else {
                        postCallback.onError(response.message())
                    }
                }

                override fun onFailure(call: Call<PostsResponse>, t: Throwable) {
                    t.localizedMessage?.let { postCallback.onError(it) }
                }

            }
        )
    }

    override fun createPost(
        postCallback: PostService.PostCallback<PostResponse>,
        postData: PostData,
        groupId: String,
    ) {
        retrofit.createPost("Bearer $ACCESS_TOKEN", groupId = groupId).enqueue(
            object : Callback<PostResponse> {
                override fun onResponse(
                    call: Call<PostResponse>,
                    response: Response<PostResponse>,
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { postCallback.onSuccess(it) }
                    } else {
                        postCallback.onError(response.message())

                    }
                }

                override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                    t.localizedMessage?.let { postCallback.onError(it) }
                }
            })
    }

    override fun editPost(
        postCallback: PostService.PostCallback<PostResponse>,
        postId: String,
        groupId: String,
    ) {
        retrofit.editPost("Bearer $ACCESS_TOKEN", groupId = groupId, postId = postId).enqueue(
            object : Callback<PostResponse> {
                override fun onResponse(
                    call: Call<PostResponse>,
                    response: Response<PostResponse>,
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { postCallback.onSuccess(it) }
                    } else {
                        postCallback.onError(response.message())
                    }
                }

                override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                    t.localizedMessage?.let { postCallback.onError(it) }
                }

            }
        )
    }

    override fun deletePost(
        postCallback: PostService.PostCallback<BaseResponse<String>>,
        postId: String,
        groupId: String,
    ) {
        retrofit.deletePost("Bearer $ACCESS_TOKEN", groupId = groupId, postId = postId).enqueue(
            object : Callback<BaseResponse<String>> {

                override fun onResponse(
                    call: Call<BaseResponse<String>>,
                    response: Response<BaseResponse<String>>,
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            postCallback.onSuccess(it)
                        }
                    } else {
                        postCallback.onError(response.message())
                    }
                }

                override fun onFailure(call: Call<BaseResponse<String>>, t: Throwable) {
                    t.localizedMessage?.let { postCallback.onError(it) }
                }

            }
        )
    }
}
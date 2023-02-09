package com.mobile.tuesplace.services

import com.mobile.tuesplace.data.BaseResponse
import com.mobile.tuesplace.data.PostData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostServiceImpl(private val retrofit: ApiServices) : PostService {

    override fun getPosts(
        postCallback: PostService.PostCallback<List<PostData>>,
        groupId: String,
    ) {
        retrofit.getPosts(groupId = groupId).enqueue(
            object : Callback<BaseResponse<List<PostData>>> {
                override fun onResponse(
                    call: Call<BaseResponse<List<PostData>>>,
                    response: Response<BaseResponse<List<PostData>>>,
                ) {
                    if (response.isSuccessful) {
                        response.body()?.response?.let { postCallback.onSuccess(it) }
                    } else {
                        postCallback.onError(response.message())
                    }
                }

                override fun onFailure(call: Call<BaseResponse<List<PostData>>>, t: Throwable) {
                    t.localizedMessage?.let { postCallback.onError(it) }
                }

            }
        )
    }

    override fun createPost(
        postCallback: PostService.PostCallback<PostData>,
        groupId: String,
        post: String,
    ) {
        retrofit.createPost(groupId = groupId, post = post).enqueue(
            object : Callback<BaseResponse<PostData>> {
                override fun onResponse(
                    call: Call<BaseResponse<PostData>>,
                    response: Response<BaseResponse<PostData>>,
                ) {
                    if (response.isSuccessful) {
                        response.body()?.response?.let { postCallback.onSuccess(it) }
                    } else {
                        postCallback.onError(response.message())
                    }
                }

                override fun onFailure(call: Call<BaseResponse<PostData>>, t: Throwable) {
                    t.localizedMessage?.let { postCallback.onError(it) }
                }

            }
        )
    }

    override fun editPost(
        postCallback: PostService.PostCallback<PostData>,
        postId: String,
        groupId: String,
        post: String,
    ) {
        retrofit.editPost(groupId = groupId, postId = postId, post = post).enqueue(
            object : Callback<BaseResponse<PostData>> {
                override fun onResponse(
                    call: Call<BaseResponse<PostData>>,
                    response: Response<BaseResponse<PostData>>,
                ) {
                    if (response.isSuccessful) {
                        response.body()?.response?.let { postCallback.onSuccess(it) }
                    } else {
                        postCallback.onError(response.message())
                    }
                }

                override fun onFailure(call: Call<BaseResponse<PostData>>, t: Throwable) {
                    t.localizedMessage?.let { postCallback.onError(it) }
                }

            }
        )
    }

    override fun deletePost(
        postCallback: PostService.PostCallback<Unit>,
        postId: String,
        groupId: String,
    ) {
        retrofit.deletePost(groupId = groupId, postId = postId).enqueue(
            object : Callback<BaseResponse<Unit>> {
                override fun onResponse(call: Call<BaseResponse<Unit>>, response: Response<BaseResponse<Unit>>) {
                    if (response.isSuccessful) {
                        response.body()?.response?.let { postCallback.onSuccess(it) }
                    } else {
                        postCallback.onError(response.message())
                    }
                }

                override fun onFailure(call: Call<BaseResponse<Unit>>, t: Throwable) {
                    t.localizedMessage?.let { postCallback.onError(it) }
                }

            }
        )
    }
}
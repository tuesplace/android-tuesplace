package com.mobile.tuesplace.services

import com.mobile.tuesplace.PostRequestData
import com.mobile.tuesplace.data.BaseResponse
import com.mobile.tuesplace.data.PostResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostServiceImpl(private val retrofit: ApiServices) : PostService {

    override suspend fun getPosts(
        postCallback: PostService.PostCallback<List<PostResponseData>>,
        groupId: String,
    ) {
        retrofit.getPosts(groupId = groupId).enqueue(
            object : Callback<BaseResponse<List<PostResponseData>>> {
                override fun onResponse(
                    call: Call<BaseResponse<List<PostResponseData>>>,
                    response: Response<BaseResponse<List<PostResponseData>>>,
                ) {
                    if (response.isSuccessful) {
                        response.body()?.response?.let { postCallback.onSuccess(it) }
                    } else {
                        postCallback.onError(response.message())
                    }
                }

                override fun onFailure(
                    call: Call<BaseResponse<List<PostResponseData>>>,
                    t: Throwable,
                ) {
                    t.localizedMessage?.let { postCallback.onError(it) }
                }

            }
        )
    }

    override suspend fun createPost(
        postCallback: PostService.PostCallback<Unit>,
        groupId: String,
        post: PostRequestData,
    ) {
        retrofit.createPost(groupId = groupId, post = post).enqueue(
            object : Callback<BaseResponse<PostRequestData>> {
                override fun onResponse(
                    call: Call<BaseResponse<PostRequestData>>,
                    response: Response<BaseResponse<PostRequestData>>,
                ) {
                    if (response.isSuccessful) {
                        postCallback.onSuccess(Unit)
                    } else {
                        postCallback.onError(response.message())
                    }
                }

                override fun onFailure(call: Call<BaseResponse<PostRequestData>>, t: Throwable) {
                    t.localizedMessage?.let { postCallback.onError(it) }
                }
            }
        )
    }

    override suspend fun getPost(
        postCallback: PostService.PostCallback<PostResponseData>,
        groupId: String,
        postId: String,
    ) {
        retrofit.getPost(groupId, postId).enqueue(
            object : Callback<BaseResponse<PostResponseData>> {
                override fun onResponse(
                    call: Call<BaseResponse<PostResponseData>>,
                    response: Response<BaseResponse<PostResponseData>>,
                ) {
                    if (response.isSuccessful) {
                        response.body()?.response?.let { postCallback.onSuccess(it) }
                    } else {
                        postCallback.onError(response.message())
                    }
                }

                override fun onFailure(call: Call<BaseResponse<PostResponseData>>, t: Throwable) {
                    t.localizedMessage?.let { postCallback.onError(it) }
                }


            })
    }

    override suspend fun editPost(
        postCallback: PostService.PostCallback<Unit>,
        postId: String,
        groupId: String,
        post: PostRequestData,
    ) {
        retrofit.editPost(groupId = groupId, postId = postId, post = post).enqueue(
            object : Callback<BaseResponse<Unit>> {
                override fun onResponse(
                    call: Call<BaseResponse<Unit>>,
                    response: Response<BaseResponse<Unit>>,
                ) {
                    if (response.isSuccessful) {
                        postCallback.onSuccess(Unit)
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

    override suspend fun deletePost(
        postCallback: PostService.PostCallback<Unit>,
        postId: String,
        groupId: String,
    ) {
        retrofit.deletePost(groupId = groupId, postId = postId).enqueue(
            object : Callback<BaseResponse<Unit>> {
                override fun onResponse(
                    call: Call<BaseResponse<Unit>>,
                    response: Response<BaseResponse<Unit>>,
                ) {
                    if (response.isSuccessful) {
                        postCallback.onSuccess(Unit)
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
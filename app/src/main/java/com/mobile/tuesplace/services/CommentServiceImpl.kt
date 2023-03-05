package com.mobile.tuesplace.services

import com.mobile.tuesplace.data.BaseResponse
import com.mobile.tuesplace.data.CommentData
import com.mobile.tuesplace.data.CommentRequestData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentServiceImpl(private val retrofit: ApiServices) : CommentService {

    override suspend fun getPostComments(
        commentCallback: CommentService.CommentCallback<List<CommentData>>,
        groupId: String,
        postId: String,
    ) {
        retrofit.getPostComments(groupId = groupId, postId = postId)
            .enqueue(
                object : Callback<BaseResponse<List<CommentData>>> {
                    override fun onResponse(
                        call: Call<BaseResponse<List<CommentData>>>,
                        response: Response<BaseResponse<List<CommentData>>>,
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.response?.let { commentCallback.onSuccess(it) }
                        } else {
                            commentCallback.onError(response.message())
                        }
                    }

                    override fun onFailure(
                        call: Call<BaseResponse<List<CommentData>>>,
                        t: Throwable,
                    ) {
                        t.localizedMessage?.let { commentCallback.onError(it) }
                    }

                }
            )
    }

    override suspend fun addPostComment(
        commentCallback: CommentService.CommentCallback<CommentRequestData>,
        groupId: String,
        postId: String,
        comment: CommentRequestData,
    ) {
        retrofit.addPostComment(
            groupId = groupId,
            postId = postId,
            comment = comment).enqueue(
            object : Callback<BaseResponse<CommentRequestData>> {
                override fun onResponse(
                    call: Call<BaseResponse<CommentRequestData>>,
                    response: Response<BaseResponse<CommentRequestData>>,
                ) {
                    if (response.isSuccessful) {
                        response.body()?.response?.let { commentCallback.onSuccess(it) }
                    } else {
                        commentCallback.onError(response.message())
                    }
                }

                override fun onFailure(call: Call<BaseResponse<CommentRequestData>>, t: Throwable) {
                    t.localizedMessage?.let { commentCallback.onError(it) }
                }

            }
        )
    }

    override fun editPostComment(
        commentCallback: CommentService.CommentCallback<CommentData>,
        groupId: String,
        postId: String,
        commentId: String,
        comment: String,
    ) {
        retrofit.editPostComment(
            groupId = groupId,
            postId = postId,
            commentId = commentId,
            commentBody = comment).enqueue(
            object : Callback<BaseResponse<CommentData>> {
                override fun onResponse(
                    call: Call<BaseResponse<CommentData>>,
                    response: Response<BaseResponse<CommentData>>,
                ) {
                    if (response.isSuccessful) {
                        response.body()?.response?.let { commentCallback.onSuccess(it) }
                    } else {
                        commentCallback.onError(response.message())
                    }
                }

                override fun onFailure(call: Call<BaseResponse<CommentData>>, t: Throwable) {
                    t.localizedMessage?.let { commentCallback.onError(it) }
                }

            }
        )
    }

    override fun addCommentReaction(
        commentCallback: CommentService.CommentCallback<Unit>,
        groupId: String,
        postId: String,
        commentId: String,
        emoji: String,
    ) {
        retrofit.addCommentReaction(
            groupId = groupId,
            postId = postId,
            commentId = commentId,
            emoji = emoji).enqueue(
            object : Callback<BaseResponse<Unit>> {
                override fun onResponse(
                    call: Call<BaseResponse<Unit>>,
                    response: Response<BaseResponse<Unit>>,
                ) {
                    if (response.isSuccessful) {
                        response.body()?.response?.let { commentCallback.onSuccess(it) }
                    } else {
                        commentCallback.onError(response.message())
                    }
                }

                override fun onFailure(call: Call<BaseResponse<Unit>>, t: Throwable) {
                    t.localizedMessage?.let { commentCallback.onError(it) }
                }
            }
        )
    }

    override fun deletePostComment(
        commentCallback: CommentService.CommentCallback<Unit>,
        groupId: String,
        postId: String,
        commentId: String,
    ) {
        retrofit.deletePostComment(
            groupId = groupId,
            postId = postId,
            commentId = commentId).enqueue(
            object : Callback<BaseResponse<Unit>> {
                override fun onResponse(
                    call: Call<BaseResponse<Unit>>,
                    response: Response<BaseResponse<Unit>>,
                ) {
                    if (response.isSuccessful) {
                        response.body()?.response?.let { commentCallback.onSuccess(it) }
                    } else {
                        commentCallback.onError(response.message())
                    }
                }

                override fun onFailure(call: Call<BaseResponse<Unit>>, t: Throwable) {
                    t.localizedMessage?.let { commentCallback.onError(it) }
                }

            }
        )
    }
}
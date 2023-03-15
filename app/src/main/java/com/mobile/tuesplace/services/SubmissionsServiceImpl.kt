package com.mobile.tuesplace.services

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.mobile.tuesplace.data.BaseResponse
import com.mobile.tuesplace.data.SubmissionAssets
import com.mobile.tuesplace.data.SubmissionData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubmissionsServiceImpl(private val retrofit: ApiServices): SubmissionsService {
    override suspend fun getSubmissions(
        groupId: String,
        postId: String,
        submissionCallback: SubmissionsService.SubmissionCallback<SnapshotStateList<SubmissionData>>,
    ) {
        retrofit.getPostSubmissions(groupId, postId).enqueue(
            object : Callback<BaseResponse<SnapshotStateList<SubmissionData>>> {
                override fun onResponse(
                    call: Call<BaseResponse<SnapshotStateList<SubmissionData>>>,
                    response: Response<BaseResponse<SnapshotStateList<SubmissionData>>>,
                ) {
                    if (response.isSuccessful) {
                        response.body()?.response?.let { submissionCallback.onSuccess(it) }
                    } else {
                        submissionCallback.onError(response.message())
                    }
                }

                override fun onFailure(
                    call: Call<BaseResponse<SnapshotStateList<SubmissionData>>>,
                    t: Throwable,
                ) {
                    t.localizedMessage?.let { submissionCallback.onError(it) }
                }

            }
        )
    }

    override suspend fun createSubmission(
        groupId: String,
        postId: String,
        assets: SubmissionAssets,
        submissionCallback: SubmissionsService.SubmissionCallback<SubmissionData>,
    ) {
        retrofit.createSubmission(groupId, postId, assets).enqueue(
            object : Callback<BaseResponse<SubmissionData>> {
                override fun onResponse(
                    call: Call<BaseResponse<SubmissionData>>,
                    response: Response<BaseResponse<SubmissionData>>,
                ) {
                    if (response.isSuccessful) {
                        response.body()?.response?.let { submissionCallback.onSuccess(it) }
                    } else {
                        submissionCallback.onError(response.message())
                    }
                }

                override fun onFailure(call: Call<BaseResponse<SubmissionData>>, t: Throwable) {
                    t.localizedMessage?.let { submissionCallback.onError(it) }
                }

            }
        )
    }

}
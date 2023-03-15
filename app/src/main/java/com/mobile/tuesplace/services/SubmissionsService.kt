package com.mobile.tuesplace.services

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.mobile.tuesplace.data.SubmissionData
import okhttp3.MultipartBody

interface SubmissionsService {
    suspend fun getSubmissions(groupId: String, postId: String, submissionCallback: SubmissionCallback<SnapshotStateList<SubmissionData>>)
    suspend fun createSubmission(groupId: String, postId: String, assets: MultipartBody.Part, submissionCallback: SubmissionCallback<Unit>)

    interface SubmissionCallback<Data> {
        fun onSuccess(data: Data)
        fun onError(error: String)
    }
}
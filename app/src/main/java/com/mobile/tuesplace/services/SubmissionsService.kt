package com.mobile.tuesplace.services

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.mobile.tuesplace.data.SubmissionAssets
import com.mobile.tuesplace.data.SubmissionData

interface SubmissionsService {
    suspend fun getSubmissions(groupId: String, postId: String, submissionCallback: SubmissionCallback<SnapshotStateList<SubmissionData>>)
    suspend fun createSubmission(groupId: String, postId: String, assets: SubmissionAssets, submissionCallback: SubmissionCallback<SubmissionData>)

    interface SubmissionCallback<Data> {
        fun onSuccess(data: Data)
        fun onError(error: String)
    }
}
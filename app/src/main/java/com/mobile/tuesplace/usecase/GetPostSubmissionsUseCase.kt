package com.mobile.tuesplace.usecase

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.mobile.tuesplace.data.SubmissionData
import com.mobile.tuesplace.services.SubmissionsService

class GetPostSubmissionsUseCase(private val submissionsService: SubmissionsService) {
    suspend operator fun invoke(groupId: String, postId: String, submissionCallback: SubmissionsService.SubmissionCallback<SnapshotStateList<SubmissionData>>){
        submissionsService.getSubmissions(groupId, postId, submissionCallback)
    }
}
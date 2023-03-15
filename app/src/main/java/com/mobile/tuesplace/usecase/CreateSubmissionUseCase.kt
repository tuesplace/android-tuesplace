package com.mobile.tuesplace.usecase

import com.mobile.tuesplace.data.SubmissionData
import com.mobile.tuesplace.services.SubmissionsService
import okhttp3.MultipartBody

class CreateSubmissionUseCase(private val submissionsService: SubmissionsService) {
    suspend operator fun invoke(groupId: String, postId: String, assets: MultipartBody.Part, submissionCallback: SubmissionsService.SubmissionCallback<Unit>) {
        submissionsService.createSubmission(groupId, postId, assets, submissionCallback)
    }
}
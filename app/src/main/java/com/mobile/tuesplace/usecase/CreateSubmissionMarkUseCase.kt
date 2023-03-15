package com.mobile.tuesplace.usecase

import com.mobile.tuesplace.data.MarkRequestData
import com.mobile.tuesplace.services.MarkService

class CreateSubmissionMarkUseCase(private val markService: MarkService) {
    suspend operator fun invoke(markCallback: MarkService.MarkCallback<Unit>, groupId: String, postId: String, submissionId: String, mark: MarkRequestData) {
        markService.createSubmissionMark(markCallback, groupId, postId, submissionId, mark)
    }
}
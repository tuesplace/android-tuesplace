package com.mobile.tuesplace.usecase

import com.mobile.tuesplace.services.MarkService

class EditMarkUseCase(private val markService: MarkService) {
    suspend operator fun invoke(markCallback: MarkService.MarkCallback<Unit>, groupId: String, studentId: String, markId: String, mark: Double) {
        markService.editStudentMark(markCallback, groupId, studentId, markId, mark)
    }
}
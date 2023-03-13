package com.mobile.tuesplace.services

import com.mobile.tuesplace.data.MarkData
import com.mobile.tuesplace.data.MarkRequestData

interface MarkService {

    fun getGroupMarks(markCallback: MarkCallback<List<MarkData>>, groupId: String)
    fun getStudentMarks(markCallback: MarkCallback<List<MarkData>>, groupId: String, studentId: String)
    fun addStudentMark(markCallback: MarkCallback<MarkData>, groupId: String, studentId: String, mark: Double)
    fun editStudentMark(markCallback: MarkCallback<MarkData>, groupId: String, studentId: String, markId: String, mark: Double)
    fun deleteStudentMark(markCallback: MarkCallback<Unit>, groupId: String, studentId: String, markId: String)
    suspend fun createSubmissionMark(markCallback: MarkCallback<Unit>,groupId: String, postId: String, submissionId: String, mark: MarkRequestData)

    interface MarkCallback<Generic>{
        fun onSuccess(generic: Generic)
        fun onError(error: String)
    }
}
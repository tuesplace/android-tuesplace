package com.mobile.tuesplace.services

import com.mobile.tuesplace.data.MarkData

interface MarkService {

    fun getGroupMarks(markCallback: MarkCallback<List<MarkData>>, groupId: String)
    fun getStudentMarks(markCallback: MarkCallback<List<MarkData>>, groupId: String, studentId: String)
    fun addStudentMark(markCallback: MarkCallback<MarkData>, groupId: String, studentId: String, mark: Double)
    fun editStudentMark(markCallback: MarkCallback<MarkData>, groupId: String, studentId: String, markId: String, mark: Double)
    fun deleteStudentMark(markCallback: MarkCallback<Unit>, groupId: String, studentId: String, markId: String)

    interface MarkCallback<Generic>{
        fun onSuccess(generic: Generic)
        fun onError(error: String)
    }
}
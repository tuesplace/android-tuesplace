package com.mobile.tuesplace.services

import com.mobile.tuesplace.data.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MarkServiceImpl(private val retrofit: ApiServices) : MarkService {

    override fun getGroupMarks(
        markCallback: MarkService.MarkCallback<List<SubmissionMarkData>>,
        groupId: String,
    ) {
        retrofit.getGroupMarks(groupId = groupId).enqueue(
            object : Callback<BaseResponse<List<SubmissionMarkData>>> {
                override fun onResponse(
                    call: Call<BaseResponse<List<SubmissionMarkData>>>,
                    response: Response<BaseResponse<List<SubmissionMarkData>>>,
                ) {
                    if (response.isSuccessful) {
                        response.body()?.response?.let { markCallback.onSuccess(it) }
                    } else {
                        markCallback.onError(response.message())
                    }
                }

                override fun onFailure(call: Call<BaseResponse<List<SubmissionMarkData>>>, t: Throwable) {
                    t.localizedMessage?.let { markCallback.onError(it) }
                }

            }
        )
    }

    override fun getStudentMarks(
        markCallback: MarkService.MarkCallback<List<SubmissionMarkData>>,
        groupId: String,
        studentId: String,
    ) {
        retrofit.getStudentMarks(groupId = groupId, studentId = studentId)
            .enqueue(
                object : Callback<BaseResponse<List<SubmissionMarkData>>> {
                    override fun onResponse(
                        call: Call<BaseResponse<List<SubmissionMarkData>>>,
                        response: Response<BaseResponse<List<SubmissionMarkData>>>,
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.response?.let { markCallback.onSuccess(it) }
                        } else {
                            markCallback.onError(response.message())
                        }
                    }

                    override fun onFailure(call: Call<BaseResponse<List<SubmissionMarkData>>>, t: Throwable) {
                        t.localizedMessage?.let { markCallback.onError(it) }
                    }

                }
            )
    }

    override fun addStudentMark(
        markCallback: MarkService.MarkCallback<SubmissionMarkData>,
        groupId: String,
        studentId: String,
        mark: Double
    ) {
        retrofit.addStudentMark(groupId = groupId, studentId = studentId, mark = mark)
            .enqueue(
                object : Callback<BaseResponse<SubmissionMarkData>> {
                    override fun onResponse(
                        call: Call<BaseResponse<SubmissionMarkData>>,
                        response: Response<BaseResponse<SubmissionMarkData>>,
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.response?.let { markCallback.onSuccess(it) }
                        } else {
                            markCallback.onError(response.message())
                        }
                    }

                    override fun onFailure(call: Call<BaseResponse<SubmissionMarkData>>, t: Throwable) {
                        t.localizedMessage?.let { markCallback.onError(it) }
                    }

                }
            )
    }

    override fun editStudentMark(
        markCallback: MarkService.MarkCallback<SubmissionMarkData>,
        groupId: String,
        studentId: String,
        markId: String,
        mark: Double
    ) {
        retrofit.editStudentMark(
            groupId = groupId,
            studentId = studentId,
            markId = markId,
            mark = mark).enqueue(
            object : Callback<BaseResponse<SubmissionMarkData>> {
                override fun onResponse(
                    call: Call<BaseResponse<SubmissionMarkData>>,
                    response: Response<BaseResponse<SubmissionMarkData>>,
                ) {
                    if (response.isSuccessful) {
                        response.body()?.response?.let { markCallback.onSuccess(it) }
                    } else {
                        markCallback.onError(response.message())
                    }
                }

                override fun onFailure(call: Call<BaseResponse<SubmissionMarkData>>, t: Throwable) {
                    t.localizedMessage?.let { markCallback.onError(it) }
                }
            })
    }

    override fun deleteStudentMark(
        markCallback: MarkService.MarkCallback<Unit>,
        groupId: String,
        studentId: String,
        markId: String,
    ) {
        retrofit.deleteStudentMark(
            groupId = groupId,
            studentId = studentId,
            markId = markId
        ).enqueue(
            object : Callback<BaseResponse<Unit>> {
                override fun onResponse(
                    call: Call<BaseResponse<Unit>>,
                    response: Response<BaseResponse<Unit>>,
                ) {
                    if (response.isSuccessful) {
                        response.body()?.response?.let { markCallback.onSuccess(it) }
                    } else {
                        markCallback.onError(response.message())
                    }
                }

                override fun onFailure(call: Call<BaseResponse<Unit>>, t: Throwable) {
                    t.localizedMessage?.let { markCallback.onError(it) }
                }

            }
        )
    }

    override suspend fun createSubmissionMark(
        markCallback: MarkService.MarkCallback<Unit>,
        groupId: String,
        postId: String,
        submissionId: String,
        mark: MarkRequestData
    ) {
        retrofit.createSubmissionMark(groupId, postId, submissionId, mark).enqueue(
            object : Callback<BaseResponse<Unit>> {
                override fun onResponse(
                    call: Call<BaseResponse<Unit>>,
                    response: Response<BaseResponse<Unit>>,
                ) {
                    if (response.isSuccessful) {
                        markCallback.onSuccess(Unit)
                    } else {
                        markCallback.onError(response.message())
                    }
                }

                override fun onFailure(call: Call<BaseResponse<Unit>>, t: Throwable) {
                    t.localizedMessage?.let { markCallback.onError(it) }
                }

            }
        )
    }
}
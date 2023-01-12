package com.mobile.tuesplace.services

import com.mobile.tuesplace.ACCESS_TOKEN
import com.mobile.tuesplace.data.BaseResponse
import com.mobile.tuesplace.data.MarkData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MarkServiceImpl : MarkService {

    private val retrofit = RetrofitHelper.getInstance().create(ApiServices::class.java)

    override fun getGroupMarks(
        markCallback: MarkService.MarkCallback<List<MarkData>>,
        groupId: String,
    ) {
        retrofit.getGroupMarks("Bearer $ACCESS_TOKEN", groupId = groupId).enqueue(
            object : Callback<BaseResponse<List<MarkData>>> {
                override fun onResponse(
                    call: Call<BaseResponse<List<MarkData>>>,
                    response: Response<BaseResponse<List<MarkData>>>,
                ) {
                    if (response.isSuccessful) {
                        response.body()?.response?.let { markCallback.onSuccess(it) }
                    } else {
                        markCallback.onError(response.message())
                    }
                }

                override fun onFailure(call: Call<BaseResponse<List<MarkData>>>, t: Throwable) {
                    t.localizedMessage?.let { markCallback.onError(it) }
                }

            }
        )
    }

    override fun getStudentMarks(
        markCallback: MarkService.MarkCallback<List<MarkData>>,
        groupId: String,
        studentId: String,
    ) {
        retrofit.getStudentMarks("Bearer $ACCESS_TOKEN", groupId = groupId, studentId = studentId)
            .enqueue(
                object : Callback<BaseResponse<List<MarkData>>> {
                    override fun onResponse(
                        call: Call<BaseResponse<List<MarkData>>>,
                        response: Response<BaseResponse<List<MarkData>>>,
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.response?.let { markCallback.onSuccess(it) }
                        } else {
                            markCallback.onError(response.message())
                        }
                    }

                    override fun onFailure(call: Call<BaseResponse<List<MarkData>>>, t: Throwable) {
                        t.localizedMessage?.let { markCallback.onError(it) }
                    }

                }
            )
    }

    override fun addStudentMark(
        markCallback: MarkService.MarkCallback<MarkData>,
        groupId: String,
        studentId: String,
        mark: Double
    ) {
        retrofit.addStudentMark("Bearer $ACCESS_TOKEN", groupId = groupId, studentId = studentId, mark = mark)
            .enqueue(
                object : Callback<BaseResponse<MarkData>> {
                    override fun onResponse(
                        call: Call<BaseResponse<MarkData>>,
                        response: Response<BaseResponse<MarkData>>,
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.response?.let { markCallback.onSuccess(it) }
                        } else {
                            markCallback.onError(response.message())
                        }
                    }

                    override fun onFailure(call: Call<BaseResponse<MarkData>>, t: Throwable) {
                        t.localizedMessage?.let { markCallback.onError(it) }
                    }

                }
            )
    }

    override fun editStudentMark(
        markCallback: MarkService.MarkCallback<MarkData>,
        groupId: String,
        studentId: String,
        markId: String,
        mark: Double
    ) {
        retrofit.editStudentMark(
            token = "Bearer $ACCESS_TOKEN",
            groupId = groupId,
            studentId = studentId,
            markId = markId,
            mark = mark).enqueue(
            object : Callback<BaseResponse<MarkData>> {
                override fun onResponse(
                    call: Call<BaseResponse<MarkData>>,
                    response: Response<BaseResponse<MarkData>>,
                ) {
                    if (response.isSuccessful) {
                        response.body()?.response?.let { markCallback.onSuccess(it) }
                    } else {
                        markCallback.onError(response.message())
                    }
                }

                override fun onFailure(call: Call<BaseResponse<MarkData>>, t: Throwable) {
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
            token = "Bearer $ACCESS_TOKEN",
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
}
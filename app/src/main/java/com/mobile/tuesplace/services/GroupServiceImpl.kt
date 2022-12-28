package com.mobile.tuesplace.services

import com.mobile.tuesplace.ACCESS_TOKEN
import com.mobile.tuesplace.data.CreateGroupResponse
import com.mobile.tuesplace.data.GroupData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GroupServiceImpl: GroupService {

    private val retrofit = RetrofitHelper.getInstance().create(ApiServices::class.java)

    override suspend fun createGroup(
        createGroupData: GroupData,
        createGroupCallback: GroupService.CreateGroupCallback,
    ) {
        retrofit.createGroup("Bearer $ACCESS_TOKEN", createGroupData).enqueue(
            object : Callback<CreateGroupResponse> {
                override fun onFailure(call: Call<CreateGroupResponse>, t: Throwable) {
                    t.localizedMessage?.let { createGroupCallback.onError(it) }
                }

                override fun onResponse(
                    call: Call<CreateGroupResponse>,
                    response: Response<CreateGroupResponse>
                ) {
                    if (response.isSuccessful){
                        response.body()?.let { createGroupCallback.onSuccess(it, createGroupData) }
                    }
                }
            }
        )
    }
}
package com.mobile.tuesplace.services

import com.mobile.tuesplace.ACCESS_TOKEN
import com.mobile.tuesplace.data.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GroupServiceImpl : GroupService {

    private val retrofit = RetrofitHelper.getInstance().create(ApiServices::class.java)

    override suspend fun createGroup(
        createGroupData: GroupData,
        createGroupCallback: GroupService.GroupCallback<GroupData>,
    ) {
        retrofit.createGroup("Bearer $ACCESS_TOKEN", createGroupData).enqueue(
            object : Callback<BaseResponse<GroupData>> {
                override fun onFailure(call: Call<BaseResponse<GroupData>>, t: Throwable) {
                    t.localizedMessage?.let { createGroupCallback.onError(it) }
                }

                override fun onResponse(
                    call: Call<BaseResponse<GroupData>>,
                    response: Response<BaseResponse<GroupData>>,
                ) {
                    if (response.isSuccessful) {
                        response.body()?.response?.let { createGroupCallback.onSuccess(it) }
                    } else {
                        createGroupCallback.onError(response.message())
                    }
                }
            }
        )
    }

    override suspend fun getGroups(groupCallback: GroupService.GroupCallback<List<GroupResponseData>>) {
        retrofit.getGroups("Bearer $ACCESS_TOKEN").enqueue(
            object : Callback<BaseResponse<List<GroupResponseData>>> {
                override fun onResponse(
                    call: Call<BaseResponse<List<GroupResponseData>>>,
                    response: Response<BaseResponse<List<GroupResponseData>>>,
                ) {
                    if (response.isSuccessful) {
                        response.body()?.response?.let { groupCallback.onSuccess(it) }
                    } else {
                        groupCallback.onError(response.message())
                    }
                }

                override fun onFailure(call: Call<BaseResponse<List<GroupResponseData>>>, t: Throwable) {
                    t.localizedMessage?.let { groupCallback.onError(it) }
                }

            }
        )
    }

    override fun getGroup(
        groupCallback: GroupService.GroupCallback<GroupData>,
        groupId: String,
    ) {
        retrofit.getGroup("Bearer $ACCESS_TOKEN", groupId).enqueue(
            object : Callback<BaseResponse<GroupData>> {
                override fun onResponse(call: Call<BaseResponse<GroupData>>, response: Response<BaseResponse<GroupData>>) {
                    if (response.isSuccessful) {
                        response.body()?.response?.let { groupCallback.onSuccess(it) }
                    } else {
                        groupCallback.onError(response.message())
                    }
                }

                override fun onFailure(call: Call<BaseResponse<GroupData>>, t: Throwable) {
                    t.localizedMessage?.let { groupCallback.onError(it) }
                }

            }
        )
    }

    override suspend fun editGroup(
        groupCallback: GroupService.GroupCallback<EditGroupData>,
        groupId: String,
        editGroupData: EditGroupData,
    ) {
        retrofit.editGroup("Bearer $ACCESS_TOKEN", groupId, editGroupData).enqueue(
            object : Callback<BaseResponse<EditGroupData>> {
                override fun onResponse(
                    call: Call<BaseResponse<EditGroupData>>,
                    response: Response<BaseResponse<EditGroupData>>,
                ) {
                    if (response.isSuccessful) {
                        response.body()?.response?.let { groupCallback.onSuccess(it) }
                    } else {
                        groupCallback.onError(response.message())
                    }
                }

                override fun onFailure(call: Call<BaseResponse<EditGroupData>>, t: Throwable) {
                    t.localizedMessage?.let { groupCallback.onError(it) }
                }
            }
        )
    }

    override fun deleteGroup(
        groupCallback: GroupService.GroupCallback<Unit>,
        groupId: String,
    ) {
        retrofit.deleteGroup("Bearer $ACCESS_TOKEN", groupId).enqueue(
            object : Callback<BaseResponse<Unit>> {
                override fun onResponse(
                    call: Call<BaseResponse<Unit>>,
                    response: Response<BaseResponse<Unit>>,
                ) {
                    if (response.isSuccessful) {
                        response.body()?.response?.let { groupCallback.onSuccess(it) }
                    } else {
                        groupCallback.onError(response.message())
                    }
                }

                override fun onFailure(call: Call<BaseResponse<Unit>>, t: Throwable) {
                    t.localizedMessage?.let { groupCallback.onError(it) }
                }

            }
        )
    }
}
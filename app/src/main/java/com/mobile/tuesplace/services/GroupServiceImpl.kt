package com.mobile.tuesplace.services

import com.mobile.tuesplace.ACCESS_TOKEN
import com.mobile.tuesplace.data.CreateGroupResponse
import com.mobile.tuesplace.data.DeleteGroupResponse
import com.mobile.tuesplace.data.EditGroupData
import com.mobile.tuesplace.data.GroupData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GroupServiceImpl : GroupService {

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
                    response: Response<CreateGroupResponse>,
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { createGroupCallback.onSuccess(it, createGroupData) }
                    }
                }
            }
        )
    }

    override suspend fun getGroups(groupCallback: GroupService.GroupCallback<List<GroupData>>) {
        retrofit.getGroups("Bearer $ACCESS_TOKEN").enqueue(
            object : Callback<List<GroupData>> {
                override fun onResponse(
                    call: Call<List<GroupData>>,
                    response: Response<List<GroupData>>,
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { groupCallback.onSuccess(it) }
                    } else {
                        groupCallback.onError(response.message())
                    }
                }

                override fun onFailure(call: Call<List<GroupData>>, t: Throwable) {
                    t.localizedMessage?.let { groupCallback.onError(it) }
                }

            }
        )
    }

    override suspend fun getGroup(
        groupCallback: GroupService.GroupCallback<GroupData>,
        groupId: String,
    ) {
        retrofit.getGroup("Bearer $ACCESS_TOKEN", groupId).enqueue(
            object : Callback<GroupData> {
                override fun onResponse(call: Call<GroupData>, response: Response<GroupData>) {
                    if (response.isSuccessful) {
                        response.body()?.let { groupCallback.onSuccess(it) }
                    } else {
                        groupCallback.onError(response.message())
                    }
                }

                override fun onFailure(call: Call<GroupData>, t: Throwable) {
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
            object : Callback<EditGroupData> {
                override fun onResponse(
                    call: Call<EditGroupData>,
                    response: Response<EditGroupData>,
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { groupCallback.onSuccess(it) }
                    } else {
                        groupCallback.onError(response.message())
                    }
                }

                override fun onFailure(call: Call<EditGroupData>, t: Throwable) {
                    t.localizedMessage?.let { groupCallback.onError(it) }
                }
            }
        )
    }

    override suspend fun deleteGroup(
        groupCallback: GroupService.GroupCallback<DeleteGroupResponse>,
        groupId: String,
    ) {
        retrofit.deleteGroup("Bearer $ACCESS_TOKEN", groupId).enqueue(
            object : Callback<DeleteGroupResponse> {
                override fun onResponse(
                    call: Call<DeleteGroupResponse>,
                    response: Response<DeleteGroupResponse>,
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { groupCallback.onSuccess(it) }
                    }
                }

                override fun onFailure(call: Call<DeleteGroupResponse>, t: Throwable) {
                    t.localizedMessage?.let { groupCallback.onError(it) }
                }

            }
        )
    }
}
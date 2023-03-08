package com.mobile.tuesplace.services

import com.mobile.tuesplace.data.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class GroupServiceImpl(private val retrofit: ApiServices) : GroupService {

//    private val retrofit = RetrofitHelper.getInstance().create(ApiServices::class.java)

    override suspend fun createGroup(
        createGroupData: GroupData,
        createGroupCallback: GroupService.GroupCallback<GroupData>,
    ) {
        retrofit.createGroup(createGroupData).enqueue(
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
        retrofit.getGroups().enqueue(
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

    override suspend fun getGroup(
        groupCallback: GroupService.GroupCallback<GroupData>,
        groupId: String,
    ) {
        retrofit.getGroup(groupId).enqueue(
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
        retrofit.editGroup(groupId, editGroupData).enqueue(
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
        retrofit.deleteGroup(groupId).enqueue(
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

    override suspend fun getMyGroups(groupCallback: GroupService.GroupCallback<List<GroupResponseData>>) {
        retrofit.getMyGroups().enqueue(
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

               override fun onFailure(
                   call: Call<BaseResponse<List<GroupResponseData>>>,
                   t: Throwable,
               ) {
                   t.localizedMessage?.let { groupCallback.onError(it) }
               }

           }
        )
    }
}
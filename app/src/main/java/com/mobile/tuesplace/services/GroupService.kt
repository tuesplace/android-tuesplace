package com.mobile.tuesplace.services

import com.mobile.tuesplace.data.CreateGroupResponse
import com.mobile.tuesplace.data.GroupData

interface GroupService {
    suspend fun createGroup(createGroupData: GroupData, createGroupCallback: CreateGroupCallback)
    suspend fun getGroups(getGroupsCallback: GetGroupsCallback)

    interface CreateGroupCallback{
        fun onSuccess(createGroupResponse: CreateGroupResponse, createGroupData: GroupData)
        fun onError(error: String)
    }

    interface GetGroupsCallback{
        fun onSuccess(groupsList: List<GroupData>)
        fun onError(error: String)
    }
}
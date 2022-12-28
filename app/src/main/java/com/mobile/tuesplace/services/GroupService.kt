package com.mobile.tuesplace.services

import com.mobile.tuesplace.data.CreateGroupResponse
import com.mobile.tuesplace.data.GroupData

interface GroupService {
    suspend fun createGroup(createGroupData: GroupData, createGroupCallback: CreateGroupCallback)

    interface CreateGroupCallback{
        fun onSuccess(createGroupResponse: CreateGroupResponse, createGroupData: GroupData)
        fun onError(error: String)
    }
}
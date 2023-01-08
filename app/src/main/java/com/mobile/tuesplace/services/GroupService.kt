package com.mobile.tuesplace.services

import com.mobile.tuesplace.data.CreateGroupResponse
import com.mobile.tuesplace.data.DeleteGroupResponse
import com.mobile.tuesplace.data.EditGroupData
import com.mobile.tuesplace.data.GroupData

interface GroupService {
    suspend fun createGroup(createGroupData: GroupData, createGroupCallback: CreateGroupCallback)
    suspend fun getGroups(groupCallback: GroupCallback<List<GroupData>>)
    suspend fun getGroup(groupCallback: GroupCallback<GroupData>, groupId: String)
    suspend fun editGroup(groupCallback: GroupCallback<EditGroupData>, groupId: String, editGroupData: EditGroupData)
    suspend fun deleteGroup(groupCallback: GroupCallback<DeleteGroupResponse>, groupId: String)

    interface CreateGroupCallback{
        fun onSuccess(createGroupResponse: CreateGroupResponse, createGroupData: GroupData)
        fun onError(error: String)
    }

    interface GroupCallback<GroupGeneric>{
        fun onSuccess(groupGeneric: GroupGeneric)
        fun onError(error: String)
    }
}
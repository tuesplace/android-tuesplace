package com.mobile.tuesplace.services

import com.mobile.tuesplace.data.*

interface GroupService {
    suspend fun createGroup(createGroupData: GroupData, createGroupCallback: GroupCallback<GroupData>)
    suspend fun getGroups(groupCallback: GroupCallback<List<GroupData>>)
    suspend fun getGroup(groupCallback: GroupCallback<GroupData>, groupId: String)
    suspend fun editGroup(groupCallback: GroupCallback<EditGroupData>, groupId: String, editGroupData: EditGroupData)
    suspend fun deleteGroup(groupCallback: GroupCallback<Unit>, groupId: String)

    interface GroupCallback<GroupGeneric>{
        fun onSuccess(groupGeneric: GroupGeneric)
        fun onError(error: String)
    }
}
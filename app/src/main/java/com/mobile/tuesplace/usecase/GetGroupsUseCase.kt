package com.mobile.tuesplace.usecase

import com.mobile.tuesplace.data.GroupData
import com.mobile.tuesplace.services.GroupService

class GetGroupsUseCase(private val groupService: GroupService) {
    suspend fun invoke(groupsCallback: GroupService.GroupCallback<List<GroupData>>){
        groupService.getGroups(groupsCallback)
    }
}
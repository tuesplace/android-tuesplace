package com.mobile.tuesplace.usecase

import com.mobile.tuesplace.data.GroupResponseData
import com.mobile.tuesplace.services.GroupService

class GetGroupsUseCase(private val groupService: GroupService) {
    suspend operator fun invoke(groupsCallback: GroupService.GroupCallback<List<GroupResponseData>>){
        groupService.getGroups(groupsCallback)
    }
}
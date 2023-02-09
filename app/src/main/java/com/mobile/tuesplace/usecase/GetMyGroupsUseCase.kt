package com.mobile.tuesplace.usecase

import com.mobile.tuesplace.data.GroupResponseData
import com.mobile.tuesplace.services.GroupService

class GetMyGroupsUseCase(private val groupsService: GroupService) {
    suspend operator fun invoke(groupCallback: GroupService.GroupCallback<List<GroupResponseData>>){
        groupsService.getMyGroups(groupCallback)
    }
}
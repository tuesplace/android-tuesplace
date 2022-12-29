package com.mobile.tuesplace.usecase

import com.mobile.tuesplace.services.GroupService

class GetGroupsUseCase(private val groupService: GroupService) {
    suspend fun invoke(getGroupsCallback: GroupService.GetGroupsCallback){
        groupService.getGroups(getGroupsCallback)
    }
}
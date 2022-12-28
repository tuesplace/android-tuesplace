package com.mobile.tuesplace.usecase

import com.mobile.tuesplace.data.GroupData
import com.mobile.tuesplace.services.GroupService

class CreateGroupUseCase(private val groupService: GroupService) {
    suspend fun invoke(groupData: GroupData, createGroupCallback: GroupService.CreateGroupCallback){
        groupService.createGroup(groupData, createGroupCallback)
    }
}
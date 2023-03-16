package com.mobile.tuesplace.usecase

import com.mobile.tuesplace.data.GroupResponseData
import com.mobile.tuesplace.services.GroupService

class GetGroupUseCase(private val groupService: GroupService) {
    suspend operator fun invoke(groupCallback: GroupService.GroupCallback<GroupResponseData>, groupId: String){
        groupService.getGroup(groupCallback, groupId)
    }
}
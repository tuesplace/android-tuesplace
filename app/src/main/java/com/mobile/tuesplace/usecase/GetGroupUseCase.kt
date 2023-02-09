package com.mobile.tuesplace.usecase

import com.mobile.tuesplace.data.GroupData
import com.mobile.tuesplace.services.GroupService

class GetGroupUseCase(private val groupService: GroupService) {
    fun invoke(groupCallback: GroupService.GroupCallback<GroupData>, groupId: String){
        groupService.getGroup(groupCallback, groupId)
    }
}
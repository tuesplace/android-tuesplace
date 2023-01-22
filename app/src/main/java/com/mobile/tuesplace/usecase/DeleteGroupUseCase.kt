package com.mobile.tuesplace.usecase

import com.mobile.tuesplace.services.GroupService

class DeleteGroupUseCase(private val groupService: GroupService) {
    fun invoke(groupCallback: GroupService.GroupCallback<Unit>, groupId: String){
        groupService.deleteGroup(groupCallback, groupId)
    }
}
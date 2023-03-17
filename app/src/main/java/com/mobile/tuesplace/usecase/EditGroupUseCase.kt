package com.mobile.tuesplace.usecase

import com.mobile.tuesplace.data.EditGroupData
import com.mobile.tuesplace.services.GroupService

class EditGroupUseCase(private val groupService: GroupService) {
    suspend operator fun invoke(
        groupCallback: GroupService.GroupCallback<Unit>,
        groupId: String,
        editGroupData: EditGroupData,
    ) {
        groupService.editGroup(groupCallback, groupId, editGroupData)
    }
}
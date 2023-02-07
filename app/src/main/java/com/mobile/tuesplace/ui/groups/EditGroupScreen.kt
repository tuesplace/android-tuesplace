package com.mobile.tuesplace.ui.groups

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.mobile.tuesplace.R
import com.mobile.tuesplace.data.GroupData
import com.mobile.tuesplace.ui.GradientBorderButtonRound
import com.mobile.tuesplace.ui.TextField
import com.mobile.tuesplace.ui.states.DeleteGroupUiState
import com.mobile.tuesplace.ui.states.GetGroupUiState

@Composable
fun EditGroupScreen(
    groupUiStateFlow: GetGroupUiState,
    groupName: String,
    setGroupName: (String) -> Unit,
    groupType: String,
    setGroupType: (String) -> Unit,
    classes: String,
    setClasses: (String) -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: (String) -> Unit,
    deleteUiState: DeleteGroupUiState,
    onBackPressed: () -> Unit,
) {
    when (groupUiStateFlow) {
        GetGroupUiState.Empty -> {}
        is GetGroupUiState.Error -> {
            onBackPressed()
        }
        GetGroupUiState.Loading -> {}
        is GetGroupUiState.Success -> {
            EditGroupUi(
                group = groupUiStateFlow.groupData,
                groupName = groupName,
                setGroupName = setGroupName,
                groupType = groupType,
                setGroupType = setGroupType,
                classes = classes,
                setClasses = setClasses,
                onEditClick = onEditClick,
                onDeleteClick = onDeleteClick
            )
        }
    }

    when (deleteUiState) {
        DeleteGroupUiState.Empty -> {}
        is DeleteGroupUiState.Error -> {}
        DeleteGroupUiState.Loading -> {}
        DeleteGroupUiState.Success -> {
            onBackPressed()
        }
    }

}

@Composable
fun EditGroupUi(
    group: GroupData,
    groupName: String,
    setGroupName: (String) -> Unit,
    groupType: String,
    setGroupType: (String) -> Unit,
    classes: String,
    setClasses: (String) -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: (String) -> Unit,
) {
    ConstraintLayout(modifier = Modifier
        .fillMaxSize()
        .background(colorResource(id = R.color.gray))
    ) {
        val (saveBtn, deleteBtn, fields) = createRefs()
        Column(modifier = Modifier
            .fillMaxWidth()
            .constrainAs(fields) {
                top.linkTo(parent.top)
                bottom.linkTo(saveBtn.top)
            }) {
            TextField(
                value = groupName,
                onValueChange = setGroupName,
                placeholder = group.name,
                modifier = null,
                enabled = true,
                isError = null
            )
            TextField(
                value = groupType,
                onValueChange = setGroupType,
                placeholder = group.type,
                modifier = null,
                enabled = true,
                isError = null
            )
            TextField(
                value = classes,
                onValueChange = setClasses,
                placeholder = group.classes?.get(0)?: "",
                modifier = null,
                enabled = true,
                isError = null
            )
        }

        GradientBorderButtonRound(
            colors = null,
            paddingValues = PaddingValues(16.dp),
            buttonText = stringResource(id = R.string.save_changes),
            onLoginClick = { onEditClick() },
            buttonPadding = PaddingValues(16.dp),
            modifier = Modifier.constrainAs(saveBtn) {
                bottom.linkTo(deleteBtn.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        GradientBorderButtonRound(
            colors = listOf(
                colorResource(id = R.color.blood_red),
                colorResource(id = R.color.brick_red),
                colorResource(id = R.color.bright_red)),
            paddingValues = PaddingValues(16.dp),
            buttonText = stringResource(id = R.string.delete_group),
            onLoginClick = { onDeleteClick("63ce4efd8bd8e60aefbc5839") },
            buttonPadding = PaddingValues(16.dp),
            modifier = Modifier.constrainAs(deleteBtn) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
    }
}

    @Composable
    @Preview
    fun EditGroupItemPreview() {
        EditGroupScreen(GetGroupUiState.Empty,
            "",
            {},
            "",
            {},
            "",
            {},
            {},
            {},
            DeleteGroupUiState.Empty,
            {})
    }
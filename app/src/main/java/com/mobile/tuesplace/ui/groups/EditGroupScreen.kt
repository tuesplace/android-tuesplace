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
import com.mobile.tuesplace.ui.GradientBorderButtonRound
import com.mobile.tuesplace.ui.TextFields
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
    onBackPressed: () -> Unit
) {

    when(groupUiStateFlow){
        GetGroupUiState.Empty -> { }
        is GetGroupUiState.Error -> { onBackPressed() }
        GetGroupUiState.Loading -> {  }
        is GetGroupUiState.Success -> { }
    }

    when(deleteUiState){
        DeleteGroupUiState.Empty -> {}
        is DeleteGroupUiState.Error -> { }
        DeleteGroupUiState.Loading -> {}
        DeleteGroupUiState.Success -> { onBackPressed() }
    }

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
            TextFields(
                value = groupName,
                onValueChange = setGroupName,
                placeholder = stringResource(id = R.string.group_name),
                modifier = null,
                enabled = true
            )
            TextFields(
                value = groupType,
                onValueChange = setGroupType,
                placeholder = stringResource(id = R.string.group_type),
                modifier = null,
                enabled = true
            )
            TextFields(
                value = classes,
                onValueChange = setClasses,
                placeholder = stringResource(id = R.string.choose_classes),
                modifier = null,
                enabled = true
            )
        }

        GradientBorderButtonRound(
            colors = null,
            paddingValues = PaddingValues(16.dp),
            buttonText = stringResource(id = R.string.save_changes),
            onLoginClick = { onEditClick() },
            buttonPadding = PaddingValues(16.dp),
            modifier = Modifier.constrainAs(saveBtn){
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
            onLoginClick = { onDeleteClick("63c1eb05b4bf15e4d6ed9023") },
            buttonPadding = PaddingValues(16.dp),
            modifier = Modifier.constrainAs(deleteBtn){
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
    EditGroupScreen(GetGroupUiState.Empty, "", {}, "", {}, "", {}, {}, {}, DeleteGroupUiState.Empty, {})
}
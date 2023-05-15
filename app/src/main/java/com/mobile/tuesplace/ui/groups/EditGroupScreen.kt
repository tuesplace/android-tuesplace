package com.mobile.tuesplace.ui.groups

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.mobile.tuesplace.R
import com.mobile.tuesplace.data.GroupResponseData
import com.mobile.tuesplace.ui.GradientBorderButtonRound
import com.mobile.tuesplace.ui.Loading
import com.mobile.tuesplace.ui.TextFieldFunction
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
    onDeleteSuccess: () -> Unit,
    editGroupUiState: DeleteGroupUiState,
    onEditSuccess: () -> Unit
) {
    when (groupUiStateFlow) {
        GetGroupUiState.Empty -> {}
        is GetGroupUiState.Error -> {
            onBackPressed()
        }
        GetGroupUiState.Loading -> {
            Loading()
        }
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
        is GetGroupUiState.Loaded -> {}
    }

    when (deleteUiState) {
        DeleteGroupUiState.Empty -> {}
        is DeleteGroupUiState.Error -> {}
        DeleteGroupUiState.Loading -> {
            Loading()
        }
        DeleteGroupUiState.Success -> {
            onDeleteSuccess()
            Toast.makeText(
                LocalContext.current,
                stringResource(id = R.string.group_deleted),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    when(editGroupUiState) {
        DeleteGroupUiState.Empty -> {}
        is DeleteGroupUiState.Error -> {}
        DeleteGroupUiState.Loading -> {
            Loading()
        }
        DeleteGroupUiState.Success -> {
            onEditSuccess()
            Toast.makeText(
                LocalContext.current,
                stringResource(id = R.string.group_edited),
                Toast.LENGTH_LONG
            ).show()
        }
    }

}

@Composable
fun EditGroupUi(
    group: GroupResponseData,
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
        .background(colorResource(id = R.color.dark_blue))
    ) {
        val (saveBtn, deleteBtn, fields) = createRefs()
        Column(modifier = Modifier
            .fillMaxWidth()
            .constrainAs(fields) {
                top.linkTo(parent.top)
                bottom.linkTo(saveBtn.top)
            }) {
            TextFieldFunction(
                value = groupName,
                onValueChange = setGroupName,
                placeholder = group.name,
                modifier = null,
                enabled = true,
                isError = null
            )
            TextFieldFunction(
                value = groupType,
                onValueChange = setGroupType,
                placeholder = group.type,
                modifier = null,
                enabled = true,
                isError = null
            )
            TextFieldFunction(
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
            onClick = { onEditClick() },
            buttonPadding = PaddingValues(16.dp),
            modifier = Modifier.constrainAs(saveBtn) {
                bottom.linkTo(deleteBtn.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Text(
            text = stringResource(id = R.string.delete_group),
            color = colorResource(id = R.color.baby_blue),
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .constrainAs(deleteBtn) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .clickable {
                    onDeleteClick(group._id)
                }
        )
    }
}

    @Composable
    @Preview
    fun EditGroupItemPreview() {
//        EditGroupScreen(GetGroupUiState.Empty,
//            "",
//            {},
//            "",
//            {},
//            "",
//            {},
//            {},
//            {},
//            DeleteGroupUiState.Empty,
//            {}, Ед)
    }
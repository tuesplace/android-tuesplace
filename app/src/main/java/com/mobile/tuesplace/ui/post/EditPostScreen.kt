package com.mobile.tuesplace.ui.post

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.mobile.tuesplace.PostRequestData
import com.mobile.tuesplace.R
import com.mobile.tuesplace.ui.GradientBorderButtonRound
import com.mobile.tuesplace.ui.Loading
import com.mobile.tuesplace.ui.TextFieldWithTitle
import com.mobile.tuesplace.ui.states.DeletePostUiState
import com.mobile.tuesplace.ui.states.EditPostUiState

@Composable
fun EditPostScreen(
    title: String,
    onTitleChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    onEditClick: (PostRequestData) -> Unit,
    onDeleteClick: () -> Unit,
    deletePostUiState: DeletePostUiState,
    onDeleteSuccess: () -> Unit,
    editPostUiState: EditPostUiState,
    onEditSuccess: () -> Unit
) {
    EditPostUi(
        title = title,
        onTitleChange = onTitleChange,
        description = description,
        onDescriptionChange = onDescriptionChange,
        onEditClick = onEditClick,
        onDeleteClick = onDeleteClick
    )

    when (deletePostUiState) {
        DeletePostUiState.Empty -> {}
        is DeletePostUiState.Error -> {
            Toast.makeText(
                LocalContext.current,
                deletePostUiState.exception ?: stringResource(R.string.create_error),
                Toast.LENGTH_LONG
            ).show()
        }
        DeletePostUiState.Loading -> {
            Loading()
        }
        DeletePostUiState.Success -> {
            Toast.makeText(
                LocalContext.current,
                "Delete successful",
                Toast.LENGTH_LONG
            ).show()
            onDeleteSuccess()
        }
    }

    when (editPostUiState) {
        EditPostUiState.Empty -> {}
        is EditPostUiState.Error -> {}
        EditPostUiState.Loading -> {
            Loading()
        }
        EditPostUiState.Success -> {
            Toast.makeText(
                LocalContext.current,
                "Edited successfully",
                Toast.LENGTH_LONG
            ).show()
            onEditSuccess()
        }
    }
}

@Composable
fun EditPostUi(
    title: String,
    onTitleChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    onEditClick: (PostRequestData) -> Unit,
    onDeleteClick: () -> Unit
){
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.dark_blue))
    ) {
        val (titleItem, descriptionItem, editBtn, deleteBtn) = createRefs()

        TextFieldWithTitle(
            title = stringResource(id = R.string.name),
            placeholder = stringResource(id = R.string.name),
            value = title,
            onValueChange = { onTitleChange(it) },
            enabled = true,
            isError = false,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .constrainAs(titleItem) {
                    top.linkTo(parent.top)
                }
        )

        TextFieldWithTitle(
            title = stringResource(id = R.string.description),
            placeholder = stringResource(id = R.string.description),
            value = description,
            onValueChange = { onDescriptionChange(it) },
            enabled = true,
            isError = false,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .constrainAs(descriptionItem) {
                    top.linkTo(titleItem.bottom)
                }
        )

        GradientBorderButtonRound(
            colors = listOf(colorResource(id = R.color.blood_red), colorResource(id = R.color.brick_red)),
            paddingValues = PaddingValues(6.dp),
            buttonText = stringResource(id = R.string.delete),
            onClick = { onDeleteClick() },
            buttonPadding = PaddingValues(6.dp),
            modifier = Modifier
                .padding(16.dp)
                .constrainAs(deleteBtn) {
                    bottom.linkTo(editBtn.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        GradientBorderButtonRound(
            colors = listOf(colorResource(id = R.color.blue), colorResource(id = R.color.royal_blue)),
            paddingValues = PaddingValues(6.dp),
            buttonText = stringResource(id = R.string.edit),
            onClick = { onEditClick(PostRequestData(title, description, assignmentInfo = null)) },
            buttonPadding = PaddingValues(6.dp),
            modifier = Modifier
                .padding(16.dp)
                .constrainAs(editBtn) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
    }
}
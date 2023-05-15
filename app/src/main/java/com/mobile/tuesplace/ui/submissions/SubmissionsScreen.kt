package com.mobile.tuesplace.ui.submissions

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import com.mobile.tuesplace.R
import com.mobile.tuesplace.ZERO_STRING
import com.mobile.tuesplace.data.PostResponseData
import com.mobile.tuesplace.data.SubmissionData
import com.mobile.tuesplace.ui.Loading
import com.mobile.tuesplace.ui.SubmissionItem
import com.mobile.tuesplace.ui.states.CreateSubmissionMarkUiState
import com.mobile.tuesplace.ui.states.GetPostSubmissionsUiState
import com.mobile.tuesplace.ui.states.GetPostUiState

@Composable
fun SubmissionsScreen(
    getPostUiState: GetPostUiState,
    getPostSubmissionsUiState: GetPostSubmissionsUiState,
    onPostSuccess: () -> Unit,
    postResponseData: PostResponseData?,
    dialogVisibility: Boolean,
    setDialogVisibility: (Boolean) -> Unit,
    setSubmissionIndex: (Int) -> Unit,
    onDeleteClick: (String) -> Unit,
    submissionIndex: Int,
    onSaveClick: (Pair<String, String>) -> Unit,
    markValue: String,
    onMarkChange: (String) -> Unit,
    submissions: SnapshotStateList<SubmissionData>,
    createSubmissionMarkUiState: CreateSubmissionMarkUiState,
    onSaveEditClick: (String, String, Double) -> Unit,
) {
    when(getPostUiState) {
        GetPostUiState.Empty -> {}
        is GetPostUiState.Error -> {
            Toast.makeText(
                LocalContext.current,
                getPostUiState.exception ?: stringResource(R.string.create_error),
                Toast.LENGTH_LONG
            ).show()
        }
        is GetPostUiState.Loaded -> {}
        GetPostUiState.Loading -> {
            Loading()
        }
        is GetPostUiState.Success -> {
            onPostSuccess()
        }
    }

    when (getPostSubmissionsUiState) {
        GetPostSubmissionsUiState.Empty -> {}
        is GetPostSubmissionsUiState.Error -> {
            Toast.makeText(
                LocalContext.current,
                getPostSubmissionsUiState.exception ?: stringResource(R.string.create_error),
                Toast.LENGTH_LONG
            ).show()
        }
        GetPostSubmissionsUiState.Loading -> {
            Loading()
        }
        is GetPostSubmissionsUiState.Success -> {
            if (postResponseData != null) {
                SubmissionsUi(
                    submissions = submissions,
                    postResponseData = postResponseData,
                    dialogVisibility = dialogVisibility,
                    setDialogVisibility = setDialogVisibility,
                    setSubmissionIndex = setSubmissionIndex,
                    onDeleteClick = onDeleteClick,
                    submissionIndex = submissionIndex,
                    onSaveClick = onSaveClick,
                    markValue = markValue,
                    onMarkChange = onMarkChange,
                    onSaveEditClick = onSaveEditClick
                )
            }
        }
    }
}

@Composable
fun SubmissionsUi(
    submissions: SnapshotStateList<SubmissionData>,
    postResponseData: PostResponseData,
    dialogVisibility: Boolean,
    setDialogVisibility: (Boolean) -> Unit,
    setSubmissionIndex: (Int) -> Unit,
    onDeleteClick: (String) -> Unit,
    submissionIndex: Int,
    onSaveClick: (Pair<String, String>) -> Unit,
    onSaveEditClick: (String, String, Double) -> Unit,
    markValue: String,
    onMarkChange: (String) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dark_blue))
    ) {
        val (titleItem, deadlineItem, handedInString, submissionsItemList) = createRefs()
        Text(
            text = postResponseData.title,
            color = colorResource(id = R.color.white),
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(6.dp)
                .fillMaxWidth()
                .constrainAs(titleItem) {
                    top.linkTo(parent.top)
                }
        )

        Text(
            text = postResponseData.assignmentInfo.deadline.toString(),
            color = colorResource(id = R.color.white),
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .constrainAs(deadlineItem) {
                    top.linkTo(titleItem.bottom)
                }
        )

        Text(
            text = stringResource(id = R.string.handed_in),
            color = colorResource(id = R.color.white),
            fontSize = 24.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
                .constrainAs(handedInString) {
                    top.linkTo(deadlineItem.bottom)
                }
        )


        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(submissionsItemList) {
                    top.linkTo(handedInString.bottom)
                    bottom.linkTo(parent.bottom)
                }
        ) {
            itemsIndexed(submissions) { index, data ->
                SubmissionItem(data, setDialogVisibility, setSubmissionIndex, index)
            }
        }

        if (dialogVisibility) {
            Dialog(
                onDismissRequest = { setDialogVisibility(false) },
                properties = DialogProperties(
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true
                )
            ) {
                ConstraintLayout(
                    modifier = Modifier
                        .size(300.dp)
                        .background(colorResource(id = R.color.white), RoundedCornerShape(8.dp))
                ) {
                    val (alert, answers, input) = createRefs()

                    Text(
                        text = stringResource(id = R.string.enter_mark),
                        color = colorResource(id = R.color.darker_sea_blue),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .constrainAs(alert) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                bottom.linkTo(answers.top)
                            }
                    )

                    Row(
                        modifier = Modifier
                            .constrainAs(input) {
                                top.linkTo(alert.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                bottom.linkTo(answers.top)
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextField(
                            value = markValue,
                            onValueChange = { onMarkChange(it) },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.Black,
                                placeholderColor = Color.Black
                            ),
                            modifier = Modifier.width(50.dp),
                            placeholder = { Text(text = if (submissions[submissionIndex].marks.isEmpty()) { ZERO_STRING } else { submissions[submissionIndex].marks[0].mark.toString() }) }
                        )

                        Text(
                            text = stringResource(id = R.string.mark_input),
                            fontSize = 12.sp,
                            color = colorResource(id = R.color.darker_sea_blue)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(6.dp)
                            .constrainAs(answers) {
                                bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            },
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.save),
                            modifier = Modifier
                                .clickable {
                                    if (submissionIndex != -1) {
                                        if (submissions[submissionIndex].marks.isEmpty()){
                                            onSaveClick(Pair(submissions[submissionIndex]._id, markValue))
                                        } else {
                                            onSaveEditClick(submissions[submissionIndex].owner._id, submissions[submissionIndex]._id, markValue.toDouble())
                                        }
                                    }
                                    setDialogVisibility(false)
                                    setSubmissionIndex(-1)
                                },
                            color = colorResource(id = R.color.darker_sea_blue),
                            fontSize = 22.sp
                        )
                        Text(
                            text = stringResource(id = R.string.cancel),
                            modifier = Modifier
                                .padding(start = 6.dp)
                                .clickable { setDialogVisibility(false) },
                            color = colorResource(id = R.color.darker_sea_blue),
                            textAlign = TextAlign.Center,
                            fontSize = 22.sp
                        )
                        Text(
                            text = stringResource(id = R.string.delete),
                            modifier = Modifier
                                .padding(start = 6.dp)
                                .clickable {
                                    setDialogVisibility(false)
                                    if (submissionIndex != -1) {
                                        onDeleteClick(submissions[submissionIndex]._id)
                                    }
                                },
                            color = colorResource(id = R.color.darker_sea_blue),
                            textAlign = TextAlign.Center,
                            fontSize = 22.sp
                        )
                    }
                }
            }
        }

    }
}
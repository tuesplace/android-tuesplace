package com.mobile.tuesplace.ui.post

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import com.mobile.tuesplace.ALL_TYPES
import com.mobile.tuesplace.MULTIPART_NAME_SUBMISSION
import com.mobile.tuesplace.PostRequestData
import com.mobile.tuesplace.R
import com.mobile.tuesplace.data.CommentData
import com.mobile.tuesplace.data.CreateCommentData
import com.mobile.tuesplace.data.PostResponseData
import com.mobile.tuesplace.data.SubmissionData
import com.mobile.tuesplace.ui.CommentItem
import com.mobile.tuesplace.ui.CreateCommentItem
import com.mobile.tuesplace.ui.ResultLauncher
import com.mobile.tuesplace.ui.states.*
import okhttp3.MultipartBody

@Composable
fun PostScreen(
    commentInput: String,
    onCommentChange: (String) -> Unit,
    getPostUiState: GetPostUiState,
    onSendClick: (CreateCommentData) -> Unit,
    getPostCommentsUiState: GetPostCommentsUiState,
    onPostSuccess: () -> Unit,
    onEditClick: (PostRequestData) -> Unit,
    commentMenuIndex: Int,
    setCommentMenuIndex: (Int) -> Unit,
    onDeleteClick: (String) -> Unit,
    onEditCommentClick: (Pair<String, String>) -> Unit,
    enabled: Int,
    setEnabled: (Int) -> Unit,
    setEditCommentBody: (Pair<String, Int>) -> Unit,
    commentData: SnapshotStateList<CommentData>,
    post: PostResponseData?,
    dialogVisibility: Boolean,
    setDialogVisibility: (Boolean) -> Unit,
    editCommentUiState: EditCommentsUiState,
    onEditCommentSuccess: () -> Unit,
    deleteCommentUiState: DeleteCommentUiState,
    onDeleteCommentSuccess: () -> Unit,
    createCommentUiState: CreateCommentUiState,
    onCreateCommentSuccess: () -> Unit,
    onUploadAssignmentClick: (MultipartBody.Part) -> Unit,
    createSubmissionUiState: CreateSubmissionUiState,
    onSubmissionSuccess: () -> Unit
) {
    when (getPostUiState) {
        GetPostUiState.Empty -> {}
        GetPostUiState.Loading -> {}
        is GetPostUiState.Error -> {}
        is GetPostUiState.Success -> {
            onPostSuccess()
        }
        is GetPostUiState.Loaded -> {}
    }

    when (getPostCommentsUiState) {
        GetPostCommentsUiState.Empty -> {}
        is GetPostCommentsUiState.Error -> {}
        GetPostCommentsUiState.Loading -> {}
        is GetPostCommentsUiState.Success -> {
            if (post != null) {
                if (post.assignmentInfo.isAssignment) {
                    AssignmentUserScreenUi(
                        postResponseData = post,
                        onEditClick = onEditClick,
                        onSendClick = onSendClick,
                        commentInput = commentInput,
                        onCommentChange = onCommentChange,
                        dialogVisibility = dialogVisibility,
                        setDialogVisibility = setDialogVisibility,
                        onDeleteClick = onDeleteClick,
                        postComments = commentData,
                        commentMenuIndex = commentMenuIndex,
                        setCommentMenuIndex = setCommentMenuIndex,
                        enabled = enabled,
                        setEnabled = setEnabled,
                        setEditCommentBody = setEditCommentBody,
                        onEditCommentClick = onEditCommentClick,
                        onUploadAssignmentClick = onUploadAssignmentClick)
                } else {
                    PostUi(
                        postResponseData = post,
                        commentInput = commentInput,
                        onCommentChange = onCommentChange,
                        postComments = commentData,
                        onSendClick = onSendClick,
                        onEditClick = onEditClick,
                        commentMenuIndex = commentMenuIndex,
                        setCommentMenuIndex = setCommentMenuIndex,
                        onDeleteClick = onDeleteClick,
                        onEditCommentClick = onEditCommentClick,
                        enabled = enabled,
                        setEnabled = setEnabled,
                        setEditCommentBody = setEditCommentBody,
                        dialogVisibility = dialogVisibility,
                        setDialogVisibility = setDialogVisibility
                    )
                }
            }
        }
    }

    when (editCommentUiState) {
        EditCommentsUiState.Empty -> {}
        is EditCommentsUiState.Error -> {}
        EditCommentsUiState.Loading -> {}
        EditCommentsUiState.Success -> {
            Toast.makeText(
                LocalContext.current,
                stringResource(id = R.string.edited),
                Toast.LENGTH_LONG
            ).show()
            onEditCommentSuccess()
        }
    }

    when (deleteCommentUiState) {
        DeleteCommentUiState.Empty -> {}
        is DeleteCommentUiState.Error -> {}
        DeleteCommentUiState.Loading -> {}
        DeleteCommentUiState.Success -> {
            Toast.makeText(
                LocalContext.current,
                stringResource(id = R.string.deleted),
                Toast.LENGTH_LONG
            ).show()
            onDeleteCommentSuccess()
        }
    }

    when (createCommentUiState) {
        CreateCommentUiState.Empty -> {}
        is CreateCommentUiState.Error -> {}
        CreateCommentUiState.Loading -> {}
        CreateCommentUiState.Success -> {
            Toast.makeText(
                LocalContext.current,
                stringResource(id = R.string.added),
                Toast.LENGTH_LONG
            ).show()
            onCreateCommentSuccess()
        }
    }

    when(createSubmissionUiState) {
        CreateSubmissionUiState.Empty -> {

        }
        is CreateSubmissionUiState.Error -> {

        }
        CreateSubmissionUiState.Loading -> {

        }
        CreateSubmissionUiState.Success -> {
            Toast.makeText(
                LocalContext.current,
                stringResource(id = R.string.submitted_successfully),
                Toast.LENGTH_LONG
            ).show()
            onSubmissionSuccess()
        }
    }
}

@Composable
fun PostUi(
    postResponseData: PostResponseData,
    commentInput: String,
    onCommentChange: (String) -> Unit,
    postComments: SnapshotStateList<CommentData>,
    onSendClick: (CreateCommentData) -> Unit,
    onEditClick: (PostRequestData) -> Unit,
    commentMenuIndex: Int,
    setCommentMenuIndex: (Int) -> Unit,
    onDeleteClick: (String) -> Unit,
    onEditCommentClick: (Pair<String, String>) -> Unit,
    enabled: Int,
    setEnabled: (Int) -> Unit,
    setEditCommentBody: (Pair<String, Int>) -> Unit,
    dialogVisibility: Boolean,
    setDialogVisibility: (Boolean) -> Unit
) {

    ConstraintLayout(
        modifier = Modifier
            .background(color = colorResource(id = R.color.white))
            .fillMaxSize()
    ) {
        val (topItem, titleItem, bodyItem, addCommentItem, commentsItem) = createRefs()
        Row(modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, colorResource(id = R.color.gray))
            .constrainAs(topItem) {
                top.linkTo(parent.top)
            },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.tues_webview),
                    contentDescription = stringResource(id = R.string.empty),
                    modifier = Modifier
                        .padding(4.dp)
                        .size(40.dp)
                        .clip(CircleShape)
                        .border(1.dp, colorResource(id = R.color.darker_sea_blue), CircleShape)
                )
                postResponseData.owner.data?.fullName?.let {
                    Text(
                        text = it,
                        color = colorResource(id = R.color.darker_sea_blue),
                        fontSize = 16.sp
                    )
                }
            }

            Text(
                text = stringResource(id = R.string.edit),
                color = colorResource(id = R.color.darker_sea_blue),
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .padding(end = 6.dp)
                    .clickable {
                        onEditClick(PostRequestData(postResponseData.title,
                            postResponseData.body,
                            assignmentInfo = null))
                    },
                fontSize = 12.sp
            )
        }

        Text(
            text = postResponseData.title,
            color = colorResource(id = R.color.black),
            fontSize = 20.sp,
            modifier = Modifier
                .padding(6.dp)
                .constrainAs(titleItem) {
                    top.linkTo(topItem.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Text(
            text = postResponseData.body,
            color = colorResource(id = R.color.black),
            fontSize = 16.sp,
            modifier = Modifier
                .padding(6.dp)
                .fillMaxWidth()
                .constrainAs(bodyItem) {
                    top.linkTo(titleItem.bottom)
                }
        )

        CreateCommentItem(
            profilePic = painterResource(id = R.drawable.tues_webview),
            onSendClick = onSendClick,
            modifier = Modifier
                .padding(top = 16.dp)
                .constrainAs(addCommentItem) {
                    top.linkTo(bodyItem.bottom)
                },
            commentInput = commentInput,
            onCommentChange = onCommentChange,
            postId = postResponseData._id
        )

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
                    val (alert, answers) = createRefs()

                    Text(
                        text = stringResource(id = R.string.delete_comment_popup_alert),
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
                            text = stringResource(id = R.string.yes),
                            modifier = Modifier
                                .clickable {
                                    onDeleteClick(postComments[commentMenuIndex]._id)
                                    setDialogVisibility(false)
                                    setCommentMenuIndex(-1)
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
                    }
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(commentsItem) {
                    top.linkTo(addCommentItem.bottom)
                },
            verticalArrangement = Arrangement.Top,
        ) {
            itemsIndexed(postComments) { index, data ->
                CommentItem(
                    commentData = data,
                    index = index,
                    commentIndex = commentMenuIndex,
                    onCommentClick = setCommentMenuIndex,
                    enabled = enabled,
                    setEnabled = setEnabled,
                    setEditCommentBody = setEditCommentBody,
                    onEditClick = onEditCommentClick,
                    setDialogVisibility = setDialogVisibility
                )
            }
        }
    }
}

@Composable
fun AssignmentUserScreenUi(
    postResponseData: PostResponseData,
    onEditClick: (PostRequestData) -> Unit,
    onSendClick: (CreateCommentData) -> Unit,
    commentInput: String,
    onCommentChange: (String) -> Unit,
    dialogVisibility: Boolean,
    setDialogVisibility: (Boolean) -> Unit,
    onDeleteClick: (String) -> Unit,
    postComments: SnapshotStateList<CommentData>,
    commentMenuIndex: Int,
    setCommentMenuIndex: (Int) -> Unit,
    enabled: Int,
    setEnabled: (Int) -> Unit,
    setEditCommentBody: (Pair<String, Int>) -> Unit,
    onEditCommentClick: (Pair<String, String>) -> Unit,
    onUploadAssignmentClick: (MultipartBody.Part) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .background(color = colorResource(id = R.color.dark_blue))
            .fillMaxSize()
    ) {
        val (topItem, titleItem, markItem, deadlineItem, commentsItem) = createRefs()

        Row(modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, colorResource(id = R.color.gray))
            .constrainAs(topItem) {
                top.linkTo(parent.top)
            },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.tues_webview),
                    contentDescription = stringResource(id = R.string.empty),
                    modifier = Modifier
                        .padding(4.dp)
                        .size(40.dp)
                        .clip(CircleShape)
                        .border(1.dp, colorResource(id = R.color.white), CircleShape)
                )
                postResponseData.owner.data?.fullName?.let {
                    Text(
                        text = it,
                        color = colorResource(id = R.color.white),
                        fontSize = 16.sp
                    )
                }
            }
        }

        Text(
            text = postResponseData.title,
            color = colorResource(id = R.color.white),
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(6.dp)
                .fillMaxWidth()
                .constrainAs(titleItem) {
                    top.linkTo(topItem.bottom)
                }
        )

        Text(
            text = postResponseData.assignmentInfo.deadline.toString(),
            color = colorResource(id = R.color.white),
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(6.dp)
                .fillMaxWidth()
                .constrainAs(deadlineItem) {
                    top.linkTo(titleItem.bottom)
                }
        )

        Text(
            text = stringResource(id = R.string.no_mark),
            color = colorResource(id = R.color.white),
            modifier = Modifier
                .padding(16.dp)
                .background(colorResource(id = R.color.darker_sea_blue), RoundedCornerShape(8.dp))
                .border(1.dp, colorResource(id = R.color.white), RoundedCornerShape(8.dp))
                .padding(16.dp)
                .constrainAs(markItem) {
                    top.linkTo(deadlineItem.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Column(
            modifier = Modifier
                .background(colorResource(id = R.color.white))
                .constrainAs(commentsItem) {
                    top.linkTo(markItem.bottom)
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = postResponseData.body,
                color = Color.Black,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(6.dp)
                    .fillMaxWidth()
            )

            Text(
                text = postResponseData.body,
                color = colorResource(id = R.color.darker_sea_blue),
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(6.dp)
                    .fillMaxWidth()
            )

            ResultLauncher(type = ALL_TYPES, onUploadClick = onUploadAssignmentClick, modifier = Modifier.size(50.dp), multipartName = MULTIPART_NAME_SUBMISSION)

            CreateCommentItem(
                profilePic = painterResource(id = R.drawable.tues_webview),
                onSendClick = onSendClick,
                modifier = Modifier
                    .padding(top = 16.dp),
                commentInput = commentInput,
                onCommentChange = onCommentChange,
                postId = postResponseData._id
            )

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
                        val (alert, answers) = createRefs()

                        Text(
                            text = stringResource(id = R.string.delete_comment_popup_alert),
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
                                text = stringResource(id = R.string.yes),
                                modifier = Modifier
                                    .clickable {
                                        onDeleteClick(postComments[commentMenuIndex]._id)
                                        setDialogVisibility(false)
                                        setCommentMenuIndex(-1)
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
                        }
                    }
                }
            }


            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
            ) {
                itemsIndexed(postComments) { index, data ->
                    CommentItem(
                        commentData = data,
                        index = index,
                        commentIndex = commentMenuIndex,
                        onCommentClick = setCommentMenuIndex,
                        enabled = enabled,
                        setEnabled = setEnabled,
                        setEditCommentBody = setEditCommentBody,
                        onEditClick = onEditCommentClick,
                        setDialogVisibility = setDialogVisibility
                    )
                }
            }
        }
    }
}

@Composable
fun AssignmentTeacherScreen(submissions: List<SubmissionData>){

}
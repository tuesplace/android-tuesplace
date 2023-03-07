package com.mobile.tuesplace.ui.post

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.mobile.tuesplace.PostRequestData
import com.mobile.tuesplace.R
import com.mobile.tuesplace.data.CommentData
import com.mobile.tuesplace.data.CreateCommentData
import com.mobile.tuesplace.data.PostResponseData
import com.mobile.tuesplace.ui.CommentItem
import com.mobile.tuesplace.ui.CreateCommentItem
import com.mobile.tuesplace.ui.states.GetPostCommentsUiState
import com.mobile.tuesplace.ui.states.GetPostUiState

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
    onDeleteClick: () -> Unit,
    onEditCommentClick: () -> Unit,
    enabled: Boolean,
    setEnabled: (Boolean) -> Unit,
    setEditCommentBody: (Pair<String, Int>) -> Unit,
    commentData: List<CommentData>
) {
    when (getPostUiState) {
        GetPostUiState.Empty -> {}
        GetPostUiState.Loading -> {}
        is GetPostUiState.Error -> {}
        is GetPostUiState.Success -> {
            onPostSuccess()
            when (getPostCommentsUiState) {
                GetPostCommentsUiState.Empty -> {}
                is GetPostCommentsUiState.Error -> {}
                GetPostCommentsUiState.Loading -> {}
                is GetPostCommentsUiState.Success -> {
                    PostUi(
                        postResponseData = getPostUiState.post,
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
                        setEditCommentBody = setEditCommentBody
                    )
                }
            }
        }
    }
}

@Composable
fun PostUi(
    postResponseData: PostResponseData,
    commentInput: String,
    onCommentChange: (String) -> Unit,
    postComments: List<CommentData>,
    onSendClick: (CreateCommentData) -> Unit,
    onEditClick: (PostRequestData) -> Unit,
    commentMenuIndex: Int,
    setCommentMenuIndex: (Int) -> Unit,
    onDeleteClick: () -> Unit,
    onEditCommentClick: () -> Unit,
    enabled: Boolean,
    setEnabled: (Boolean) -> Unit,
    setEditCommentBody: (Pair<String, Int>) -> Unit,
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
                    onDeleteClick = onDeleteClick,
                    enabled = enabled,
                    setEnabled = setEnabled,
                    setEditCommentBody = setEditCommentBody,
                    onEditClick = { onEditCommentClick() }
                )
            }
        }
    }
}
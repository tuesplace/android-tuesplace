package com.mobile.tuesplace.ui.classroom

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.mobile.tuesplace.R
import com.mobile.tuesplace.data.GroupData
import com.mobile.tuesplace.data.PostResponseData
import com.mobile.tuesplace.ui.PostItem
import com.mobile.tuesplace.ui.states.GetGroupUiState
import com.mobile.tuesplace.ui.states.GetPostsUiState

@Composable
fun ClassroomUserScreen(
    setProfile: (String) -> Unit,
    getGroupUiState: GetGroupUiState,
    onGroupSuccess: () -> Unit,
    getPostsUiState: GetPostsUiState,
    onCreatePostClick: () -> Unit,
    onEditPostClick: () -> Unit,
    onSendCommentClick: (String) -> Unit,
    onPostClick: () -> Unit,
    comment: String,
    setComment: (String) -> Unit,
) {
    var group = GroupData("", null, "", null)
    when (getGroupUiState) {
        GetGroupUiState.Empty -> {}
        is GetGroupUiState.Error -> {}
        GetGroupUiState.Loading -> {}
        is GetGroupUiState.Success -> {
            onGroupSuccess()
            group = getGroupUiState.groupData
        }
    }

    when(getPostsUiState){
        GetPostsUiState.Empty -> {}
        is GetPostsUiState.Error -> {}
        GetPostsUiState.Loading -> {}
        is GetPostsUiState.Success -> {
            val posts = getPostsUiState.groups
            ClassroomUserUi(group = group,
                posts = getPostsUiState.groups,
                onCreatePostClick,
                onEditPostClick,
                onSendCommentClick,
                onPostClick, comment, setComment)
        }
    }
}

@Composable
fun ClassroomUserUi(
    group: GroupData,
    posts: List<PostResponseData>,
    onAddClick: () -> Unit,
    onEditPostClick: () -> Unit,
    onSendCommentClick: (String) -> Unit,
    onPostClick: () -> Unit,
    comment: String,
    setComment: (String) -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dark_blue))
    ) {
        val (title, post, add) = createRefs()
        Text(
            text = group.name,
            modifier = Modifier
                .padding(6.dp)
                .constrainAs(title) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            fontSize = 25.sp
        )

        Text(
            text = stringResource(id = R.string.add),
            modifier = Modifier
                .padding(16.dp)
                .background(colorResource(id = R.color.dark_blue), RoundedCornerShape(8.dp))
                .padding(2.dp)
                .clickable { onAddClick() }
                .constrainAs(add) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                },
            color = Color.White
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .constrainAs(post) {
                    top.linkTo(title.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            itemsIndexed(posts) { _, data ->
                PostItem(
                    post = data,
                    onPostClick = onEditPostClick,
                    onSendClick = onSendCommentClick,
                    onViewCommentsClick = onPostClick,
                    commentInput = comment,
                    onCommentChange = setComment)
            }
        }
    }
}
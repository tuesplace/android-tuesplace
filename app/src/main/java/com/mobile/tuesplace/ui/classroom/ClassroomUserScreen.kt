package com.mobile.tuesplace.ui.classroom

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.mobile.tuesplace.R
import com.mobile.tuesplace.data.GroupData
import com.mobile.tuesplace.data.GroupResponseData
import com.mobile.tuesplace.data.PostResponseData
import com.mobile.tuesplace.ui.AssignmentItem
import com.mobile.tuesplace.ui.PostItem
import com.mobile.tuesplace.ui.states.GetGroupUiState
import com.mobile.tuesplace.ui.states.GetPostsUiState

@Composable
fun ClassroomUserScreen(
    getGroupUiState: GetGroupUiState,
    onGroupSuccess: () -> Unit,
    getPostsUiState: GetPostsUiState,
    onCreatePostClick: () -> Unit,
    onEditPostClick: () -> Unit,
    onPostClick: (Pair<String, Boolean>) -> Unit,
    group: GroupResponseData?
) {
    when (getGroupUiState) {
        GetGroupUiState.Empty -> {}
        is GetGroupUiState.Error -> {}
        GetGroupUiState.Loading -> {}
        is GetGroupUiState.Success -> {
            onGroupSuccess()
        }
        is GetGroupUiState.Loaded -> {}
    }

    when (getPostsUiState) {
        GetPostsUiState.Empty -> {}
        is GetPostsUiState.Error -> {}
        GetPostsUiState.Loading -> {}
        is GetPostsUiState.Success -> {
            if (group != null) {
                ClassroomUserUi(
                    group = group,
                    posts = getPostsUiState.groups,
                    onCreatePostClick,
                    onEditPostClick,
                    onPostClick,
                )
            }
        }
    }
}

@Composable
fun ClassroomUserUi(
    group: GroupResponseData,
    posts: List<PostResponseData>,
    onAddClick: () -> Unit,
    onEditPostClick: () -> Unit,
    onPostClick: (Pair<String, Boolean>) -> Unit
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

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .constrainAs(post) {
                    top.linkTo(title.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
        ) {
            itemsIndexed(posts) { _, data ->
                if (data.assignmentInfo.isAssignment) {
                    AssignmentItem(post = data, modifier = null, onAssignmentClick = onPostClick)
                } else {
                    PostItem(post = data, onPostClick = onPostClick)
                }
            }
        }

        Image(
            painter = painterResource(id = R.drawable.add_icon),
            contentDescription = stringResource(id = R.string.add),
            modifier = Modifier
                .size(50.dp)
                .padding(6.dp)
                .clickable { onAddClick() }
                .constrainAs(add) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }
        )
    }

}
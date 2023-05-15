package com.mobile.tuesplace.ui.chats

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import com.mobile.tuesplace.R
import com.mobile.tuesplace.data.CreateCommentData
import com.mobile.tuesplace.data.GroupResponseData
import com.mobile.tuesplace.data.ProfileResponseData
import com.mobile.tuesplace.ui.CreateCommentItem
import com.mobile.tuesplace.ui.Loading
import com.mobile.tuesplace.ui.states.GetGroupUiState

@Composable
fun ChatroomScreen(
    getGroupUiState: GetGroupUiState,
    onSendClick: (CreateCommentData) -> Unit,
    commentInput: String,
    onCommentChange: (String) -> Unit
) {
    when (getGroupUiState) {
        GetGroupUiState.Empty -> {}
        is GetGroupUiState.Error -> {
            Toast.makeText(
                LocalContext.current,
                getGroupUiState.exception ?: stringResource(R.string.create_error),
                Toast.LENGTH_LONG
            ).show()
        }
        GetGroupUiState.Loading -> {
            Loading()
        }
        is GetGroupUiState.Success -> {
            ChatroomUi(group = getGroupUiState.groupData, onSendClick, commentInput, onCommentChange)
        }
        is GetGroupUiState.Loaded -> {}
    }
}

@Composable
fun ChatroomUi(
    group: GroupResponseData,
    onSendClick: (CreateCommentData) -> Unit,
    commentInput: String,
    onCommentChange: (String) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dark_blue))
    ) {
        val (title, chatsItems, createCommentItem) = createRefs()
        Text(
            text = group.name,
            color = White,
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })
        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .constrainAs(chatsItems) {
                top.linkTo(title.bottom)
                start.linkTo(parent.start)
            }) {
//                itemsIndexed(chats) { _, data ->
//                    ChatItem(chat = data, onChatClick)
//                }
        }
// myProfileResponseData.assets.profilePic[0].data?.let { it.src }
        CreateCommentItem(
            profilePic = null,
            onSendClick = onSendClick,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(createCommentItem) {
                    bottom.linkTo(parent.bottom)
                },
            commentInput = commentInput,
            onCommentChange = onCommentChange,
            postId = group._id
        )
    }
}

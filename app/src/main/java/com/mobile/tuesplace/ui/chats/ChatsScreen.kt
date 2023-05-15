package com.mobile.tuesplace.ui.chats

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.mobile.tuesplace.R
import com.mobile.tuesplace.data.GroupResponseData
import com.mobile.tuesplace.ui.EmptyScreen
import com.mobile.tuesplace.ui.Loading
import com.mobile.tuesplace.ui.states.GetMyGroupsUiState

@Composable
fun ChatsScreen(getMyGroupsUiState: GetMyGroupsUiState, onChatClick: (String) -> Unit) {
    when (getMyGroupsUiState) {
        GetMyGroupsUiState.Empty -> {}
        is GetMyGroupsUiState.Error -> {
            Toast.makeText(
                LocalContext.current,
                getMyGroupsUiState.exception ?: stringResource(R.string.create_error),
                Toast.LENGTH_LONG
            ).show()
        }
        GetMyGroupsUiState.Loading -> {
            Loading()
        }
        is GetMyGroupsUiState.Success -> {
            if (getMyGroupsUiState.groups.isEmpty()) {
                EmptyScreen()
            } else {
                ChatsUi(chats = getMyGroupsUiState.groups, onChatClick = onChatClick)
            }
        }
    }
}

@Composable
fun ChatsUi(chats: List<GroupResponseData>, onChatClick: (String) -> Unit) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dark_blue))
    ) {
        val (title, chatsItems) = createRefs()

        Text(
            text = stringResource(id = R.string.groups),
            fontSize = 24.sp,
            color = colorResource(id = R.color.white),
            modifier = Modifier
                .padding(16.dp)
                .constrainAs(title) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
        )
        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .constrainAs(chatsItems) {
                top.linkTo(title.bottom)
                start.linkTo(parent.start)
            }) {
            itemsIndexed(chats) { _, data ->
                ChatItem(chat = data, onChatClick)
            }
        }
    }
}

@Composable
fun ChatItem(chat: GroupResponseData, onChatClick: (String) -> Unit) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp)
            .background(colorResource(id = R.color.dark_blue))
            .border(1.dp, colorResource(id = R.color.baby_blue))
            .clickable { onChatClick(chat._id) }
    ) {
        val (name) = createRefs()

        Text(
            text = chat.name,
            color = colorResource(id = R.color.baby_blue),
            modifier = Modifier
                .padding(6.dp)
                .constrainAs(name) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                }
        )
    }
}
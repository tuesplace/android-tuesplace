package com.mobile.tuesplace.ui.chats

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.mobile.tuesplace.data.GroupResponseData
import com.mobile.tuesplace.ui.EmptyScreen
import com.mobile.tuesplace.ui.states.GetMyGroupsUiState
import com.mobile.tuesplace.ui.theme.BabyBlue

@Composable
fun ChatsScreen(getMyGroupsUiState: GetMyGroupsUiState, onChatClick: (String) -> Unit) {
    when (getMyGroupsUiState) {
        GetMyGroupsUiState.Empty -> {}
        is GetMyGroupsUiState.Error -> {}
        GetMyGroupsUiState.Loading -> {}
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
    Box(modifier = Modifier
        .fillMaxSize()
        .background(BabyBlue)
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
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
            .height(30.dp)
            .background(White)
            .border(1.dp, Gray)
            .clickable { onChatClick(chat._id) }
    ) {
        val (name) = createRefs()

        Text(
            text = chat.name,
            color = Black,
            modifier = Modifier
                .padding(6.dp)
                .constrainAs(name) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )
    }
}
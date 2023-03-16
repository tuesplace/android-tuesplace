package com.mobile.tuesplace.ui.chats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.constraintlayout.compose.ConstraintLayout
import com.mobile.tuesplace.data.GroupData
import com.mobile.tuesplace.data.GroupResponseData
import com.mobile.tuesplace.ui.states.GetGroupUiState
import com.mobile.tuesplace.ui.theme.BabyBlue

@Composable
fun ChatroomScreen(getGroupUiState: GetGroupUiState) {
    when (getGroupUiState) {
        GetGroupUiState.Empty -> {}
        is GetGroupUiState.Error -> {}
        GetGroupUiState.Loading -> {}
        is GetGroupUiState.Success -> {
            ChatroomUi(group = getGroupUiState.groupData)
        }
        is GetGroupUiState.Loaded -> {}
    }
}

@Composable
fun ChatroomUi(group: GroupResponseData){
    ConstraintLayout(modifier = Modifier
        .fillMaxSize()
        .background(BabyBlue)
    ) {
        val (title) = createRefs()
        Text(
            text = group.name,
            color =  White,
            modifier = Modifier
                .constrainAs(title){
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })
    }
}

@Composable
fun MessageItem(){

}
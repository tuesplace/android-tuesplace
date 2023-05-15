package com.mobile.tuesplace.ui.videoroom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.mobile.tuesplace.R
import com.mobile.tuesplace.data.GroupResponseData
import com.mobile.tuesplace.ui.Loading
import com.mobile.tuesplace.ui.states.GetMyGroupsUiState

@Composable
fun VideoroomScreen(
    groupsUiState: GetMyGroupsUiState,
    onBackPressed: () -> Unit
) {
    when (groupsUiState) {
        GetMyGroupsUiState.Empty -> {}
        is GetMyGroupsUiState.Error -> {}
        GetMyGroupsUiState.Loading -> {
            Loading()
        }
        is GetMyGroupsUiState.Success -> {
            VideoroomUi(groups = groupsUiState.groups, onBackPressed)
        }
    }
}

@Composable
fun VideoroomUi(
    groups: List<GroupResponseData>,
    onBackPressed: () -> Unit
){


    ConstraintLayout(modifier = Modifier
        .fillMaxSize()
        .background(Color.Gray)) {
        LazyColumn() {
            itemsIndexed(groups) { _, group ->
                classItem(group = group)
            }
        }
    }
}

@Composable
fun classItem(group: GroupResponseData){
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = 2.dp,
        backgroundColor = colorResource(id = R.color.logo_blue),
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = group.name)
        }
    }
}
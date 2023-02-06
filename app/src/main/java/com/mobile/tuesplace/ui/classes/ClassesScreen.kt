package com.mobile.tuesplace.ui.classes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.mobile.tuesplace.R
import com.mobile.tuesplace.data.GroupResponseData
import com.mobile.tuesplace.ui.EmptyScreen
import com.mobile.tuesplace.ui.states.GetMyGroupsUiState

@Composable
fun ClassesScreen(
    onClassClick: (String) -> Unit,
    getMyGroupsUiState: GetMyGroupsUiState,
) {
    when (getMyGroupsUiState) {
        GetMyGroupsUiState.Empty -> {}
        is GetMyGroupsUiState.Error -> {}
        GetMyGroupsUiState.Loading -> {}
        is GetMyGroupsUiState.Success -> {
            if (getMyGroupsUiState.groups.isEmpty()) {
                EmptyScreen()
            } else {
                ClassesUi(onClassClick = onClassClick, groups = getMyGroupsUiState.groups)
            }
        }
    }
}

@Composable
fun ClassesUi(onClassClick: (String) -> Unit, groups: List<GroupResponseData>) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dark_blue))
    ) {
        LazyColumn {
            itemsIndexed(groups) { _, data ->
                classItem(groupData = data, onClassClick = onClassClick)
            }
        }
    }
}

@Composable
fun classItem(
    groupData: GroupResponseData,
    onClassClick: (String) -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable { onClassClick(groupData._id) },
        elevation = 2.dp,
        backgroundColor = colorResource(id = R.color.logo_blue),
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            //Text(text = groupData. + " - " + subjectData.duration)
            Text(text = groupData.name)
        }
    }
}
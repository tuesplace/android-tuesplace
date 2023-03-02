package com.mobile.tuesplace.ui.classes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.mobile.tuesplace.R
import com.mobile.tuesplace.data.GroupResponseData
import com.mobile.tuesplace.ui.EmptyScreen
import com.mobile.tuesplace.ui.GroupClassItem
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
                GroupClassItem(groupData = data, onGroupClick = onClassClick)
            }
        }
    }
}

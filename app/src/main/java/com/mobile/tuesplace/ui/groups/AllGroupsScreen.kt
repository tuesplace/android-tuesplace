package com.mobile.tuesplace.ui.groups

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.mobile.tuesplace.R
import com.mobile.tuesplace.data.GroupResponseData
import com.mobile.tuesplace.ui.states.GetGroupsUiState

@Composable
fun AllGroupsScreen(allGroupsUiState: GetGroupsUiState,
                    onGroupClick: (GroupResponseData) -> Unit,
                    onAddClick: () -> Unit) {
    when (allGroupsUiState) {
        GetGroupsUiState.Empty -> { }
        is GetGroupsUiState.Error -> { }
        GetGroupsUiState.Loading -> { }
        is GetGroupsUiState.Success -> {
            AllGroupsUi(
                groups = allGroupsUiState.groups,
                onGroupClick = onGroupClick,
                onAddClick = onAddClick
            )
        }
    }
}

@Composable
fun AllGroupsUi(
    groups: List<GroupResponseData>,
    onGroupClick: (GroupResponseData) -> Unit,
    onAddClick: () -> Unit
){
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.gray))
    ) {
        val (addGroup, group) = createRefs()

        Box(modifier = Modifier
            .padding(6.dp)
            .width(100.dp)
            .height(70.dp)
            .constrainAs(addGroup) {
                top.linkTo(parent.top)
                end.linkTo(parent.end)
            }
            .padding(PaddingValues(16.dp))
            .background(colorResource(id = R.color.dark_blue), RoundedCornerShape(8.dp))
            .clickable { onAddClick() }
        ){
            Text(text = stringResource(id = R.string.add), color = Color.White, textAlign = TextAlign.Center)
        }

        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .constrainAs(group) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }) {
            itemsIndexed(groups) { _, group ->
                GroupItem(groupData = group, onGroupClick = onGroupClick)
            }
        }
    }
}

@Composable
fun GroupItem(groupData: GroupResponseData, onGroupClick: (GroupResponseData) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(5.dp)
            .border(1.dp, Color.White, RoundedCornerShape(8.dp))
            .background(colorResource(id = R.color.logo_blue), RoundedCornerShape(8.dp))
            .clickable {
                onGroupClick(groupData)
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = groupData.name,
            fontSize = 20.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
@Preview
fun GroupItemPreview() {
    AllGroupsScreen(GetGroupsUiState.Empty, {}) {}
}


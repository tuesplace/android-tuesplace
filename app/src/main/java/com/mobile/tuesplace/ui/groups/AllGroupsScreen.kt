package com.mobile.tuesplace.ui.groups

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.mobile.tuesplace.R
import com.mobile.tuesplace.data.GroupResponseData
import com.mobile.tuesplace.ui.GroupChatItem
import com.mobile.tuesplace.ui.GroupClassItem
import com.mobile.tuesplace.ui.buttonChangeColorOnClick
import com.mobile.tuesplace.ui.states.GetGroupsUiState

@Composable
fun AllGroupsScreen(
    allGroupsUiState: GetGroupsUiState,
    onGroupClick: (String) -> Unit,
    onAddClick: () -> Unit,
    groupsType: Boolean,
    setGroupsType: (Boolean) -> Unit
) {
    when (allGroupsUiState) {
        GetGroupsUiState.Empty -> {}
        is GetGroupsUiState.Error -> {}
        GetGroupsUiState.Loading -> {}
        is GetGroupsUiState.Success -> {
            if (groupsType){
                AllGroupsUi(
                    groups = allGroupsUiState.groups.filter { group ->  group.type == "chat" },
                    onGroupClick = onGroupClick,
                    onAddClick = onAddClick,
                    groupsType = true,
                    setGroupsType = setGroupsType
                )
            }else{
                AllGroupsUi(
                    groups = allGroupsUiState.groups.filter { group ->  group.type == "subject" },
                    onGroupClick = onGroupClick,
                    onAddClick = onAddClick,
                    groupsType = false,
                    setGroupsType = setGroupsType
                )
            }
        }
    }
}

@Composable
fun AllGroupsUi(
    groups: List<GroupResponseData>,
    onGroupClick: (String) -> Unit,
    onAddClick: () -> Unit,
    groupsType: Boolean,
    setGroupsType: (Boolean) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dark_blue))
    ) {
        val (allGroups, groupTypes, addGroup, groupsList) = createRefs()

        Text(
            text = stringResource(id = R.string.all_groups),
            fontSize = 30.sp,
            color = colorResource(id = R.color.white),
            modifier = Modifier
                .padding(16.dp)
                .constrainAs(allGroups) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }
        )
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(groupTypes){
                    top.linkTo(allGroups.bottom)
                },
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            buttonChangeColorOnClick(
                text = stringResource(id = R.string.classes),
                colorState = groupsType,
                setColor = setGroupsType
            )

            buttonChangeColorOnClick(
                text = stringResource(id = R.string.chats),
                colorState = !groupsType,
                setColor = { setGroupsType(groupsType) }
            )
        }

        Text(
            text = stringResource(id = R.string.create_new),
            fontSize = 25.sp,
            color = colorResource(id = R.color.baby_blue),
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .padding(16.dp)
                .clickable { onAddClick() }
                .constrainAs(addGroup) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(groupTypes.bottom)
                }
        )

        LazyColumn(modifier = Modifier
            .constrainAs(groupsList) {
                top.linkTo(addGroup.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        ) {
            itemsIndexed(groups) { _, group ->
                if (groupsType){
                    GroupChatItem(groupData = group, onGroupClick = { onGroupClick(it) })
                } else {
                    GroupClassItem(groupData = group, onGroupClick = { onGroupClick(it) })
                }
            }
        }
    }
}

@Composable
@Preview
fun GroupItemPreview() {
    AllGroupsScreen(GetGroupsUiState.Empty, {}, {},false) {}
}


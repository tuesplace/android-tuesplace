package com.mobile.tuesplace.ui.classroom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.mobile.tuesplace.R
import com.mobile.tuesplace.data.GroupData
import com.mobile.tuesplace.data.PostData
import com.mobile.tuesplace.data.ProfileData
import com.mobile.tuesplace.ui.PostItem
import com.mobile.tuesplace.ui.states.GetGroupUiState
import com.mobile.tuesplace.ui.states.GetProfileByIdUiState

@Composable
fun ClassroomScreen(
    setProfile: (String) -> Unit,
    getGroupUiState: GetGroupUiState,
    getProfileByIdUiState: GetProfileByIdUiState
) {
    val group: GroupData
    when (getGroupUiState) {
        GetGroupUiState.Empty -> { }
        is GetGroupUiState.Error -> { }
        GetGroupUiState.Loading -> { }
        is GetGroupUiState.Success -> {
            group = getGroupUiState.groupData
            setProfile(group.name)
            when (getProfileByIdUiState) {
                GetProfileByIdUiState.Empty -> { }
                is GetProfileByIdUiState.Error -> { }
                GetProfileByIdUiState.Loading -> { }
                is GetProfileByIdUiState.Success -> {
                    ClassroomUi(group = group, posts = listOf(), teacher = getProfileByIdUiState.profile )
                }
            }
        }
    }


}

@Composable
fun  ClassroomUi(group: GroupData, posts: List<PostData>, teacher: ProfileData){
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.baby_blue))
    ){
        val (title, post) = createRefs()
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
                }
        ){
            itemsIndexed(posts) { _, data ->
                PostItem(post = data)
            }
        }
    }
}


@Preview
@Composable
fun Preview(){
    //PostItem(post = PostData("", listOf(), "", "", "This is a test. I want to see how exactly the post will look like. I don't know what to write."))
    ClassroomUi(group = GroupData("12B", arrayListOf(), "", arrayListOf()),
        posts = listOf(PostData("", listOf(), "2023-01-31T18:27:34.464Z", "", "This is a test. I want to see how exactly the post will look like. I don't know what to write."), PostData("", listOf(), "", "", "This is a test. I want to see how exactly the post will look like. I don't know what to write.")), ProfileData("Dora Tsvetanova", "d", "", ""))
}
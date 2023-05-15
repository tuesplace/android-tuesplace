package com.mobile.tuesplace.ui.activities

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.mobile.tuesplace.R
import com.mobile.tuesplace.data.ProfileResponseData
import com.mobile.tuesplace.ui.EmptyScreen
import com.mobile.tuesplace.ui.Loading
import com.mobile.tuesplace.ui.SearchView
import com.mobile.tuesplace.ui.StudentItem
import com.mobile.tuesplace.ui.states.GetAllProfilesUiState

@Composable
fun ActivitiesTeachersScreen(
    getAllProfileUiState: GetAllProfilesUiState,
    onTeacherClick: (String) -> Unit,
) {
    when (getAllProfileUiState) {
        GetAllProfilesUiState.Empty -> {
            EmptyScreen()
        }
        is GetAllProfilesUiState.Error -> {}
        GetAllProfilesUiState.Loading -> {
            Loading()
        }
        is GetAllProfilesUiState.Success -> {
            ActivitiesTeachersUi(
                list = getAllProfileUiState.profiles.filter { profile ->
                profile.role == stringResource(id = R.string.teacher_role) },
                onTeacherClick = onTeacherClick
            )
        }
    }
}

@Composable
fun ActivitiesTeachersUi(
    list: List<ProfileResponseData>,
    onTeacherClick: (String) -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dark_blue))
    ) {
        val (title, search, itemsList) = createRefs()

        Text(
            text = stringResource(id = R.string.teachers_agenda),
            color = colorResource(id = R.color.white),
            fontSize = 20.sp,
            modifier = Modifier
                .padding(6.dp)
                .constrainAs(title) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        val textState = remember { mutableStateOf(TextFieldValue("")) }

        SearchView(
            state = textState,
            modifier = Modifier
                .padding(16.dp)
                .border(
                    1.dp,
                    colorResource(id = R.color.darker_sea_blue),
                    RoundedCornerShape(8.dp)
                )
                .constrainAs(search) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(title.bottom)
                },
            placeholder = stringResource(id = R.string.search_by_name)
        )

        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
                .background(colorResource(id = R.color.dark_blue))
                .constrainAs(itemsList) {
                    top.linkTo(search.bottom)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                },
            columns = GridCells.Adaptive(minSize = 150.dp)
        ) {
            itemsIndexed(list.filter { profile -> profile.fullName.contains(textState.value.text) }) { _, data ->
                StudentItem(student = data, onClick = onTeacherClick)
            }
        }
    }
}


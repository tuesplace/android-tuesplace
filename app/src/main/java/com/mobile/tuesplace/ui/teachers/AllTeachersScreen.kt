package com.mobile.tuesplace.ui.teachers

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.style.TextDecoration
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
fun AllTeachersScreen(
    getAllProfilesUiState: GetAllProfilesUiState,
    onTeacherClick: (String) -> Unit,
    onCreateNewClick: () -> Unit,
) {
    when (getAllProfilesUiState) {
        GetAllProfilesUiState.Empty -> {
            EmptyScreen()
        }
        is GetAllProfilesUiState.Error -> {

        }
        GetAllProfilesUiState.Loading -> {
            Loading()
        }
        is GetAllProfilesUiState.Success -> {
            AllTeachersUi(
                profiles = getAllProfilesUiState.profiles.filter { profile -> profile.role == "teacher" },
                onStudentClick = onTeacherClick,
                onCreateNewClick = onCreateNewClick
            )
        }
    }
}

@Composable
fun AllTeachersUi(
    profiles: List<ProfileResponseData>,
    onStudentClick: (String) -> Unit,
    onCreateNewClick: () -> Unit,
) {
    ConstraintLayout(modifier = Modifier
        .fillMaxSize()
        .background(colorResource(id = R.color.dark_blue))
    ) {
        val (searchView, allStudents, addStudent, studentsList) = createRefs()

        val textState = remember { mutableStateOf(TextFieldValue("")) }

        Text(
            text = stringResource(id = R.string.all_teachers),
            fontSize = 30.sp,
            color = colorResource(id = R.color.white),
            modifier = Modifier
                .padding(16.dp)
                .constrainAs(allStudents) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }
        )

        Text(
            text = stringResource(id = R.string.create_new_profile),
            fontSize = 25.sp,
            color = colorResource(id = R.color.baby_blue),
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .padding(16.dp)
                .clickable { onCreateNewClick() }
                .constrainAs(addStudent) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(allStudents.bottom)
                }
        )

        SearchView(
            state = textState,
            modifier = Modifier
                .padding(16.dp)
                .border(
                    1.dp,
                    colorResource(id = R.color.darker_sea_blue),
                    RoundedCornerShape(8.dp)
                )
                .constrainAs(searchView) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(addStudent.bottom)
                },
            placeholder = stringResource(id = R.string.search_by_name)
        )

        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
                .background(colorResource(id = R.color.dark_blue))
                .constrainAs(studentsList) {
                    top.linkTo(searchView.bottom)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                },
            columns = GridCells.Adaptive(minSize = 150.dp)
        ) {
            itemsIndexed(profiles.filter { profile -> profile.fullName.contains(textState.value.text) }) { _, data ->
                StudentItem(student = data, onClick = onStudentClick)
            }
        }
    }
}

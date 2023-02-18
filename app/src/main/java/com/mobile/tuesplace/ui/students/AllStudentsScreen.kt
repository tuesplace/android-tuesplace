package com.mobile.tuesplace.ui.students

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.mobile.tuesplace.data.ProfileResponseData
import com.mobile.tuesplace.ui.states.GetAllProfilesUiState
import com.mobile.tuesplace.R
import com.mobile.tuesplace.ui.EmptyScreen

@Composable
fun AllStudentsScreen(
    getAllProfilesUiState: GetAllProfilesUiState,
    onStudentClick: (String) -> Unit,
) {
    when (getAllProfilesUiState) {
        GetAllProfilesUiState.Empty -> {
            EmptyScreen()
        }
        is GetAllProfilesUiState.Error -> {
            val error = getAllProfilesUiState.exception
        }
        GetAllProfilesUiState.Loading -> {}
        is GetAllProfilesUiState.Success -> {
            AllStudentsUi(profiles = getAllProfilesUiState.profiles,
                onStudentClick = onStudentClick)
        }
    }
}

@Composable
fun AllStudentsUi(profiles: List<ProfileResponseData>, onStudentClick: (String) -> Unit) {

    ConstraintLayout( modifier = Modifier
        .fillMaxSize()
        .background(colorResource(id = R.color.dark_blue))
    ) {
        val (allStudents, addStudent, studentsList) = createRefs()

        Text(
            text = stringResource(id = R.string.all_groups),
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
            text = stringResource(id = R.string.create_new),
            fontSize = 25.sp,
            color = colorResource(id = R.color.baby_blue),
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .padding(16.dp)
                .clickable { }
                .constrainAs(addStudent) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(allStudents.bottom)
                }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.dark_blue))
                .constrainAs(studentsList){
                    top.linkTo(addStudent.bottom)
                }
        ) {
            itemsIndexed(profiles) { _, data ->
                com.mobile.tuesplace.ui.StudentItem(student = data)
            }
        }
    }
}

@Composable
fun StudentItem(student: ProfileResponseData, onStudentClick: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable { onStudentClick(student._id) }
            .background(Color.Gray),
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            Card(
                modifier = Modifier
                    .padding(6.dp)
                    .size(50.dp)
                    .border(1.dp, Color.White, RoundedCornerShape(8.dp))
            ) {

            }
            Text(text = student.fullName,
                color = Color.Black,
                modifier = Modifier.padding(top = 16.dp))
        }
    }
}
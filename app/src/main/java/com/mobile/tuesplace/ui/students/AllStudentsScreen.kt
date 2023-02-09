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
import androidx.compose.ui.unit.dp
import com.mobile.tuesplace.data.ProfileResponseData
import com.mobile.tuesplace.ui.states.GetAllProfilesUiState
import com.mobile.tuesplace.ui.theme.BabyBlue

@Composable
fun AllStudentsScreen(getAllProfilesUiState: GetAllProfilesUiState, onStudentClick: (String) -> Unit) {
    when(getAllProfilesUiState) {
        GetAllProfilesUiState.Empty -> { }
        is GetAllProfilesUiState.Error -> { }
        GetAllProfilesUiState.Loading -> { }
        is GetAllProfilesUiState.Success -> {
            AllStudentsUi(profiles = getAllProfilesUiState.profiles, onStudentClick = onStudentClick)
        }
    }
}

@Composable
fun AllStudentsUi(profiles: List<ProfileResponseData>, onStudentClick: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BabyBlue)
    ) {
        itemsIndexed(profiles) { _, data ->
            StudentItem(student = data, onStudentClick)
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
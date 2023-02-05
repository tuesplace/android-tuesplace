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
import com.mobile.tuesplace.data.SubjectData

@Composable
fun ClassesScreen(onClassClick: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dark_blue))
    ) {
        val list = listOf(SubjectData("Math", startTime = "12", duration = "13"), SubjectData("BEL", startTime = "12", duration = "13"), SubjectData("History", startTime = "12", duration = "13"))
        LazyColumn{
            itemsIndexed(list) { _, data ->
                classItem(subjectData = data, onClassClick = onClassClick)
            }
        }
    }
}

@Composable
fun classItem(
    subjectData: SubjectData,
    onClassClick: (String) -> Unit
){
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable { onClassClick(subjectData.subjectName) },
        elevation = 2.dp,
        backgroundColor = colorResource(id = R.color.logo_blue),
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = subjectData.startTime + " - " + subjectData.duration)
            Text(text = subjectData.subjectName)
        }
    }
}
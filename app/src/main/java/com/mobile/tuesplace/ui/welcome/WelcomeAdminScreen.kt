package com.mobile.tuesplace.ui.welcome

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

@Composable
fun WelcomeAdminScreen(onClick: (String) -> Unit) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.gray))
    ) {
        val allGroups = stringResource(id = R.string.all_groups)
        val addStudent = stringResource(id = R.string.add_student)
        val removeStudent = stringResource(id = R.string.remove_student)
        val (menu) = createRefs()

        LazyColumn(
            modifier = Modifier
                .padding(8.dp)
                .constrainAs(menu) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        ) {
            itemsIndexed(listOf(allGroups, addStudent, removeStudent)) { _, data ->
                MenuItem(text = data, onClick)
            }
        }
    }

}


@Composable
fun MenuItem(text: String, onClick: (String) -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(5.dp)
            .border(1.dp, Color.White, RoundedCornerShape(8.dp))
            .background(colorResource(id = R.color.royal_blue), RoundedCornerShape(8.dp))
            .clickable {
                onClick(text)
            },
        contentAlignment = Alignment.Center
    ) {
            Text(
                text = text,
                fontSize = 20.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
            )
    }
}

@Composable
@Preview
fun MenuItemPreview() {
    //MenuItem(text = "Create Group") { }
    WelcomeAdminScreen {

    }
}
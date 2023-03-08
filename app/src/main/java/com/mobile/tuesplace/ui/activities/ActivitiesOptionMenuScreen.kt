package com.mobile.tuesplace.ui.activities

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mobile.tuesplace.R
import com.mobile.tuesplace.ui.MenuItem

@Composable
fun ActivitiesOptionMenuScreen(
    onStudentsClick: () -> Unit,
    onTeachersClick: () -> Unit,
    onChangeAgendaClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dark_blue))
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.dark_blue)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MenuItem(
                image = painterResource(id = R.drawable.students),
                string = stringResource(id = R.string.students_agenda),
                modifier = Modifier.padding(6.dp),
                onClick = onStudentsClick
            )

            MenuItem(
                image = painterResource(id = R.drawable.teacher_icon),
                string = stringResource(id = R.string.teachers_agenda),
                modifier = Modifier.padding(6.dp),
                onClick = onTeachersClick
            )

            MenuItem(
                image = painterResource(id = R.drawable.agenda),
                string = stringResource(id = R.string.change_agenda),
                modifier = Modifier.padding(6.dp),
                onClick = onChangeAgendaClick
            )
        }

    }
}
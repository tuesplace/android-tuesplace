package com.mobile.tuesplace.ui.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.mobile.tuesplace.R
import com.mobile.tuesplace.ui.WebViewImage

@Composable
fun WelcomeAdminScreen(
    onLinkClick: () -> Unit,
    onGroupsClick: () -> Unit,
    onStudentsClick: () -> Unit,
    onTeachersClick: () -> Unit,
    onAgendaClick: () -> Unit
) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dark_blue))
    ) {
//        val addStudent = stringResource(id = R.string.add_student)
//        val removeStudent = stringResource(id = R.string.all_students)
//        val agenda = stringResource(id = R.string.agenda)

        val (webView, groups, students, teachers, agenda) = createRefs()

        WebViewImage(
            onImageClick = { onLinkClick() },
            image = painterResource(id = R.drawable.tues_webview),
            text = stringResource(id = R.string.link_to_website),
            modifier = Modifier
                .height(200.dp)
                .constrainAs(webView) {
                    top.linkTo(parent.top)
                }
        )

        com.mobile.tuesplace.ui.MenuItem(
            image = painterResource(id = R.drawable.groups),
            string = stringResource(id = R.string.groups),
            modifier = Modifier
                .padding(6.dp)
                .padding(top = 50.dp)
                .constrainAs(groups) {
                    top.linkTo(webView.bottom)
                },
            onClick = { onGroupsClick() }
        )

        com.mobile.tuesplace.ui.MenuItem(
            image = painterResource(id = R.drawable.students),
            string = stringResource(id = R.string.students),
            modifier = Modifier
                .padding(6.dp)
                .constrainAs(students) {
                    top.linkTo(groups.bottom)
                },
            onClick = { onStudentsClick() }
        )

        com.mobile.tuesplace.ui.MenuItem(
            image = painterResource(id = R.drawable.teacher_icon),
            string = stringResource(id = R.string.teachers),
            modifier = Modifier
                .padding(6.dp)
                .constrainAs(teachers) {
                    top.linkTo(students.bottom)
                },
            onClick = { onTeachersClick() }
        )

        com.mobile.tuesplace.ui.MenuItem(
            image = painterResource(id = R.drawable.agenda),
            string = stringResource(id = R.string.agenda),
            modifier = Modifier
                .padding(6.dp)
                .constrainAs(agenda) {
                    top.linkTo(teachers.bottom)
                },
            onClick = { onAgendaClick() }
        )
    }
}

@Composable
@Preview
fun MenuItemPreview() {
    WelcomeAdminScreen({}, {}, {}, {}, {})
}
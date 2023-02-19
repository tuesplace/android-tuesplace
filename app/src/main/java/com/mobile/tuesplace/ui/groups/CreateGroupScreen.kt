package com.mobile.tuesplace.ui.groups

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.mobile.tuesplace.R
import com.mobile.tuesplace.ui.GradientBorderButtonRound
import com.mobile.tuesplace.ui.TextFieldWithTitle
import com.mobile.tuesplace.ui.buttonChangeColorOnClick

@Composable
fun CreateGroupScreen(
    groupName: String,
    setGroupName: (String) -> Unit,
    classes: String,
    setClasses: (String) -> Unit,
    onCreateGroupClick: () -> Unit,
    teacher: String,
    setTeacher: (String) -> Unit,
    groupsType: Boolean,
    setGroupsType: (Boolean) -> Unit,
    query: String,
    setQuery: (String) -> Unit
) {
    ConstraintLayout(modifier = Modifier
        .fillMaxSize()
        .background(colorResource(id = R.color.dark_blue))
    ) {
        val (btn, fields) = createRefs()
        Column(modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .constrainAs(fields) {
                top.linkTo(parent.top)
                bottom.linkTo(btn.top)
            },
            verticalArrangement = Arrangement.Center) {

            TextFieldWithTitle(
                title = stringResource(id = R.string.name),
                value = groupName,
                onValueChange = setGroupName,
                placeholder = stringResource(id = R.string.group_name),
                enabled = true,
                isError = null,
                modifier = Modifier.clickable {  }
            )

            TextFieldWithTitle(
                title = stringResource(id = R.string.classes),
                value = classes,
                onValueChange = setClasses,
                placeholder = stringResource(id = R.string.choose_classes),
                enabled = true,
                isError = null,
                modifier = Modifier.clickable {  }
            )

            TextFieldWithTitle(
                title = stringResource(id = R.string.teachers),
                value = teacher,
                onValueChange = setTeacher,
                placeholder = stringResource(id = R.string.choose_teacher),
                enabled = true,
                isError = null,
                modifier = Modifier.clickable {  }
            )

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .background(Color.Transparent),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = stringResource(id = R.string.group_type).uppercase(),
                    fontSize = 25.sp,
                    color = colorResource(id = R.color.baby_blue),
                    modifier = Modifier.padding(start = 6.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
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
            }
        }

        GradientBorderButtonRound(
            colors = listOf(colorResource(id = R.color.baby_blue), colorResource(id = R.color.lighter_dark_blue)),
            paddingValues = PaddingValues(16.dp),
            buttonText = stringResource(id = R.string.create_group),
            onClick = { onCreateGroupClick() },
            buttonPadding = PaddingValues(16.dp),
            modifier = Modifier
                .constrainAs(btn) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
            }
        )
    }
}



@Composable
@Preview
fun Preview() {
    CreateGroupScreen(
        groupName = "Ime",
        setGroupName = {},
        classes = "12b",
        setClasses = {},
        onCreateGroupClick = {}, "", {}, false, {}, "", {})
}
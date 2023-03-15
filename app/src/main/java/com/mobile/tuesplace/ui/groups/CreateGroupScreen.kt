package com.mobile.tuesplace.ui.groups

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.mobile.tuesplace.R
import com.mobile.tuesplace.data.ProfileResponseData
import com.mobile.tuesplace.ui.*
import com.mobile.tuesplace.ui.states.CreateGroupUiState

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
    onClassClick: (String) -> Unit,
    onTeacherClick: (String) -> Unit,
    classListVisibility: Boolean,
    setClassVisibility: (Boolean) -> Unit,
    teacherListVisibility: Boolean,
    setTeacherListVisibility: (Boolean) -> Unit,
    teachers: List<ProfileResponseData>?,
    createGroupUiState: CreateGroupUiState,
    onCreateGroupSuccess: () -> Unit
) {
    teachers?.let {
        CreateGroupUi(
            groupName = groupName,
            setGroupName = setGroupName,
            classes = classes,
            setClasses = setClasses,
            onCreateGroupClick = onCreateGroupClick,
            teacher = teacher,
            setTeacher = setTeacher,
            groupsType = groupsType,
            setGroupsType = setGroupsType,
            onClassClick = onClassClick,
            onTeacherClick = onTeacherClick,
            classListVisibility = classListVisibility,
            setClassVisibility = setClassVisibility,
            teacherListVisibility = teacherListVisibility,
            setTeacherListVisibility = setTeacherListVisibility,
            teachers = it
        )
    }

    when (createGroupUiState) {
        CreateGroupUiState.Empty -> {}
        is CreateGroupUiState.Error -> {}
        CreateGroupUiState.Loading -> {}
        CreateGroupUiState.Success -> {
            onCreateGroupSuccess()
            Toast.makeText(
                LocalContext.current,
                stringResource(id = R.string.group_created),
                Toast.LENGTH_LONG
            ).show()
        }
    }
}

@Composable
fun CreateGroupUi(
    groupName: String,
    setGroupName: (String) -> Unit,
    classes: String,
    setClasses: (String) -> Unit,
    onCreateGroupClick: () -> Unit,
    teacher: String,
    setTeacher: (String) -> Unit,
    groupsType: Boolean,
    setGroupsType: (Boolean) -> Unit,
    onClassClick: (String) -> Unit,
    onTeacherClick: (String) -> Unit,
    classListVisibility: Boolean,
    setClassVisibility: (Boolean) -> Unit,
    teacherListVisibility: Boolean,
    setTeacherListVisibility: (Boolean) -> Unit,
    teachers: List<ProfileResponseData>,
) {
    ConstraintLayout(modifier = Modifier
        .fillMaxSize()
        .background(colorResource(id = R.color.dark_blue))
    ) {
        val (btn, groupNameField, groupClassesField, groupTeachersField, groupTypeField, classesList, teachersList) = createRefs()

        TextFieldWithTitle(
            title = stringResource(id = R.string.name),
            value = groupName,
            onValueChange = setGroupName,
            placeholder = stringResource(id = R.string.group_name),
            enabled = true,
            isError = null,
            modifier = Modifier
                .padding(16.dp)
                .constrainAs(groupNameField) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            setClassVisibility = null
        )

        TextFieldWithTitle(
            title = stringResource(id = R.string.classes),
            value = classes,
            onValueChange = setClasses,
            placeholder = stringResource(id = R.string.choose_classes),
            enabled = true,
            isError = null,
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                .constrainAs(groupClassesField) {
                    top.linkTo(groupNameField.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            setClassVisibility = setClassVisibility
        )

        TextFieldWithTitle(
            title = stringResource(id = R.string.teachers),
            value = teacher,
            onValueChange = setTeacher,
            placeholder = stringResource(id = R.string.choose_teacher),
            enabled = true,
            isError = null,
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                .constrainAs(groupTeachersField) {
                    top.linkTo(groupClassesField.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            setClassVisibility = setTeacherListVisibility
        )

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .background(Color.Transparent)
                .constrainAs(groupTypeField) {
                    top.linkTo(groupTeachersField.bottom)
                },
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

        GradientBorderButtonRound(
            colors = listOf(colorResource(id = R.color.baby_blue),
                colorResource(id = R.color.lighter_dark_blue)),
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
        if (classListVisibility) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(classesList) {
                        top.linkTo(groupClassesField.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                itemsIndexed(listAllGrades().filter { grade -> grade.contains(classes) }) { _, data ->
                    ClassSearchItem(data, onClassClick, setClassVisibility, LocalContext.current)
                }
            }
        }

        if (teacherListVisibility) {
            LazyColumn(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp)
                    .fillMaxWidth()
                    .constrainAs(teachersList) {
                        top.linkTo(groupTeachersField.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                itemsIndexed(teachers.filter { profile -> profile.fullName.contains(teacher) }) { _, data ->
                    TeacherSearchItem(data,
                        onTeacherClick,
                        setTeacherListVisibility,
                        LocalContext.current)
                }
            }
        }
    }
}

@Composable
@Preview
fun Preview() {
//    CreateGroupScreen(
//        groupName = "Ime",
//        setGroupName = {},
//        classes = "12b",
//        setClasses = {},
//        onCreateGroupClick = {}, "", {}, false, {}, "", {}, {}, {}, true, {}, false,{} , mutableListOf<ProfileResponseData>())
}
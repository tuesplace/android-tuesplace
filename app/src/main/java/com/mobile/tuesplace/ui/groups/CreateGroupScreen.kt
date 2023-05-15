package com.mobile.tuesplace.ui.groups

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.mobile.tuesplace.ui.states.GetAllProfilesUiState

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
    onTeacherClick: (ProfileResponseData) -> Unit,
    classListVisibility: Boolean,
    setClassVisibility: (Boolean) -> Unit,
    teacherListVisibility: Boolean,
    setTeacherListVisibility: (Boolean) -> Unit,
    teachers: List<ProfileResponseData>?,
    createGroupUiState: CreateGroupUiState,
    onCreateGroupSuccess: () -> Unit,
    getAllProfilesUiState: GetAllProfilesUiState,
    selectedTeachers: ArrayList<ProfileResponseData>,
) {
    when (getAllProfilesUiState) {
        GetAllProfilesUiState.Empty -> {}
        is GetAllProfilesUiState.Error -> {}
        GetAllProfilesUiState.Loading -> {
            Loading()
        }

        is GetAllProfilesUiState.Success -> {
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
                    teachers = it,
                    selectedTeachers = selectedTeachers
                )
            }
        }
    }


    when (createGroupUiState) {
        CreateGroupUiState.Empty -> {}
        is CreateGroupUiState.Error -> {
            Toast.makeText(
                LocalContext.current,
                createGroupUiState.exception ?: stringResource(R.string.create_error),
                Toast.LENGTH_LONG
            ).show()
        }

        CreateGroupUiState.Loading -> {
            Loading()
        }

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
    onTeacherClick: (ProfileResponseData) -> Unit,
    classListVisibility: Boolean,
    setClassVisibility: (Boolean) -> Unit,
    teacherListVisibility: Boolean,
    setTeacherListVisibility: (Boolean) -> Unit,
    teachers: List<ProfileResponseData>,
    selectedTeachers: ArrayList<ProfileResponseData>
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dark_blue))
    ) {
        val (btn, groupNameField, groupClassesField, groupTeachersField, selectedTeachersList, groupTypeField, classesList, teachersList) = createRefs()

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
                }
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
                }
        )

        Box(modifier = Modifier
            .padding(start = 16.dp, top = 8.dp)
            .fillMaxWidth()
            .constrainAs(selectedTeachersList) {
                top.linkTo(groupClassesField.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }){
            if (selectedTeachers.isNotEmpty()) {
                LazyRow{
                    itemsIndexed(selectedTeachers) { _, data ->
                        StudentItem(
                            student = data,
                            onClick = {},
                            modifier = Modifier
                                .padding(6.dp)
                                .size(100.dp)
                                .border(
                                    1.dp,
                                    colorResource(id = R.color.baby_blue),
                                    RoundedCornerShape(8.dp)
                                )
                        )
                    }
                }
            }
        }

        TextFieldWithTitle(
            title = stringResource(id = R.string.teachers),
            value = teacher,
            onValueChange = {
                setTeacher(it)
                if ((teacher.isNotEmpty() && (teacher.length==1))) {
                    setTeacherListVisibility(false)
                } else {
                    setTeacherListVisibility(true)
                }
                            },
            placeholder = stringResource(id = R.string.choose_teacher),
            enabled = true,
            isError = null,
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                .constrainAs(groupTeachersField) {
                    top.linkTo(selectedTeachersList.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
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
                ButtonChangeColorOnClick(
                    text = stringResource(id = R.string.classes),
                    colorState = groupsType,
                    setColor = setGroupsType
                )

                ButtonChangeColorOnClick(
                    text = stringResource(id = R.string.chats),
                    colorState = !groupsType,
                    setColor = { setGroupsType(groupsType) }
                )
            }
        }

        GradientBorderButtonRound(
            colors = listOf(
                colorResource(id = R.color.baby_blue),
                colorResource(id = R.color.lighter_dark_blue)
            ),
            paddingValues = PaddingValues(16.dp),
            buttonText = stringResource(id = R.string.create_group),
            onClick = { onCreateGroupClick() },
            buttonPadding = PaddingValues(16.dp),
            modifier = Modifier
                .constrainAs(btn) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(groupTypeField.bottom)
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
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
                    .constrainAs(teachersList) {
                        top.linkTo(groupTeachersField.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                teachers.forEach {
                    if (it.fullName.contains(teacher)) {
                        TeacherSearchItem(
                            it,
                            onTeacherClick,
                            setTeacherListVisibility,
                            LocalContext.current
                        )
                    }
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
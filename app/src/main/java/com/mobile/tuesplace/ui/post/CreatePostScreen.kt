package com.mobile.tuesplace.ui.post

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.mobile.tuesplace.AssignmentInfo
import com.mobile.tuesplace.EMPTY_STRING
import com.mobile.tuesplace.PostRequestData
import com.mobile.tuesplace.R
import com.mobile.tuesplace.ui.GradientBorderButtonRound
import com.mobile.tuesplace.ui.Loading
import com.mobile.tuesplace.ui.TextFieldWithTitle
import com.mobile.tuesplace.ui.ButtonChangeColorOnClick
import com.mobile.tuesplace.ui.states.CreatePostUiState
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun CreatePostScreen(
    role: String,
    postName: String,
    setPostName: (String) -> Unit,
    postDescription: String,
    setPostDescription: (String) -> Unit,
    deadline: String,
    setDeadline: (String) -> Unit,
    postType: Boolean,
    setPostType: (Boolean) -> Unit,
    onCreateClick: (PostRequestData) -> Unit,
    createPostUiState: CreatePostUiState,
    onCreatePostSuccess: () -> Unit,
) {

    if (role == stringResource(id = R.string.teacher_role)) {
        CreatePostTeacherUi(
            postName = postName,
            setPostName = setPostName,
            postDescription = postDescription,
            setPostDescription = setPostDescription,
            deadline = deadline,
            setDeadline = setDeadline,
            postType = postType,
            setPostType = setPostType,
            onCreateClick = onCreateClick
        )
    } else {
        CreatePostStudentUi(
            postName = postName,
            setPostName = setPostName,
            postDescription = postDescription,
            setPostDescription = setPostDescription,
            onCreateClick = onCreateClick
        )
    }
    when (createPostUiState) {
        CreatePostUiState.Empty -> {}
        is CreatePostUiState.Error -> {}
        CreatePostUiState.Loading -> {
            Loading()
        }
        CreatePostUiState.Success -> {
            onCreatePostSuccess()
        }
    }
}

@Composable
fun CreatePostTeacherUi(
    postName: String,
    setPostName: (String) -> Unit,
    postDescription: String,
    setPostDescription: (String) -> Unit,
    deadline: String,
    setDeadline: (String) -> Unit,
    postType: Boolean,
    setPostType: (Boolean) -> Unit,
    onCreateClick: (PostRequestData) -> Unit,
) {
    val dialogState = rememberMaterialDialogState()

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dark_blue))
    ) {
        val (postNameItem, postDescriptionItem, postTypeItem, deadlineItem, createBtnItem) = createRefs()



        TextFieldWithTitle(
            title = stringResource(id = R.string.name),
            placeholder = stringResource(id = R.string.post_name_placeholder_teacher),
            value = postName,
            onValueChange = setPostName,
            enabled = true,
            isError = false,
            modifier = Modifier.constrainAs(postNameItem) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
            }
        )

        TextFieldWithTitle(
            title = stringResource(id = R.string.description),
            placeholder = stringResource(id = R.string.post_description_teacher),
            value = postDescription,
            onValueChange = setPostDescription,
            enabled = true,
            isError = false,
            modifier = Modifier
                .padding(top = 6.dp)
                .constrainAs(postDescriptionItem) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(postNameItem.bottom)
                }
        )

        Column(
            modifier = Modifier
                .padding(top = 6.dp)
                .fillMaxWidth()
                .background(Color.Transparent)
                .constrainAs(postTypeItem) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(postDescriptionItem.bottom)
                },
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(id = R.string.group_type).uppercase(),
                fontSize = 25.sp,
                color = colorResource(id = R.color.baby_blue),
                modifier = Modifier
                    .padding(start = 16.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                ButtonChangeColorOnClick(
                    text = stringResource(id = R.string.post),
                    colorState = postType,
                    setColor = setPostType
                )

                ButtonChangeColorOnClick(
                    text = stringResource(id = R.string.assignment),
                    colorState = !postType,
                    setColor = { setPostType(postType) }
                )
            }
        }

        if (postType) {
//            TextFieldWithTitle(
//                title = stringResource(id = R.string.deadline),
//                placeholder = stringResource(id = R.string.deadline_placeholder),
//                value = deadline,
//                onValueChange = setDeadline,
//                enabled = true,
//                isError = false,
//                modifier = Modifier
//                    .padding(top = 6.dp, start = 6.dp)
//                    .constrainAs(deadlineItem) {
//                        start.linkTo(parent.start)
//                        end.linkTo(parent.end)
//                        top.linkTo(postTypeItem.bottom)
//                    },
//                setClassVisibility = null
//            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp)
                    .constrainAs(deadlineItem) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(postTypeItem.bottom)
                    }
                    .clickable { dialogState.show() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.calendar_icon),
                    contentDescription = EMPTY_STRING,
                    Modifier.size(30.dp)
                )
                Text(
                    text = stringResource(id = R.string.deadline),
                    color = colorResource(id = R.color.white),
                    modifier = Modifier.padding(start = 6.dp)
                )
                Text(
                    text = deadline,
                    color = colorResource(id = R.color.baby_blue),
                    modifier = Modifier.padding(start = 16.dp)
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
            onClick = {
                onCreateClick(
                    PostRequestData(
                        title = postName,
                        body = postDescription,
                        assignmentInfo = AssignmentInfo(postType, deadline.toFloat())
                    )
                )
            },
            buttonPadding = PaddingValues(16.dp),
            modifier = Modifier
                .padding(16.dp)
                .constrainAs(createBtnItem) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
        )
    }

    MaterialDialog(
        dialogState = dialogState,
        buttons = {
            positiveButton(text = "Ok")
            negativeButton(text = "Cancel")
        },
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            datepicker(
                initialDate = LocalDate.now(),
                title = "Pick a date",
                colors = DatePickerDefaults.colors(
                    headerBackgroundColor = colorResource(id = R.color.darker_sea_blue),
                    headerTextColor = colorResource(id = R.color.baby_blue),
                    calendarHeaderTextColor = colorResource(id = R.color.white),
                    dateActiveBackgroundColor = colorResource(id = R.color.dark_blue),
                    dateActiveTextColor = colorResource(id = R.color.white),
                    dateInactiveTextColor = colorResource(id = R.color.baby_blue),
                )
            ) {
                setDeadline(it.format(DateTimeFormatter.ofPattern("dd MMM yyyy")))
            }
        }
    }
}

@Composable
fun CreatePostStudentUi(
    postName: String,
    setPostName: (String) -> Unit,
    postDescription: String,
    setPostDescription: (String) -> Unit,
    onCreateClick: (PostRequestData) -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dark_blue))
    ) {
        val (postNameItem, postDescriptionItem, createBtnItem) = createRefs()

        TextFieldWithTitle(
            title = stringResource(id = R.string.name),
            placeholder = stringResource(id = R.string.post_name_placeholder_teacher),
            value = postName,
            onValueChange = setPostName,
            enabled = true,
            isError = false,
            modifier = Modifier
                .constrainAs(postNameItem) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }
        )

        TextFieldWithTitle(
            title = stringResource(id = R.string.description),
            placeholder = stringResource(id = R.string.post_description_teacher),
            value = postDescription,
            onValueChange = setPostDescription,
            enabled = true,
            isError = false,
            modifier = Modifier
                .padding(top = 6.dp)
                .constrainAs(postDescriptionItem) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(postNameItem.bottom)
                }
        )

        GradientBorderButtonRound(
            colors = listOf(
                colorResource(id = R.color.baby_blue),
                colorResource(id = R.color.lighter_dark_blue)
            ),
            paddingValues = PaddingValues(16.dp),
            buttonText = stringResource(id = R.string.create_post),
            onClick = {
                onCreateClick(
                    PostRequestData(
                        title = postName, body = postDescription,
                        assignmentInfo = null
                    )
                )
            },
            buttonPadding = PaddingValues(16.dp),
            modifier = Modifier
                .padding(16.dp)
                .constrainAs(createBtnItem) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
        )
    }
}
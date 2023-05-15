package com.mobile.tuesplace.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.ContextCompat
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.mobile.tuesplace.EMPTY_STRING
import com.mobile.tuesplace.R
import com.mobile.tuesplace.ZERO_STRING
import com.mobile.tuesplace.data.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


@Composable
fun TextFieldFunction(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier?,
    enabled: Boolean?,
    isError: Boolean?,
) {
    TextField(
        value = value,
        enabled = enabled ?: true,
        maxLines = 1,
        isError = isError ?: false,
        onValueChange = { onValueChange(it) },
        modifier = modifier ?: Modifier
            .padding(top = 22.dp, start = 12.dp, end = 12.dp)
            .fillMaxWidth()
            .border(BorderStroke(2.dp, White), RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        placeholder = { Text(placeholder) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = White,
            focusedBorderColor = Transparent,
            unfocusedBorderColor = Transparent,
            textColor = Black,
            placeholderColor = Black,
        )
    )
}

@Composable
fun GradientBorderButtonRound(
    colors: List<Color>?,
    paddingValues: PaddingValues,
    buttonText: String,
    modifier: Modifier? = Modifier,
    onClick: () -> Unit,
    buttonPadding: PaddingValues?,
) {
    val currentModifier = modifier ?: Modifier.fillMaxWidth()
    Box(
        modifier = currentModifier
            .padding(buttonPadding ?: PaddingValues(start = 12.dp, end = 12.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = colors ?: listOf(
                        colorResource(id = R.color.logo_blue),
                        colorResource(id = R.color.blue)
                    )
                ),
                shape = RoundedCornerShape(percent = 10)
            )
            .clip(shape = RoundedCornerShape(percent = 10))
            .clickable {
                onClick()
            },
        contentAlignment = Center
    ) {
        Text(
            text = buttonText,
            color = White,
            fontSize = 25.sp,
            modifier = Modifier.padding(paddingValues),
            fontWeight = FontWeight.Medium
        )
    }
}


@Composable
fun PostItem(
    post: PostResponseData,
    onPostClick: (Pair<String, Boolean>) -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth()
            .heightIn(100.dp, 9999.dp)
            .clickable { onPostClick(Pair(post._id, post.assignmentInfo.isAssignment)) }
            .background(White, RoundedCornerShape(8.dp))
            .border(2.dp, colorResource(id = R.color.darker_sea_blue), RoundedCornerShape(8.dp))
    ) {
        val (profilePic, teacherName, createTime, title, body, commentItem) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.tues_webview),
            contentDescription = EMPTY_STRING,
            modifier = Modifier
                .padding(6.dp)
                .size(45.dp)
                .clip(CircleShape)
                .border(
                    1.dp,
                    colorResource(id = R.color.darker_sea_blue),
                    CircleShape
                )
                .constrainAs(profilePic) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
        )

        post.owner.data?.fullName?.let {
            Text(
                text = it,
                color = colorResource(id = R.color.darker_sea_blue),
                modifier = Modifier
                    .padding(top = 6.dp, start = 6.dp)
                    .constrainAs(teacherName) {
                        start.linkTo(profilePic.end)
                        top.linkTo(parent.top)
                    }
            )
        }

        Text(
            text = date(post.createdAt),
            color = colorResource(id = R.color.black),
            modifier = Modifier
                .padding(start = 6.dp)
                .constrainAs(createTime) {
                    start.linkTo(teacherName.start)
                    top.linkTo(teacherName.bottom)
                },
            fontSize = 12.sp
        )

        Text(
            text = post.title,
            fontSize = 16.sp,
            color = colorResource(id = R.color.black),
            modifier = Modifier
                .padding(top = 6.dp, start = 6.dp)
                .constrainAs(title) {
                    start.linkTo(profilePic.start)
                    top.linkTo(createTime.bottom)
                }
        )

        Text(
            text = post.body,
            color = Black,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(6.dp)
                .fillMaxWidth()
                .constrainAs(body) {
                    top.linkTo(title.bottom)
                    bottom.linkTo(commentItem.top)
                }
        )
    }
}

@Composable
fun CreateCommentItem(
    profilePic: Painter?,
    onSendClick: (CreateCommentData) -> Unit,
    modifier: Modifier?,
    commentInput: String,
    onCommentChange: (String) -> Unit,
    postId: String,
) {
    val currentModifier = modifier ?: Modifier
    Row(
        modifier = currentModifier
            .height(64.dp)
            .fillMaxWidth()
            .background(colorResource(id = R.color.white), RoundedCornerShape(8.dp))
            .border(1.dp, colorResource(id = R.color.darker_sea_blue), RoundedCornerShape(8.dp)),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = CenterVertically
    ) {
        Image(
            painter = (profilePic ?: painterResource(id = R.drawable.ic_launcher_background)),
            contentDescription = stringResource(id = R.string.empty),
            modifier = Modifier
                .padding(2.dp)
                .size(40.dp)
                .clip(CircleShape)
                .border(1.dp, colorResource(id = R.color.darker_sea_blue), CircleShape),
            contentScale = Crop
        )

        TextField(
            value = commentInput,
            maxLines = 1,
            isError = false,
            singleLine = true,
            onValueChange = { onCommentChange(it) },
            modifier = Modifier
                .wrapContentWidth()
                .background(colorResource(id = R.color.white), RoundedCornerShape(8.dp)),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.add_comment),
                    color = colorResource(id = R.color.darker_sea_blue),
                    fontSize = 12.sp
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = White,
                focusedBorderColor = Transparent,
                unfocusedBorderColor = Transparent,
                textColor = Black,
                placeholderColor = colorResource(id = R.color.darker_sea_blue),
            )
        )
        Image(
            painter = painterResource(id = R.drawable.ic_baseline_keyboard_arrow_right_24_darker_sea_blue),
            contentDescription = stringResource(id = R.string.empty),
            modifier = Modifier
                .size(25.dp)
                .clickable {
                    onSendClick(
                        CreateCommentData(
                            postId = postId,
                            CommentRequestData(commentInput, true)
                        )
                    )
                    onCommentChange(EMPTY_STRING)
                })
    }
}

//        Text(
//            text = stringResource(id = R.string.view_all),
//            color = colorResource(id = R.color.darker_sea_blue),
//            textDecoration = TextDecoration.Underline,
//            modifier = Modifier
//                .wrapContentWidth()
//                .padding(2.dp)
//                .clickable { onViewCommentsClick() }
//        )

@Composable
fun CommentItem(
    commentData: CommentData,
    index: Int,
    commentIndex: Int,
    onCommentClick: (Int) -> Unit,
    onEditClick: (Pair<String, String>) -> Unit,
    enabled: Int,
    setEnabled: (Int) -> Unit,
    setEditCommentBody: (Pair<String, Int>) -> Unit,
    setDialogVisibility: (Boolean) -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, colorResource(id = R.color.darker_sea_blue))
            .padding(4.dp),
    ) {
        val (bodyItem, menuItem) = createRefs()

        Row(
            modifier = Modifier
                .constrainAs(bodyItem) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        ) {
            Image(
                painter = painterResource(id = R.drawable.tues_webview),
                contentDescription = stringResource(id = R.string.empty),
                modifier = Modifier
                    .padding(2.dp)
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(1.dp, colorResource(id = R.color.darker_sea_blue), CircleShape)
            )

            Column(modifier = Modifier.padding(start = 2.dp)) {
                commentData.owner.data?.fullName?.let {
                    Text(
                        text = it,
                        color = colorResource(id = R.color.darker_sea_blue)
                    )
                }
                TextField(
                    value = commentData.body,
                    onValueChange = { setEditCommentBody(Pair(it, index)) },
                    enabled = enabled != -1 && enabled == index,
                    singleLine = true,
                    keyboardActions = KeyboardActions(
                        onDone = {
                            onEditClick(Pair(commentData._id, commentData.body))
                            setEnabled(-1)
                        }
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Transparent,
                        focusedBorderColor = Transparent,
                        unfocusedBorderColor = Transparent,
                        textColor = Black,
                        disabledTextColor = Black,
                        disabledLabelColor = Transparent,
                        focusedLabelColor = Transparent,
                        unfocusedLabelColor = Transparent
                    )
                )
            }
        }
        Box(modifier = Modifier
            .constrainAs(menuItem) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
            }) {
            Image(
                painter = painterResource(id = R.drawable.menu_dots),
                contentDescription = stringResource(id = R.string.empty),
                modifier = Modifier
                    .padding(end = 6.dp)
                    .size(16.dp)
                    .clickable {
                        onCommentClick(index)
                    }

            )

            if (commentIndex == index) {
                DropdownMenu(
                    expanded = true,
                    onDismissRequest = {},
                    modifier = Modifier
                        .background(colorResource(id = R.color.white))
                ) {
                    Text(
                        text = stringResource(id = R.string.edit),
                        color = colorResource(id = R.color.darker_sea_blue),
                        fontSize = 14.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                setEnabled(commentIndex)
                                onCommentClick(-1)
                            }
                    )

                    Text(
                        text = stringResource(id = R.string.delete),
                        color = colorResource(id = R.color.darker_sea_blue),
                        fontSize = 14.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                setDialogVisibility(true)
                            }
                    )
                }
            }
        }
    }
}


@Composable
fun EmptyScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dark_blue)),
        contentAlignment = Center
    ) {
        Text(
            text = stringResource(id = R.string.screen_not_found),
            color = White,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun MenuItem(image: Painter?, string: String, modifier: Modifier?, onClick: () -> Unit) {
    val currentModifier = modifier ?: Modifier
    ConstraintLayout(
        modifier = currentModifier
            .clickable { onClick() }
            .fillMaxWidth()
            .height(50.dp)
            .background(
                colorResource(id = R.color.darker_sea_blue),
                RoundedCornerShape(8.dp)
            )
    ) {
        val (startIcon, text, arrow) = createRefs()
        if (image != null) {
            Image(
                painter = image,
                contentDescription = "",
                modifier = Modifier
                    .padding(6.dp)
                    .size(30.dp)
                    .constrainAs(startIcon) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
            )
        }
        Text(
            text = string,
            modifier = Modifier
                .padding(start = 16.dp)
                .fillMaxWidth()
                .constrainAs(text) {
                    start.linkTo(startIcon.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
            color = White,
            fontSize = 20.sp
        )
        Image(
            painter = painterResource(id = R.drawable.ic_baseline_keyboard_arrow_right_24),
            contentDescription = "",
            modifier = Modifier
                .padding(end = 6.dp)
                .size(30.dp)
                .constrainAs(arrow) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )
    }

}

@Composable
fun WebViewImage(onImageClick: () -> Unit, image: Painter, text: String, modifier: Modifier?) {
    val currentModifier = modifier ?: Modifier
    Box(modifier = currentModifier
        .clickable { onImageClick() }
        .padding(top = 50.dp)
        .padding(6.dp)
        .background(White, RoundedCornerShape(8.dp))
        .border(
            2.dp,
            colorResource(id = R.color.white),
            RoundedCornerShape(8.dp)
        )
    ) {

        Image(
            painter = image,
            contentDescription = stringResource(id = R.string.empty),
            contentScale = Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp)),
        )

        Text(
            text = text,
            color = White,
            modifier = Modifier
                .align(Center),
            fontSize = 25.sp
        )
    }
}

@Composable
fun GroupClassItem(groupData: GroupResponseData, onGroupClick: (String) -> Unit) {
    Box(modifier = Modifier
        .padding(6.dp)
        .width(344.dp)
        .height(289.dp)
        .clickable { onGroupClick(groupData._id) }
        .border(2.dp, colorResource(id = R.color.white), RoundedCornerShape(8.dp))
    ) {
        Image(
            painter = groupData.owners?.get(0)?.data?.assets?.profilePic.let {
                rememberAsyncImagePainter(ImageRequest.Builder(LocalContext.current)
                    .data(data = it?.get(0)?.data?.src)
                    .apply(block = fun ImageRequest.Builder.() {
                        crossfade(true)
                    }).build()
                )
            },
            contentDescription = stringResource(id = R.string.empty),
            contentScale = Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp))
        )
        ConstraintLayout(
            modifier = Modifier
                .width(344.dp)
                .height(110.dp)
                .background(colorResource(id = R.color.darker_sea_blue), RoundedCornerShape(8.dp))
                .border(2.dp, colorResource(id = R.color.white), RoundedCornerShape(8.dp))
                .align(BottomCenter)
        ) {
            val (title, teacher, classString, className) = createRefs()

            Text(
                text = groupData.name,
                color = White,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(6.dp)
                    .constrainAs(title) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    }
            )

//            Text(
//                text = stringResource(id = R.string.teacher).uppercase(),
//                color = colorResource(id = R.color.baby_blue),
//                fontSize = 6.sp,
//                modifier = Modifier
//                    .constrainAs(classString) {
//                        start.linkTo(parent.start)
//                        bottom.linkTo(teacher.top)
//                    }
//            )

            Text(
                text = stringResource(id = R.string.class_string).uppercase(),
                color = colorResource(id = R.color.baby_blue),
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(end = 6.dp)
                    .constrainAs(classString) {
                        end.linkTo(parent.end)
                        bottom.linkTo(className.top)
                    }
            )

            groupData.owners?.get(0)?.let {
                it.data?.fullName?.let { it1 ->
                    Text(
                        text = it1,
                        color = White,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(6.dp)
                            .padding(bottom = 6.dp)
                            .constrainAs(teacher) {
                                start.linkTo(parent.start)
                                bottom.linkTo(parent.bottom)
                            }
                    )
                }
            }


            groupData.classes?.get(0)?.let {
                Text(
                    text = it,
                    color = colorResource(id = R.color.white),
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(6.dp)
                        .padding(bottom = 6.dp)
                        .constrainAs(className) {
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        }
                )
            }
        }
    }
}


@Composable
fun GroupChatItem(groupData: GroupResponseData, onGroupClick: (String) -> Unit) {

    ConstraintLayout(modifier = Modifier
        .padding(6.dp)
        .width(344.dp)
        .height(110.dp)
        .background(colorResource(id = R.color.darker_sea_blue), RoundedCornerShape(8.dp))
        .border(2.dp, colorResource(id = R.color.white), RoundedCornerShape(8.dp))
        .clickable { onGroupClick(groupData._id) }
    ) {
        val (title, teacher, classString, className) = createRefs()

        Text(
            text = groupData.name,
            color = White,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(6.dp)
                .constrainAs(title) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                }
        )
//            Text(
//                text = stringResource(id = R.string.teacher).uppercase(),
//                color = colorResource(id = R.color.baby_blue),
//                fontSize = 6.sp,
//                modifier = Modifier
//                    .constrainAs(classString) {
//                        start.linkTo(parent.start)
//                        bottom.linkTo(teacher.top)
//                    }
//            )

        Text(
            text = stringResource(id = R.string.class_string).uppercase(),
            color = colorResource(id = R.color.baby_blue),
            fontSize = 20.sp,
            modifier = Modifier
                .padding(end = 6.dp)
                .constrainAs(classString) {
                    end.linkTo(parent.end)
                    bottom.linkTo(className.top)
                }
        )

        groupData.owners?.get(0)?.let {
            it.data?.fullName?.let { it1 ->
                Text(
                    text = it1,
                    color = White,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(6.dp)
                        .padding(bottom = 6.dp)
                        .constrainAs(teacher) {
                            start.linkTo(parent.start)
                            bottom.linkTo(parent.bottom)
                        }
                )
            }
        }


        groupData.classes?.get(0)?.let {
            Text(
                text = it,
                color = colorResource(id = R.color.white),
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(6.dp)
                    .padding(bottom = 6.dp)
                    .constrainAs(className) {
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
            )
        }
    }
}

@Composable
fun ButtonChangeColorOnClick(
    text: String,
    colorState: Boolean,
    setColor: (Boolean) -> Unit,
) {
    val color: Color = if (colorState) {
        colorResource(id = R.color.baby_blue)
    } else {
        colorResource(id = R.color.white)
    }
    Box(
        modifier = Modifier
            .background(colorResource(id = R.color.darker_sea_blue), RoundedCornerShape(8.dp))
            .border(2.dp, color, RoundedCornerShape(8.dp))
            .clickable { setColor(colorState) }
            .padding(6.dp)
            .padding(start = 6.dp, end = 6.dp)
    ) {
        Text(text = text.uppercase(), color = color, fontSize = 25.sp)
    }
}

@Composable
fun TextFieldWithTitle(
    title: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean?,
    isError: Boolean?,
    modifier: Modifier?,
) {

    val currentModifier = modifier ?: Modifier
    Column(
        modifier = currentModifier
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .fillMaxWidth()
            .background(colorResource(id = R.color.dark_blue))
            .clickable { },
        horizontalAlignment = Start
    ) {
        Text(
            text = title.uppercase(),
            fontSize = 25.sp,
            color = colorResource(id = R.color.baby_blue)
        )

        TextField(
            value = value,
            onValueChange = { onValueChange(it) },
            enabled = enabled ?: true,
            isError = isError ?: false,
            singleLine = true,
            placeholder = {
                Text(
                    text = placeholder,
                    color = colorResource(id = R.color.baby_blue)
                )
            },
            modifier = Modifier
                .background(colorResource(id = R.color.dark_blue))
                .border(2.dp, colorResource(id = R.color.baby_blue), RoundedCornerShape(8.dp))
        )
    }
}

@Composable
fun StudentItem(student: ProfileResponseData, onClick: (String) -> Unit, modifier: Modifier?) {
    val currentModifier = modifier ?: Modifier
        .padding(6.dp)
        .size(150.dp)
        .border(1.dp, colorResource(id = R.color.baby_blue), RoundedCornerShape(8.dp))
    ConstraintLayout(
        modifier = currentModifier
            .clickable { onClick(student._id) }
    ) {
        Image(
            painter = student.assets?.profilePic.let {
                rememberAsyncImagePainter(ImageRequest.Builder(LocalContext.current)
                    .data(data = it?.get(0)?.data?.src)
                    .apply(block = fun ImageRequest.Builder.() {
                        crossfade(true)
                    }).build()
                )
            },
            contentDescription = stringResource(id = R.string.empty),
            contentScale = Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp))
        )
        val (classString, nameString) = createRefs()
        student.className?.let {
            Text(
                text = it,
                color = colorResource(id = R.color.baby_blue),
                modifier = Modifier
                    .padding(start = 6.dp)
                    .constrainAs(classString) {
                        start.linkTo(parent.start)
                        bottom.linkTo(nameString.top)
                    }
            )
        }

        Text(
            text = student.fullName,
            color = colorResource(id = R.color.white),
            modifier = Modifier
                .padding(start = 6.dp, bottom = 6.dp)
                .constrainAs(nameString) {
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                }
        )
    }
}

@Composable
fun SearchView(state: MutableState<TextFieldValue>, modifier: Modifier?, placeholder: String) {
    val currentModifier = modifier ?: Modifier
    TextField(
        value = state.value,
        onValueChange = { value ->
            state.value = value
        },
        modifier = currentModifier
            .fillMaxWidth()
            .border(2.dp, colorResource(id = R.color.baby_blue), RoundedCornerShape(8.dp)),
        textStyle = TextStyle(color = White, fontSize = 18.sp),
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "",
                modifier = Modifier
                    .padding(15.dp)
                    .size(24.dp)
            )
        },
        placeholder = { Text(text = placeholder) },
        trailingIcon = {
            if (state.value != TextFieldValue(EMPTY_STRING)) {
                IconButton(
                    onClick = {
                        state.value =
                            TextFieldValue(EMPTY_STRING)
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = stringResource(id = R.string.empty),
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp)
                    )
                }
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.textFieldColors(
            textColor = colorResource(id = R.color.baby_blue),
            cursorColor = colorResource(id = R.color.baby_blue),
            leadingIconColor = colorResource(id = R.color.baby_blue),
            trailingIconColor = colorResource(id = R.color.baby_blue),
            backgroundColor = colorResource(id = R.color.darker_sea_blue),
            focusedIndicatorColor = Transparent,
            unfocusedIndicatorColor = Transparent,
            disabledIndicatorColor = Transparent,
            placeholderColor = colorResource(id = R.color.baby_blue)
        )
    )
}

@Composable
fun AgendaItem(agendaData: AgendaResponseData) {
    ConstraintLayout(
        modifier = Modifier
            .padding(6.dp)
            .fillMaxWidth()
            .background(colorResource(id = R.color.water_blue), RoundedCornerShape(8.dp))
    ) {
        val (time, groupName, arrow) = createRefs()

        Column(
            modifier = Modifier
                .padding(start = 6.dp)
                .constrainAs(time) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        ) {
            Text(text = convertMinutesToHoursAndMinutes(agendaData.start), color = White)
            Text(text = convertMinutesToHoursAndMinutes(agendaData.end), color = White)
        }

        Column(modifier = Modifier
            .padding(6.dp)
            .constrainAs(groupName) {
                start.linkTo(time.end)
                end.linkTo(arrow.start)
                top.linkTo(parent.top)
            }) {
            Text(text = agendaData.associations.group.data.name)
            Text(
                text = agendaData.associations.group.data.owners[0].data.fullName,
                color = colorResource(id = R.color.baby_blue)
            )
        }

        Image(painter = painterResource(
            id = R.drawable.ic_baseline_keyboard_arrow_right_24
        ),
            contentDescription = "",
            modifier = Modifier
                .padding(end = 6.dp)
                .constrainAs(arrow) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )
    }
}

@Composable
fun InfoItem(title: String, text: String) {
    Column(horizontalAlignment = Start) {
        Text(
            text = title.uppercase(),
            color = colorResource(id = R.color.baby_blue),
            fontSize = 25.sp
        )
        Text(text = text, color = White, fontSize = 20.sp)
    }
}

@Composable
fun DailyAgendaItem(day: String, agendaList: List<AgendaResponseData>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, colorResource(id = R.color.baby_blue))
            .background(colorResource(id = R.color.darker_sea_blue))
    ) {
        Text(
            text = day,
            color = colorResource(id = R.color.white),
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 6.dp, top = 6.dp, bottom = 16.dp)
        )
        agendaList.forEach { activity ->
            AgendaItem(agendaData = activity)
        }
    }
}

@Composable
fun SettingsMenuItem(text: String, onClick: () -> Unit, modifier: Modifier?) {
    val currentModifier = modifier ?: Modifier
    Row(
        modifier = currentModifier
            .clickable { onClick() }
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = text, color = colorResource(id = R.color.white))
        Image(
            painter = painterResource(id = R.drawable.ic_baseline_keyboard_arrow_right_24_white),
            contentDescription = stringResource(id = R.string.empty)
        )
    }
}

@Composable
fun ResultLauncher(
    type: String,
    onUploadClick: (MultipartBody.Part) -> Unit,
    modifier: Modifier?,
    multipartName: String,
) {
    val currentModifier = modifier ?: Modifier
    val selectedFileUri = remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val getContentLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            selectedFileUri.value = uri
        }
    val requestPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                getContentLauncher.launch(type)
            } else {
                //viewModel.showPermissionError()
            }
        }

    Image(
        painter = painterResource(id = R.drawable.add_icon),
        contentDescription = EMPTY_STRING,
        modifier = currentModifier
            .clickable {
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    getContentLauncher.launch(type)
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }
    )

    selectedFileUri.value?.let { uri ->
        val file = getFileWithFileDescriptor(context, uri)
        val fileName = uri.path?.let { File(it) }

        val requestFile =
            file?.let {
                RequestBody.create(type.toMediaTypeOrNull(), it)
            }
        if (requestFile != null) {
            val filePart = MultipartBody.Part.createFormData(
                multipartName,
                fileName?.name,
                requestFile
            )
            onUploadClick(filePart)
            selectedFileUri.value = null
        }
    }
}

@Composable
fun UserMessage(profile: ProfileData, createTime: String, message: String) {
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (row, name) = createRefs()
        Text(
            text = profile.fullName,
            color = colorResource(id = R.color.white),
            fontSize = 12.sp,
            modifier = Modifier
                .padding(start = 38.dp)
                .constrainAs(name) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(row) {
                    start.linkTo(parent.start)
                    top.linkTo(name.bottom)
                },
            verticalAlignment = CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.tues_webview),
                contentDescription = stringResource(id = R.string.empty),
                modifier = Modifier
                    .padding(2.dp)
                    .size(30.dp)
                    .clip(CircleShape)
                    .border(1.dp, colorResource(id = R.color.darker_sea_blue), CircleShape)
            )

            Box(
                modifier = Modifier
                    .widthIn(min = 25.dp)
                    .padding(start = 2.dp)
                    .background(
                        colorResource(id = R.color.darker_sea_blue),
                        RoundedCornerShape(8.dp)
                    )
                    .border(1.dp, colorResource(id = R.color.baby_blue), RoundedCornerShape(8.dp))
                    .padding(6.dp),
            ) {
                Text(
                    text = message,
                    color = colorResource(id = R.color.white),
                    fontSize = 10.sp
                )
            }
            Text(
                text = createTime,
                color = colorResource(id = R.color.baby_blue),
                fontSize = 10.sp,
                modifier = Modifier.padding(start = 2.dp)
            )
        }
    }
}


@Composable
fun MyMessage(profile: ProfileData, createTime: String, message: String) {
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (row, name) = createRefs()
        Text(
            text = profile.fullName,
            color = colorResource(id = R.color.white),
            fontSize = 12.sp,
            modifier = Modifier
                .padding(start = 38.dp)
                .constrainAs(name) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(row) {
                    start.linkTo(parent.start)
                    top.linkTo(name.bottom)
                },
            verticalAlignment = CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .widthIn(min = 25.dp)
                    .padding(start = 2.dp)
                    .background(
                        colorResource(id = R.color.darker_sea_blue),
                        RoundedCornerShape(8.dp)
                    )
                    .border(1.dp, colorResource(id = R.color.baby_blue), RoundedCornerShape(8.dp))
                    .padding(6.dp),
            ) {
                Text(
                    text = createTime,
                    color = colorResource(id = R.color.baby_blue),
                    fontSize = 10.sp,
                    modifier = Modifier.padding(start = 2.dp)
                )
                Text(
                    text = message,
                    color = colorResource(id = R.color.white),
                    fontSize = 10.sp
                )
            }

            Image(
                painter = painterResource(id = R.drawable.tues_webview),
                contentDescription = stringResource(id = R.string.empty),
                modifier = Modifier
                    .padding(2.dp)
                    .size(30.dp)
                    .clip(CircleShape)
                    .border(1.dp, colorResource(id = R.color.darker_sea_blue), CircleShape)
            )
        }
    }
}

@Composable
fun AssignmentItem(
    post: PostResponseData,
    modifier: Modifier?,
    onAssignmentClick: (Pair<String, Boolean>) -> Unit,
) {
    val currentModifier = modifier ?: Modifier
    Row(
        modifier = currentModifier
            .padding(4.dp)
            .fillMaxWidth()
            .background(colorResource(id = R.color.white), RoundedCornerShape(8.dp))
            .border(1.dp, colorResource(id = R.color.darker_sea_blue), RoundedCornerShape(8.dp))
            .padding(4.dp)
            .clickable { onAssignmentClick(Pair(post._id, post.assignmentInfo.isAssignment)) },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = CenterVertically
    ) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.assigment_icon),
                contentDescription = stringResource(id = R.string.empty),
                modifier = Modifier
                    .size(30.dp)
            )

            Column {
                post.owner.data?.fullName?.let {
                    Text(
                        text = it,
                        color = colorResource(id = R.color.darker_sea_blue),
                        fontSize = 12.sp,
                        modifier = Modifier
                            .padding(top = 6.dp, start = 6.dp)
                    )
                }

                Text(
                    text = date(post.createdAt),
                    color = colorResource(id = R.color.black),
                    fontSize = 10.sp,
                    modifier = Modifier
                        .padding(start = 6.dp)
                )
            }
        }

        Text(
            text = stringResource(id = R.string.new_assignment),
            fontSize = 12.sp,
            color = colorResource(id = R.color.black),
        )
    }
}

@Composable
fun SubmissionItem(
    submissionData: SubmissionData,
    setDialogVisibility: (Boolean) -> Unit,
    setSubmissionIndex: (Int) -> Unit,
    index: Int,
) {
    Row(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .background(colorResource(id = R.color.white), RoundedCornerShape(8.dp))
            .border(1.dp, colorResource(id = R.color.darker_sea_blue), RoundedCornerShape(8.dp))
            .padding(6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.assigment_icon),
            contentDescription = stringResource(id = R.string.empty),
            modifier = Modifier
                .size(30.dp)
        )

        Column {
            submissionData.owner.data?.fullName?.let {
                Text(
                    text = it,
                    color = colorResource(id = R.color.darker_sea_blue),
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(top = 6.dp, start = 6.dp)
                )
            }
        }

        Row(verticalAlignment = CenterVertically) {

            Text(
                text = if (submissionData.marks.isEmpty()) {
                    ZERO_STRING
                } else {
                    submissionData.marks[0].mark.toString()
                },
                fontSize = 12.sp,
                color = colorResource(id = R.color.darker_sea_blue),
                modifier = Modifier.clickable {
                    setDialogVisibility(true)
                    setSubmissionIndex(index)
                }
            )

            Text(
                text = stringResource(id = R.string.mark_input),
                fontSize = 12.sp,
                color = colorResource(id = R.color.darker_sea_blue)
            )
        }

        Image(
            painter = painterResource(id = R.drawable.download_icon),
            contentDescription = stringResource(id = R.string.empty),
            modifier = Modifier.size(30.dp)
        )
    }
}

@Composable
fun BlockedProfile() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dark_blue)),
        contentAlignment = Center
    ) {
        Text(
            text = stringResource(id = R.string.blocked_alert),
            color = colorResource(id = R.color.white),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(16.dp)
        )
    }
}

@Composable
fun TeacherSearchItem(
    profileResponseData: ProfileResponseData,
    onTeacherClick: (ProfileResponseData) -> Unit,
    setTeacherListVisibility: (Boolean) -> Unit,
    context: Context,
) {
    val teacherAdded = stringResource(id = R.string.teacher_added)
    Row(
        verticalAlignment = CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
            .background(colorResource(id = R.color.dark_blue), RoundedCornerShape(8.dp))
            .border(1.dp, colorResource(id = R.color.baby_blue), RoundedCornerShape(8.dp))
            .clickable {
                onTeacherClick(profileResponseData)
                setTeacherListVisibility(false)
                Toast
                    .makeText(
                        context,
                        teacherAdded,
                        Toast.LENGTH_LONG
                    )
                    .show()
            }
    ) {
        Image(
            painter = if (profileResponseData.assets?.profilePic?.get(0)?.data?.src?.isEmpty() == true) {
                painterResource(id = R.drawable.ic_launcher_background)
            } else {
                rememberAsyncImagePainter(ImageRequest.Builder(LocalContext.current)
                    .data(data = profileResponseData.assets?.profilePic?.get(0)?.data?.src)
                    .apply(block = fun ImageRequest.Builder.() {
                        crossfade(true)
                    }).build()
                )
            },
            contentDescription = stringResource(id = R.string.email),
            contentScale = Crop,
            modifier = Modifier
                .size(50.dp)
                .padding(6.dp)
                .clip(RoundedCornerShape(8.dp))
                .border(1.dp, colorResource(id = R.color.white), RoundedCornerShape(8.dp))

        )

        Text(
            text = profileResponseData.fullName,
            color = colorResource(id = R.color.white),
            fontSize = 14.sp,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun ClassSearchItem(
    className: String,
    onClassClick: (String) -> Unit,
    setClassVisibility: ((Boolean) -> Unit)?,
    context: Context,
) {
    val classAdded = stringResource(id = R.string.class_added)
    Row(
        modifier = Modifier
            .padding(start = 25.dp, end = 25.dp)
            .fillMaxWidth()
            .background(colorResource(id = R.color.dark_blue))
            .border(1.dp, colorResource(id = R.color.baby_blue))
            .clickable {
                setClassVisibility?.let { it(false) }
                onClassClick(className)
                Toast
                    .makeText(
                        context,
                        classAdded,
                        Toast.LENGTH_LONG
                    )
                    .show()
            }
    ) {
        Text(
            text = className,
            color = colorResource(id = R.color.white),
            fontSize = 14.sp,
            modifier = Modifier
                .padding(16.dp)
        )
    }
}


@Composable
fun Loading() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(colorResource(id = R.color.dark_blue))
    ) {
        val (headphones, loading) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.splash_logo2),
            contentDescription = EMPTY_STRING,
            modifier = Modifier
                .size(250.dp)
                .constrainAs(headphones) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })

        Box(
            modifier = Modifier
                .size(200.dp)
                .constrainAs(loading) {
                    top.linkTo(parent.top, 70.dp)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }, contentAlignment = Center
        ) {
//            LottieAnimation(
//                composition = composition,
//                iterations = LottieConstants.IterateForever
//            )
        }

//        Text(
//            text = stringResource(id = R.string.loading), modifier = Modifier
//                .size(250.dp)
//                .constrainAs(title) {
//                    top.linkTo(headphones.bottom, 50.dp)
//                    start.linkTo(parent.start)
//                    end.linkTo(parent.end)
//                }, style = TextStyle(
//                color = colorResource(id = R.color.orange),
//                textAlign = TextAlign.Center, fontSize = 26.sp, fontWeight = FontWeight.Bold
//            )
//        )
    }
}

@Composable
@Preview
fun Preview() {

//    UserMessage(profile = ProfileData("Kalina Valeva", "", "", "", ""),
//        createTime = "12:30",
//        message = "Hello, there!")
}


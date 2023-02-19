package com.mobile.tuesplace.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
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
import com.mobile.tuesplace.R
import com.mobile.tuesplace.data.GroupResponseData
import com.mobile.tuesplace.data.PostData
import com.mobile.tuesplace.data.ProfileData
import com.mobile.tuesplace.data.ProfileResponseData
import com.mobile.tuesplace.ui.theme.BabyBlue

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
            textColor = Color.Black,
            placeholderColor = Color.Black,
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
                brush = Brush.horizontalGradient(colors = colors ?: listOf(
                    colorResource(id = R.color.logo_blue),
                    colorResource(id = R.color.blue)
                )),
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
fun PostItem(post: PostData, onPostClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(300.dp, 9999.dp)
            .padding(16.dp)
            .clickable { onPostClick() }
            .background(White, RoundedCornerShape(8.dp))
    ) {
        Text(
            text = "Dora",
            color = Color.Black,
            modifier = Modifier
                .padding(6.dp)
                .align(Alignment.TopStart)
        )
        Text(
            text = post.createdAt,
            modifier = Modifier
                .padding(6.dp)
                .align(Alignment.TopEnd)
        )
        Text(
            text = post.body,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
                .align(Center)
        )
    }
}

@Composable
fun EmptyScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BabyBlue)) {
        Text(text = stringResource(id = R.string.screen_not_found),
            color = White,
            textAlign = TextAlign.Center)
    }
}

@Composable
fun MenuItem(image: Painter, string: String, modifier: Modifier?, onClick: () -> Unit) {
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
        .border(2.dp,
            colorResource(id = R.color.white),
            RoundedCornerShape(8.dp)
        )
    ) {

        Image(
            painter = image,
            contentDescription = "",
            contentScale = ContentScale.Crop,
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
        //AsyncImage(model = groupData.teachers?.get(0)., contentDescription = null)
        ConstraintLayout(modifier = Modifier
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

            groupData.teachers?.get(0)?.let {
                Text(
                    text = it.fullName,
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

        groupData.teachers?.get(0)?.let {
            Text(
                text = it.fullName,
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
fun buttonChangeColorOnClick(
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
    modifier: Modifier?
) {
    val currentModifier = modifier?: Modifier
    Column(
        modifier = currentModifier
            .padding(16.dp)
            .fillMaxWidth()
            .background(colorResource(id = R.color.dark_blue)),
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
            placeholder = {
                Text(text = placeholder,
                    color = colorResource(id = R.color.baby_blue))
            },
            modifier = Modifier
                .background(colorResource(id = R.color.dark_blue))
                .border(2.dp, colorResource(id = R.color.baby_blue), RoundedCornerShape(8.dp))
        )
    }
}

@Composable
fun StudentItem(student: ProfileResponseData, onClick: (String) -> Unit) {
    ConstraintLayout(
        modifier = Modifier
            .clickable { onClick(student._id) }
            .padding(6.dp)
            .size(150.dp)
            .border(1.dp, colorResource(id = R.color.baby_blue), RoundedCornerShape(8.dp))
    ) {
        //Image(painter = , contentDescription = )
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
fun SearchView(state: MutableState<TextFieldValue>, modifier: Modifier?) {
    val currentModifier = modifier?: Modifier
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
        placeholder = { Text(text = stringResource(id = R.string.search))},
        trailingIcon = {
            if (state.value != TextFieldValue("")) {
                IconButton(
                    onClick = {
                        state.value =
                            TextFieldValue("") // Remove text from TextField when you press the 'X' icon
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp)
                    )
                }
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(8.dp), // The TextFiled has rounded corners top left and right by default
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
fun InfoItem(title: String, text: String){
    Column(horizontalAlignment = Start) {
        Text(text = title.uppercase(), color = colorResource(id = R.color.baby_blue), fontSize = 25.sp)
        Text(text = text, color = White, fontSize = 20.sp)
    }
}

@Composable
@Preview
fun Preview() {
    // MenuItem(image = painterResource(id = R.drawable.teacher_icon), string = "Учители", null) {}
    GroupClassItem(GroupResponseData("",
        "Bulgarian Language and Literature",
        "",
        arrayListOf("9B"),
        arrayListOf(ProfileData("Dora Tsvetanova", "", "", "", "")))) {}
}


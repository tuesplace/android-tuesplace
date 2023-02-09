package com.mobile.tuesplace.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobile.tuesplace.R
import com.mobile.tuesplace.data.PostData
import com.mobile.tuesplace.ui.theme.BabyBlue

@Composable
fun TextField(
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
            .border(BorderStroke(2.dp, Color.White), RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        placeholder = { Text(placeholder) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = Color.White,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
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
    onLoginClick: () -> Unit,
    buttonPadding: PaddingValues?,
) {
    val currentModifier = modifier ?: Modifier
    Box(
        modifier = currentModifier
            .fillMaxWidth()
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
                onLoginClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = buttonText,
            color = Color.White,
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
            .background(Color.White, RoundedCornerShape(8.dp))
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
                .align(Alignment.Center)
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
            color = Color.White,
            textAlign = TextAlign.Center)
    }
}


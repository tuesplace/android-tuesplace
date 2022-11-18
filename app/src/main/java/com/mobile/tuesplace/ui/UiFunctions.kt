package com.mobile.tuesplace.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobile.tuesplace.R

@Composable
fun TextFields(
    value: String,
    onValueChange: (String) -> Unit,
    stringId: Int
) {
    TextField(
        value = value,
        maxLines = 1,
        onValueChange = { onValueChange(it) },
        modifier = Modifier
            .padding(top = 22.dp, start = 12.dp, end = 12.dp)
            .fillMaxWidth()
            .border(BorderStroke(2.dp, Color.White), RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        placeholder = { Text(stringResource(id = stringId)) },
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
    val defaultColors = colors ?: listOf(
        colorResource(id = R.color.logo_blue),
        colorResource(id = R.color.blue)
    )
    val defaultButtonPadding = buttonPadding ?: PaddingValues(start = 12.dp, end = 12.dp)
    val modifierInfo = modifier ?: Modifier.fillMaxWidth()
    Box(
        modifier = modifierInfo
            .padding(defaultButtonPadding)
            .background(
                brush = Brush.horizontalGradient(colors = defaultColors),
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


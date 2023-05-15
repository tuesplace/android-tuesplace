package com.mobile.tuesplace.ui.login

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mobile.tuesplace.EMPTY_GRADE
import com.mobile.tuesplace.EMPTY_STRING
import com.mobile.tuesplace.R
import com.mobile.tuesplace.ui.GradientBorderButtonRound
import com.mobile.tuesplace.ui.Loading
import com.mobile.tuesplace.ui.TextFieldFunction
import com.mobile.tuesplace.ui.states.SignInUiState

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit,
    onForgottenPasswordClick: () -> Unit,
    email: String,
    setEmail: (String) -> Unit,
    password: String,
    setPassword: (String) -> Unit,
    passwordVisibility: Boolean,
    setPasswordVisibility: (Boolean) -> Unit,
    uiState: SignInUiState,
    onSuccess: () -> Unit,
    isCorrectPassword: Boolean,
    isCorrectEmail: Boolean,
) {
    when (uiState) {
        SignInUiState.Empty -> {
        }

        is SignInUiState.Error -> {
        }

        SignInUiState.Loading -> {
            Loading()
        }

        SignInUiState.Success -> {
            onSuccess()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dark_blue))
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.align(Alignment.Center)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_2),
                contentDescription = "",
                modifier = Modifier.padding(
                    PaddingValues(start = 16.dp)
                )
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .padding(PaddingValues(start = 16.dp, end = 16.dp))
                    .border(
                        BorderStroke(2.dp, colorResource(id = R.color.logo_blue)),
                        RoundedCornerShape(8)
                    )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    TextFieldFunction(
                        email,
                        setEmail,
                        stringResource(id = R.string.email),
                        null,
                        true,
                        isError = isCorrectEmail
                    )
                    TextField(
                        value = password,
                        isError = isCorrectPassword,
                        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                        onValueChange = { setPassword(it) },
                        trailingIcon = {
                            IconButton(onClick = {
                                setPasswordVisibility(passwordVisibility)
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_view),
                                    contentDescription = EMPTY_STRING,
                                    tint = Color.Black
                                )
                            }
                        },
                        maxLines = 1,
                        placeholder = { Text(stringResource(id = R.string.password)) },
                        modifier = Modifier
                            .padding(top = 22.dp, start = 12.dp, end = 12.dp)
                            .fillMaxWidth()
                            .border(BorderStroke(2.dp, Color.White), RoundedCornerShape(16.dp))
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            backgroundColor = Color.White,
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            textColor = Color.Black,
                            disabledTrailingIconColor = Color.Black,
                            disabledLeadingIconColor = Color.Black,
                            placeholderColor = Color.Black
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    )
//                    Text(
//                        text = stringResource(id = R.string.forgotten_password),
//                        color = colorResource(id = R.color.white),
//                        style = TextStyle(textDecoration = TextDecoration.Underline),
//                        modifier = Modifier
//                            .padding(PaddingValues(16.dp))
//                            .clickable { onForgottenPasswordClick() }
//                    )
                    GradientBorderButtonRound(
                        paddingValues = PaddingValues(1.dp),
                        buttonText = stringResource(id = R.string.login),
                        modifier = Modifier
                            .width(200.dp)
                            .height(100.dp),
                        onClick = {
                            onLoginClick()
                        },
                        buttonPadding = PaddingValues(16.dp),
                        colors = null
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun ComposablePreview() {
    LoginScreen({}, {}, "", {}, "", {}, true, {}, SignInUiState.Empty, {},
        isCorrectPassword = false,
        isCorrectEmail = false
    )
}
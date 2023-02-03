package com.mobile.tuesplace.ui.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.mobile.tuesplace.R
import com.mobile.tuesplace.services.ApiServices
import com.mobile.tuesplace.services.RetrofitHelper
import com.mobile.tuesplace.ui.theme.BabyBlue
import com.mobile.tuesplace.ui.theme.Blue

@Composable
fun WelcomeScreen(
    onMessageClick: () -> Unit,
    onEnterClassClick: () -> Unit,
    onEnterVideoroomClick: () -> Unit,
    onLinkClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dark_blue))
    ) {

        val quotesApi = RetrofitHelper.getInstance().create(ApiServices::class.java)

        ConstraintLayout(modifier = Modifier
            .fillMaxSize()
        ) {
            val (messages, enterClass, enterClassroom, bottomImage, text) = createRefs()

            Button(
                modifier = Modifier
                    .padding(top = 150.dp)
                    .size(200.dp)
                    .constrainAs(messages) {
                        start.linkTo(parent.start)
                    },
                onClick = { onMessageClick() },
                shape = CircleShape,
            ) {
                Text(
                    text = stringResource(id = R.string.messages),
                    color = Color.White
                )
            }

            Button(
                modifier = Modifier
                    .padding(start = 100.dp, top = 300.dp)
                    .size(200.dp)
                    .constrainAs(enterClass) {
                        start.linkTo(parent.start)
                        top.linkTo(messages.top)
                    },
                onClick = { onEnterClassClick() },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = BabyBlue
                )
            ) {
                Text(
                    text = stringResource(id = R.string.enter_class),
                    color = Color.Black
                )
            }

            Button(
                modifier = Modifier
                    .padding(top = 30.dp)
                    .size(170.dp)
                    .constrainAs(enterClassroom) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    },
                onClick = { onEnterVideoroomClick() },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Blue
                )
            ) {
                Text(
                    text = stringResource(id = R.string.enter_classroom),
                    color = Color.White
                )
            }

            Image(
                painter = painterResource(id = R.drawable.bottom),
                contentDescription = "",
                modifier = Modifier
                    .padding(top = 530.dp)
                    .size(500.dp)
                    .constrainAs(bottomImage) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })

            val annotatedText = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                        color = Color.White,
                        fontSize = 30.sp)) {
                    append(stringResource(id = R.string.link_to_website))
                }
            }

            ClickableText(
                text = annotatedText,
                onClick = {
                    onLinkClick()
                },
                style = TextStyle(textDecoration = TextDecoration.Underline),
                modifier = Modifier
                    .padding(50.dp)
                    .constrainAs(text) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
            )
        }
    }
}

@Preview
@Composable
fun ComposablePreview() {
    WelcomeScreen({}, {}, {}, {})
}
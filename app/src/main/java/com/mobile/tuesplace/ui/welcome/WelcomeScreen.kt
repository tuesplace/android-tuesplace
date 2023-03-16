package com.mobile.tuesplace.ui.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.mobile.tuesplace.R
import com.mobile.tuesplace.ui.BlockedProfile
import com.mobile.tuesplace.ui.WebViewImage

@Composable
fun WelcomeScreen(
    onMessageClick: () -> Unit,
    onEnterClassClick: () -> Unit,
    onEnterVideoroomClick: () -> Unit,
    onAgendaClick: () -> Unit,
    onLinkClick: () -> Unit,
    blocked: Boolean,
) {
    WelcomeUi(
        onMessageClick = onMessageClick,
        onEnterClassClick = onEnterClassClick,
        onEnterVideoroomClick = onEnterVideoroomClick,
        onAgendaClick = onAgendaClick,
        onLinkClick = onLinkClick,
        blocked = blocked
    )
}

@Composable
fun WelcomeUi(
    onMessageClick: () -> Unit,
    onEnterClassClick: () -> Unit,
    onEnterVideoroomClick: () -> Unit,
    onAgendaClick: () -> Unit,
    onLinkClick: () -> Unit,
    blocked: Boolean,
) {
    if (blocked) {
        BlockedProfile()
    } else {
        ConstraintLayout(modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dark_blue))
        ) {
            val (messages, enterClass, enterClassroom, agenda, webView) = createRefs()

            WebViewImage(
                onImageClick = { onLinkClick() },
                image = painterResource(id = R.drawable.tues_webview),
                text = stringResource(id = R.string.link_to_website),
                modifier = Modifier
                    .padding(16.dp)
                    .height(200.dp)
                    .constrainAs(webView) {
                        top.linkTo(parent.top)
                    }
            )

            com.mobile.tuesplace.ui.MenuItem(
                image = painterResource(id = R.drawable.messages),
                string = stringResource(id = R.string.messages),
                modifier = Modifier
                    .padding(6.dp)
                    .padding(top = 50.dp)
                    .constrainAs(messages) {
                        top.linkTo(webView.bottom)
                    },
                onClick = onMessageClick
            )
            com.mobile.tuesplace.ui.MenuItem(
                image = painterResource(id = R.drawable.online_class),
                stringResource(id = R.string.enter_class),
                modifier = Modifier
                    .padding(6.dp)
                    .constrainAs(enterClass) {
                        top.linkTo(messages.bottom)
                    },
                onClick = onEnterVideoroomClick
            )

            com.mobile.tuesplace.ui.MenuItem(
                image = painterResource(id = R.drawable.classroom_icon),
                stringResource(id = R.string.enter_classroom),
                modifier = Modifier
                    .padding(6.dp)
                    .constrainAs(enterClassroom) {
                        top.linkTo(enterClass.bottom)
                    },
                onClick = onEnterClassClick
            )

            com.mobile.tuesplace.ui.MenuItem(
                image = painterResource(id = R.drawable.agenda),
                stringResource(id = R.string.agenda),
                modifier = Modifier
                    .padding(6.dp)
                    .constrainAs(agenda) {
                        top.linkTo(enterClassroom.bottom)

                    },
                onClick = onAgendaClick
            )
        }
    }
}

@Preview
@Composable
fun ComposablePreview() {
    WelcomeScreen({}, {}, {}, {}, {}, true)
}
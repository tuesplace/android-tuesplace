package com.mobile.tuesplace.ui.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.mobile.tuesplace.R

@Composable
fun SettingsScreen(onEditClick: () -> Unit, onSignOutClick: () -> Unit) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dark_blue))
    ) {
        val (profile, signOut) = createRefs()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(profile) {
                    top.linkTo(parent.top)
                }
        ) {

            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = stringResource(id = R.string.email),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .padding(16.dp)
                    .clip(CircleShape)
            )

            Text(
                text = stringResource(id = R.string.edit),
                color = Color.White,
                modifier = Modifier
                    .align(CenterVertically)
                    .padding(6.dp)
                    .clickable {
                        onEditClick()
                    }
            )
        }

        Text(
            text = stringResource(id = R.string.sign_out),
            color = Color.White,
            modifier = Modifier
                .padding(6.dp)
                .clickable {
                    onSignOutClick()
                }
                .constrainAs(signOut){
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                }
        )
    }
}
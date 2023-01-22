package com.mobile.tuesplace.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.mobile.tuesplace.R
import com.mobile.tuesplace.data.ProfileData
import com.mobile.tuesplace.ui.GradientBorderButtonRound
import com.mobile.tuesplace.ui.welcome.WelcomeAdminScreen
import kotlinx.coroutines.NonDisposableHandle.parent

@Composable
fun ProfileScreen(profileData: ProfileData) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.gray))
    ) {
        val (photo, name, role, email, editBtn) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = stringResource(id = R.string.email),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(200.dp)
                .padding(16.dp)
                .clip(CircleShape)
                .constrainAs(photo) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Text(text = profileData.role.uppercase(),
            color = Color.White,
            fontSize = 30.sp,
            modifier = Modifier
                .padding(16.dp)
                .constrainAs(role) {
                    top.linkTo(photo.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Text(
            text = profileData.fullName,
            color = Color.White,
            fontSize = 25.sp,
            modifier = Modifier
                .padding(6.dp)
                .constrainAs(name) {
                    top.linkTo(role.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Text(
            text = profileData.email,
            color = Color.White,
            fontSize = 20.sp,
            style = TextStyle(textDecoration = TextDecoration.Underline),
            modifier = Modifier
                .padding(16.dp)
                .constrainAs(email) {
                    top.linkTo(name.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        GradientBorderButtonRound(
            colors = null,
            paddingValues = PaddingValues(16.dp),
            buttonText = stringResource(id = R.string.edit),
            onLoginClick = { },
            buttonPadding = PaddingValues(16.dp),
            modifier = Modifier
                .padding(16.dp)
                .constrainAs(editBtn){
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
    }
}

@Composable
@Preview
fun ProfilePreview() {
    ProfileScreen(ProfileData("Kalina Valeva", "kalina.valevaa@gmail.com", "kalina2w3", "admin"))
}
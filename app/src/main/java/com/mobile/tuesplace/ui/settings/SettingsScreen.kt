package com.mobile.tuesplace.ui.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.mobile.tuesplace.R
import com.mobile.tuesplace.data.ProfileResponseData
import com.mobile.tuesplace.ui.GradientBorderButtonRound
import com.mobile.tuesplace.ui.SettingsMenuItem
import com.mobile.tuesplace.ui.states.GetProfileUiState

@Composable
fun SettingsScreen(
    onEditClick: (String) -> Unit,
    onSignOutClick: () -> Unit,
    onForgottenPasswordClick: () -> Unit,
    getProfileUiState: GetProfileUiState,
) {
    when (getProfileUiState) {
        GetProfileUiState.Empty -> {}
        is GetProfileUiState.Error -> {}
        GetProfileUiState.Loading -> {}
        is GetProfileUiState.Success -> {
            SettingsUi(
                onEditClick = onEditClick,
                onSignOutClick = onSignOutClick,
                onForgottenPasswordClick = onForgottenPasswordClick,
                profile = getProfileUiState.profile
            )
        }
    }
}

@Composable
fun SettingsUi(
    onEditClick: (String) -> Unit,
    onSignOutClick: () -> Unit,
    onForgottenPasswordClick: () -> Unit,
    profile: ProfileResponseData,
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dark_blue))
    ) {
        val (profilePicture, editProfile, forgotPassword, signOut) = createRefs()


        Image(
            painter = if (profile.assets?.profilePic?.get(0)?.data?.src?.isEmpty() == true) {
                painterResource(id = R.drawable.ic_launcher_background)
            } else {
                rememberAsyncImagePainter(ImageRequest.Builder(LocalContext.current)
                    .data(data = profile.assets?.profilePic?.get(0)?.data?.src)
                    .apply(block = fun ImageRequest.Builder.() {
                        crossfade(true)
                    }).build())
            },
            contentDescription = stringResource(id = R.string.email),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .padding(16.dp)
                .clip(RoundedCornerShape(8.dp))
                .border(3.dp, colorResource(id = R.color.white), RoundedCornerShape(8.dp))
                .constrainAs(profilePicture) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
        )

        SettingsMenuItem(
            text = stringResource(id = R.string.my_profile),
            onClick = { onEditClick(profile._id) },
            modifier = Modifier
                .constrainAs(editProfile) {
                    top.linkTo(profilePicture.bottom)

                }
        )

        SettingsMenuItem(
            text = stringResource(id = R.string.forgotten_password),
            onClick = { onForgottenPasswordClick() },
            modifier = Modifier
                .constrainAs(forgotPassword) {
                    top.linkTo(editProfile.bottom)
                }
        )

        GradientBorderButtonRound(
            colors = listOf(colorResource(id = R.color.darker_sea_blue),
                colorResource(id = R.color.baby_blue)),
            paddingValues = PaddingValues(16.dp),
            buttonText = stringResource(id = R.string.sign_out),
            onClick = { onSignOutClick() },
            buttonPadding = null,
            modifier = Modifier
                .padding(6.dp)
                .constrainAs(signOut) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
    }
}


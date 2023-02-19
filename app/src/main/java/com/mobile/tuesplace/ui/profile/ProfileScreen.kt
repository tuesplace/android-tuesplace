package com.mobile.tuesplace.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.mobile.tuesplace.R
import com.mobile.tuesplace.data.ProfileData
import com.mobile.tuesplace.data.ProfileResponseData
import com.mobile.tuesplace.ui.GradientBorderButtonRound
import com.mobile.tuesplace.ui.InfoItem
import com.mobile.tuesplace.ui.states.EditProfileUiState
import com.mobile.tuesplace.ui.states.GetProfileUiState

@Composable
fun ProfileScreen(profileUiState: GetProfileUiState, onEditClick: () -> Unit) {
    when (profileUiState) {
        GetProfileUiState.Empty -> {}
        is GetProfileUiState.Error -> {}
        GetProfileUiState.Loading -> {}
        is GetProfileUiState.Success -> {
            ProfileUi(profileData = profileUiState.profile, onEditClick)
        }
        EditProfileUiState.Loading -> {}
    }
}

@Composable
fun ProfileUi(profileData: ProfileResponseData, onEditClick: () -> Unit) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.darker_sea_blue))
    ) {
        val (photo, photoBackground, info, editButton) = createRefs()

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .background(colorResource(id = R.color.dark_blue))
            .constrainAs(photoBackground) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(colorResource(id = R.color.dark_blue), RoundedCornerShape(50.dp))
                .constrainAs(photo) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = stringResource(id = R.string.email),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(16.dp)
                    .size(100.dp)
                    .border(2.dp, colorResource(id = R.color.white), CircleShape)
                    .clip(CircleShape)
            )

        }

        Column(
            modifier = Modifier
                .constrainAs(info) {
                    start.linkTo(parent.start)
                    top.linkTo(photo.bottom)
                    bottom.linkTo(editButton.top)
                }
                .padding(16.dp)
        ) {
            InfoItem(title = stringResource(id = R.string.name), text = profileData.fullName)
            Spacer(modifier = Modifier.padding(6.dp))
            InfoItem(title = stringResource(id = R.string.role), text = profileData.role)
            Spacer(modifier = Modifier.padding(6.dp))
            InfoItem(title = stringResource(id = R.string.email), text = profileData.email)
            Spacer(modifier = Modifier.padding(6.dp))
//            InfoItem(title = stringResource(id = R.string.phone), text = profileData.)
            if (profileData.role == stringResource(id = R.string.student_role)) {
                profileData.className?.let {
                    InfoItem(title = stringResource(id = R.string.class_string),
                        text = it)
                }
            }
        }


        if(profileData.role == stringResource(id = R.string.admin_role)){
            GradientBorderButtonRound(
                colors = listOf(colorResource(id = R.color.baby_blue), colorResource(id = R.color.lighter_dark_blue)),
                paddingValues = PaddingValues(16.dp),
                buttonText = stringResource(id = R.string.edit),
                onClick = { onEditClick() },
                buttonPadding = PaddingValues(16.dp),
                modifier = Modifier
                    .constrainAs(editButton) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
        }
    }
}

@Composable
@Preview
fun ProfilePreview() {
    ProfileUi(ProfileResponseData( "", "Kalina Valeva", "kalina.valevaa@gmail.com", "kalina2w3", "admin", "")) {}
}
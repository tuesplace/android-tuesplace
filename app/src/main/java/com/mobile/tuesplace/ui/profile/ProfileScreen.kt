package com.mobile.tuesplace.ui.profile

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.mobile.tuesplace.EMPTY_STRING
import com.mobile.tuesplace.R
import com.mobile.tuesplace.data.ProfileResponseData
import com.mobile.tuesplace.session.SessionManager
import com.mobile.tuesplace.ui.EmptyScreen
import com.mobile.tuesplace.ui.GradientBorderButtonRound
import com.mobile.tuesplace.ui.InfoItem
import com.mobile.tuesplace.ui.Loading
import com.mobile.tuesplace.ui.states.GetProfileByIdUiState

@Composable
fun ProfileScreen(
    profileUiState: GetProfileByIdUiState,
    onEditClick: (String) -> Unit,
    isAdmin: Boolean,
) {
    val myProfileData = SessionManager.getUser()

    if (isAdmin) {
        when (profileUiState) {
            GetProfileByIdUiState.Empty -> {
                EmptyScreen()
            }
            is GetProfileByIdUiState.Error -> {
                Toast.makeText(
                    LocalContext.current,
                    profileUiState.exception ?: stringResource(R.string.create_error),
                    Toast.LENGTH_LONG
                ).show()
            }

            GetProfileByIdUiState.Loading -> {
                Loading()
            }

            is GetProfileByIdUiState.Success -> {
                if (myProfileData != null) {
                    ProfileUi(
                        profileData = profileUiState.profile,
                        onEditClick,
                        myProfile = myProfileData
                    )
                } else {
                    EmptyScreen()
                    Toast.makeText(
                        LocalContext.current,
                        stringResource(R.string.create_error),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    } else {
        if (myProfileData != null) {
            ProfileUi(
                profileData = null,
                onEditClick = onEditClick,
                myProfile = myProfileData
            )
        } else {
            EmptyScreen()
            Toast.makeText(
                LocalContext.current,
                stringResource(R.string.create_error),
                Toast.LENGTH_LONG
            ).show()
        }
    }
}

@Composable
fun ProfileUi(
    profileData: ProfileResponseData?,
    onEditClick: (String) -> Unit,
    myProfile: ProfileResponseData,
) {
    val setProfile = profileData?: myProfile
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
                painter = if (setProfile.assets?.profilePic?.get(0)?.data?.src?.isEmpty() == true) {
                    painterResource(id = R.drawable.ic_launcher_background)
                } else {
                    rememberAsyncImagePainter(ImageRequest.Builder(LocalContext.current)
                        .data(data = setProfile.assets?.profilePic?.get(0)?.data?.src)
                        .apply(block = fun ImageRequest.Builder.() {
                            crossfade(true)
                        }).build()
                    )
                },
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
            InfoItem(title = stringResource(id = R.string.name), text = setProfile.fullName)
            Spacer(modifier = Modifier.padding(6.dp))
            InfoItem(title = stringResource(id = R.string.role), text = setProfile.role)
            Spacer(modifier = Modifier.padding(6.dp))
            InfoItem(
                title = stringResource(id = R.string.email),
                text = setProfile.email ?: EMPTY_STRING
            )
            Spacer(modifier = Modifier.padding(6.dp))
//            InfoItem(title = stringResource(id = R.string.phone), text = profileData.)
            if (setProfile.role == stringResource(id = R.string.student_role)) {
                setProfile.className?.let {
                    InfoItem(
                        title = stringResource(id = R.string.class_string),
                        text = it
                    )
                }
            }
        }


        if (myProfile.role == stringResource(id = R.string.admin_role)) {
            GradientBorderButtonRound(
                colors = listOf(
                    colorResource(id = R.color.baby_blue),
                    colorResource(id = R.color.lighter_dark_blue)
                ),
                paddingValues = PaddingValues(16.dp),
                buttonText = stringResource(id = R.string.edit),
                onClick = { onEditClick(setProfile._id) },
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
    //ProfileUi(ProfileResponseData( "", "Kalina Valeva", "kalina.valevaa@gmail.com", "kalina2w3", "admin", "", )) {}
}
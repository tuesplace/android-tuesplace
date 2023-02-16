package com.mobile.tuesplace.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
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
import com.mobile.tuesplace.ui.GradientBorderButtonRound
import com.mobile.tuesplace.ui.TextField
import com.mobile.tuesplace.ui.states.GetProfileUiState

@Composable
fun EditProfileScreen(profileUiState: GetProfileUiState, enabled: Boolean, onSaveChanges: () -> Unit) {
    when(profileUiState){
        GetProfileUiState.Empty -> { }
        is GetProfileUiState.Error -> { }
        GetProfileUiState.Loading -> { }
        is GetProfileUiState.Success -> {
            EditProfileUi(profileData = profileUiState.profile, enabled = enabled, onSaveChanges = onSaveChanges)
        }
    }
}

@Composable
fun EditProfileUi(profileData: ProfileData, enabled: Boolean, onSaveChanges: () -> Unit){
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.gray))
    ) {
        val (photo, fields, editBtn) = createRefs()

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

        Column(modifier = Modifier
            .fillMaxWidth()
            .constrainAs(fields) {
                top.linkTo(photo.top)
                bottom.linkTo(editBtn.top)
            }) {
            TextField(
                value = "",
                onValueChange = {},
                placeholder = profileData.role,
                modifier = null,
                enabled = enabled,
                isError = null
            )

            TextField(
                value = "",
                onValueChange = {},
                placeholder = profileData.fullName,
                modifier = null,
                enabled = enabled,
                isError = null
            )

            TextField(
                value = "",
                onValueChange = {},
                placeholder = profileData.email,
                modifier = null,
                enabled = enabled,
                isError = null
            )
        }

        GradientBorderButtonRound(
            colors = null,
            paddingValues = PaddingValues(16.dp),
            buttonText = stringResource(id = R.string.save_changes),
            onClick = { onSaveChanges() },
            buttonPadding = PaddingValues(16.dp),
            modifier = Modifier
                .padding(16.dp)
                .constrainAs(editBtn) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
    }
}

@Composable
@Preview
fun EditProfilePreview() {
    EditProfileUi(ProfileData("Kalina Valeva", "kalina.valevaa@gmail.com", "kalina2w3", "admin"), false) {}
}
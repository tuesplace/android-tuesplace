package com.mobile.tuesplace.ui.profile

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import com.mobile.tuesplace.data.ProfileResponseData
import com.mobile.tuesplace.ui.GradientBorderButtonRound
import com.mobile.tuesplace.ui.TextFieldWithTitle
import com.mobile.tuesplace.ui.states.EditProfileUiState
import com.mobile.tuesplace.ui.states.GetProfileUiState

@Composable
fun EditProfileScreen(
    profileUiState: GetProfileUiState,
    editProfileStateFlow: EditProfileUiState,
    onSaveChanges: () -> Unit,
    onAddPhotoClick: () -> Unit,
    changeName: String,
    setChangedName: (String) -> Unit,
    changeClass: String,
    setChangedClass: (String) -> Unit,
    changeEmail: String,
    setChangedEmail: (String) -> Unit,
) {
    when (profileUiState) {
        GetProfileUiState.Empty -> {}
        is GetProfileUiState.Error -> {
            // val error = profileUiState.exception
        }
        GetProfileUiState.Loading -> {}
        is GetProfileUiState.Success -> {
            EditProfileUi(
                profileData = profileUiState.profile,
                onSaveChanges = onSaveChanges,
                onAddPhotoClick = onAddPhotoClick,
                changeName = changeName,
                setChangedName = setChangedName,
                changeClass = changeClass,
                setChangedClass = setChangedClass,
                changeEmail = changeEmail,
                setChangedEmail = setChangedEmail
            )
        }
        EditProfileUiState.Loading -> {}
    }
    when(editProfileStateFlow){
        EditProfileUiState.Empty -> {  }
        is EditProfileUiState.Error -> {
            val error = editProfileStateFlow.exception
        }
        EditProfileUiState.Success -> {
            val success = "da"
        }
    }
}

@Composable
fun EditProfileUi(
    profileData: ProfileResponseData,
    onSaveChanges: () -> Unit,
    onAddPhotoClick: () -> Unit,
    changeName: String,
    setChangedName: (String) -> Unit,
    changeClass: String,
    setChangedClass: (String) -> Unit,
    changeEmail: String,
    setChangedEmail: (String) -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dark_blue))
    ) {
        val (photo, addPhoto, fields, saveChanges) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = stringResource(id = R.string.email),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(top = 50.dp)
                .size(130.dp)
                .border(2.dp, colorResource(id = R.color.baby_blue), CircleShape)
                .clip(CircleShape)
                .constrainAs(photo) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Box(
            modifier = Modifier
                .padding(top = 150.dp)
                .clickable { onAddPhotoClick() }
                .size(35.dp)
                .clip(CircleShape)
                .background(colorResource(id = R.color.darker_sea_blue))
                .constrainAs(addPhoto) {
                    top.linkTo(photo.top)
                    end.linkTo(photo.end)
                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.add_icon),
                contentDescription = "",
                modifier = Modifier.size(20.dp)
            )
        }

        Column(modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .constrainAs(fields) {
                top.linkTo(photo.bottom)
                bottom.linkTo(saveChanges.top)
            }) {
            TextFieldWithTitle(
                title = stringResource(id = R.string.name),
                value = changeName,
                onValueChange = setChangedName,
                placeholder = profileData.fullName,
                enabled = true,
                isError = null,
                modifier = Modifier.clickable { }
            )

            TextFieldWithTitle(
                title = stringResource(id = R.string.email),
                value = changeEmail,
                onValueChange = setChangedEmail,
                placeholder = profileData.email,
                enabled = true,
                isError = null,
                modifier = Modifier.clickable { }
            )

            if (profileData.role == stringResource(id = R.string.student_role)) {
                profileData.className?.let {
                    TextFieldWithTitle(
                        title = stringResource(id = R.string.class_string),
                        value = changeClass,
                        onValueChange = setChangedClass,
                        placeholder = it,
                        enabled = true,
                        isError = null,
                        modifier = Modifier.clickable { }
                    )
                }
            }
        }

        GradientBorderButtonRound(
            colors = listOf(colorResource(id = R.color.baby_blue), colorResource(id = R.color.lighter_dark_blue)),
            paddingValues = PaddingValues(16.dp),
            buttonText = stringResource(id = R.string.save_changes),
            onClick = { onSaveChanges() },
            buttonPadding = PaddingValues(16.dp),
            modifier = Modifier
                .constrainAs(saveChanges) {
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
    EditProfileUi(ProfileResponseData(
        "",
        "Kalina Valeva",
        "kalina.valevaa@gmail.com",
        "kalina2w3",
        "admin",
        ""),
        {}, {}, "", {}, "", {}, "", {})
}
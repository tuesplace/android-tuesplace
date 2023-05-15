package com.mobile.tuesplace.ui.profile

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import com.mobile.tuesplace.IMAGE_FILE_TYPE
import com.mobile.tuesplace.MULTIPART_NAME_IMAGE
import com.mobile.tuesplace.R
import com.mobile.tuesplace.ui.GradientBorderButtonRound
import com.mobile.tuesplace.ui.Loading
import com.mobile.tuesplace.ui.ResultLauncher
import com.mobile.tuesplace.ui.TextFieldWithTitle
import com.mobile.tuesplace.ui.states.CreateProfileUiState
import okhttp3.MultipartBody

@Composable
fun CreateProfileScreen(
    name: String,
    setName: (String) -> Unit,
    email: String,
    setEmail: (String) -> Unit,
    role: String,
    setRole: (String) -> Unit,
    grade: String,
    setGrade: (String) -> Unit,
    onCreateClick: () -> Unit,
    onImageUpload: (MultipartBody.Part) -> Unit,
    onAddPhotoClick: () -> Unit,
    createProfileUiState: CreateProfileUiState,
    onCreateSuccess: () -> Unit,
) {
    CreateProfileUi(
        name = name,
        setName = setName,
        email = email,
        setEmail = setEmail,
        role = role,
        setRole = setRole,
        grade = grade,
        setGrade = setGrade,
        onCreateClick = onCreateClick,
        onImageUpload = onImageUpload,
        onAddPhotoClick = onAddPhotoClick
    )

    when (createProfileUiState) {
        CreateProfileUiState.Empty -> {}
        is CreateProfileUiState.Error -> {
            Toast.makeText(
                LocalContext.current,
                createProfileUiState.exception ?: stringResource(R.string.create_error),
                Toast.LENGTH_LONG
            ).show()
        }

        CreateProfileUiState.Loading -> {
            Loading()
        }
        CreateProfileUiState.Success -> {
            Toast.makeText(
                LocalContext.current,
                stringResource(id = R.string.account_created),
                Toast.LENGTH_LONG
            ).show()
            onCreateSuccess()
        }
    }
}

@Composable
fun CreateProfileUi(
    name: String,
    setName: (String) -> Unit,
    email: String,
    setEmail: (String) -> Unit,
    role: String,
    setRole: (String) -> Unit,
    grade: String,
    setGrade: (String) -> Unit,
    onCreateClick: () -> Unit,
    onImageUpload: (MultipartBody.Part) -> Unit,
    onAddPhotoClick: () -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dark_blue))
    ) {
        val (photo, addPhoto, fields, saveChanges) = createRefs()

        Image(
            painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = stringResource(id = R.string.empty),
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

        ResultLauncher(
            type = IMAGE_FILE_TYPE,
            onUploadClick = onImageUpload,
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
            multipartName = MULTIPART_NAME_IMAGE
        )

        Column(modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .constrainAs(fields) {
                top.linkTo(addPhoto.bottom)
                bottom.linkTo(saveChanges.top)
            }) {
            TextFieldWithTitle(
                title = stringResource(id = R.string.name),
                value = name,
                onValueChange = setName,
                placeholder = stringResource(id = R.string.enter),
                enabled = true,
                isError = null,
                modifier = Modifier.clickable { },
                setClassVisibility = null
            )

            TextFieldWithTitle(
                title = stringResource(id = R.string.email),
                value = email,
                onValueChange = { setEmail(it) },
                placeholder = stringResource(id = R.string.enter),
                enabled = true,
                isError = null,
                modifier = Modifier.clickable { },
                setClassVisibility = null
            )

            TextFieldWithTitle(
                title = stringResource(id = R.string.class_string),
                value = grade,
                onValueChange = { setGrade(it) },
                placeholder = stringResource(id = R.string.enter),
                enabled = true,
                isError = null,
                modifier = Modifier.clickable { },
                setClassVisibility = null
            )

            TextFieldWithTitle(
                title = stringResource(id = R.string.role),
                value = role,
                onValueChange = { setRole(it) },
                placeholder = stringResource(id = R.string.enter),
                enabled = true,
                isError = null,
                modifier = Modifier.clickable { },
                setClassVisibility = null
            )
        }

        GradientBorderButtonRound(
            colors = listOf(
                colorResource(id = R.color.baby_blue),
                colorResource(id = R.color.lighter_dark_blue)
            ),
            paddingValues = PaddingValues(16.dp),
            buttonText = stringResource(id = R.string.create),
            onClick = { onCreateClick() },
            buttonPadding = PaddingValues(16.dp),
            modifier = Modifier
                .constrainAs(saveChanges) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(fields.bottom)
                }
        )

    }
}


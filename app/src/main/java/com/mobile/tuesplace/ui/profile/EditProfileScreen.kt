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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.mobile.tuesplace.IMAGE_FILE_TYPE
import com.mobile.tuesplace.MULTIPART_NAME_IMAGE
import com.mobile.tuesplace.R
import com.mobile.tuesplace.data.ProfileResponseData
import com.mobile.tuesplace.ui.GradientBorderButtonRound
import com.mobile.tuesplace.ui.ResultLauncher
import com.mobile.tuesplace.ui.TextFieldWithTitle
import com.mobile.tuesplace.ui.states.EditProfileUiState
import com.mobile.tuesplace.ui.states.GetProfileByIdUiState
import com.mobile.tuesplace.ui.states.GetProfileUiState
import com.mobile.tuesplace.ui.states.PutMyProfileAssetsUiState
import okhttp3.MultipartBody

@Composable
fun EditProfileScreen(
    profileUiState: GetProfileByIdUiState,
    editProfileStateFlow: EditProfileUiState,
    onSaveChanges: () -> Unit,
    onAddPhotoClick: () -> Unit,
    changeName: String,
    setChangedName: (String) -> Unit,
    changeClass: String,
    setChangedClass: (String) -> Unit,
    changeEmail: String,
    setChangedEmail: (String) -> Unit,
    onImageUpload: (MultipartBody.Part) -> Unit,
    imageUpload: MultipartBody.Part?,
    setImageUpload: (MultipartBody.Part) -> Unit,
    getProfileUiState: GetProfileUiState,
    onEditProfileSuccess: () -> Unit,
    profileAssetsUiState: PutMyProfileAssetsUiState
) {
    when (profileUiState) {
        GetProfileByIdUiState.Empty -> {}
        is GetProfileByIdUiState.Error -> {}
        GetProfileByIdUiState.Loading -> {}
        is GetProfileByIdUiState.Success -> {
            EditProfileUi(
                profileData = profileUiState.profile,
                onSaveChanges = onSaveChanges,
                onAddPhotoClick = onAddPhotoClick,
                changeName = changeName,
                setChangedName = setChangedName,
                changeClass = changeClass,
                setChangedClass = setChangedClass,
                changeEmail = changeEmail,
                setChangedEmail = setChangedEmail,
                onImageUpload = onImageUpload,
                imageUpload = imageUpload,
                setImageUpload = setImageUpload
            )
        }
    }
    when (getProfileUiState) {
        GetProfileUiState.Empty -> {}
        is GetProfileUiState.Error -> {}
        GetProfileUiState.Loading -> {}
        is GetProfileUiState.Success -> {
            EditProfileUi(
                profileData = getProfileUiState.profile,
                onSaveChanges = onSaveChanges,
                onAddPhotoClick = onAddPhotoClick,
                changeName = changeName,
                setChangedName = setChangedName,
                changeClass = changeClass,
                setChangedClass = setChangedClass,
                changeEmail = changeEmail,
                setChangedEmail = setChangedEmail,
                onImageUpload = onImageUpload,
                imageUpload = imageUpload,
                setImageUpload = setImageUpload
            )
        }
    }
    when (editProfileStateFlow) {
        EditProfileUiState.Empty -> {}
        is EditProfileUiState.Error -> {

        }
        EditProfileUiState.Success -> {
            onEditProfileSuccess()
            Toast.makeText(
                LocalContext.current,
                stringResource(id = R.string.profile_edited),
                Toast.LENGTH_LONG
            ).show()
        }
        EditProfileUiState.Loading -> {}
    }

    when(profileAssetsUiState) {
        PutMyProfileAssetsUiState.Empty -> {}
        is PutMyProfileAssetsUiState.Error -> {}
        PutMyProfileAssetsUiState.Loading -> {}
        PutMyProfileAssetsUiState.Success -> {
            onEditProfileSuccess()
            Toast.makeText(
                LocalContext.current,
                stringResource(id = R.string.profile_edited),
                Toast.LENGTH_LONG
            ).show()
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
    onImageUpload: (MultipartBody.Part) -> Unit,
    imageUpload: MultipartBody.Part?,
    setImageUpload: (MultipartBody.Part) -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dark_blue))
    ) {
        val (photo, addPhoto, fields, saveChanges) = createRefs()

        Image(
            painter = if (profileData.assets?.profilePic?.get(0)?.data?.src?.isEmpty() == true) {
                painterResource(id = R.drawable.ic_launcher_background)
            } else {
                rememberAsyncImagePainter(ImageRequest.Builder(LocalContext.current)
                    .data(data = profileData.assets?.profilePic?.get(0)?.data?.src)
                    .apply(block = fun ImageRequest.Builder.() {
                        crossfade(true)
                    }).build())
            },
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
                value = changeName,
                onValueChange = setChangedName,
                placeholder = profileData.fullName,
                enabled = true,
                isError = null,
                modifier = Modifier.clickable { },
                setClassVisibility = null
            )

            TextFieldWithTitle(
                title = stringResource(id = R.string.email),
                value = changeEmail,
                onValueChange = setChangedEmail,
                placeholder = profileData.email,
                enabled = true,
                isError = null,
                modifier = Modifier.clickable { },
                setClassVisibility = null
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
                        modifier = Modifier.clickable { },
                        setClassVisibility = null
                    )
                }
            }
        }

        GradientBorderButtonRound(
            colors = listOf(colorResource(id = R.color.baby_blue),
                colorResource(id = R.color.lighter_dark_blue)),
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
//    EditProfileUi(ProfileResponseData(
//        "",
//        "Kalina Valeva",
//        "kalina.valevaa@gmail.com",
//        "kalina2w3",
//        "admin",
//        "",
//        assets = ProfileAssets(arrayListOf())),
//        {}, {}, "", {}, "", {}, "", {}, {}, null) {}
}
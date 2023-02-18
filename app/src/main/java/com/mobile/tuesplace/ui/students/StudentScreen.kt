package com.mobile.tuesplace.ui.students

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import com.mobile.tuesplace.data.ProfileResponseData
import com.mobile.tuesplace.ui.TextFieldFunction
import com.mobile.tuesplace.ui.theme.BabyBlue

@Composable
fun StudentScreen() {
}

@Composable
fun StudentUi(
    profile: ProfileResponseData,
    nameChange: String,
    setNameChange: (String) -> Unit,
    emailChange: String,
    setEmailChange: (String) -> Unit,
) {
    ConstraintLayout(modifier = Modifier
        .fillMaxSize()
        .background(BabyBlue)
    ) {
        TextFieldFunction(
            value = nameChange,
            onValueChange = { setNameChange(nameChange) },
            placeholder = profile.fullName,
            modifier = null,
            enabled = true,
            isError = null
        )
    }
}
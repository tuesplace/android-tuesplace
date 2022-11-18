package com.mobile.tuesplace.ui.forgottenpassword

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.mobile.tuesplace.R

@Composable
fun ForgottenPasswordScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dark_blue))
    ) {

    }
}

@Preview
@Composable
fun ComposablePreview() {
    ForgottenPasswordScreen()
}
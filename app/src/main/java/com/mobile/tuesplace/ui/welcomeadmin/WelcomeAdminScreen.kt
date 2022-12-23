package com.mobile.tuesplace.ui.welcomeadmin

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*


import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mobile.tuesplace.R
import com.mobile.tuesplace.ui.theme.BabyBlue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WelcomeAdminScreen(
    onActionClick: (String) -> Unit
) {
    Box(
    modifier = Modifier
        .fillMaxSize()
        .background(colorResource(id = R.color.dark_blue))
    ) {
        val array = arrayListOf("Create group", "Create class", "Create new profile", "Delete profile")
        LazyVerticalGrid(
            cells = GridCells.Adaptive(minSize = 80.dp),
            contentPadding = PaddingValues(20.dp)
        ) {
            items(array){ arr->
                MenuItem(text = arr) { onActionClick(arr) }
            }
        }
    }
}

@Composable
fun MenuItem(text: String, onActionClick: (String) -> Unit){
    Box(
        modifier = Modifier
            .padding(5.dp)
            .size(80.dp)
            .clickable {
                onActionClick(text)
            }
            .background(BabyBlue, RoundedCornerShape(8.dp)),
        contentAlignment = Center){
            Text(text = text)
    }
}


@Preview
@Composable
fun ComposablePreview() {
    WelcomeAdminScreen({})
}
package com.mobile.tuesplace.ui.activities

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mobile.tuesplace.R
import com.mobile.tuesplace.ui.MenuItem

@Composable
fun ActivitiesStudentsClassMenuScreen(
    onEightGradeClick: () -> Unit,
    onNinthGradeClick: () -> Unit,
    onTenthGradeClick: () -> Unit,
    onEleventhGradeClick: () -> Unit,
    onTwelfthGradClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dark_blue))
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.dark_blue)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MenuItem(
                image = null,
                string = stringResource(id = R.string.eight_grade),
                modifier = Modifier.padding(6.dp),
                onClick = onEightGradeClick
            )

            MenuItem(
                image = null,
                string = stringResource(id = R.string.ninth_grade),
                modifier = Modifier.padding(6.dp),
                onClick = onNinthGradeClick
            )

            MenuItem(
                image = null,
                string = stringResource(id = R.string.tenth_grade),
                modifier = Modifier.padding(6.dp),
                onClick = onTenthGradeClick
            )

            MenuItem(
                image = null,
                string = stringResource(id = R.string.eleventh_grade),
                modifier = Modifier.padding(6.dp),
                onClick = onEleventhGradeClick
            )

            MenuItem(
                image = null,
                string = stringResource(id = R.string.twelfth_grade),
                modifier = Modifier.padding(6.dp),
                onClick = onTwelfthGradClick
            )
        }
    }
}

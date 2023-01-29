package com.mobile.tuesplace.ui.groups

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.mobile.tuesplace.R
import com.mobile.tuesplace.ui.GradientBorderButtonRound
import com.mobile.tuesplace.ui.TextFields

@Composable
fun CreateGroupScreen(
    groupName: String,
    setGroupName: (String) -> Unit,
    groupType: String,
    setGroupType: (String) -> Unit,
    classes: String,
    setClasses: (String) -> Unit,
    onCreateGroupClick: () -> Unit
) {
    ConstraintLayout(modifier = Modifier
        .fillMaxSize()
        .background(colorResource(id = R.color.gray))
    ) {
        val (btn, fields) = createRefs()
        Column(modifier = Modifier
            .fillMaxWidth()
            .constrainAs(fields) {
                top.linkTo(parent.top)
                bottom.linkTo(btn.top)
            }) {
            TextFields(
                value = groupName,
                onValueChange = setGroupName,
                placeholder = stringResource(id = R.string.group_name),
                modifier = null,
                enabled = true,
                isError = null
            )
            TextFields(
                value = groupType,
                onValueChange = setGroupType,
                placeholder = stringResource(id =R.string.group_type),
                modifier = null,
                enabled = true,
                isError = null
            )
            TextFields(
                value = classes,
                onValueChange = setClasses,
                placeholder = stringResource(id = R.string.choose_classes),
                modifier = null,
                enabled = true,
                isError = null
            )
        }

        GradientBorderButtonRound(
            colors = null,
            paddingValues = PaddingValues(16.dp),
            buttonText = stringResource(id = R.string.create_group),
            onLoginClick = { onCreateGroupClick() },
            buttonPadding = PaddingValues(16.dp),
            modifier = Modifier.constrainAs(btn){
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
    }
}

@Composable
@Preview
fun Preview() {
    CreateGroupScreen(
        groupName = "Ime",
        setGroupName = {},
        groupType = "Messages",
        setGroupType = {},
        classes = "12b",
        setClasses = {},
        onCreateGroupClick = {})
}
package com.mobile.tuesplace.ui.activities

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.mobile.tuesplace.R
import com.mobile.tuesplace.data.AgendaResponseData
import com.mobile.tuesplace.ui.DailyAgendaItem
import com.mobile.tuesplace.ui.EmptyScreen
import com.mobile.tuesplace.ui.numToDay
import com.mobile.tuesplace.ui.states.GetActivitiesUiState

@Composable
fun ActivitiesTeacherScreen(
    getActivitiesUiState: GetActivitiesUiState,
    profileId: String
) {
    when (getActivitiesUiState) {
        GetActivitiesUiState.Empty -> {
            EmptyScreen()
        }
        is GetActivitiesUiState.Error -> {
            val exception = getActivitiesUiState.exception
        }
        GetActivitiesUiState.Loading -> {}
        is GetActivitiesUiState.Success -> {
            ActivitiesTeacherUi(profileId = profileId,
                list = getActivitiesUiState.activities)
        }
    }
}

@Composable
fun ActivitiesTeacherUi(
    profileId: String,
    list: List<AgendaResponseData>,
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dark_blue))
    ) {
        val (title, classesList) = createRefs()
        Text(
            text = stringResource(id = R.string.agenda),
            color = colorResource(id = R.color.white),
            fontSize = 20.sp,
            modifier = Modifier
                .padding(6.dp)
                .constrainAs(title) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Column(modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
            .background(colorResource(id = R.color.darker_sea_blue))
            .constrainAs(classesList) {
                top.linkTo(title.bottom)
                bottom.linkTo(parent.bottom)
                height = Dimension.fillToConstraints
            }
        ) {

            DailyAgendaItem(
                day = stringResource(id = numToDay(1)),
                agendaList = list.filter { activity ->
                    activity.associations.group.data.owners.filter { owner -> owner._id == profileId}
                        .isNotEmpty()
                            && activity.day == 1
                })

            DailyAgendaItem(
                day = stringResource(id = numToDay(2)),
                agendaList = list.filter { activity ->
                    activity.associations.group.data.owners.filter { owner -> owner._id == profileId }
                        .isNotEmpty()
                            && activity.day == 2
                })

            DailyAgendaItem(
                day = stringResource(id = numToDay(3)),
                agendaList = list.filter { activity ->
                    activity.associations.group.data.owners.filter { owner -> owner._id == profileId }
                        .isNotEmpty()
                            && activity.day == 3
                })

            DailyAgendaItem(
                day = stringResource(id = numToDay(4)),
                agendaList = list.filter { activity ->
                    activity.associations.group.data.owners.filter { owner -> owner._id == profileId }
                        .isNotEmpty()
                            && activity.day == 4
                })

            DailyAgendaItem(
                day = stringResource(id = numToDay(5)),
                agendaList = list.filter { activity ->
                    activity.associations.group.data.owners.filter { owner -> owner._id == profileId }
                        .isNotEmpty()
                            && activity.day == 5
                })
        }
    }
}
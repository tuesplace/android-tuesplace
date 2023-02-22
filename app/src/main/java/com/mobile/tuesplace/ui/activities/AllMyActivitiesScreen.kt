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
import com.mobile.tuesplace.ui.numToDay
import com.mobile.tuesplace.ui.states.GetMyActivitiesUiState

@Composable
fun AllMyActivitiesScreen(getMyActivitiesUiState: GetMyActivitiesUiState) {
    when (getMyActivitiesUiState) {
        GetMyActivitiesUiState.Empty -> {}
        is GetMyActivitiesUiState.Error -> {}
        GetMyActivitiesUiState.Loading -> {}
        is GetMyActivitiesUiState.Success -> {
            AllMyActivitiesUi(list = getMyActivitiesUiState.activities)
        }
    }
}

@Composable
fun AllMyActivitiesUi(list: List<AgendaResponseData>) {
    ConstraintLayout(modifier = Modifier
        .fillMaxSize()
        .background(colorResource(id = R.color.dark_blue))
    ) {
        
        val (title, agenda) = createRefs()
        
        Text(
            text = stringResource(id = R.string.agenda),
            color = colorResource(id = R.color.white),
            fontSize = 20.sp,
            modifier = Modifier
                .padding(6.dp)
                .constrainAs(title) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }
        )

        Column(modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
            .background(colorResource(id = R.color.darker_sea_blue))
            .constrainAs(agenda) {
                top.linkTo(title.bottom)
                bottom.linkTo(parent.bottom)
                height = Dimension.fillToConstraints
            }
        ) {

            DailyAgendaItem(
                day = stringResource(id = numToDay(1)),
                agendaList = list
                    .filter { classes -> classes.day == 1 }
            )

            DailyAgendaItem(
                day = stringResource(id = numToDay(2)),
                agendaList = list
                    .filter { classes -> classes.day == 2 }
            )

            DailyAgendaItem(
                day = stringResource(id = numToDay(3)),
                agendaList = list
                    .filter { classes -> classes.day == 3 }
            )

            DailyAgendaItem(
                day = stringResource(id = numToDay(4)),
                agendaList = list
                    .filter { classes -> classes.day == 4 }
            )

            DailyAgendaItem(
                day = stringResource(id = numToDay(5)),
                agendaList = list
                    .filter { classes -> classes.day == 5 }
            )
        }
    }
}

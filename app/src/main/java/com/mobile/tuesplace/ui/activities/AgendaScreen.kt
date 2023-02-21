package com.mobile.tuesplace.ui.activities

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.mobile.tuesplace.R
import com.mobile.tuesplace.data.AgendaResponseData
import com.mobile.tuesplace.ui.AgendaItem
import com.mobile.tuesplace.ui.states.GetActivitiesUiState
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun AgendaScreen(getActivitiesUiState: GetActivitiesUiState) {
    when(getActivitiesUiState){
        GetActivitiesUiState.Empty -> {}
        is GetActivitiesUiState.Error -> {
           getActivitiesUiState.exception
        }
        GetActivitiesUiState.Loading -> {}
        is GetActivitiesUiState.Success -> {
            AgendaUi(list = getActivitiesUiState.activities)
        }
    }
}

@Composable
fun AgendaUi(list: List<AgendaResponseData>){
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dark_blue))
    ) {
        val (fullAgenda, currentDate, currentAgenda) = createRefs()

        Text(
            text = stringResource(id = R.string.full_agenda),
            textDecoration = TextDecoration.Underline,
            color = colorResource(id = R.color.baby_blue),
            fontSize = 16.sp,
            modifier = Modifier
                .padding(16.dp)
                .constrainAs(fullAgenda) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("EEE, MMM d")
        val formattedDateTime = currentDateTime.format(formatter)
        Text(
            text = formattedDateTime,
            color = colorResource(id = R.color.white),
            fontSize = 20.sp,
            modifier = Modifier
                .padding(6.dp)
                .constrainAs(currentDate) {
                    top.linkTo(fullAgenda.bottom)
                    start.linkTo(parent.start)
                }
        )

        LazyColumn(modifier = Modifier
            .padding(top = 50.dp)
            .fillMaxWidth()
            .constrainAs(currentAgenda) {
                top.linkTo(currentDate.bottom)
            }) {
                itemsIndexed(list) { _, data ->
                    AgendaItem(agendaData = data)
                }
        }
    }
}
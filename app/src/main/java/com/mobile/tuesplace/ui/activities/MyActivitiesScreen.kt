package com.mobile.tuesplace.ui.activities

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.mobile.tuesplace.DATE_PATTERN
import com.mobile.tuesplace.R
import com.mobile.tuesplace.data.AgendaResponseData
import com.mobile.tuesplace.ui.AgendaItem
import com.mobile.tuesplace.ui.Loading
import com.mobile.tuesplace.ui.dayToNum
import com.mobile.tuesplace.ui.states.GetMyActivitiesUiState
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyActivitiesScreen(
    getMyActivitiesUiState: GetMyActivitiesUiState,
    onFullAgendaClick: () -> Unit,
) {
    when (getMyActivitiesUiState) {
        GetMyActivitiesUiState.Empty -> {}
        is GetMyActivitiesUiState.Error -> {
            Toast.makeText(
                LocalContext.current,
                getMyActivitiesUiState.exception ?: stringResource(R.string.create_error),
                Toast.LENGTH_LONG
            ).show()
        }
        GetMyActivitiesUiState.Loading -> {
            Loading()
        }
        is GetMyActivitiesUiState.Success -> {
            MyActivitiesUi(
                list = getMyActivitiesUiState.activities.filter { activity ->
                    activity.day == dayToNum(LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern(DATE_PATTERN)))
                },
                onFullAgendaClick = onFullAgendaClick
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyActivitiesUi(list: List<AgendaResponseData>, onFullAgendaClick: () -> Unit) {
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
                .clickable { onFullAgendaClick() }
                .padding(16.dp)
                .constrainAs(fullAgenda) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Text(
            text = LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEE, MMM d")),
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
            .padding(6.dp)
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
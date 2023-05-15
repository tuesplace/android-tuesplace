package com.mobile.tuesplace.ui.activities

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.mobile.tuesplace.*
import com.mobile.tuesplace.R
import com.mobile.tuesplace.data.AgendaResponseData
import com.mobile.tuesplace.ui.DailyAgendaItem
import com.mobile.tuesplace.ui.Loading
import com.mobile.tuesplace.ui.ButtonChangeColorOnClick
import com.mobile.tuesplace.ui.indexToLetter
import com.mobile.tuesplace.ui.numToDay
import com.mobile.tuesplace.ui.states.GetActivitiesUiState

@Composable
fun ActivitiesStudentsScreen(
    grade: String,
    getActivitiesUiState: GetActivitiesUiState,
    setVisibilityStateFlow: ActivitiesStudentsViewModel.SetVisibility,
    setVisibility: (Int) -> Unit,
) {
    when (getActivitiesUiState) {
        GetActivitiesUiState.Empty -> {}
        is GetActivitiesUiState.Error -> {
            Toast.makeText(
                LocalContext.current,
                getActivitiesUiState.exception ?: stringResource(R.string.create_error),
                Toast.LENGTH_LONG
            ).show()
        }
        GetActivitiesUiState.Loading -> {
            Loading()
        }
        is GetActivitiesUiState.Success -> {
            when (setVisibilityStateFlow) {
                is ActivitiesStudentsViewModel.SetVisibility.Loaded -> {
                    ActivitiesStudentsUi(
                        grade = grade,
                        agendaList = getActivitiesUiState.activities.filter { activity ->
                            activity.associations.group.data.name.contains(grade)
                        },
                        classVisibility = setVisibilityStateFlow.items,
                        setVisibility = setVisibility
                    )
                }
                ActivitiesStudentsViewModel.SetVisibility.None -> {}
            }
        }
    }


}

@Composable
fun ActivitiesStudentsUi(
    grade: String,
    agendaList: List<AgendaResponseData>,
    classVisibility: ArrayList<Int>,
    setVisibility: (Int) -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dark_blue))
    ) {
        val (title, classMenu, classesList) = createRefs()
        Text(
            text = stringResource(id = R.string.agenda) + stringResource(id = R.string.empty) + grade + stringResource(id = R.string.empty) + stringResource(id = R.string.class_string),
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

        Row(
            modifier = Modifier
                .padding(6.dp)
                .fillMaxWidth()
                .constrainAs(classMenu) {
                    top.linkTo(title.bottom)
                },
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ButtonChangeColorOnClick(
                text = stringResource(id = R.string.a_class),
                colorState = classVisibility[0] == 1,
                setColor = { setVisibility(0) }
            )

            ButtonChangeColorOnClick(
                text = stringResource(id = R.string.b_class),
                colorState = classVisibility[1] == 1,
                setColor = { setVisibility(1) }
            )

            ButtonChangeColorOnClick(
                text = stringResource(id = R.string.v_class),
                colorState = classVisibility[2] == 1,
                setColor = { setVisibility(2) }
            )

            ButtonChangeColorOnClick(
                text = stringResource(id = R.string.g_class),
                colorState = classVisibility[3] == 1,
                setColor = { setVisibility(3) }
            )
        }

        Column(modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
            .background(colorResource(id = R.color.darker_sea_blue))
            .constrainAs(classesList) {
                top.linkTo(classMenu.bottom)
                bottom.linkTo(parent.bottom)
                height = Dimension.fillToConstraints
            }
        ) {
            DailyAgendaItem(
                day = stringResource(id = numToDay(ONE)),
                agendaList = agendaList
                    .filter { agenda ->
                        agenda.associations.group.data.name.contains(
                            indexToLetter(classVisibility))
                    }
                    .filter { classes -> classes.day == 1 }
            )

            DailyAgendaItem(
                day = stringResource(id = numToDay(TWO)),
                agendaList = agendaList
                    .filter { agenda ->
                        agenda.associations.group.data.name.contains(
                            indexToLetter(classVisibility))
                    }
                    .filter { classes -> classes.day == 2 }
            )

            DailyAgendaItem(
                day = stringResource(id = numToDay(THREE)),
                agendaList = agendaList
                    .filter { agenda ->
                        agenda.associations.group.data.name.contains(
                            indexToLetter(classVisibility))
                    }
                    .filter { classes -> classes.day == 3 }
            )

            DailyAgendaItem(
                day = stringResource(id = numToDay(FOUR)),
                agendaList = agendaList
                    .filter { agenda ->
                        agenda.associations.group.data.name.contains(
                            indexToLetter(classVisibility))
                    }
                    .filter { classes -> classes.day == 4 }
            )

            DailyAgendaItem(
                day = stringResource(id = numToDay(FIVE)),
                agendaList = agendaList
                    .filter { agenda ->
                        agenda.associations.group.data.name.contains(
                            indexToLetter(classVisibility))
                    }
                    .filter { classes -> classes.day == 5 }
            )
        }
    }
}
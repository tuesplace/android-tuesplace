package com.mobile.tuesplace.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.navigation.*
import com.mobile.tuesplace.R
import java.io.File

fun convertMinutesToHoursAndMinutes(minutes: Int): String {
    val hours = minutes / 60
    val remainingMinutes = minutes % 60
    return "$hours:$remainingMinutes"
}

fun indexToLetter(arrayList: ArrayList<Int>): String{
    return when(arrayList.indexOf(1)){
        0 -> "A"
        1 -> "Б"
        2 -> "В"
        3 -> "Г"
        else -> { "" }
    }
}

fun numToDay(number: Int): Int{
    return when(number){
        1 -> R.string.monday
        2 -> R.string.tuesday
        3 -> R.string.wednesday
        4 -> R.string.thursday
        5 -> R.string.friday
        else -> { 0 }
    }
}

fun dayToNum(day: String): Int{
    return when(day) {
        "Mon" -> 1
        "Tue" -> 2
        "Wed" -> 3
        "Thu" -> 4
        "Fri" -> 5
        else -> { 0 }
    }
}

@SuppressLint("RestrictedApi")
fun NavHostController.navigate(
    route: String,
    args: Bundle,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    val routeLink = NavDeepLinkRequest
        .Builder
        .fromUri(NavDestination.createRoute(route).toUri())
        .build()

    val deepLinkMatch = graph.matchDeepLink(routeLink)
    if (deepLinkMatch != null) {
        val destination = deepLinkMatch.destination
        val id = destination.id
        navigate(id, args, navOptions, navigatorExtras)
    } else {
        navigate(route, navOptions, navigatorExtras)
    }
}


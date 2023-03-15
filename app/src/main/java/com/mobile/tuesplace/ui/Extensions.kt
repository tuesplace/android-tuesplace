package com.mobile.tuesplace.ui

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import androidx.core.net.toUri
import androidx.navigation.*
import com.mobile.tuesplace.*
import com.mobile.tuesplace.R
import java.io.*


fun convertMinutesToHoursAndMinutes(minutes: Int): String {
    val hours = minutes / 60
    val remainingMinutes = minutes % 60
    return "$hours:$remainingMinutes"
}

fun indexToLetter(arrayList: ArrayList<Int>): String {
    return if (arrayList.size > 1) {
        when (arrayList.indexOf(1)) {
            ZERO -> A_LETTER
            ONE -> B_LETTER
            TWO -> C_LETTER
            THREE -> D_LETTER
            else -> {
                EMPTY_STRING
            }
        }
    } else {
        EMPTY_STRING
    }
}

fun numToDay(number: Int): Int {
    return when (number) {
        1 -> R.string.monday
        2 -> R.string.tuesday
        3 -> R.string.wednesday
        4 -> R.string.thursday
        5 -> R.string.friday
        else -> {
            0
        }
    }
}

fun dayToNum(day: String): Int {
    return when (day) {
        MONDAY -> 1
        TUESDAY -> 2
        WEDNESDAY -> 3
        THURSDAY -> 4
        FRIDAY -> 5
        else -> {
            0
        }
    }
}

@SuppressLint("RestrictedApi")
fun NavHostController.navigate(
    route: String,
    args: Bundle,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
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

fun getFileWithFileDescriptor(context: Context, uri: Uri): ByteArray? {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        try {
            val pfd: ParcelFileDescriptor? = context.contentResolver.openFileDescriptor(uri, "r")
            if (pfd != null) {
                return FileInputStream(pfd.fileDescriptor).readBytes()
            }
        } catch (_: IOException) { }
    } else {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? = context.contentResolver.query(uri, projection, null, null, null)
        if (cursor != null) {
            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            val filePath = cursor.getString(columnIndex)
            cursor.close()

            return File(filePath).readBytes()
        }
    }

    return null
}

fun listAllGrades(): List<String> {
    return listOf(
        EIGHT_A_GRADE,
        EIGHT_B_GRADE,
        EIGHT_C_GRADE,
        EIGHT_D_GRADE,
        NINTH_A_GRADE,
        NINTH_B_GRADE,
        NINTH_C_GRADE,
        NINTH_D_GRADE,
        TENTH_A_GRADE,
        TENTH_B_GRADE,
        TENTH_C_GRADE,
        TENTH_D_GRADE,
        ELEVENTH_A_GRADE,
        ELEVENTH_B_GRADE,
        ELEVENTH_C_GRADE,
        ELEVENTH_D_GRADE,
        TWELFTH_A_GRADE,
        TWELFTH_B_GRADE,
        TWELFTH_C_GRADE,
        TWELFTH_D_GRADE,
        EMPTY_GRADE
    )
}



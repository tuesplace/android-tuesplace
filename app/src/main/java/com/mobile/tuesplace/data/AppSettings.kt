package com.mobile.tuesplace.data

import com.mobile.tuesplace.EMPTY_STRING
import kotlinx.serialization.Serializable

@Serializable
data class AppSettings(
    val token: String = EMPTY_STRING,
    val refreshToken: String = EMPTY_STRING,
    val user: String = EMPTY_STRING
)
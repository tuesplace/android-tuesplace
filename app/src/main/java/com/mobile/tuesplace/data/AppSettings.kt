package com.mobile.tuesplace.data

import kotlinx.serialization.Serializable

@Serializable
data class AppSettings(
    val token: String = "",
    val refreshToken: String = "",
    val user: String = ""
)
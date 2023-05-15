package com.mobile.tuesplace.data

import kotlinx.serialization.Serializable

@Serializable
data class ResolvedAssociation<T>(
    var _id: String,
    var data: T?
)

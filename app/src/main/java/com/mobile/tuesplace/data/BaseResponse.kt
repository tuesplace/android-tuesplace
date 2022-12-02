package com.mobile.tuesplace.data

open class BaseResponse<T> (
    var success: Boolean = false,
    var response: T? = null,
    var error: ErrorData? = null
)
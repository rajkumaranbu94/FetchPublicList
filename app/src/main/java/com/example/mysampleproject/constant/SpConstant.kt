package com.example.mysampleproject.constant

object SpConstant {
    const val GENERIC_ERROR: String =
        "We are unable to process the request at this time. This may be due to a system issue. Please try again or contact your supervisor if this continues."
    const val ERROR_TIMEOUT = "TIMEOUT"
    const val ERROR_UNKNOWN_HOST = "UNKNOWN_HOST"
    const val INTERNET_ERROR = "INTERNET_NOT_AVAILABLE"

    object ErrorSource {
        const val NONE = -1
        const val NO_INTERNET = 3
        const val UNKNOWN_HOST = 4
        const val SOCKET_TIMEOUT = 5
        const val HTTP_EXCEPTION = 6
        const val JSON_DATA_EXCEPTION = 7
        const val IO_EXCEPTION = 8
    }
}
package com.example.mysampleproject.base

import com.example.mysampleproject.constant.SpConstant.ERROR_TIMEOUT
import com.example.mysampleproject.constant.SpConstant.ERROR_UNKNOWN_HOST
import com.example.mysampleproject.constant.SpConstant.ErrorSource.HTTP_EXCEPTION
import com.example.mysampleproject.constant.SpConstant.ErrorSource.IO_EXCEPTION
import com.example.mysampleproject.constant.SpConstant.ErrorSource.JSON_DATA_EXCEPTION
import com.example.mysampleproject.constant.SpConstant.ErrorSource.NONE
import com.example.mysampleproject.constant.SpConstant.ErrorSource.NO_INTERNET
import com.example.mysampleproject.constant.SpConstant.ErrorSource.SOCKET_TIMEOUT
import com.example.mysampleproject.constant.SpConstant.ErrorSource.UNKNOWN_HOST
import com.example.mysampleproject.constant.SpConstant.GENERIC_ERROR
import com.example.mysampleproject.constant.SpConstant.INTERNET_ERROR
import com.example.mysampleproject.network.response.ErrorResponse
import com.example.mysampleproject.network.retrofit.wrapper.ErrorWrapper
import com.example.mysampleproject.network.retrofit.wrapper.InternetNotAvailableException
import com.example.mysampleproject.network.retrofit.wrapper.ResponseWrapper
import com.squareup.moshi.JsonDataException
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.reflect.KClass

open class BaseRepository {
    suspend fun <T : Any> safeApiCall(
        call: suspend () -> Response<T>,
        errorClass: KClass<*> = ErrorResponse::class,
        parseError: ((String) -> ErrorWrapper)? = null,
    ): ResponseWrapper<T> {
        var responseWrapper: ResponseWrapper<T>
        try {
            val response: Response<T> = call.invoke()
            responseWrapper = if (response.isSuccessful) {
                val result: HashMap<String, String> = HashMap(response.headers().size)
                for (header in response.headers()) {
                    result[header.first] = header.second
                }

                ResponseWrapper.Success(
                    data = response.body(),
                    httpCode = response.code(),
                    responseHeaders = result
                )
            } else {
                ResponseWrapper.Error(
                    parseApiError(response.errorBody(), errorClass, parseError), response.code()
                )
            }
            return responseWrapper
        } catch (exception: Exception) {
            responseWrapper = getError(exception)
        }
        return responseWrapper
    }

    private fun <T : Any> getError(exception: Exception): ResponseWrapper<T> {
        val stackTrace = StringBuilder()

        try {
            exception.stackTrace.forEach {
                stackTrace.append("\tat ${it ?: ""}")
            }
        } catch (e: Exception) {
            stackTrace.append("Could not get Stacktrace!")
        }
        return when (exception) {
            is InternetNotAvailableException -> ResponseWrapper.Error(
                ErrorWrapper(
                    errorMessage = INTERNET_ERROR,
                    exceptionStackTrace = stackTrace.toString(),
                    moduleCode = NO_INTERNET
                )
            )
            is UnknownHostException -> ResponseWrapper.Error(
                ErrorWrapper(
                    errorMessage = ERROR_UNKNOWN_HOST,
                    exceptionStackTrace = stackTrace.toString(),
                    moduleCode = UNKNOWN_HOST
                )
            )
            is JsonDataException -> ResponseWrapper.Error(
                ErrorWrapper(
                    errorMessage = exception.message.toString(),
                    exceptionStackTrace = stackTrace.toString(),
                    moduleCode = JSON_DATA_EXCEPTION
                )
            )
            is SocketTimeoutException -> ResponseWrapper.Error(
                ErrorWrapper(
                    errorMessage = ERROR_TIMEOUT,
                    exceptionStackTrace = stackTrace.toString(),
                    moduleCode = SOCKET_TIMEOUT
                )
            )
            is HttpException -> {
                ResponseWrapper.Error(
                    ErrorWrapper(
                        errorMessage = exception.message.toString(),
                        exceptionStackTrace = stackTrace.toString(),
                        moduleCode = HTTP_EXCEPTION
                    )
                )
            }
            is IOException -> {
                ResponseWrapper.Error(
                    ErrorWrapper(
                        errorMessage = exception.message.toString(),
                        exceptionStackTrace = stackTrace.toString(),
                        moduleCode = IO_EXCEPTION
                    )
                )
            }
            else -> {
                ResponseWrapper.Error(
                    ErrorWrapper(
                        errorMessage = exception.message.toString(),
                        exceptionStackTrace = stackTrace.toString(),
                        moduleCode = NONE
                    )
                )
            }
        }
    }

    private fun parseApiError(
        error: ResponseBody?,
        clazz: KClass<*>,
        parseError: ((String) -> ErrorWrapper)?
    ): ErrorWrapper {
        return genericErrorMessage()
    }

    protected fun genericErrorMessage(): ErrorWrapper {
        return ErrorWrapper(
            errorMessage = GENERIC_ERROR,
            moduleCode = NONE
        )
    }
}
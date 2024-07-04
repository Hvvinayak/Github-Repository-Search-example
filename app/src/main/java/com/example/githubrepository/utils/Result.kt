package com.example.githubrepository.utils

import okhttp3.internal.http2.ErrorCode

sealed class Result<out R> {
    data class Success<out T>(val data: T): Result<T>()
    data class Error(val exception: Exception, val errorCode: ErrorCode = ErrorCode.INTERNAL_ERROR): Result<Nothing>()
}

val<T> Result<T>.data: T?
    get() = (this as? Result.Success)?.data
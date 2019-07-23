package com.jrtou.gitviewer.framework

sealed class BaseResource<T>(val data: T?, val message: String? = null) {
    class Success<T>(data: T) : BaseResource<T>(data)
    class Loading<T>(data: T? = null) : BaseResource<T>(data)
    class Error<T>(message: String, data: T? = null) : BaseResource<T>(data, message)
}
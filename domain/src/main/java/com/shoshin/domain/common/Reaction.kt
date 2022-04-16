package com.shoshin.domain.common

sealed class Reaction<out T> {
    data class Progress<out T>(val data: T? = null) : Reaction<T>()
    data class Success<out T>(val data: T) : Reaction<T>()
    data class Error<out T>(
        val message: String? = null,
        val data: T? = null
    ): Reaction<T>()
}

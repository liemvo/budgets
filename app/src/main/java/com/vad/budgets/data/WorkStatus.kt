package com.vad.budgets.data

sealed class WorkStatus<out T : Any> {
    object Initial : WorkStatus<Nothing>()
    class Loading<T : Any>(val data: T? = null) : WorkStatus<T>()
    class Success<T : Any>(val data: T) : WorkStatus<T>()
    object Finish : WorkStatus<Nothing>()
    class Error<T : Any>(val data: T? = null, val exception: Throwable) : WorkStatus<T>()

    val isLoading: Boolean
        get() = this is Loading

    val isSuccess: Boolean
        get() = this is Success

    val isError: Boolean
        get() = this is Error

    val isFinished: Boolean
        get() = this is Finish

    val isNullableData: T?
        get() = when (this) {
            Initial, Finish -> null
            is Loading -> data
            is Success -> data
            is Error -> data
        }
}

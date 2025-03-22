package com.jrprofessor.platformcommons.network

/**
 * A generic class that holds a value with its loading status.
 * */
data class Resource<out T>(
    val status: Status,
    val data: T? = null,
    val message: String? = null
) {
    companion object {
        fun <T> success(data: T? = null, message: String? = null): Resource<T> {
            return Resource(
                Status.SUCCESS,
                data,
                message = message
            )
        }
        fun <T> error(error: String?, data: T? = null): Resource<T> {
            return Resource(
                Status.ERROR,
                data,
                error
            )
        }
        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(
                Status.LOADING,
                data,
                null
            )
        }
    }

    fun isSuccess(): Boolean = status == Status.SUCCESS

    fun isLoading(): Boolean = status == Status.LOADING
}

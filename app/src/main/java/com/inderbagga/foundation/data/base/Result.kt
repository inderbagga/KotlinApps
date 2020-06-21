package com.inderbagga.foundation.data.base

/**
 * Created by Inder Bagga on 19/06/20.
 * Email er[dot]inderbagga[at]gmail[dot]com
 */
data class Result<out T>(val status: Status, val response: T?, val message: String?) {

    companion object {

        fun <T> success(response: T?): Result<T> {
            return Result(Status.SUCCESS, response, null)
        }

        fun <T> error(msg: String, response: T?): Result<T> {
            return Result(Status.ERROR, response, msg)
        }

        fun <T> loading(response: T?): Result<T> {
            return Result(Status.LOADING, response, null)
        }

    }

}

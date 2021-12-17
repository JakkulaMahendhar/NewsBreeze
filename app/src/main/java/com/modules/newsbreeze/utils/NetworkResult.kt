package com.modules.newsbreeze.utils

import retrofit2.Response

sealed class NetworkResult{

    object Requesting : NetworkResult()

    data class Success<T>(val response: Response<T>) : NetworkResult()

    data class Failure(val message:String?) : NetworkResult()
}

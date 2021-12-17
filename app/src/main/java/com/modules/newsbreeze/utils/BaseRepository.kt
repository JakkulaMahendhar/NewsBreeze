package com.modules.newsbreeze.utils

import com.google.gson.JsonSyntaxException
import retrofit2.Response
import java.io.IOException
import java.lang.Exception

open class BaseRepository {

    suspend fun <T : Any> apiCalls(call : suspend() -> Response<T>) : NetworkResult{
        return try {
            NetworkResult.Success(call.invoke())
        }catch (e:IOException){
            NetworkResult.Failure(e.message)
        }catch (e:JsonSyntaxException){
            NetworkResult.Failure(e.message)
        }catch (e:IllegalStateException){
            NetworkResult.Failure(e.message)
        }catch (e:Exception){
            NetworkResult.Failure(e.message)
        }

    }
}
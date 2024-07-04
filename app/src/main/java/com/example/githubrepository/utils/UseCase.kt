package com.example.githubrepository.utils

import com.squareup.moshi.JsonDataException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class UseCase<in P, R> {
    suspend operator fun invoke(parameters: P): Result<R> {
        return try {
            withContext(Dispatchers.Default) {
                execute(parameters).let {
                    Result.Success(it)
                }
            }
        } catch (e: HttpException){
            Result.Error(e)
        } catch (e: JsonDataException){
            Result.Error(e)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(parameters: P): R
}

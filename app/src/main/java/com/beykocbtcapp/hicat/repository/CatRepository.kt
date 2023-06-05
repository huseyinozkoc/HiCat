package com.beykocbtcapp.hicat.repository


import CatService
import com.beykocbtcapp.hicat.model.Cat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CatRepository {
    private val dummyService: CatService =
        ApiClient.getClient().create(CatService::class.java)


    fun geCat(): Flow<ApiResponse<List<Cat?>?>> = flow {
        while (true) {
            emit(ApiResponse.Loading)
            try {
                val response = dummyService.getRandomCat().execute()
                if (response.isSuccessful) {
                    val cat = response.body()
                    emit(ApiResponse.Success(cat))
                } else {
                    emit(ApiResponse.Error(response.message()))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message ?: "Unknown error occurred"))
            }
            kotlinx.coroutines.delay(5000)
        }
    }.catch { e ->
        emit(ApiResponse.Error(e.message ?: "Unknown error occurred"))
    }.flowOn(Dispatchers.IO)

}

/* Without Flow(Interface'i Call halinde çağırmassan Execute() metodunu kullanamassın. )
suspend fun getUser(userId: String): ApiResponse<Cat?> {
    return try {
        val response = dummyService.getRandomCat().execute()
        if (response.isSuccessful) {
            val cat = response.body()
            ApiResponse.Success(cat)
        } else {
            ApiResponse.Error(response.message())
        }
    } catch (e: Exception) {
        ApiResponse.Error(e.message ?: "Unknown error occurred")
    }
}

 */




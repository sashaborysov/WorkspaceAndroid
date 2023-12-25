package com.workspaceandroid.data.api.service

import com.workspaceandroid.data.dto.auth.AuthResponseNetDTO
import com.workspaceandroid.data.dto.auth.AuthorizationNetDTO
import com.workspaceandroid.data.dto.auth.RefreshTokenNetDTO
import com.workspaceandroid.data.dto.phrases.CollectionNetDTO
import com.workspaceandroid.data.dto.phrases.PhraseNetDTO
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface NetworkApiService {

    //TODO update path mapping /api/

    @POST("api/auth/signin")
    suspend fun auth(@Body model: AuthorizationNetDTO): AuthResponseNetDTO

    @POST("api/auth/refresh")
    suspend fun refreshToken(): RefreshTokenNetDTO

    @GET("api/phrases")
    suspend fun getUserPhrases(): List<PhraseNetDTO>

    @POST("api/phrases")
    suspend fun createUserPhrase(@Body model: PhraseNetDTO)

    @DELETE("api/phrases")
    suspend fun deleteUserPhrase(@Query("phraseId") phraseId: Long)

    @GET("api/collections")
    suspend fun getUserCollections(): List<CollectionNetDTO>

    @GET("api/prediction")
    suspend fun loadPrediction(@Query("phraseText") phraseText: String): PhraseNetDTO

}
package com.workspaceandroid.data.api.source.impl

import com.workspaceandroid.data.api.RefreshTokenService
import com.workspaceandroid.data.api.service.NetworkApiService
import com.workspaceandroid.data.api.source.IPhrasesNetSource
import com.workspaceandroid.data.api.source.base.BaseNetSource
import com.workspaceandroid.data.dto.phrases.CollectionNetDTO
import com.workspaceandroid.data.dto.phrases.PhraseNetDTO

internal class PhrasesNetSource(
    networkApiService: NetworkApiService,
    refreshTokenService: RefreshTokenService,
) : BaseNetSource<NetworkApiService>(networkApiService, refreshTokenService), IPhrasesNetSource {

    override suspend fun fetchUserPhrases(): List<PhraseNetDTO> {
        return performRequest { getUserPhrases() }
    }

    override suspend fun addUserPhrase(userPhrase: PhraseNetDTO) {
        return performRequest { createUserPhrase(userPhrase) }
    }

    override suspend fun removePhrase(phraseId: Long) {
        performRequest { deleteUserPhrase(phraseId) }
    }

    override suspend fun getPhrasePrediction(phraseText: String): PhraseNetDTO {
        return try {
            performRequest {
                loadPrediction(phraseText)
            }
        } catch (e: Exception) {
             PhraseNetDTO("123", "", 1L, "image", emptyList(), "definition", 1L)
        }
    }

    override suspend fun getUserCollections(): List<CollectionNetDTO> {
        return performRequest { getUserCollections() }
    }

}
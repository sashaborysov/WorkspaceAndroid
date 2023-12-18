package com.workspaceandroid.data.api.source.impl

import com.workspaceandroid.data.api.RefreshTokenService
import com.workspaceandroid.data.api.service.NetworkApiService
import com.workspaceandroid.data.api.source.IAuthNetSource
import com.workspaceandroid.data.api.source.IPhrasesNetSource
import com.workspaceandroid.data.api.source.base.BaseNetSource
import com.workspaceandroid.data.dto.auth.AuthResponseNetDTO
import com.workspaceandroid.data.dto.auth.AuthorizationNetDTO
import com.workspaceandroid.data.dto.phrases.PhraseNetDTO
import com.workspaceandroid.domain.models.phrase.Phrase
import java.util.Date

internal class PhrasesNetSource(
    networkApiService: NetworkApiService,
    refreshTokenService: RefreshTokenService,
) : BaseNetSource<NetworkApiService>(networkApiService, refreshTokenService), IPhrasesNetSource {

    override suspend fun fetchUserPhrases(): List<PhraseNetDTO> {
        return performRequest {
            getPersonalCollection()
        }
    }

    override suspend fun addUserPhrase(userPhrase: PhraseNetDTO) {
        return performRequest {
            createUserPhrase(userPhrase)
        }
    }

    override suspend fun removePhrase(phraseId: Long) {
        performRequest {
            deleteUserPhrase(phraseId)
        }
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

}
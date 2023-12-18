package com.workspaceandroid.data.api.source

import com.workspaceandroid.data.dto.phrases.PhraseNetDTO

interface IPhrasesNetSource {
    suspend fun fetchUserPhrases(): List<PhraseNetDTO>
    suspend fun addUserPhrase(userPhrase: PhraseNetDTO)
    suspend fun removePhrase(phraseId: Long)
    suspend fun getPhrasePrediction(phraseText: String): PhraseNetDTO
}
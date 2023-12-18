package com.workspaceandroid.domain.repositories

import com.workspaceandroid.domain.models.phrase.Phrase
import com.workspaceandroid.domain.models.phrase.PhraseInput

interface ICollectionRepository {
    suspend fun fetchUserPhrases(): List<Phrase>
    suspend fun addUserPhrase(phrase: PhraseInput)
    suspend fun removePhrase(phraseId: Long)
    suspend fun getPhrasePrediction(phraseText: String): Phrase
}
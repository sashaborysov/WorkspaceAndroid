package com.workspaceandroid.ui.screens.search

import androidx.lifecycle.viewModelScope
import com.workspaceandroid.base.BaseViewModel
import com.workspaceandroid.domain.interactors.CollectionInteractor
import com.workspaceandroid.domain.models.phrase.PhraseInput
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val collectionInteractor: CollectionInteractor) :
    BaseViewModel<SearchContract.Event, SearchContract.State, SearchContract.Effect>() {

    private val userInputPhrase = PhraseInput()

    override fun setInitialState(): SearchContract.State = SearchContract.State().copy(isLoading = true)

    override fun handleEvents(event: SearchContract.Event) {
        when (event) {
            is SearchContract.Event.OnPhraseUpdated -> {
                event.phraseBuilder.invoke(userInputPhrase)
                loadPhrasePrediction()
                setState { copy(selectedCollectionChip = userInputPhrase.selectedCollectionId) }
            }
            SearchContract.Event.OnSaveButtonClicked -> {
                addUserPhrase()
            }
        }
    }

    init {
        viewModelScope.launch {
            val fetchedCollections = collectionInteractor.getUserCollections().map { Pair(it.id, it.name) }
            setState { copy(userCollections = fetchedCollections) }
        }
    }

    private fun addUserPhrase() {
        viewModelScope.launch { collectionInteractor.addUserPhrase(userInputPhrase) }
    }

    private fun loadPhrasePrediction() {
        if (userInputPhrase.text.length > 2) {
            viewModelScope.launch {
                val predictedPhrase = collectionInteractor.getPhrasePrediction(userInputPhrase.text)
                setState { this.copy(predictedPhrase = predictedPhrase, isLoading = false) }
            }
        }
    }

}
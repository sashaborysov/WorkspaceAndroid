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

    private val userPhrase = PhraseInput()

    override fun setInitialState(): SearchContract.State = SearchContract.State().copy(isLoading = true)

    override fun handleEvents(event: SearchContract.Event) {
        when (event) {
            is SearchContract.Event.OnPhraseUpdated -> {
                event.phraseBuilder.invoke(userPhrase)
                loadPhrasePrediction()
            }
            SearchContract.Event.OnSaveButtonClicked -> {
                addUserPhrase()
            }
        }
    }

    private fun addUserPhrase() {
        viewModelScope.launch {
            collectionInteractor.addUserPhrase(userPhrase)
        }
    }

    private fun loadPhrasePrediction() {
        if (userPhrase.text.length > 2) {
            viewModelScope.launch {
                val predictedPhrase = collectionInteractor.getPhrasePrediction(userPhrase.text)
                setState { this.copy(predictedPhrase = predictedPhrase, isLoading = false) }
            }
        }
    }

}
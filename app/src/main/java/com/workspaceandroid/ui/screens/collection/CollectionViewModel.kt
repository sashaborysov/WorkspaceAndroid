package com.workspaceandroid.ui.screens.collection

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.workspaceandroid.base.BaseViewModel
import com.workspaceandroid.data.common.ITimeHelper
import com.workspaceandroid.domain.interactors.CollectionInteractor
import com.workspaceandroid.domain.models.phrase.Phrase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    private val collectionInteractor: CollectionInteractor,
    private val timeHelper: ITimeHelper,
) : BaseViewModel<CollectionContract.Event, CollectionContract.State, CollectionContract.Effect>() {

    enum class SortType {
        AZ, DATE
    }

    private var userPhrases: List<Phrase> = emptyList()

    init {
        fetchUserCollection()
    }

    override fun setInitialState(): CollectionContract.State =
        CollectionContract.State(isLoading = true)

    override fun handleEvents(event: CollectionContract.Event) {
        when (event) {
            is CollectionContract.Event.AddButtonClicked -> fetchUserCollection()
            is CollectionContract.Event.OnItemSelected -> updateExpandedCards(event.selectedPhrase)
            is CollectionContract.Event.OnSearchInput -> filterPhrases(event.searchText)
            is CollectionContract.Event.OnPhraseRemove -> removePhrase(event.phraseId)
        }
    }

    private fun updateExpandedCards(selectedPhrase: Phrase) {
        setState {
            val updatedCards = phrases.map {
                if (it.id == selectedPhrase.id) it.copy(isExpanded = !it.isExpanded)
                else it
            }.toMutableList()
            copy(phrases = updatedCards)
        }
    }

    private fun filterPhrases(searchInput: String) {
        setState {
            copy(phrases = userPhrases.filter { it.text.contains(searchInput) })
        }
    }

    private fun fetchUserCollection() {
        if (userPhrases.isNotEmpty()) {
            return
        }
        viewModelScope.launch {
            userPhrases = collectionInteractor.getUserPhrases().sortByDate()
            setState {
                copy(phrases = userPhrases, isLoading = false)
            }
        }
    }

    private fun removePhrase(phraseId: Long) {
        viewModelScope.launch {
            collectionInteractor.removePhrase(phraseId)
        }
        userPhrases = userPhrases.filter { it.id != phraseId }
        setState {
            copy(phrases = userPhrases, isLoading = false)
        }
    }

    private fun List<Phrase>.sortByDate(): List<Phrase> = this.sortedByDescending { it.createdAt }

}
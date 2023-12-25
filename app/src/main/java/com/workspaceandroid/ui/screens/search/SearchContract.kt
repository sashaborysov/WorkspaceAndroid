package com.workspaceandroid.ui.screens.search

import com.workspaceandroid.base.ViewEvent
import com.workspaceandroid.base.ViewSideEffect
import com.workspaceandroid.base.ViewState
import com.workspaceandroid.domain.models.phrase.Phrase
import com.workspaceandroid.domain.models.phrase.PhraseInput

class SearchContract {

    sealed class Event : ViewEvent {
        object OnSaveButtonClicked : Event()
        data class OnPhraseUpdated(val phraseBuilder: PhraseInput.() -> Unit) : Event()
    }

//    sealed class SearchState: ViewState {
//        object Idle : SearchState()
//        object Loading : SearchState()
//        data class Success(val name : String) : SearchState()
//        data class Error(val errorMessage : String) : SearchState()
//        data class ValidationError(val errorMessage : String) : SearchState()
//    }

    data class State(
        val isLoading: Boolean = false,
        val predictedPhrase: Phrase? = null,
        val userCollections: List<Pair<Long, String>> = emptyList(),
        val selectedCollectionChip: Long = -1
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        data class ShowToast(val message: String) : Effect()

        sealed class Navigation : Effect() {
            data class ToMain(val userSearch: String): Navigation()
        }
    }
}
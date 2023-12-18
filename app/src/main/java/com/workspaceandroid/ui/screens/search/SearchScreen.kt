package com.workspaceandroid.ui.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.workspaceandroid.R
import com.workspaceandroid.domain.models.phrase.PhraseInput
import com.workspaceandroid.ui.theme.*
import com.workspaceandroid.ui.widgets.ActionButton
import com.workspaceandroid.ui.widgets.InputTextField
import com.workspaceandroid.ui.widgets.TextInput
import com.workspaceandroid.ui.widgets.ToolbarComponent

@Composable
fun SearchScreen(
    navController: NavHostController,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    SearchScreen(
        state = viewModel.viewState.collectAsState().value,
        onSaveClick = { viewModel.setEvent(SearchContract.Event.OnSaveButtonClicked) },
        phraseBuilder = { builder ->
            viewModel.setEvent(
                SearchContract.Event.OnPhraseUpdated(builder)
            )
        }
    )

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is SearchContract.Effect.Navigation.ToMain -> TODO()
                is SearchContract.Effect.ShowToast -> TODO()
            }
        }
    }
}

@Composable
fun SearchScreen(
    state: SearchContract.State,
    onSaveClick: () -> Unit,
    phraseBuilder: (PhraseInput.() -> Unit) -> Unit,
) {

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        color = light_gray
    ) {

        ConstraintLayout(
            Modifier.padding(offset_16)
        ) {

            var textFieldCount by remember { mutableStateOf(0) }
            var isEditableCard by remember { mutableStateOf(false) }

            ToolbarComponent(
                text = stringResource(R.string.search_title)
            )

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                elevation = CardDefaults.cardElevation(defaultElevation = elevation_4)
            ) {
                Column(
                    modifier = Modifier
                        .padding(offset_16),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {

                    TextInput(
                        onInputChanged = {
                            isEditableCard = false
                            phraseBuilder {
                                text = it
                            }
                        },
                        label = stringResource(id = R.string.create_card_phrase_label),
                        placeholderText = stringResource(id = R.string.create_card_phrase_placeholder)
                    )

                    TextInput(
                        inputValue = state.predictedPhrase?.examples?.firstOrNull().orEmpty(),
                        onInputChanged = {
                            phraseBuilder {
                                definition = it
                            }
                        },
                        label = stringResource(id = R.string.create_card_translation_label),
                        placeholderText = stringResource(id = R.string.create_card_translation_placeholder)
                    )

                    TextInput(
                        inputValue = state.predictedPhrase?.definition.orEmpty(),
                        onInputChanged = {
                            phraseBuilder {
                                definition = it
                            }
                        },
                        label = stringResource(id = R.string.create_card_definition_label),
                        placeholderText = stringResource(id = R.string.create_card_definition_placeholder),
                        isEditable = isEditableCard
                    )

                    ActionButton(
                        painter = painterResource(R.drawable.ic_add_circle_outline),
                        buttonText = stringResource(id = R.string.create_card_add_example),
                        backgroundColor = Color.White,
                        onClick = {
                            textFieldCount++
                            isEditableCard = !isEditableCard
                        }
                    )

                    repeat(textFieldCount) {
                        TextInput(
                            modifier = Modifier.padding(top = offset_12),
                            label = "Example #${it + 1}",
                            placeholderText = stringResource(id = R.string.create_card_example_placeholder),
                            onInputChanged = { exampleText ->
                                phraseBuilder {
//                                examples.get(1) = exampleText
//                                text = ""
                                }
                            },
                        )
                    }


                }

                Divider(
                    color = light_gray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .width(5.dp)
                )

                ActionButton(
                    Modifier.padding(start = offset_12),
                    painter = painterResource(R.drawable.ic_save),
                    buttonText = stringResource(id = R.string.create_card_save),
                    fontColor = light_gray2,
                    onClick = { onSaveClick() }
                )

            }
        }


    }
}


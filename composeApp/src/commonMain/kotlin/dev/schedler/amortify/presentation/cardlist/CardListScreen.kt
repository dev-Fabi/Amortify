package dev.schedler.amortify.presentation.cardlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.schedler.amortify.domain.model.CardModel
import dev.schedler.amortify.domain.model.ISimpleCard
import dev.schedler.amortify.domain.model.SimpleCardModel
import dev.schedler.amortify.presentation.components.CardItemView
import dev.schedler.amortify.presentation.util.PreviewData
import dev.schedler.amortify.presentation.util.Resource
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun CardListScreen(
    cards: Resource<List<CardModel>>,
    onSave: (SimpleCardModel) -> Unit,
    onClick: (CardModel) -> Unit,
    onAddUsage: (CardModel) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    var sheetConfig by remember { mutableStateOf<SheetConfig?>(null) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { sheetConfig = SheetConfig.NewCard }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Card")
            }
        }
    ) {
        when (cards) {
            is Resource.Loading -> LoadingIndicator()
            is Resource.Error -> Text("Error: ${stringResource(cards.message)}")
            is Resource.Success -> {
                if (cards.data.isEmpty()) {
                    Text(
                        text = "No cards available. Click the + button to add a new card.",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    )

                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(cards.data) { card ->
                            CardItemView(
                                card = card,
                                showPercentage = false,
                                onEdit = { sheetConfig = SheetConfig.EditCard(card) },
                                onClick = { onClick(card) },
                                onAddUsage = { onAddUsage(card) },
                            )
                        }
                    }
                }

                if (sheetConfig != null) {
                    ModalBottomSheet(
                        onDismissRequest = { sheetConfig = null },
                        sheetState = sheetState,
                        dragHandle = null
                    ) {
                        CardForm(
                            modifier = Modifier.padding(16.dp),
                            model = (sheetConfig as? SheetConfig.EditCard)?.model,
                            onCancel = {
                                coroutineScope.launch {
                                    sheetState.hide()
                                }.invokeOnCompletion {
                                    if (!sheetState.isVisible) sheetConfig = null
                                }
                            },
                            onSave = {
                                onSave(it)
                                coroutineScope.launch {
                                    sheetState.hide()
                                }.invokeOnCompletion {
                                    if (!sheetState.isVisible) sheetConfig = null
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

private sealed interface SheetConfig {
    object NewCard : SheetConfig
    class EditCard(val model: ISimpleCard) : SheetConfig
}

@Composable
@Preview(showBackground = true)
private fun CardListScreenPreview() {
    CardListScreen(
        cards = Resource.Success(
            listOf(
                PreviewData.gymCard,
                PreviewData.poolCard,
                PreviewData.skiPass,
                PreviewData.transportCard,
                PreviewData.boulderCard,
            )
        ),
        onSave = {},
        onClick = {},
        onAddUsage = {}
    )
}
package dev.schedler.amortify.presentation.carddetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import dev.schedler.amortify.domain.model.UsageEntryModel
import dev.schedler.amortify.presentation.components.CardItemView
import dev.schedler.amortify.presentation.usageentry.UsageEntryForm
import dev.schedler.amortify.presentation.util.PreviewData
import dev.schedler.amortify.presentation.util.Resource
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun CardDetailScreen(
    card: Resource<CardModel?>,
    onBack: () -> Unit,
    onSaveUsage: (UsageEntryModel) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    var sheetConfig by remember { mutableStateOf<SheetConfig?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { sheetConfig = SheetConfig.NewUsage }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Usage")
            }
        }
    ) { paddingValues ->
        when (card) {
            is Resource.Loading -> LoadingIndicator()
            is Resource.Error -> Text("Error: ${stringResource(card.message)}")
            is Resource.Success if card.data != null -> {
                Column(modifier = Modifier.padding(paddingValues).padding(8.dp)) {
                    CardItemView(
                        card = card.data,
                        showPercentage = true,
                        onEdit = {},
                        onClick = null,
                        onAddUsage = null,
                    )

                    UsageTemplateButtonRow(
                        templates = card.data.usageTemplates,
                        onQuickAdd = { template ->
                            val newUsage = UsageEntryModel.fromTemplate(template)
                            onSaveUsage(newUsage)
                        },
                        modifier = Modifier.padding(top = 12.dp)
                    )

                    HorizontalDivider(modifier = Modifier.padding(top = 12.dp))

                    UsageEntryList(
                        modifier = Modifier.padding(top = 12.dp),
                        entries = card.data.usages,
                        onClick = { sheetConfig = SheetConfig.EditUsage(it) }
                    )
                }

                if (sheetConfig != null) {
                    ModalBottomSheet(
                        onDismissRequest = { sheetConfig = null },
                        sheetState = sheetState,
                        dragHandle = null
                    ) {
                        UsageEntryForm(
                            modifier = Modifier.padding(16.dp),
                            model = (sheetConfig as? SheetConfig.EditUsage)?.model,
                            onCancel = {
                                coroutineScope.launch {
                                    sheetState.hide()
                                }.invokeOnCompletion {
                                    if (!sheetState.isVisible) sheetConfig = null
                                }
                            },
                            onSave = {
                                onSaveUsage(it)
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

            is Resource.Success -> {
                Text("No card found.", modifier = Modifier.padding(paddingValues).padding(16.dp))
            }
        }
    }
}

private sealed interface SheetConfig {
    object NewUsage : SheetConfig
    data class EditUsage(val model: UsageEntryModel) : SheetConfig
}

@Composable
@Preview(showBackground = true)
private fun PreviewCardDetailScreen() {
    CardDetailScreen(
        card = Resource.Success(PreviewData.gymCard),
        onBack = {},
        onSaveUsage = {},
    )
}

@Composable
@Preview(showBackground = true)
private fun PreviewCardDetailScreen_NoCard() {
    CardDetailScreen(
        card = Resource.Success(null),
        onBack = {},
        onSaveUsage = {},
    )
}
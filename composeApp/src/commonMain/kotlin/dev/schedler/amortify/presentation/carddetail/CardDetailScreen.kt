package dev.schedler.amortify.presentation.carddetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
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
import dev.schedler.amortify.domain.model.UsageTemplate
import dev.schedler.amortify.presentation.components.CardItemView
import dev.schedler.amortify.presentation.usageentry.UsageEntryForm
import dev.schedler.amortify.presentation.util.PreviewData
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardDetailScreen(
    card: CardModel,
    onBack: () -> Unit,
    onAddUsage: () -> Unit,
    onAddUsageFromTemplate: (UsageTemplate) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    var showSheet by remember { mutableStateOf(false) }
    var editUsageModel by remember { mutableStateOf<UsageEntryModel?>(null) }

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
            FloatingActionButton(onClick = { showSheet = true }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Usage")
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(8.dp)) {
            CardItemView(
                card = card,
                showPercentage = true,
                onEdit = {},
                onClick = null,
                onAddUsage = null,
            )

            UsageTemplateButtonRow(
                templates = card.usageTemplates,
                onQuickAdd = onAddUsageFromTemplate,
                modifier = Modifier.padding(top = 12.dp)
            )

            HorizontalDivider(modifier = Modifier.padding(top = 12.dp))

            UsageEntryList(
                modifier = Modifier.padding(top = 12.dp),
                entries = card.usages,
                onClick = {
                    editUsageModel = it
                    showSheet = true
                }
            )
        }

        if (showSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    editUsageModel = null
                    showSheet = false
                },
                sheetState = sheetState,
                dragHandle = null
            ) {
                UsageEntryForm(
                    modifier = Modifier.padding(16.dp),
                    model = editUsageModel,
                    onCancel = {
                        coroutineScope.launch {
                            sheetState.hide()
                        }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                editUsageModel = null
                                showSheet = false
                            }
                        }
                    },
                    onSave = { it ->
                        onAddUsage()
                        coroutineScope.launch {
                            sheetState.hide()
                        }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                editUsageModel = null
                                showSheet = false
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun PreviewCardDetailScreen() {
    CardDetailScreen(
        card = PreviewData.gymCard,
        onBack = {},
        onAddUsage = {},
        onAddUsageFromTemplate = {}
    )
}
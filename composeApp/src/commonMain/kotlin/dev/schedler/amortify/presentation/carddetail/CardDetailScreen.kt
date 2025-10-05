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
import dev.schedler.amortify.domain.model.UsageTemplateModel
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
    onSaveUsage: (UsageEntryModel) -> Unit,
    onAddUsageFromTemplate: (UsageTemplateModel) -> Unit,
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
}

private sealed interface SheetConfig {
    object NewUsage : SheetConfig
    data class EditUsage(val model: UsageEntryModel) : SheetConfig
}

@Composable
@Preview(showBackground = true)
private fun PreviewCardDetailScreen() {
    CardDetailScreen(
        card = PreviewData.gymCard,
        onBack = {},
        onSaveUsage = {},
        onAddUsageFromTemplate = {}
    )
}
package dev.schedler.amortify.presentation.carddetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.schedler.amortify.domain.model.UsageEntryModel
import dev.schedler.amortify.presentation.util.PreviewData
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun UsageEntryList(
    entries: List<UsageEntryModel>,
    modifier: Modifier = Modifier,
    onClick: (UsageEntryModel) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = entries) { entry ->
            UsageEntryItem(
                modifier = Modifier.clickable { onClick(entry) },
                entry = entry
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewUsageEntryList() {
    UsageEntryList(
        entries = PreviewData.usageEntries,
        onClick = {}
    )
}
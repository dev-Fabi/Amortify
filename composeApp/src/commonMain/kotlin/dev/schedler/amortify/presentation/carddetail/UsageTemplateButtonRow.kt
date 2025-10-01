package dev.schedler.amortify.presentation.carddetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.schedler.amortify.domain.model.UsageTemplate

@Composable
fun UsageTemplateButtonRow(
    templates: List<UsageTemplate>,
    onQuickAdd: (UsageTemplate) -> Unit,
    modifier: Modifier = Modifier
) {
    if (templates.isEmpty()) return

    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(templates) { template ->
            Button(
                onClick = { onQuickAdd(template) },
            ) {
                Text(template.description)
            }
        }
    }
}
package dev.schedler.amortify.presentation.cardlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.schedler.amortify.domain.model.CardModel
import dev.schedler.amortify.presentation.components.CardItemView
import dev.schedler.amortify.presentation.util.PreviewData
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CardListScreen(cards: List<CardModel>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(cards) { card ->
            CardItemView(
                card = card,
                showPercentage = false,
                onEdit = {},
                onClick = {},
                onAddUsage = {}
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun CardListScreenPreview() {
    CardListScreen(
        cards = listOf(
            PreviewData.gymCard,
            PreviewData.poolCard,
            PreviewData.skiPass,
            PreviewData.transportCard,
            PreviewData.boulderCard,
        )
    )
}
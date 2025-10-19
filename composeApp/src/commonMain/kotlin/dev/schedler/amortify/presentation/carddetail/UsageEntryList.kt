package dev.schedler.amortify.presentation.carddetail

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    onDelete: (UsageEntryModel) -> Unit,
    onSaveAsTemplate: (UsageEntryModel) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = entries) { entry ->
            Box {
                var showMenu by remember { mutableStateOf(false) }
                UsageEntryItem(
                    modifier = Modifier.combinedClickable(
                        onClick = { onClick(entry) },
                        onClickLabel = "Edit",
                        onLongClick = { showMenu = true },
                        onLongClickLabel = "Show Options"
                    ),
                    entry = entry
                )
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Edit") },
                        leadingIcon = {
                            Icon(Icons.Filled.Edit, contentDescription = "Edit usage entry")
                        },
                        onClick = {
                            showMenu = false
                            onClick(entry)
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Delete") },
                        leadingIcon = {
                            Icon(Icons.Filled.Delete, contentDescription = "Delete usage entry")
                        },
                        onClick = {
                            showMenu = false
                            onDelete(entry)
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Save as Template") },
                        leadingIcon = {
                            Icon(Icons.Filled.Bookmark, contentDescription = "Save as template")
                        },
                        onClick = {
                            showMenu = false
                            onSaveAsTemplate(entry)
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewUsageEntryList() {
    UsageEntryList(
        entries = PreviewData.usageEntries,
        onClick = {},
        onDelete = {},
        onSaveAsTemplate = {}
    )
}
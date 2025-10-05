package dev.schedler.amortify.presentation.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.schedler.amortify.presentation.util.DateFormat
import dev.schedler.amortify.presentation.util.now
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.format
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun DateRangePicker(
    modifier: Modifier = Modifier,
    initialStart: LocalDate = LocalDate.now(),
    initialEnd: LocalDate = LocalDate.now(),
    label: @Composable () -> Unit,
    onChange: (Pair<LocalDate, LocalDate>) -> Unit,
) {
    var showDatePicker by remember { mutableStateOf(0) }

    val startDatePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialStart.atStartOfDayIn(TimeZone.UTC).toEpochMilliseconds()
    )
    val startDateString by derivedStateOf {
        startDatePickerState.selectedDateMillis?.let {
            Instant.fromEpochMilliseconds(it)
                .toLocalDateTime(TimeZone.currentSystemDefault())
                .format(DateFormat.dateOnly)
        } ?: ""
    }

    val endDatePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialEnd.atStartOfDayIn(TimeZone.UTC).toEpochMilliseconds()
    )
    val endDateString by derivedStateOf {
        endDatePickerState.selectedDateMillis?.let {
            Instant.fromEpochMilliseconds(it)
                .toLocalDateTime(TimeZone.currentSystemDefault())
                .format(DateFormat.dateOnly)
        } ?: ""
    }

    Column {
        label()
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = startDateString,
                onValueChange = {},
                modifier = Modifier.weight(1f),
                label = { Text("Start") },
                readOnly = true,
                singleLine = true,
                leadingIcon = {
                    Icon(Icons.Default.CalendarMonth, contentDescription = "Select Start Date")
                },
                interactionSource = remember { MutableInteractionSource() }.also { interactionSource ->
                    LaunchedEffect(interactionSource) {
                        interactionSource.interactions
                            .filterIsInstance<PressInteraction.Release>() // Same as onClick
                            .collect { showDatePicker = 1 }
                    }
                }
            )

            Text("-")

            OutlinedTextField(
                value = endDateString,
                onValueChange = {},
                modifier = Modifier.weight(1f),
                label = { Text("End") },
                readOnly = true,
                singleLine = true,
                trailingIcon = {
                    Icon(Icons.Default.CalendarMonth, contentDescription = "Select End Date")
                },
                interactionSource = remember { MutableInteractionSource() }.also { interactionSource ->
                    LaunchedEffect(interactionSource) {
                        interactionSource.interactions
                            .filterIsInstance<PressInteraction.Release>() // Same as onClick
                            .collect { showDatePicker = 2 }
                    }
                }
            )
        }
    }

    if (showDatePicker != 0) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = 0 },
            confirmButton = {
                TextButton(onClick = {
                    showDatePicker = 0

                    val start = startDatePickerState.selectedDateMillis
                        ?.let(Instant::fromEpochMilliseconds)
                        ?.toLocalDateTime(TimeZone.currentSystemDefault())
                        ?.date
                        ?: return@TextButton

                    val end = endDatePickerState.selectedDateMillis
                        ?.let(Instant::fromEpochMilliseconds)
                        ?.toLocalDateTime(TimeZone.currentSystemDefault())
                        ?.date
                        ?: return@TextButton

                    onChange(start to end)
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = 0 }) { Text("Cancel") }
            }
        ) {
            DatePicker(state = if (showDatePicker == 1) startDatePickerState else endDatePickerState)
        }
    }
}

@OptIn(ExperimentalTime::class)
@Composable
@Preview(showBackground = true)
private fun PreviewDateRangePicker() {
    DateRangePicker(
        label = { Text("Validity Period") },
        onChange = {}
    )
}
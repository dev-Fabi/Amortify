package dev.schedler.amortify.presentation.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.schedler.amortify.presentation.util.DateFormat
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.format
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun DateTimePicker(
    modifier: Modifier = Modifier,
    initial: Instant = Clock.System.now(),
    onChange: (Instant) -> Unit,
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initial.toEpochMilliseconds()
    )
    val timePickerState = rememberTimePickerState(
        initialHour = initial.toLocalDateTime(TimeZone.currentSystemDefault()).hour,
        initialMinute = initial.toLocalDateTime(TimeZone.currentSystemDefault()).minute,
        is24Hour = true
    )

    val dateString by derivedStateOf {
        datePickerState.selectedDateMillis?.let {
            Instant.fromEpochMilliseconds(it)
                .toLocalDateTime(TimeZone.currentSystemDefault())
                .format(DateFormat.dateOnly)
        } ?: ""
    }
    val timeString by derivedStateOf {
        "${timePickerState.hour.toString().padStart(2, '0')}:" +
                timePickerState.minute.toString().padStart(2, '0')
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        OutlinedTextField(
            value = dateString,
            onValueChange = {},
            modifier = Modifier.weight(2f),
            label = { Text("Date") },
            readOnly = true,
            singleLine = true,
            leadingIcon = {
                Icon(Icons.Default.CalendarMonth, contentDescription = "Select Date")
            },
            interactionSource = remember { MutableInteractionSource() }.also { interactionSource ->
                LaunchedEffect(interactionSource) {
                    interactionSource.interactions
                        .filterIsInstance<PressInteraction.Release>() // Same as onClick
                        .collect { showDatePicker = true }
                }
            }
        )

        OutlinedTextField(
            value = timeString,
            onValueChange = {},
            modifier = Modifier.weight(1f),
            label = { Text("Time") },
            readOnly = true,
            singleLine = true,
            trailingIcon = {
                Icon(Icons.Default.Timer, contentDescription = "Select Time")
            },
            interactionSource = remember { MutableInteractionSource() }.also { interactionSource ->
                LaunchedEffect(interactionSource) {
                    interactionSource.interactions
                        .filterIsInstance<PressInteraction.Release>() // Same as onClick
                        .collect { showTimePicker = true }
                }
            }
        )
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    showDatePicker = false

                    val instant = datePickerState.selectedDateMillis
                        ?.let(Instant::fromEpochMilliseconds)
                        ?.toLocalDateTime(TimeZone.currentSystemDefault())
                        ?.date
                        ?.atTime(timePickerState.hour, timePickerState.minute)
                        ?.toInstant(TimeZone.currentSystemDefault())
                        ?: return@TextButton

                    onChange(instant)
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("Cancel") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    if (showTimePicker) {
        AlertDialog(
            onDismissRequest = { showTimePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    showTimePicker = false

                    val instant = datePickerState.selectedDateMillis
                        ?.let(Instant::fromEpochMilliseconds)
                        ?.toLocalDateTime(TimeZone.currentSystemDefault())
                        ?.date
                        ?.atTime(timePickerState.hour, timePickerState.minute)
                        ?.toInstant(TimeZone.currentSystemDefault())
                        ?: return@TextButton

                    onChange(instant)
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showTimePicker = false }) { Text("Cancel") }
            },
            text = {
                TimePicker(state = timePickerState)
            }
        )
    }
}

@OptIn(ExperimentalTime::class)
@Composable
@Preview(showBackground = true)
private fun PreviewDateTimePicker() {
    DateTimePicker(
        onChange = {}
    )
}
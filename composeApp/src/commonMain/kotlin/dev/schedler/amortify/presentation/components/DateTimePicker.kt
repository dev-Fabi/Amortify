@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)

package dev.schedler.amortify.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.schedler.amortify.presentation.util.DateFormat
import dev.schedler.amortify.presentation.util.rememberClickableInteractionSource
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.format
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Composable
fun DateTimePicker(
    modifier: Modifier = Modifier,
    state: DateTimePickerState = rememberDateTimePickerState()
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    val dateString = state.instant
        ?.toLocalDateTime(TimeZone.currentSystemDefault())
        ?.format(DateFormat.dateOnly)
        .orEmpty()
    val timeString = state.instant
        ?.toLocalDateTime(TimeZone.currentSystemDefault())
        ?.format(DateFormat.timeOnly)
        .orEmpty()

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
            interactionSource = rememberClickableInteractionSource { showDatePicker = true }
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
            interactionSource = rememberClickableInteractionSource { showTimePicker = true }
        )
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("Cancel") }
            }
        ) {
            DatePicker(state = state.date)
        }
    }

    if (showTimePicker) {
        AlertDialog(
            onDismissRequest = { showTimePicker = false },
            confirmButton = {
                TextButton(onClick = { showTimePicker = false }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showTimePicker = false }) { Text("Cancel") }
            },
            text = {
                TimePicker(state = state.time)
            }
        )
    }
}

@Composable
fun rememberDateTimePickerState(
    initial: Instant? = null,
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): DateTimePickerState {
    val dateState = rememberDatePickerState(
        initialSelectedDateMillis = initial?.toEpochMilliseconds()
    )
    val timeState = rememberTimePickerState(
        initialHour = initial?.toLocalDateTime(timeZone)?.hour ?: 0,
        initialMinute = initial?.toLocalDateTime(timeZone)?.minute ?: 0,
        is24Hour = true
    )

    return remember(dateState, timeState, timeZone) {
        DateTimePickerState(dateState, timeState, timeZone)
    }
}

@Stable
class DateTimePickerState(
    val date: DatePickerState,
    val time: TimePickerState,
    private val timeZone: TimeZone,
) {
    val instant: Instant? by derivedStateOf {
        date.selectedDateMillis
            ?.let(Instant::fromEpochMilliseconds)
            ?.toLocalDateTime(timeZone)
            ?.date
            ?.atTime(time.hour, time.minute)
            ?.toInstant(timeZone)
    }

    fun isSet(): Boolean = instant != null
}

@Composable
@Preview(showBackground = true)
private fun PreviewDateTimePicker() {
    Column {
        DateTimePicker(state = rememberDateTimePickerState(Clock.System.now()))
        DateTimePicker()
    }
}
@file:OptIn(ExperimentalMaterial3Api::class)

package dev.schedler.amortify.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.schedler.amortify.presentation.util.DateFormat
import dev.schedler.amortify.presentation.util.rememberClickableInteractionSource
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.format
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.Instant

@Composable
fun DateRangePicker(
    modifier: Modifier = Modifier,
    state: DateRangePickerState = rememberDateRangePickerState(),
    label: @Composable () -> Unit,
) {
    var showDatePicker by remember { mutableStateOf(PickerType.None) }

    val startDateString = state.range.first?.format(DateFormat.date).orEmpty()
    val endDateString = state.range.second?.format(DateFormat.date).orEmpty()

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
                interactionSource = rememberClickableInteractionSource {
                    showDatePicker = PickerType.Start
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
                isError = state.isSet() && !state.isValid(),
                trailingIcon = {
                    Icon(Icons.Default.CalendarMonth, contentDescription = "Select End Date")
                },
                interactionSource = rememberClickableInteractionSource {
                    showDatePicker = PickerType.End
                }
            )
        }
    }

    if (showDatePicker != PickerType.None) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = PickerType.None },
            confirmButton = {
                TextButton(onClick = { showDatePicker = PickerType.None }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = PickerType.None }) { Text("Cancel") }
            }
        ) {
            DatePicker(state = if (showDatePicker == PickerType.Start) state.start else state.end)
        }
    }
}

@Composable
fun rememberDateRangePickerState(
    initialStart: LocalDate? = null,
    initialEnd: LocalDate? = null,
    timeZone: TimeZone = TimeZone.currentSystemDefault(),
): DateRangePickerState {
    val startState = rememberDatePickerState(
        initialSelectedDateMillis = initialStart?.atStartOfDayIn(TimeZone.UTC)
            ?.toEpochMilliseconds()
    )
    val endState = rememberDatePickerState(
        initialSelectedDateMillis = initialEnd?.atStartOfDayIn(TimeZone.UTC)
            ?.toEpochMilliseconds()
    )

    return remember(startState, endState, timeZone) {
        DateRangePickerState(startState, endState, timeZone)
    }
}

@Stable
class DateRangePickerState(
    val start: DatePickerState,
    val end: DatePickerState,
    private val timeZone: TimeZone
) {
    val range: Pair<LocalDate?, LocalDate?> by derivedStateOf {
        start.toLocalDate(timeZone) to end.toLocalDate(timeZone)
    }

    fun isSet(): Boolean = start.selectedDateMillis != null && end.selectedDateMillis != null
    fun isValid(): Boolean = start.selectedDateMillis?.let { start ->
        end.selectedDateMillis?.let { end -> start < end }
    } == true

    companion object {
        private fun DatePickerState.toLocalDate(timeZone: TimeZone): LocalDate? = selectedDateMillis
            ?.let(Instant::fromEpochMilliseconds)
            ?.toLocalDateTime(timeZone)
            ?.date
    }
}

private enum class PickerType { None, Start, End }

@Composable
@Preview(showBackground = true)
private fun PreviewDateRangePicker() {
    DateRangePicker(
        label = { Text("Validity Period") },
    )
}
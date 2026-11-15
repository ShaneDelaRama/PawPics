
package com.example.pawpics.ui

import android.widget.ImageView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

// Generate a date identifier.
fun uiDateKey(calendar: Calendar): String {
    return SimpleDateFormat(
        "yyyy-MM-dd",
        Locale.US
    ).format(calendar.time)
}

// Format the current date.
fun uiTodayLabel(): String {
    return SimpleDateFormat(
        "EEEE, MMMM d",
        Locale.getDefault()
    ).format(Calendar.getInstance().time)
}

// Format the displayed month.
fun uiMonthTitle(calendar: Calendar): String {
    return SimpleDateFormat(
        "MMMM yyyy",
        Locale.getDefault()
    ).format(calendar.time)
}

// Determine the number of days in a month.
private fun getDaysInMonth(calendar: Calendar): Int {
    return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
}

// Calculate the first weekday offset.
private fun getStartingOffset(calendar: Calendar): Int {

    val firstDay = calendar.get(Calendar.DAY_OF_WEEK)
    val weekStart = calendar.firstDayOfWeek

    return (firstDay - weekStart + 7) % 7
}

// Generate all calendar cells.
private fun getCalendarDays(calendar: Calendar): List<Int?> {

    val days = mutableListOf<Int?>()

    val offset = getStartingOffset(calendar)
    val totalDays = getDaysInMonth(calendar)

    repeat(offset) {
        days.add(null)
    }

    for (day in 1..totalDays) {
        days.add(day)
    }

    val remaining = (7 - days.size % 7) % 7

    repeat(remaining) {
        days.add(null)
    }

    return days
}

// Generate weekday names.
private fun getWeekdayLabels(calendar: Calendar): List<String> {

    return (0..6).map { offset ->

        val weekday = calendar.clone() as Calendar

        weekday.set(Calendar.DAY_OF_MONTH, 1)
        weekday.set(
            Calendar.DAY_OF_WEEK,
            calendar.firstDayOfWeek
        )

        weekday.add(Calendar.DAY_OF_WEEK, offset)

        SimpleDateFormat(
            "EEEEE",
            Locale.getDefault()
        ).format(weekday.time)
    }
}

// Check whether a calendar cell represents today.
private fun isCurrentDay(
    month: Calendar,
    day: Int
): Boolean {

    val today = Calendar.getInstance()

    return month.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
            month.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
            day == today.get(Calendar.DAY_OF_MONTH)
}

@Composable
fun CalendarView(
    displayedMonth: Calendar,
    entries: Map<String, UiDiaryEntry>,
    onDateClick: (String) -> Unit
) {

    val weekdays = getWeekdayLabels(displayedMonth)
    val calendarDays = getCalendarDays(displayedMonth)

    Card(
        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(20.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(12.dp),

            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            // WEEKDAY HEADERS

            Row(
                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {

                weekdays.forEach { weekday ->

                    Text(
                        text = weekday,

                        modifier = Modifier.weight(1f),

                        textAlign = TextAlign.Center,

                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF927D73)
                    )
                }
            }

            // CALENDAR GRID

            calendarDays.chunked(7).forEach { week ->

                Row(
                    modifier = Modifier.fillMaxWidth(),

                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {

                    week.forEach { day ->

                        if (day == null) {

                            Spacer(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(72.dp)
                            )

                        } else {

                            val dateCalendar =
                                (displayedMonth.clone() as Calendar).apply {
                                    set(Calendar.DAY_OF_MONTH, day)
                                }

                            val dateKey = uiDateKey(dateCalendar)

                            val entry = entries[dateKey]

                            val todayCell = isCurrentDay(
                                displayedMonth,
                                day
                            )

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(72.dp)
                                    .clip(RoundedCornerShape(9.dp))
                                    .background(
                                        if (todayCell) {
                                            Color(0xFFE7C5AF)
                                        } else {
                                            Color(0xFFF7F0EA)
                                        }
                                    )
                                    .clickable {
                                        onDateClick(dateKey)
                                    }
                            ) {

                                // DISPLAY SAVED PHOTO

                                if (entry != null) {

                                    AndroidView(
                                        modifier = Modifier.fillMaxSize(),

                                        factory = { context ->

                                            ImageView(context).apply {
                                                scaleType =
                                                    ImageView.ScaleType.CENTER_CROP
                                            }
                                        },

                                        update = { imageView ->

                                            imageView.setImageURI(
                                                entry.photoUri
                                            )
                                        }
                                    )

                                } else {

                                    Text(
                                        text = "+",

                                        modifier = Modifier.align(
                                            Alignment.Center
                                        ),

                                        fontSize = 18.sp,
                                        color = Color(0xFFB69A88)
                                    )
                                }

                                // DATE OVERLAY

                                Text(
                                    text = day.toString(),

                                    modifier = Modifier
                                        .align(Alignment.TopStart)
                                        .padding(3.dp)
                                        .background(
                                            Color.Black.copy(alpha = 0.5f),
                                            RoundedCornerShape(4.dp)
                                        )
                                        .padding(
                                            horizontal = 4.dp,
                                            vertical = 2.dp
                                        ),

                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }

            Text(
                text = "♡ Tap a date to view its memory.",

                fontSize = 12.sp,
                color = Color(0xFF927D73)
            )
        }
    }
}

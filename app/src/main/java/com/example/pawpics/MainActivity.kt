
package com.example.pawpics

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                PawPicsHomeScreen()
            }
        }
    }
}

@Composable
fun PawPicsHomeScreen() {

    val context = LocalContext.current

    // Today's date
    val today = Calendar.getInstance()

    // Currently displayed month
    var displayedMonth by remember {
        mutableStateOf(
            Calendar.getInstance().apply {
                set(Calendar.DAY_OF_MONTH, 1)
            }
        )
    }

    // Format current month
    val monthYear = SimpleDateFormat(
        "MMMM yyyy",
        Locale.getDefault()
    ).format(displayedMonth.time)

    // Format today's date
    val formattedDate = SimpleDateFormat(
        "EEEE, MMMM d",
        Locale.getDefault()
    ).format(today.time)

    // Get number of days in the displayed month
    val daysInMonth = displayedMonth.getActualMaximum(
        Calendar.DAY_OF_MONTH
    )

    // Calculate first day of the month
    val firstDay = displayedMonth.get(Calendar.DAY_OF_WEEK)

    val firstDayOfWeek = displayedMonth.getFirstDayOfWeek()

    val startingOffset = (
            firstDay - firstDayOfWeek + 7
            ) % 7

    // Create calendar cells
    val calendarDays = buildList {
        repeat(startingOffset) {
            add(null)
        }

        for (day in 1..daysInMonth) {
            add(day)
        }
    }

    // Complete the final calendar row
    val remainingCells = (7 - calendarDays.size % 7) % 7

    repeat(remainingCells) {
        // Add empty calendar cells
        // Actual list is constructed below
    }

    val completeCalendarDays = calendarDays + List(remainingCells) {
        null
    }

    // Weekday labels
    val weekdays = (0..6).map { offset ->

        val calendar = displayedMonth.clone() as Calendar

        calendar.set(
            Calendar.DAY_OF_WEEK,
            firstDayOfWeek
        )

        calendar.add(Calendar.DAY_OF_WEEK, offset)

        SimpleDateFormat(
            "EEEEE",
            Locale.getDefault()
        ).format(calendar.time)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFFFF9F5)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(
                    androidx.compose.foundation.rememberScrollState()
                )
                .padding(20.dp),

            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            // 1. PAWPICS HEADER

            Column(
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {

                Text(
                    text = "🐾 PawPics",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF604B43)
                )

                Text(
                    text = "Your pet's daily memories.",
                    fontSize = 14.sp,
                    color = Color(0xFF927D73)
                )
            }

            // 2. GREETING

            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {

                Text(
                    text = "Hello, pet parent! ♡",
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF604B43)
                )

                Text(
                    text = "Every day is a memory worth keeping.",
                    fontSize = 14.sp,
                    color = Color(0xFF806E65)
                )
            }

            // 3. MONTHLY CALENDAR

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
                    modifier = Modifier.padding(14.dp),

                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    // Month navigation

                    Row(
                        modifier = Modifier.fillMaxWidth(),

                        horizontalArrangement = Arrangement.SpaceBetween,

                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = monthYear,
                            fontSize = 21.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF604B43)
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            TextButton(
                                onClick = {
                                    displayedMonth =
                                        (displayedMonth.clone() as Calendar).apply {
                                            add(Calendar.MONTH, -1)
                                        }
                                }
                            ) {

                                Text(
                                    text = "‹",
                                    fontSize = 27.sp,
                                    color = Color(0xFF806E65)
                                )
                            }

                            TextButton(
                                onClick = {
                                    displayedMonth =
                                        (displayedMonth.clone() as Calendar).apply {
                                            add(Calendar.MONTH, 1)
                                        }
                                }
                            ) {

                                Text(
                                    text = "›",
                                    fontSize = 27.sp,
                                    color = Color(0xFF806E65)
                                )
                            }
                        }
                    }

                    // Weekday labels

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

                    // Calendar dates

                    completeCalendarDays.chunked(7).forEach { week ->

                        Row(
                            modifier = Modifier.fillMaxWidth(),

                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {

                            week.forEach { day ->

                                if (day == null) {

                                    Spacer(
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(64.dp)
                                    )

                                } else {

                                    val isToday =
                                        displayedMonth.get(Calendar.YEAR) ==
                                                today.get(Calendar.YEAR) &&

                                                displayedMonth.get(Calendar.MONTH) ==
                                                today.get(Calendar.MONTH) &&

                                                day == today.get(Calendar.DAY_OF_MONTH)

                                    val cellColor = if (isToday) {
                                        Color(0xFFE7C5AF)
                                    } else {
                                        Color(0xFFF7F0EA)
                                    }

                                    Card(
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(64.dp),

                                        shape = RoundedCornerShape(9.dp),

                                        colors = CardDefaults.cardColors(
                                            containerColor = cellColor
                                        )
                                    ) {

                                        Box(
                                            modifier = Modifier.fillMaxSize()
                                        ) {

                                            // Date number

                                            Text(
                                                text = day.toString(),

                                                modifier = Modifier
                                                    .align(Alignment.TopStart)
                                                    .padding(5.dp),

                                                fontSize = 10.sp,

                                                fontWeight = FontWeight.SemiBold,

                                                color = if (isToday) {
                                                    Color(0xFF704936)
                                                } else {
                                                    Color(0xFF806E65)
                                                }
                                            )

                                            // Photo placeholder
                                            // Actual images will appear here
                                            // when photo storage is implemented.

                                            Text(
                                                text = "＋",

                                                modifier = Modifier
                                                    .align(Alignment.Center),

                                                fontSize = 17.sp,

                                                color = Color(0xFFB69A88)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Calendar description

                    Text(
                        text = "♡ Your saved pet photographs will appear inside their respective dates.",

                        fontSize = 12.sp,

                        color = Color(0xFF927D73),

                        lineHeight = 18.sp
                    )
                }
            }

            // 4. ADD PHOTO SECTION

            Card(
                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(20.dp),

                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF3E5DA)
                )
            ) {

                Column(
                    modifier = Modifier.padding(20.dp),

                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    Text(
                        text = "TODAY'S MEMORY",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF92705F)
                    )

                    Text(
                        text = formattedDate,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF604B43)
                    )

                    Text(
                        text = "Capture today's little moments!",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF604B43)
                    )

                    Text(
                        text = "Add a photograph of your pet and keep another precious memory in your diary.",

                        fontSize = 14.sp,

                        color = Color(0xFF806E65),

                        lineHeight = 21.sp
                    )

                    Button(
                        onClick = {

                            Toast.makeText(
                                context,
                                "Photo selection will be implemented next!",
                                Toast.LENGTH_SHORT
                            ).show()
                        },

                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Text(
                            text = "＋  Add Today's Photo",

                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            // 5. FOOTER

            Text(
                text = "Made with ♡ for your furry friends.",

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),

                fontSize = 12.sp,

                color = Color(0xFFA18D83),

                textAlign = TextAlign.Center
            )
        }
    }
}


package com.example.pawpics.ui

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar

@Composable
fun HomeScreen() {

    val context = LocalContext.current

    // Temporary frontend diary entries.
    val entries = remember {
        mutableStateMapOf<String, UiDiaryEntry>()
    }

    // Currently displayed month.
    var displayedMonth by remember {
        mutableStateOf(Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, 1)
        })
    }

    // Selected photograph.
    var selectedPhotoUri by remember {
        mutableStateOf<Uri?>(null)
    }

    // Photo caption.
    var caption by remember {
        mutableStateOf("")
    }

    // Selected calendar date.
    var selectedDate by remember {
        mutableStateOf<String?>(null)
    }

    // Gallery picker.
    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->

        if (uri != null) {
            selectedPhotoUri = uri
            caption = ""
        }
    }

    // ADD PHOTO SCREEN
    if (selectedPhotoUri != null) {

        AddPhotoScreen(
            photoUri = selectedPhotoUri!!,

            caption = caption,

            onCaptionChange = { newCaption ->
                caption = newCaption
            },

            onSave = {

                val todayKey = uiDateKey(Calendar.getInstance())

                val photoUri = selectedPhotoUri

                if (photoUri != null) {

                    entries[todayKey] = UiDiaryEntry(
                        dateKey = todayKey,
                        photoUri = photoUri,
                        caption = caption.trim()
                    )

                    selectedPhotoUri = null
                    caption = ""

                    Toast.makeText(
                        context,
                        "Photo added to PawPics!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },

            onCancel = {
                selectedPhotoUri = null
                caption = ""
            }
        )

    } else {

        // HOMEPAGE

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFFFFF9F5)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp),

                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {

                // PAWPICS TITLE

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

                // GREETING

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

                // MONTH NAVIGATION

                Row(
                    modifier = Modifier.fillMaxWidth(),

                    horizontalArrangement = Arrangement.SpaceBetween,

                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = uiMonthTitle(displayedMonth),

                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF604B43)
                    )

                    Row {

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
                                fontSize = 27.sp
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
                                fontSize = 27.sp
                            )
                        }
                    }
                }

                // MONTHLY PHOTO CALENDAR

                CalendarView(
                    displayedMonth = displayedMonth,

                    entries = entries,

                    onDateClick = { dateKey ->

                        if (entries.containsKey(dateKey)) {

                            selectedDate = dateKey

                        } else {

                            Toast.makeText(
                                context,
                                "No photo saved on this date.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                )

                // ADD PHOTO SECTION

                Column(
                    verticalArrangement = Arrangement.spacedBy(9.dp)
                ) {

                    Text(
                        text = "TODAY'S MEMORY",

                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF92705F)
                    )

                    Text(
                        text = uiTodayLabel(),

                        fontSize = 16.sp,
                        color = Color(0xFF604B43)
                    )

                    Text(
                        text = "Capture today's little moments!",

                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF604B43)
                    )

                    Button(
                        onClick = {
                            photoPicker.launch("image/*")
                        },

                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Text(
                            text = "+ Add Today's Photo",
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                // FOOTER

                Text(
                    text = "Made with ♡ for your furry friends.",

                    modifier = Modifier.fillMaxWidth(),

                    textAlign = TextAlign.Center,

                    fontSize = 12.sp,
                    color = Color(0xFFA18D83)
                )
            }
        }
    }

    // PHOTO DETAIL DIALOG

    val entry: UiDiaryEntry? = if (selectedDate != null) {
        entries[selectedDate!!]
    } else {
        null
    }

    if (entry != null) {

        PhotoDetailScreen(
            entry = entry,

            onDismiss = {
                selectedDate = null
            },

            onDelete = {

                entries.remove(entry.dateKey)
                selectedDate = null

                Toast.makeText(
                    context,
                    "Photo removed.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )
    }
}

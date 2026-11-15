
package com.example.pawpics

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
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

    // Get the current date
    val today = Date()

    val monthYear = SimpleDateFormat(
        "MMMM yyyy",
        Locale.getDefault()
    ).format(today)

    val formattedDate = SimpleDateFormat(
        "EEEE, MMMM d",
        Locale.getDefault()
    ).format(today)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFFFF9F5)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),

            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            // Application Header
            Column {

                Text(
                    text = "🐾 PawPics",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF604B43)
                )

                Text(
                    text = "A little memory, every day.",
                    fontSize = 14.sp,
                    color = Color(0xFF927D73)
                )
            }

            // Welcome Message
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {

                Text(
                    text = "Hello, pet parent! ♡",
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF604B43)
                )

                Text(
                    text = "Every little moment with your pet is worth remembering.",
                    fontSize = 15.sp,
                    color = Color(0xFF806E65),
                    lineHeight = 22.sp
                )
            }

            // Today's Diary Entry Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF3E5DA)
                )
            ) {

                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Text(
                        text = "TODAY'S MEMORY",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF92705F)
                    )

                    Text(
                        text = formattedDate,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF604B43)
                    )

                    Text(
                        text = "Have you captured a moment today?",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF604B43)
                    )

                    Text(
                        text = "Take a picture or choose one from your gallery to save today's memory.",
                        fontSize = 14.sp,
                        color = Color(0xFF806E65),
                        lineHeight = 21.sp
                    )

                    Button(
                        onClick = {
                            Toast.makeText(
                                context,
                                "Photo entry will be implemented next!",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Text(
                            text = "＋ Add Today's Photo",
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            // Calendar Preview Card
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
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Text(
                        text = "YOUR PET'S DIARY",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF92705F)
                    )

                    Text(
                        text = monthYear,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF604B43)
                    )

                    Text(
                        text = "Your monthly collection of little moments will appear here.",
                        fontSize = 14.sp,
                        color = Color(0xFF806E65),
                        lineHeight = 21.sp
                    )

                    OutlinedButton(
                        onClick = {
                            Toast.makeText(
                                context,
                                "Monthly calendar will be implemented soon!",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Text("View Photo Calendar")
                    }
                }
            }

            // Footer
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


package com.example.pawpics.ui

import android.widget.ImageView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun PhotoDetailScreen(
    entry: UiDiaryEntry,
    onDismiss: () -> Unit,
    onDelete: () -> Unit
) {

    Dialog(
        onDismissRequest = onDismiss
    ) {

        Card(
            modifier = Modifier.fillMaxWidth(),

            shape = RoundedCornerShape(20.dp),

            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFFF9F5)
            )
        ) {

            Column(
                modifier = Modifier.padding(18.dp),

                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Text(
                    text = "🐾 Your Pet's Memory",

                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF604B43)
                )

                // PHOTO DATE

                val formattedDate = try {

                    val date = SimpleDateFormat(
                        "yyyy-MM-dd",
                        Locale.US
                    ).parse(entry.dateKey)

                    if (date != null) {
                        SimpleDateFormat(
                            "MMMM d, yyyy",
                            Locale.getDefault()
                        ).format(date)
                    } else {
                        entry.dateKey
                    }

                } catch (_: Exception) {
                    entry.dateKey
                }

                Text(
                    text = formattedDate,

                    fontSize = 14.sp,
                    color = Color(0xFF927D73)
                )

                // FULL PHOTO

                AndroidView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                        .clip(RoundedCornerShape(12.dp)),

                    factory = { context ->

                        ImageView(context).apply {
                            scaleType = ImageView.ScaleType.FIT_CENTER
                        }
                    },

                    update = { imageView ->
                        imageView.setImageURI(entry.photoUri)
                    }
                )

                // PHOTO DESCRIPTION

                Text(
                    text = entry.caption.ifBlank {
                        "No description added."
                    },

                    fontSize = 16.sp,
                    color = Color(0xFF604B43)
                )

                // DELETE PHOTO

                Button(
                    onClick = onDelete,

                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text("Delete Memory")
                }

                // CLOSE

                TextButton(
                    onClick = onDismiss,

                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text("Close")
                }
            }
        }
    }
}

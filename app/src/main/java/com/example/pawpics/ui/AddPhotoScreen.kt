
package com.example.pawpics.ui

import android.net.Uri
import android.widget.ImageView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun AddPhotoScreen(
    photoUri: Uri,
    caption: String,
    onCaptionChange: (String) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit
) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFFFF9F5)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),

            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = "Add a Memory ♡",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF604B43)
            )

            Text(
                text = "A little moment worth remembering.",
                fontSize = 14.sp,
                color = Color(0xFF927D73)
            )

            // Selected photo preview
            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(16.dp)),

                factory = { context ->
                    ImageView(context).apply {
                        scaleType = ImageView.ScaleType.CENTER_CROP
                    }
                },

                update = { imageView ->
                    imageView.setImageURI(photoUri)
                }
            )

            // Caption input
            OutlinedTextField(
                value = caption,
                onValueChange = { newCaption ->
                    onCaptionChange(newCaption)
                },

                modifier = Modifier.fillMaxWidth(),

                label = {
                    Text("Photo description")
                },

                placeholder = {
                    Text("What did your pet do today?")
                },

                maxLines = 3,
                shape = RoundedCornerShape(12.dp)
            )

            // Save photo
            Button(
                onClick = onSave,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Save to PawPics",
                    fontWeight = FontWeight.SemiBold
                )
            }

            // Cancel
            OutlinedButton(
                onClick = onCancel,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancel")
            }
        }
    }
}

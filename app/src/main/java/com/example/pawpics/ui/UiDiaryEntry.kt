
package com.example.pawpics.ui

import android.net.Uri

data class UiDiaryEntry(
    val dateKey: String,
    val photoUri: Uri,
    val caption: String
)

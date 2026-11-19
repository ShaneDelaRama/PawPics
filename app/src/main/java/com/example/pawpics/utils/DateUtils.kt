
package com.example.pawpics.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {

    fun getDateKey(date: Date = Date()): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return formatter.format(date)
    }

    fun formatDate(dateKey: String): String {
        return try {
            val parser = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = parser.parse(dateKey)

            val formatter = SimpleDateFormat("MMMM d, yyyy", Locale.getDefault())
            formatter.format(date ?: return dateKey)

        } catch (e: Exception) {
            dateKey
        }
    }

    fun formatMonth(date: Date = Date()): String {
        val formatter = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
        return formatter.format(date)
    }
}

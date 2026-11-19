package com.example.pawpics.data

import android.content.ContentValues
import android.content.Context

class DiaryRepository(context: Context) {

    private val diaryStorage = DiaryStorage(context)

    //CRUD FUNCTIONS
    //CREATE
    fun saveEntry(entry: DiaryEntry): Long {
        val db = diaryStorage.writableDatabase

        val values = ContentValues().apply {
            put(DiaryStorage.COLUMN_DATE, entry.dateKey)
            put(DiaryStorage.COLUMN_PHOTO, entry.photoPath)
            put(DiaryStorage.COLUMN_CAPTION, entry.caption)
        }

        val cursor = db.query(
            DiaryStorage.TABLE_DIARY,
            arrayOf(DiaryStorage.COLUMN_ID),
            "${DiaryStorage.COLUMN_DATE} = ?",
            arrayOf(entry.dateKey),
            null,
            null,
            null
        )

        cursor.use {
            if (it.moveToFirst()) {
                // An entry already exists for this date.
                return db.update(
                    DiaryStorage.TABLE_DIARY,
                    values,
                    "${DiaryStorage.COLUMN_DATE} = ?",
                    arrayOf(entry.dateKey)
                ).toLong()
            }
        }
        //for no entry
        return db.insert(
            DiaryStorage.TABLE_DIARY,
            null,
            values
        )
    }

    //READ
    fun getAllEntries(): List<DiaryEntry> {
        val entries = mutableListOf<DiaryEntry>()
        val db = diaryStorage.readableDatabase

        val cursor = db.query(
            DiaryStorage.TABLE_DIARY,
            null,
            null,
            null,
            null,
            null,
            "${DiaryStorage.COLUMN_DATE} DESC"
        )

        cursor.use {
            while (it.moveToNext()) {
                entries.add(
                    DiaryEntry(
                        dateKey = it.getString(
                            it.getColumnIndexOrThrow(DiaryStorage.COLUMN_DATE)
                        ),
                        photoPath = it.getString(
                            it.getColumnIndexOrThrow(DiaryStorage.COLUMN_PHOTO)
                        ),
                        caption = it.getString(
                            it.getColumnIndexOrThrow(DiaryStorage.COLUMN_CAPTION)
                        ) ?: ""
                    )
                )
            }
        }

        return entries
    }

    //READ
    fun getEntryByDate(dateKey: String): DiaryEntry? {
        return getAllEntries().firstOrNull {
            it.dateKey == dateKey
        }
    }

    //DELETE
    fun deleteEntry(dateKey: String): Int {
        val db = diaryStorage.writableDatabase

        return db.delete(
            DiaryStorage.TABLE_DIARY,
            "${DiaryStorage.COLUMN_DATE} = ?",
            arrayOf(dateKey)
        )
    }

    fun close() {
        diaryStorage.close()
    }
}

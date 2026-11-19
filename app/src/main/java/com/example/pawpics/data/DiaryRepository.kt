package com.example.pawpics.data

import android.content.ContentValues
import android.content.Context

class DiaryRepository(context: Context) {

    private val diaryStorage = DiaryStorage(context)

    //CRUD FUNCTIONS
    //CREATE
    fun insertEntry(entry: DiaryEntry): Long {
        val db = diaryStorage.writableDatabase
        val values = ContentValues().apply {
            put(DiaryStorage.COLUMN_DATE, entry.dateKey)
            put(DiaryStorage.COLUMN_PHOTO, entry.photoPath)
            put(DiaryStorage.COLUMN_CAPTION, entry.caption)
        }
        return db.insert(DiaryStorage.TABLE_DIARY, null, values)
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
            "${DiaryStorage.COLUMN_ID} DESC"
        )

        cursor.use {
            while (it.moveToNext()) {
                val dateKey = it.getString(
                    it.getColumnIndexOrThrow(DiaryStorage.COLUMN_DATE)
                )

                val photoPath = it.getString(
                    it.getColumnIndexOrThrow(DiaryStorage.COLUMN_PHOTO)
                )

                val caption = it.getString(
                    it.getColumnIndexOrThrow(DiaryStorage.COLUMN_CAPTION)
                ) ?: ""

                entries.add(
                    DiaryEntry(dateKey, photoPath, caption)
                )
            }
        }

        return entries
    }

    //UPDATE
    fun updateEntry(id: Long, entry: DiaryEntry): Int {
        val db = diaryStorage.writableDatabase

        val values = ContentValues().apply {
            put(DiaryStorage.COLUMN_DATE, entry.dateKey)
            put(DiaryStorage.COLUMN_PHOTO, entry.photoPath)
            put(DiaryStorage.COLUMN_CAPTION, entry.caption)
        }

        return db.update(
            DiaryStorage.TABLE_DIARY,
            values,
            "${DiaryStorage.COLUMN_ID} = ?",
            arrayOf(id.toString())
        )
    }

    //DELETE
    fun deleteEntry(id: Long): Int {
        val db = diaryStorage.writableDatabase

        return db.delete(
            DiaryStorage.TABLE_DIARY,
            "${DiaryStorage.COLUMN_ID} = ?",
            arrayOf(id.toString())
        )
    }

    fun close() {
        diaryStorage.close()
    }
}

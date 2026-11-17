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


    //UPDATE

    //DELETE


    fun close() {
        diaryStorage.close()
    }
}

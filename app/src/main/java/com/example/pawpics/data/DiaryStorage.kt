package com.example.pawpics.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DiaryStorage(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "PawPics.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_DIARY = "diary_entries"
        const val COLUMN_ID = "id"
        const val COLUMN_DATE = "date_key"
        const val COLUMN_PHOTO = "photo_path"
        const val COLUMN_CAPTION = "caption"
    }


    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
        CREATE TABLE $TABLE_DIARY (
            $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_DATE TEXT NOT NULL UNIQUE,
            $COLUMN_PHOTO TEXT NOT NULL,
            $COLUMN_CAPTION TEXT
        )
    """.trimIndent()

        db.execSQL(createTable)
    }


    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_DIARY")
        onCreate(db)
    }
}

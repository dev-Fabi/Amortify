package dev.schedler.amortify.data.local.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

fun getDatabaseBuilder(context: Context): RoomDatabase.Builder<AmortifyDatabase> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath("amortify.db")
    return Room.databaseBuilder<AmortifyDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}
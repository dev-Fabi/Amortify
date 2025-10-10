package dev.schedler.amortify.data.local.db

import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File
import kotlin.io.path.Path

fun getDatabaseBuilder(): RoomDatabase.Builder<AmortifyDatabase> {
    val os = System.getProperty("os.name").lowercase()
    val userHome = System.getProperty("user.home")?.takeIf { it.isNotEmpty() }
        ?: throw IllegalStateException("Could not determine user home directory")

    val basePath = when {
        os.contains("win") -> System.getenv("APPDATA")?.takeIf { it.isNotEmpty() }?.let(::Path)
        os.contains("mac") -> Path(userHome, "Library", "Application Support")
        else -> System.getenv("XDG_DATA_HOME")?.takeIf { it.isNotEmpty() }?.let(::Path)
            ?: Path(userHome, ".local", "share")
    }?.toString()
    requireNotNull(basePath) { "Could not determine base path for application data" }

    val appDataFile = File(basePath, "dev.schedler.amortify")
    appDataFile.mkdirs()

    return Room.databaseBuilder<AmortifyDatabase>(
        name = appDataFile.resolve("amortify.db").absolutePath,
    )
}
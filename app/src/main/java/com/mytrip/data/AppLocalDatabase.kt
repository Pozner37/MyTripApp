package com.mytrip.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mytrip.MyTripApp
import com.mytrip.data.user.User
import com.mytrip.data.user.UserDAO

@Database(entities = [User::class], version = 7, exportSchema = true)
abstract class AppLocalDbRepository : RoomDatabase() {
    abstract fun userDao(): UserDAO
}

object AppLocalDatabase {
    val db: AppLocalDbRepository by lazy {
        val context = MyTripApp.Globals.appContext
            ?: throw IllegalStateException("Application context not available")

        Room.databaseBuilder(
            context,
            AppLocalDbRepository::class.java,
            "my-trip"
        ).fallbackToDestructiveMigration()
            .build()
    }
}
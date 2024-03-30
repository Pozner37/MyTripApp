package com.mytrip.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mytrip.MyTripApp
import com.mytrip.data.post.PostDAO
import com.mytrip.data.user.User
import com.mytrip.data.user.UserDAO
import com.mytrip.data.post.LatLngConverter
import com.mytrip.data.post.Post

@Database(entities = [User::class, Post::class], version = 7, exportSchema = true)
@TypeConverters(LatLngConverter::class)
abstract class AppLocalDbRepository : RoomDatabase() {
    abstract fun userDao(): UserDAO
    abstract fun postDao(): PostDAO
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
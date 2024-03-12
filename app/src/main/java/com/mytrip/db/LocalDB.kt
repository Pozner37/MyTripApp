package com.mytrip.db;

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mytrip.MyTripApp
import com.mytrip.db.post.Post
import com.mytrip.db.post.PostDAO
import com.mytrip.db.user.User
import com.mytrip.db.user.UserDAO

@Database(entities = [Post::class, User::class], version = 7, exportSchema = true)
abstract class LocalDBRepo : RoomDatabase() {
    abstract fun postDao(): PostDAO
    abstract fun userDao(): UserDAO
}

object LocalDB {
    val db: LocalDBRepo by lazy {
        val context = MyTripApp.Globals.appContext
            ?: throw IllegalStateException("Application context not available")

        Room.databaseBuilder(
            context,
            LocalDBRepo::class.java,
            "my-trip"
        ).fallbackToDestructiveMigration()
            .build()
    }
}

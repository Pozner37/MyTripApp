package com.mytrip.data.post
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
@Dao
interface PostDAO {
    @Query("SELECT * FROM post WHERE country = :countryCode")
    fun getCountryPosts(countryCode: String): LiveData<MutableList<Post>>

    @Query("SELECT * FROM post WHERE userId = :userId")
    fun getPostsByUserId(userId: String): LiveData<MutableList<Post>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(post: Post)

    @Delete
    fun delete(post: Post)
}
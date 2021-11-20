package com.carvajal.lucas.supertaster.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CookbookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCookbook(cookbook: Cookbook): Long

    @Query("SELECT * FROM cookbooks")
    fun getAllCookbooks(): LiveData<List<Cookbook>>

    @Query("SELECT * FROM cookbooks where id = :id")
    fun getCookbook(id: Long): LiveData<Cookbook>
}
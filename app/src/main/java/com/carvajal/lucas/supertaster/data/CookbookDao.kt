package com.carvajal.lucas.supertaster.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CookbookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCookbook(vararg cookbook: Cookbook)

    @Query("SELECT * FROM cookbooks")
    fun getAllCookbooks(): List<Cookbook>
}
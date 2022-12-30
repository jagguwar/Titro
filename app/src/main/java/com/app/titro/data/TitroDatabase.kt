package com.app.titro.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.titro.data.models.TitroNote

@Database(entities = [TitroNote::class], version = 1, exportSchema = false)
abstract class TitroDatabase: RoomDatabase() {

    abstract fun titroDao(): TitroDao

}
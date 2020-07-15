package ru.pestoff.cardealer.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.pestoff.cardealer.model.Car
import java.lang.NullPointerException

@Database(entities = arrayOf(Car::class), version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getCarsDao(): CarsDao


    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getAppDatabase(context: Context): AppDatabase {

            INSTANCE = INSTANCE ?: Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "appDatabase").build()

            return INSTANCE!!
        }
    }
}
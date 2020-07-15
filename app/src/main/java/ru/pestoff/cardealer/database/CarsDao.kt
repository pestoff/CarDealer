package ru.pestoff.cardealer.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ru.pestoff.cardealer.model.Car

@Dao
public interface CarsDao {

    @Insert
    suspend fun insertAll(vararg cars: Car)

    @Delete
    suspend fun delete(car: Car)

    @Query("SELECT * FROM cars")
    suspend fun getAllCars(): List<Car>
}
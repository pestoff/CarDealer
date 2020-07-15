package ru.pestoff.cardealer

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.core.net.toUri
import ru.pestoff.cardealer.database.AppDatabase
import ru.pestoff.cardealer.model.Car
import java.io.ByteArrayOutputStream
import java.io.File

class CarRepository(context: Context) {

    val database = AppDatabase.getAppDatabase(context).getCarsDao()

    private val cars = mutableListOf<Car>()

    suspend fun getAllCars() : List<Car> {
        return database.getAllCars()
    }

    suspend fun addCar(car: Car) {
        database.insertAll(car)
    }

    suspend fun deleteCar(car: Car) {
        database.delete(car)
    }

    suspend fun addImage(context: Context, image: Drawable, fileName: String) {

        val source: Bitmap = (image as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        source.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val bitmapData = stream.toByteArray()

        context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
            it.write(bitmapData)

        }
    }
}
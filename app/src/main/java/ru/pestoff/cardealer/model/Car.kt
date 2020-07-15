package ru.pestoff.cardealer.model

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "cars")
data class Car (
    @PrimaryKey
    var id: String,
    val phone: String,
    val name: String,
    val year: Int,
    val mileage: Int,
    val color: String,
    val body: String,
    val owners: Int,
    val transmission: String,
    val engine: String,
    val drive: String,
    val steeringWheel: String,
    val passport: String,
    val ownershipTime: String,
    val customs: Boolean,
    val vehicleNumber: String,
    val vin: String,
    val description: String,
    val imagePath: String?
) : Serializable
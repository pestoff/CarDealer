package ru.pestoff.cardealer.viewModel

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.kotlincoroutines.util.singleArgViewModelFactory
import kotlinx.coroutines.launch
import ru.pestoff.cardealer.CarRepository
import ru.pestoff.cardealer.model.Car
import java.io.File
import java.net.URI

class mainViewModel(private val repository: CarRepository) : ViewModel() {

    companion object {
        val FACTORY = singleArgViewModelFactory(::mainViewModel)
    }

    val selectedItem = MutableLiveData<Car>()
    val cars = MutableLiveData<List<Car>>()
    val onAddCarBtnSelected = MutableLiveData<Boolean>()

    fun selectItem(item: Car) {
        selectedItem.value = item
    }

    fun selectAddCarBtn() {
        onAddCarBtnSelected.value = onAddCarBtnSelected.value?.not() ?: true
    }

    fun getAllCars () {
        viewModelScope.launch {
            cars.value  = repository.getAllCars()
        }
    }

    fun addCar(car: Car) {
        viewModelScope.launch {
            repository.addCar(car)
            getAllCars()
        }
    }

    fun deleteCar(car: Car) {
        viewModelScope.launch {
            repository.deleteCar(car)
            getAllCars()
        }
    }

    fun addImage(context: Context, image: Drawable, name: String) {
        viewModelScope.launch {
             repository.addImage(context, image, name)
        }
    }
}
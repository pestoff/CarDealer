package ru.pestoff.cardealer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import ru.pestoff.cardealer.databinding.ActivityMainBinding
import ru.pestoff.cardealer.fragment.AddCarFragment
import ru.pestoff.cardealer.fragment.CarDetailsFragment
import ru.pestoff.cardealer.fragment.CarListFragment
import ru.pestoff.cardealer.viewModel.mainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = CarRepository(this)
        val viewModel = ViewModelProviders
            .of(this, mainViewModel.FACTORY(repository))
            .get(mainViewModel::class.java)

        viewModel.selectedItem.observe(this, Observer { item ->
            supportFragmentManager.beginTransaction()
                .replace(R.id.car_list_fragment_container, CarDetailsFragment())
                .addToBackStack(null)
                .commit()
        })

        viewModel.onAddCarBtnSelected.observe(this, Observer {
            supportFragmentManager.beginTransaction()
                .replace(R.id.car_list_fragment_container, AddCarFragment())
                .addToBackStack(null)
                .commit()
        })

        supportFragmentManager.beginTransaction()
            .add(R.id.car_list_fragment_container, CarListFragment())
            .commit()

    }
}

package ru.pestoff.cardealer.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import ru.pestoff.cardealer.R
import ru.pestoff.cardealer.adapter.CarAdapter
import ru.pestoff.cardealer.databinding.FragmentCarListBinding
import ru.pestoff.cardealer.model.Car
import ru.pestoff.cardealer.viewModel.SwipeToDeleteCallback
import ru.pestoff.cardealer.viewModel.mainViewModel
import java.lang.Exception

class CarListFragment : Fragment() {
    private var _binding: FragmentCarListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: mainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = activity?.run {
            ViewModelProviders.of(this) [mainViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCarListBinding.inflate(inflater, container, false)

        binding.toolbarListCar.title = "Car Dealer"
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarListCar)

        binding.addCarButton.setOnClickListener { viewModel.selectAddCarBtn() }

        binding.recyclerView.layoutManager = LinearLayoutManager(activity)

        val listener = object : CarAdapter.AdapterEventListener {
            override fun onClick(car: Car) {
                viewModel.selectItem(car)
            }

            override fun onCarDelete(car: Car) {
                viewModel.deleteCar(car)
                val message =  getString(R.string.delete_snackbar, car.name)
                Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
                    .show()
            }
        }

        viewModel.getAllCars()
        val adapter = CarAdapter(listener)

        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(adapter))
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

        binding.recyclerView.adapter = adapter

        viewModel.cars.observe(viewLifecycleOwner, Observer { list ->
            adapter.cars = list
            adapter.notifyDataSetChanged()
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}




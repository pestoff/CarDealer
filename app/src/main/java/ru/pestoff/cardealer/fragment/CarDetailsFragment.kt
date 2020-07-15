package ru.pestoff.cardealer.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_view.view.*
import ru.pestoff.cardealer.databinding.FragmentDetailViewBinding
import ru.pestoff.cardealer.viewModel.mainViewModel
import java.lang.Exception

class CarDetailsFragment : Fragment() {
    private var _binding: FragmentDetailViewBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: mainViewModel

    private lateinit var telephoneNumber: String

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
        _binding = FragmentDetailViewBinding.inflate(inflater, container, false)

        configureToolbar()

        binding.callButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$telephoneNumber")
            startActivity(intent)
        }

        viewModel.selectedItem.observe(viewLifecycleOwner, Observer { car ->
            binding.apply {
                toolbarLayout.title = car.name
                productionYearValue.text = car.year.toString()
                mileageValue.text = car.mileage.toString()
                colorValue.text = car.color
                bodyValue.text = car.body
                ownersValue.text = car.owners.toString()
                transmissionValue.text = car.transmission
                engineValue.text = car.engine
                driveValue.text = car.drive
                steeringWheelValue.text = car.steeringWheel
                passportValue.text = car.passport
                ownershipTimeValue.text = car.ownershipTime
                customsValue.text = if (car.customs) "Yes" else "No"
                vehicleNumberValue.text = car.vehicleNumber
                vinNumberValue.text = car.vin
                descriptionValue.text = car.description

                car.imagePath?.let {
                    Glide.with(this.root).load(it).into(toolbarImageView)
                }

                telephoneNumber = car.phone
            }
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun configureToolbar() {
        val appCompatActivity = activity as AppCompatActivity

        appCompatActivity.setSupportActionBar(binding.toolbar)

        appCompatActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        appCompatActivity.supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.toolbar.setNavigationOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }
}
package ru.pestoff.cardealer.fragment

import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import ru.pestoff.cardealer.R
import ru.pestoff.cardealer.databinding.FragmentAddCarBinding
import ru.pestoff.cardealer.model.Car
import ru.pestoff.cardealer.viewModel.mainViewModel
import java.io.File
import java.lang.Exception
import java.util.*

class AddCarFragment : Fragment() {

    private val PICK_IMAGE = 1

    private var _binding: FragmentAddCarBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: mainViewModel

    private var image: Drawable? = null

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

        _binding = FragmentAddCarBinding.inflate(inflater, container, false)

        configureToolbar()

        binding.addPhotoImageView.setOnClickListener {
            addPhoto()
        }

        binding.addCarButton.setOnClickListener {
            addCar()
        }

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_IMAGE) {
            val imageUri = data?.data
            Glide.with(binding.root)
                .load(imageUri)
                .into(object : CustomTarget<Drawable>() {
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        binding.addPhotoImageView.setImageDrawable(resource)
                        binding.addPhotoImageView.setBackgroundColor(resources.getColor(R.color.white))

                        image = resource
                }
                    override fun onLoadCleared(placeholder: Drawable?) {
                        binding.addPhotoImageView.setImageDrawable(placeholder)
                    }
                })
        }
    }

    private fun configureToolbar() {
        val appCompatActivity = activity as AppCompatActivity

        binding.toolbarAddCar.title = getString(R.string.add_toolbar)

        appCompatActivity.setSupportActionBar(binding.toolbarAddCar)
        appCompatActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        appCompatActivity.supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.toolbarAddCar.setNavigationOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }

    private fun addCar() {
        binding.apply {

            val context = activity?.applicationContext!!
            val id = UUID.randomUUID().toString()
            var path: String? = null

            image?.let {
                viewModel.addImage(context, it, "$id.JPEG")
                path = context.filesDir.absolutePath + "/$id.JPEG"
            }

            val car = Car(
                id,
                phoneEdit.text.toString(),
                nameEdit.text.toString(),
                yearEdit.text.toString().toInt(),
                mileageEdit.text.toString().toInt(),
                colorEdit.text.toString(),
                bodyEdit.text.toString(),
                ownersEdit.text.toString().toInt(),
                transmissionEdit.text.toString(),
                engineEdit.text.toString(),
                driveEdit.text.toString(),
                steeringEdit.text.toString(),
                passportEdit.text.toString(),
                ownershipEdit.text.toString(),
                customSwitch.isChecked,
                numberEdit.text.toString(),
                vinEdit.text.toString(),
                descriptionEdit.text.toString(),
                path
            )

            viewModel.addCar(car)

            activity?.supportFragmentManager?.popBackStack()
        }
    }

    private fun addPhoto() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select picture"), PICK_IMAGE)
    }
}
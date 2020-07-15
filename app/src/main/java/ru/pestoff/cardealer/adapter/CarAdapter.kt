package ru.pestoff.cardealer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.pestoff.cardealer.R
import ru.pestoff.cardealer.databinding.ItemViewBinding
import ru.pestoff.cardealer.model.Car

class CarAdapter(private val adapterEventListener: AdapterEventListener) : RecyclerView.Adapter<CarAdapter.CarHolder>() {

    var cars: List<Car> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()}

    public interface AdapterEventListener {
        fun onClick(car: Car)
        fun onCarDelete(car: Car)
    }

    fun remove(position: Int) {
        adapterEventListener.onCarDelete(cars[position])
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarHolder {

        return CarHolder(ItemViewBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount() = cars.size

    override fun onBindViewHolder(holder: CarHolder, position: Int) {
        val car = cars.get(position)

        holder.bind(car)
    }

    inner class CarHolder : RecyclerView.ViewHolder {

        private var itemBinding: ItemViewBinding

        constructor(itemBinding: ItemViewBinding) : super(itemBinding.root) {
            this.itemBinding = itemBinding
            this.itemBinding.root.setOnClickListener {
                adapterEventListener.onClick(cars[layoutPosition])
            }
        }

        fun bind(car: Car) {
            itemBinding.listName.text = car.name
            itemBinding.listYearValue.text = car.year.toString()
            itemBinding.listMileageValue.text = car.mileage.toString()
            itemBinding.listBodyValue.text = car.body
            itemBinding.listOwnerValue.text = car.owners.toString()


            Glide.with(itemBinding.root)
                .load(car.imagePath)
                .error(R.drawable.ic_car_stub)
                .into(itemBinding.imageView)
        }
    }
}
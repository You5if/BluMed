package com.component.pharma.ui.home.city

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.component.pharma.databinding.CityListItemBinding
import com.component.pharma.model.responses.GetCities
import com.component.pharma.model.responses.GetCitiesItem

class CityAdapter(val CData: GetCities): RecyclerView.Adapter<CityAdapter.CityViewHolder>() {
    inner class CityViewHolder(val binding: CityListItemBinding): RecyclerView.ViewHolder(binding.root)

    lateinit var bundle: Bundle

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        return CityViewHolder(CityListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun getItemCount() = CData.size

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.binding.apply {
            val city = CData[position]
            tvCityName.text = city.cityName
            tvCityPrice.text = city.price.toInt().toString()
            holder.itemView.setOnClickListener {
                onItemClickListener?.let { it(city) }
            }
        }
    }
    private var onItemClickListener: ((GetCitiesItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (GetCitiesItem) -> Unit) {
        onItemClickListener = listener
    }

}
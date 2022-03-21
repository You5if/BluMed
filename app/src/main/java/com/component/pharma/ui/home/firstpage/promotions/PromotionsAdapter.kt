package com.component.pharma.ui.home.firstpage.promotions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.component.pharma.databinding.CityListItemBinding
import com.component.pharma.databinding.PromotionsImagesBinding
import com.component.pharma.model.responses.GetCities
import com.component.pharma.model.responses.GetProm
import com.component.pharma.model.responses.GetPromItems
import com.component.pharma.ui.home.city.CityAdapter

class PromotionsAdapter(val CData: List<GetPromItems>): RecyclerView.Adapter<PromotionsAdapter.PromotionsViewHolder>() {
    inner class PromotionsViewHolder(val binding: PromotionsImagesBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PromotionsViewHolder {
        return PromotionsViewHolder(PromotionsImagesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun getItemCount(): Int = CData.size

    override fun onBindViewHolder(holder: PromotionsViewHolder, position: Int) {
            val promotion = CData[position]
        holder.binding.apply {
            promTitle.text = promotion.title
            Glide.with(holder.binding.root).load(promotion.imagePath).into(promImage)
        }
    }
}
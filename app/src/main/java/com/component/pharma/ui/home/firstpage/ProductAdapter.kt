package com.component.pharma.ui.home.firstpage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.component.pharma.databinding.ProductCardItemBinding
import com.component.pharma.model.TestProductModel
import com.component.pharma.model.responses.GetProduct
import com.component.pharma.model.responses.GetProductArray
import com.component.pharma.ui.visible

class ProductAdapter() : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(val binding: ProductCardItemBinding) : RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<TestProductModel>() {
        override fun areItemsTheSame(oldItem: TestProductModel, newItem: TestProductModel): Boolean {
            return oldItem.phProductId == newItem.phProductId
        }

        override fun areContentsTheSame(oldItem: TestProductModel, newItem: TestProductModel): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(ProductCardItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        ))
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val record = differ.currentList[position]
        holder.binding.apply {
            Glide.with(holder.binding.root).load("http://pharmacyapi.autopay-mcs.com/"+record.apiPath).into(ProductImage)
            name.text = record.productName
//            price.text = record.price.toString()
            price.text = String.format("%.2f", record.price).toDouble().toString()
            unit.text = record.productUnit
            discreption.text = record.description
            removeFromCart.visible(false)

            if (record.removeBtn) {
                addToCart.visible(false)
                removeFromCart.visible(true)
            } else {
                addToCart.visible(true)
                removeFromCart.visible(false)
            }

            holder.binding.addToCart.setOnClickListener {
                onItemClickListener?.let { it(record) }
                addToCart.visible(false)
                removeFromCart.visible(true)

            }
            holder.binding.removeFromCart.setOnClickListener {
                onItemClickListener3?.let { it(record) }
                addToCart.visible(true)
                removeFromCart.visible(false)

            }

        }

    }
    private var onItemClickListener: ((TestProductModel) -> Unit)? = null
    private var onItemClickListener3: ((TestProductModel) -> Unit)? = null
    fun setOnItemClickListener(listener: (TestProductModel) -> Unit) {
        onItemClickListener = listener

    }
    fun setOnItemClickListener2(listener: (TestProductModel) -> Unit) {
        onItemClickListener3 = listener
    }
}
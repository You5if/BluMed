package com.component.pharma.ui.home.fourthpage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.component.pharma.databinding.CartCardItemBinding
import com.component.pharma.model.TestProductModel
import com.component.pharma.model.responses.GetProduct

class CartAdapter() : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    inner class CartViewHolder(val binding: CartCardItemBinding): RecyclerView.ViewHolder(binding.root)



    private var oldList = emptyList<TestProductModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(CartCardItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        ))
    }

    override fun getItemCount() = oldList.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val record = oldList[position]
        holder.binding.apply {
            Glide.with(holder.binding.root).load("http://pharmacyapi.autopay-mcs.com/"+record.apiPath).into(ProductImage)
            name.text = record.productName
            price.text = record.price.toString()
            unit.text = record.unitMeasure
            discreption.text = record.description
            quantity.text = record.quantity.toString()

            holder.binding.reomve.setOnClickListener {
                onItemClickListener2?.let { it(record) }
            }
            holder.binding.add.setOnClickListener {
                onItemClickListener1?.let { it(record) }
            }

        }
    }

    fun setData(newList: List<TestProductModel>) {
        val diffUtil = CartDiffUtil(oldList, newList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        oldList = newList
        diffResults.dispatchUpdatesTo(this)
    }

    private var onItemClickListener1: ((TestProductModel) -> Unit)? = null
private var onItemClickListener2: ((TestProductModel) -> Unit)? = null


    fun setOnItemClickListener1(listener: (TestProductModel) -> Unit) {
        onItemClickListener1 = listener
    }
fun setOnItemClickListener2(listener2: (TestProductModel) -> Unit) {
    onItemClickListener2 = listener2
}
}
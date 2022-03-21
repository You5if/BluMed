package com.component.pharma.ui.home.thirdpage.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.component.pharma.databinding.NotificationItemBinding
import com.component.pharma.databinding.OrderDetItemBinding
import com.component.pharma.databinding.OrdersItemBinding
import com.component.pharma.model.responses.*
import com.component.pharma.ui.home.thirdpage.notification.NotificationAdapter

class OrderAdapter(val CData: OrderDetails): RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(val binding: OrderDetItemBinding): RecyclerView.ViewHolder(binding.root)

    lateinit var bundle: Bundle

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderAdapter.OrderViewHolder {
        return OrderViewHolder(
                OrderDetItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                ))
    }

    override fun getItemCount() = CData.size

    override fun onBindViewHolder(holder: OrderAdapter.OrderViewHolder, position: Int) {
        holder.binding.apply {
            val notify = CData[position]
            productName.text = notify.productName
            productPrice.text = notify.price.toString()
            holder.itemView.setOnClickListener {
                onItemClickListener?.let { it(notify) }
            }
        }
    }
    private var onItemClickListener: ((OrderDetailsItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (OrderDetailsItem) -> Unit) {
        onItemClickListener = listener
    }
}
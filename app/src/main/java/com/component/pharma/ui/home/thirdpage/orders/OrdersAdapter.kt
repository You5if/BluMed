package com.component.pharma.ui.home.thirdpage.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.component.pharma.databinding.NotificationItemBinding
import com.component.pharma.databinding.OrdersItemBinding
import com.component.pharma.model.responses.AllOrdersResponse
import com.component.pharma.model.responses.AllOrdersResponseItem
import com.component.pharma.model.responses.Groups
import com.component.pharma.model.responses.GroupsItem
import com.component.pharma.ui.home.thirdpage.notification.NotificationAdapter

class OrdersAdapter(val CData: AllOrdersResponse): RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder>() {

    inner class OrdersViewHolder(val binding: OrdersItemBinding): RecyclerView.ViewHolder(binding.root)

    lateinit var bundle: Bundle

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersAdapter.OrdersViewHolder {
        return OrdersViewHolder(
                OrdersItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                ))
    }

    override fun getItemCount() = CData.size

    override fun onBindViewHolder(holder: OrdersAdapter.OrdersViewHolder, position: Int) {
        holder.binding.apply {
            val notify = CData[position]
            notification.text = notify.invoiceNo
            holder.itemView.setOnClickListener {
                onItemClickListener?.let { it(notify) }
            }
        }
    }
    private var onItemClickListener: ((AllOrdersResponseItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (AllOrdersResponseItem) -> Unit) {
        onItemClickListener = listener
    }
}
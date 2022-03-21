package com.component.pharma.ui.home.thirdpage.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.component.pharma.databinding.GroupsListItemBinding
import com.component.pharma.databinding.NotificationItemBinding
import com.component.pharma.model.responses.AllNotificationsResponse
import com.component.pharma.model.responses.AllNotificationsResponseItem
import com.component.pharma.model.responses.Groups
import com.component.pharma.model.responses.GroupsItem
import com.component.pharma.ui.home.firstpage.search.group.GroupsAdapter

class NotificationAdapter(val CData: AllNotificationsResponse): RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    inner class NotificationViewHolder(val binding: NotificationItemBinding): RecyclerView.ViewHolder(binding.root)

    lateinit var bundle: Bundle

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationAdapter.NotificationViewHolder {
        return NotificationViewHolder(
                NotificationItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                ))
    }

    override fun getItemCount() = CData.size

    override fun onBindViewHolder(holder: NotificationAdapter.NotificationViewHolder, position: Int) {
        holder.binding.apply {
            val notify = CData[position]
            notification.text = notify.notification
            holder.itemView.setOnClickListener {
                onItemClickListener?.let { it(notify) }
            }
        }
    }
    private var onItemClickListener: ((AllNotificationsResponseItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (AllNotificationsResponseItem) -> Unit) {
        onItemClickListener = listener
    }
}
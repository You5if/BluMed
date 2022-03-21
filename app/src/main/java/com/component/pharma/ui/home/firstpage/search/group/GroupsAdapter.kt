package com.component.pharma.ui.home.firstpage.search.group

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.component.pharma.databinding.CategoryListItemBinding
import com.component.pharma.databinding.GroupsListItemBinding
import com.component.pharma.model.responses.Categories
import com.component.pharma.model.responses.CategoriesItem
import com.component.pharma.model.responses.Groups
import com.component.pharma.model.responses.GroupsItem

class GroupsAdapter(val CData: Groups): RecyclerView.Adapter<GroupsAdapter.GroupsViewHolder>() {
    inner class GroupsViewHolder(val binding: GroupsListItemBinding): RecyclerView.ViewHolder(binding.root)

    lateinit var bundle: Bundle

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupsViewHolder {
        return GroupsViewHolder(
            GroupsListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ))
    }

    override fun getItemCount() = CData.size

    override fun onBindViewHolder(holder: GroupsViewHolder, position: Int) {
        holder.binding.apply {
            val group = CData[position]
            tvGroupName.text = group.groupName
            holder.itemView.setOnClickListener {
                onItemClickListener?.let { it(group) }
            }
        }
    }
    private var onItemClickListener: ((GroupsItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (GroupsItem) -> Unit) {
        onItemClickListener = listener
    }

}
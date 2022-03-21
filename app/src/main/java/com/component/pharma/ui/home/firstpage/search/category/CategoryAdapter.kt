package com.component.pharma.ui.home.firstpage.search.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.component.pharma.databinding.CategoryListItemBinding
import com.component.pharma.model.responses.Categories
import com.component.pharma.model.responses.CategoriesItem



class CategoryAdapter(val CData: Categories): RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
        inner class CategoryViewHolder(val binding: CategoryListItemBinding): RecyclerView.ViewHolder(binding.root)

        lateinit var bundle: Bundle

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
            return CategoryViewHolder(
                CategoryListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ))
        }

        override fun getItemCount() = CData.size

        override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
            holder.binding.apply {
                val category = CData[position]
                tvCategoryName.text = category.categoryName
                holder.itemView.setOnClickListener {
                    onItemClickListener?.let { it(category) }
                }
            }
        }
        private var onItemClickListener: ((CategoriesItem) -> Unit)? = null

        fun setOnItemClickListener(listener: (CategoriesItem) -> Unit) {
            onItemClickListener = listener
        }

    }
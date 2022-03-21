package com.component.pharma.ui.home.fourthpage

import androidx.recyclerview.widget.DiffUtil
import com.component.pharma.model.TestProductModel
import com.component.pharma.model.responses.GetProduct

class CartDiffUtil(
        private val oldList: List<TestProductModel>,
        private val newList: List<TestProductModel>
): DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].phProductId == newList[newItemPosition].phProductId
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].phProductId != newList[newItemPosition].phProductId -> {
                false
            }
            oldList[oldItemPosition].productName != newList[newItemPosition].productName -> {
                false
            }
            oldList[oldItemPosition].price != newList[newItemPosition].price -> {
                false
            }
            oldList[oldItemPosition].apiImagePath != newList[newItemPosition].apiImagePath -> {
                false
            }
            oldList[oldItemPosition].description != newList[newItemPosition].description -> {
                false
            }
//            oldList[oldItemPosition].quantity != newList[newItemPosition].quantity -> {
//                false
//            }
            else -> true
        }
    }
}
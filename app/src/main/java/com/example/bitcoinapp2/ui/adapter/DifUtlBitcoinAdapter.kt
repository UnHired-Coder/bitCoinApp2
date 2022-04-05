package com.example.bitcoinapp2.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.bitcoinapp2.models.BitCoin

class DifUtlBitcoinAdapter(
    private val newList: List<BitCoin>,
    private val oldList: List<BitCoin>
) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]

    override fun areContentsTheSame(oldP: Int, newP: Int): Boolean {
        val oldItem: BitCoin = oldList[oldP]
        val newItem: BitCoin = newList[newP]

        return when {
            (oldItem.x.hash != newItem.x.hash) -> false
            else -> true
        }
    }
}
package com.example.bitcoinapp2.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bitcoinapp2.getValueAmount
import com.example.bitcoinapp2.databinding.LayoutTransactionItemBinding
import com.example.bitcoinapp2.models.BitCoin

class BitCoinsAdapter :
    RecyclerView.Adapter<BitCoinsAdapter.BitCoinViewHolder>() {
    private var bitCoinTransactionsList = listOf<BitCoin>()

    fun updateList(newList: List<BitCoin>) {
        val difUtil = DifUtlBitcoinAdapter(newList, bitCoinTransactionsList)
        val difUtilResult = DiffUtil.calculateDiff(difUtil)
        bitCoinTransactionsList = newList
        difUtilResult.dispatchUpdatesTo(this)
    }

    inner class BitCoinViewHolder(view: LayoutTransactionItemBinding) :
        RecyclerView.ViewHolder(view.root) {
        val hash: TextView = view.tvTrnxHash
        val amount: TextView = view.tvTrnxAmount
        val date: TextView = view.tvTrnxDate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BitCoinViewHolder {
        val binding =
            LayoutTransactionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BitCoinViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BitCoinViewHolder, position: Int) {
        val item = bitCoinTransactionsList[position]
        holder.hash.setTransaction(item.x.hash)
        holder.amount.setAmount(item.getValueAmount())
        holder.date.setDate(item.x.time)
    }

    override fun getItemCount(): Int {
        return bitCoinTransactionsList.size
    }
}

// create a repo, share link
// share apk link
// share via email
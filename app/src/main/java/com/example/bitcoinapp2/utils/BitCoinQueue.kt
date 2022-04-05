package com.example.bitcoinapp2.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bitcoinapp2.valueGreaterThan100
import com.example.bitcoinapp2.models.BitCoin
import java.util.*


class BitCoinQueue() {
    // This can be a template class
    companion object {
        const val MAX_QUEUE_SIZE = 5
    }

    private val liveBitCoinsList: MutableLiveData<List<BitCoin>> = MutableLiveData()
    private val queue: Queue<BitCoin> = LinkedList<BitCoin>(listOf())

    fun addBitCoin(bitCoin: BitCoin) {
        // logic to maintain the 5 latest bitcoin transactions
        if (!bitCoin.valueGreaterThan100())
            return // value less that $100

        // synchronized
        queue.add(bitCoin)
        if (queue.size > MAX_QUEUE_SIZE)
            queue.remove(queue.first())
        liveBitCoinsList.postValue(queue.asIterable().toList())
    }

    fun getBitCoins(): List<BitCoin> {
        return queue.asIterable().toList()
    }

    fun clearBitCoinQueue() {
        queue.clear()
        liveBitCoinsList.postValue(queue.asIterable().toList())
    }

    fun getObservableList(): LiveData<List<BitCoin>> {
        return liveBitCoinsList
    }
}

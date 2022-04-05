package com.example.bitcoinapp2
import com.example.bitcoinapp2.models.BitCoin

// 1 Satoshi -> 0.00000001 bitcoin
// 1 bitcoin -> 46630.55

val cache: MutableMap<Long, Double> = mutableMapOf()
fun Long.satoshiToDollars(): Double {
    if (cache.containsKey(this))
        return cache[this]!!
    val result = (this * 0.00000001 * 46630.55)
    cache[this] = result
    return result
}

fun BitCoin.valueGreaterThan100(): Boolean {
    return this.getValueAmount() > 100.0
}

fun BitCoin.getValueAmount(): Double {
    var totalValue = 0.0
    this.x.inputs.forEach { totalValue += it.prevOut.value.satoshiToDollars() }
    return totalValue
}

package com.example.bitcoinapp2.models

import com.google.gson.annotations.SerializedName

data class BitCoin(
    val op: String,
    val x: X
)

data class X(
    @SerializedName("lock_time")
    val lockTime: Long,

    val ver: Long,
    val size: Long,
    val inputs: List<Input>,
    val time: Long,

    @SerializedName("tx_index")
    val txIndex: Long,

    @SerializedName("vin_sz")
    val vinSz: Long,

    val hash: String,

    @SerializedName("vout_sz")
    val voutSz: Long,

    @SerializedName("relayed_by")
    val relayedBy: String,

    val out: List<Out>
)

data class Input(
    val sequence: Long,

    @SerializedName("prev_out")
    val prevOut: Out,

    val script: String
)

data class Out(
    val spent: Boolean,

    @SerializedName("tx_index")
    val txIndex: Long,

    val type: Long,
    val addr: String,
    val value: Long,
    val n: Long,
    val script: String
)

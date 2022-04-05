package com.example.bitcoinapp2.ui.adapter

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SetTextI18n")
@BindingAdapter("transaction")
fun TextView.setTransaction(value: String) {
    this.text = " Trx: $value"
}

@SuppressLint("SetTextI18n")
@BindingAdapter("amount")
fun TextView.setAmount(value: Double) {
    this.text = " Amount: $${(value * 100).toLong() / 100.0}"
}

@SuppressLint("SetTextI18n")
@BindingAdapter("transaction")
fun TextView.setDate(value: Long) {
    this.text = " Date: ${value.toDDmmYY()}"
}

fun Long.toDDmmYY(): String {
    val format = "yyyy-MM-dd HH:mm:ss"
    return SimpleDateFormat(format, Locale.ENGLISH).format(Date(this))
}


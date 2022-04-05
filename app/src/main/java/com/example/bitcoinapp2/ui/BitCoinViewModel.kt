package com.example.bitcoinapp2.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bitcoinapp2.R
import com.example.bitcoinapp2.utils.BitCoinQueue
import com.google.gson.Gson
import okhttp3.*
import okio.ByteString
import com.example.bitcoinapp2.models.BitCoin
import java.lang.Exception


class BitCoinViewModel : ViewModel() {
    private var server: OkHttpClient = OkHttpClient()
    private val bitCoinQueue: BitCoinQueue = BitCoinQueue()
    private val connectionStatus: MutableLiveData<Int> = MutableLiveData()
    private val connected: MutableLiveData<Boolean> = MutableLiveData(false)

    fun addBitCoin(bitCoin: BitCoin) = bitCoinQueue.addBitCoin(bitCoin)
    fun clearTransactions() = bitCoinQueue.clearBitCoinQueue()
    fun getBitCoinTransactions(): LiveData<List<BitCoin>> = bitCoinQueue.getObservableList()

    fun connectToServer() {
        if (connected.value == true)
            return
        connected.postValue(true)
        val request = buildRequest()
        val listener = BitcoinWebSocket()
        server.newWebSocket(request, listener)
        server.dispatcher.executorService
    }

    fun getConnectionLiveData(): LiveData<Int> {
        return connectionStatus
    }

    fun getConnectedLiveData(): LiveData<Boolean> {
        return connected
    }

    private fun buildRequest(): Request =
        Request.Builder().url("wss://ws.blockchain.info/inv").build()

    inner class BitcoinWebSocket : WebSocketListener() {
        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            connectionStatus.postValue(ConnectionStatus.disconnected)
            connected.postValue(false)
            super.onClosed(webSocket, code, reason)
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            connectionStatus.postValue(ConnectionStatus.unknown)
            connected.postValue(false)
            super.onClosing(webSocket, code, reason)
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            connectionStatus.postValue(ConnectionStatus.disconnected)
            connected.postValue(false)
            super.onFailure(webSocket, t, response)
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            connectionStatus.postValue(ConnectionStatus.connected)
            Log.d(MainActivity.TAG_RESPONSE, text)
            try {
                val bitCoin = Gson().fromJson(text, BitCoin::class.java)
                addBitCoin(bitCoin)
            } catch (e: Exception) {
                Log.d(MainActivity.TAG_RESPONSE, e.message.toString())
            }
            super.onMessage(webSocket, text)
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            connectionStatus.postValue(ConnectionStatus.connected)
            super.onMessage(webSocket, bytes)
        }

        override fun onOpen(webSocket: WebSocket, response: Response) {
            connectionStatus.postValue(ConnectionStatus.connecting)
            webSocket.send(subscribeRequest)
            super.onOpen(webSocket, response)
        }
    }

    companion object {
        const val subscribeRequest = "{\n" +
                "  \"op\": \"unconfirmed_sub\"\n" +
                "}"
    }

    object ConnectionStatus {
        const val connected = R.string.connection_status_connected
        const val disconnected = R.string.connection_status_disconnected
        const val connecting = R.string.connection_status_connecting
        const val unknown = R.string.connection_status_unknown
    }
}


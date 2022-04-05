package com.example.bitcoinapp2.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bitcoinapp2.R
import com.example.bitcoinapp2.databinding.ActivityMainBinding
import com.example.bitcoinapp2.ui.adapter.BitCoinsAdapter

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: BitCoinViewModel

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(BitCoinViewModel::class.java)

        recyclerView = binding.rvBitcoinTransaction.apply {
            adapter = BitCoinsAdapter()
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        viewModel.apply {
            getConnectionLiveData().observe(this@MainActivity) {
                binding.tvConnectionStatus.text = getString(it)
            }
            getConnectedLiveData().observe(this@MainActivity) { connected ->
                binding.btnStartConnection.text =
                    getString(if (connected) R.string.connection_status_connected else R.string.start_connection)
            }
            getBitCoinTransactions().observe(this@MainActivity) {
                (recyclerView.adapter as BitCoinsAdapter).updateList(it)
            }
        }

        binding.apply {
            btnStartConnection.setOnClickListener {
                viewModel.connectToServer()
            }
            btnClearTransactions.setOnClickListener {
                viewModel.clearTransactions()
            }
        }
    }

    companion object {
        const val TAG_RESPONSE = "server_response"
    }
}

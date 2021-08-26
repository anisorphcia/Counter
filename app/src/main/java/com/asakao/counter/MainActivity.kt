package com.asakao.counter

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global.putInt
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.asakao.counter.databinding.ActivityMainBinding
import java.util.EnumSet.of

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel

    lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sp = getPreferences(Context.MODE_PRIVATE)
        val countReserved = sp.getInt("count", 0)

        viewModel = ViewModelProvider(this, MainViewModelFactory(countReserved)).get(MainViewModel::class.java)

        binding.plusOneBtn.setOnClickListener {
            viewModel.counter++
            refreshCounter()
        }

        binding.clear.setOnClickListener {
            viewModel.counter = 0
            refreshCounter()
        }

        refreshCounter()
    }

    private fun refreshCounter(){
        binding.infoText.text = viewModel.counter.toString()
    }

    override fun onPause() {
        super.onPause()
        sp.edit{
            putInt("count", viewModel.counter)
        }
    }

    // https://blog.csdn.net/u010356768/article/details/109671555
}
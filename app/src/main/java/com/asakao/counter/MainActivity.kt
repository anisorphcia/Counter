package com.asakao.counter

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global.putInt
import androidx.core.content.edit
import androidx.lifecycle.Observer
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
            viewModel.plusOne()
        }

        binding.clear.setOnClickListener {
            viewModel.clear()
        }

        // 观察数据变化
        viewModel.counter.observe(this, Observer { count ->
            binding.infoText.text = count.toString()
        })

        refreshCounter()

        binding.getUserBtn.setOnClickListener {
            val userId = (0..10000).random().toString()
            viewModel.getUser(userId)
        }

        viewModel.user.observe(this, Observer { user ->
            binding.infoText.text = user.fn
        })
    }

    private fun refreshCounter(){
        binding.infoText.text = viewModel.counter.toString()
    }

    override fun onPause() {
        super.onPause()
        sp.edit{
            putInt("count", viewModel.counter.value ?: 0)
        }
    }

    // https://blog.csdn.net/u010356768/article/details/109671555
}
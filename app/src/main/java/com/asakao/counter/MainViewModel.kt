package com.asakao.counter

import androidx.lifecycle.ViewModel

class MainViewModel(countReserved: Int) : ViewModel() {

    var counter = countReserved

}
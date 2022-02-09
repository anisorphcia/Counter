package com.asakao.counter

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(var fn:String, var ln:String, var age:Int){

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

}

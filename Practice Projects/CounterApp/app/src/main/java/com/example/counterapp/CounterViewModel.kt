package com.example.counterapp

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel

class CounterViewModel : ViewModel() {
    private var _count = mutableIntStateOf(0)

    var count : MutableState<Int> = _count

    fun increment() {
        _count.intValue += 1
    }

    fun decrement() {
        _count.intValue -= 1
    }
}
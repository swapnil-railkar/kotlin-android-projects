package com.example.kotlinprac

fun main() {

    println("Enter your age : ")
    val age : Int = readln().toInt()

    if (age in 18..40) {
        print("You can enter")
    } else {
        print("You cannot enter")
    }
}
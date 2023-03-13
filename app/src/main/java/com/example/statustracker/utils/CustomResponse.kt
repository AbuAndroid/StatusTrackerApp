package com.example.statustracker.utils

sealed class CustomResponse<out V,out E> {
    data class Success<out V>(val data:V):CustomResponse<V,Nothing>()
    data class Failure<out E>(val error:E):CustomResponse<Nothing,E>()
}
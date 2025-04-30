package com.example.billeteravirtual


import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData

class SaldoViewModel : ViewModel() {
    val saldoDisponible = MutableLiveData<Double>().apply {
        value = 7350.0 // Saldo inicial
    }
}
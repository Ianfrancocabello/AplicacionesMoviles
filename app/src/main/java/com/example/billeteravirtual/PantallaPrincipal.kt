package com.example.billeteravirtual

import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import java.text.NumberFormat

class PantallaPrincipal : AppCompatActivity() {

    private lateinit var viewModel: SaldoViewModel
    private val formatoMoneda = NumberFormat.getCurrencyInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_principal)

        // Configuración del ViewModel
        viewModel = ViewModelProvider(this)[SaldoViewModel::class.java]

        val textoSaldo: TextView = findViewById(R.id.textoSaldo)
        val campoMonto: EditText = findViewById(R.id.campoMonto)
        val botonRetirar: Button = findViewById(R.id.botonRetirar)

        // Filtro para solo números y un punto decimal
        campoMonto.filters = arrayOf<InputFilter>(InputFilter { source, _, _, _, _, _ ->
            source.toString().replace(Regex("[^0-9.]"), "")
        })

        // Observador del saldo
        viewModel.saldoDisponible.observe(this) { saldo ->
            textoSaldo.text = "Tu saldo: ${formatoMoneda.format(saldo)}"
        }

        botonRetirar.setOnClickListener {
            val montoTexto = campoMonto.text.toString()
            val monto = montoTexto.toDoubleOrNull()

            when {
                monto == null -> mostrarError("❌ Monto inválido")
                monto <= 0 -> mostrarError("⚠️ El monto debe ser mayor a 0")
                monto > viewModel.saldoDisponible.value!! -> mostrarError("🚫 Saldo insuficiente")
                else -> realizarRetiro(monto)
            }
        }
    }

    private fun realizarRetiro(monto: Double) {
        viewModel.saldoDisponible.value = viewModel.saldoDisponible.value!! - monto
        val intent = Intent(this, PantallaComprobante::class.java).apply {
            putExtra("monto_retirado", monto)
        }
        startActivity(intent)
    }

    private fun mostrarError(mensaje: String) {
        Snackbar.make(
            findViewById(android.R.id.content),
            mensaje,
            Snackbar.LENGTH_LONG
        ).setAction("REINTENTAR") {
            findViewById<EditText>(R.id.campoMonto).apply {
                text.clear()
                requestFocus()
            }
        }.show()
    }
}
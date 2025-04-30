package com.example.billeteravirtual

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.NumberFormat

class PantallaComprobante : AppCompatActivity() {

    private val formatoMoneda = NumberFormat.getCurrencyInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_comprobante)

        val montoRetirado = intent.getDoubleExtra("monto_retirado", 0.0)
        val textoComprobante: TextView = findViewById(R.id.textoComprobante)
        val botonVolver: Button = findViewById(R.id.botonVolver)

        textoComprobante.text = """
            💸 Monto retirado: ${formatoMoneda.format(montoRetirado)}
            ✅ Transacción exitosa
            📅 ${java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(java.util.Date())}
        """.trimIndent()

        botonVolver.setOnClickListener {
            finish() // Cierra esta actividad
        }
    }
}
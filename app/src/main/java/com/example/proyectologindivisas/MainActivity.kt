package com.example.proyectologindivisas

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencia al layout principal
        val mainLayout: ConstraintLayout = findViewById(R.id.main_layout)
        mainLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.background_color))

        // Configuración del login
        val usernameEditText: EditText = findViewById(R.id.username_edit_text)
        val passwordEditText: EditText = findViewById(R.id.password_edit_text)
        val loginButton: Button = findViewById(R.id.login_button)
        val closeAppButton: Button = findViewById(R.id.close_app_button)

        loginButton.setBackgroundColor(ContextCompat.getColor(this, R.color.button_color_login))
        closeAppButton.setBackgroundColor(ContextCompat.getColor(this, R.color.button_color_close_app))

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            if (username == "Jorge" && password == "avalos") {
                Toast.makeText(this, "Login exitoso", Toast.LENGTH_SHORT).show()
                // Mostrar la pantalla de cambio de divisas
                showCurrencyConverter()
            } else {
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
            }
        }

        closeAppButton.setOnClickListener {
            // Cerrar la aplicación
            finishAffinity()
        }
    }

    private fun showCurrencyConverter() {
        setContentView(R.layout.activity_currency_converter)

        val amountEditText: EditText = findViewById(R.id.amount_edit_text)
        val convertButton: Button = findViewById(R.id.convert_button)
        val resultTextView: TextView = findViewById(R.id.result_text_view)
        val spinnerFrom: Spinner = findViewById(R.id.spinner_from)
        val spinnerTo: Spinner = findViewById(R.id.spinner_to)
        val logoutButton: Button = findViewById(R.id.logout_button)
        val closeAppButton: Button = findViewById(R.id.close_app_button)

        convertButton.setBackgroundColor(ContextCompat.getColor(this, R.color.button_color_convert))
        logoutButton.setBackgroundColor(ContextCompat.getColor(this, R.color.button_color_logout))
        closeAppButton.setBackgroundColor(ContextCompat.getColor(this, R.color.button_color_close_app))

        val currencies = arrayOf("USD", "EUR", "PEN", "JPY", "GBP")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        spinnerFrom.adapter = adapter
        spinnerTo.adapter = adapter

        convertButton.setOnClickListener {
            val amount = amountEditText.text.toString().toDoubleOrNull()
            if (amount != null) {
                val fromCurrency = spinnerFrom.selectedItem.toString()
                val toCurrency = spinnerTo.selectedItem.toString()
                val rate = getExchangeRate(fromCurrency, toCurrency)
                val result = amount * rate
                resultTextView.text = "$amount $fromCurrency equivale a $result $toCurrency"
            } else {
                Toast.makeText(this, "Ingrese un monto válido", Toast.LENGTH_SHORT).show()
            }
        }

        logoutButton.setOnClickListener {
            // Volver al login
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        closeAppButton.setOnClickListener {
            // Cerrar la aplicación
            finishAffinity()
        }
    }

    private fun getExchangeRate(from: String, to: String): Double {
        // Aquí puedes implementar la lógica para obtener la tasa de cambio real.
        // Esto puede ser desde una API o usar tasas de cambio fijas.
        // Por simplicidad, se retornan tasas ficticias.
        return when (from to to) {
            "USD" to "EUR" -> 0.85
            "EUR" to "USD" -> 1.18
            "USD" to "PEN" -> 3.8
            "PEN" to "USD" -> 0.26
            "USD" to "JPY" -> 110.0
            else -> 1.0
        }
    }
}

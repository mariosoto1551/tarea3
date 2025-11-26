package com.example.cursosapp.View

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cursosapp.ViewModel.CursosViewModel
import com.example.cursosapp.R
import com.example.cursosapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: CursosViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        // Observar cambios en la información mostrada
        viewModel.informacionMostrada.observe(this) { info ->
            binding.informacion.text = info
        }

        // Observar cambios en la visibilidad
        viewModel.mostrarInformacion.observe(this) { mostrar ->
            binding.informacion.visibility = if (mostrar) View.VISIBLE else View.GONE
        }
    }

    private fun setupListeners() {
        binding.checkAcepto.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                procesarFormulario()
            } else {
                viewModel.limpiarInformacion()
            }
        }
    }

    private fun procesarFormulario() {
        // Capturar datos del formulario
        viewModel.nombre = binding.nombre.text.toString()
        viewModel.carnet = binding.carnet.text.toString()

        // Capturar cursos seleccionados
        viewModel.cursosSeleccionados.clear()
        if (binding.excel.isChecked) viewModel.cursosSeleccionados.add("Excel")
        if (binding.powerBi.isChecked) viewModel.cursosSeleccionados.add("PowerBI")
        if (binding.pytho.isChecked) viewModel.cursosSeleccionados.add("Python")

        // Capturar nivel seleccionado
        viewModel.nivel = when (binding.nivel.checkedRadioButtonId) {
            R.id.basico -> "Basico"
            R.id.intermedio -> "Intermedio"
            R.id.avanzado -> "Avanzado"
            else -> ""
        }

        // Capturar tipo de pago
        viewModel.tipoPago = when (binding.tipoPago.checkedRadioButtonId) {
            R.id.efectivo -> "Efectivo"
            R.id.qr -> "QR"
            R.id.tarjeta -> "Tarjeta"
            else -> ""
        }

        // Generar información (esto actualizará automáticamente la UI a través del LiveData)
        viewModel.generarInformacion()
    }
}
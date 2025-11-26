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


        if (viewModel.informacionMostrada.isNotEmpty()) {
            binding.informacion.text = viewModel.informacionMostrada
            binding.informacion.visibility = View.VISIBLE
        } else {
            binding.informacion.visibility = View.GONE
        }


        binding.checkAcepto.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                procesarFormulario()
            } else {
                limpiarInformacion()
            }
        }
    }

    private fun procesarFormulario() {

        viewModel.nombre = binding.nombre.text.toString()
        viewModel.carnet = binding.carnet.text.toString()


        viewModel.cursosSeleccionados.clear()
        if (binding.excel.isChecked) viewModel.cursosSeleccionados.add("Excel")
        if (binding.powerBi.isChecked) viewModel.cursosSeleccionados.add("PowerBI")
        if (binding.pytho.isChecked) viewModel.cursosSeleccionados.add("Python")


        viewModel.nivel = when (binding.nivel.checkedRadioButtonId) {
            R.id.basico -> "Basico"
            R.id.intermedio -> "Intermedio"
            R.id.avanzado -> "Avanzado"
            else -> ""
        }


        viewModel.tipoPago = when (binding.tipoPago.checkedRadioButtonId) {
            R.id.efectivo -> "Efectivo"
            R.id.qr -> "QR"
            R.id.tarjeta -> "Tarjeta"
            else -> ""
        }


        val info = viewModel.generarInformacion()
        binding.informacion.text = info
        binding.informacion.visibility = View.VISIBLE
    }

    private fun limpiarInformacion() {
        viewModel.informacionMostrada = ""
        binding.informacion.text = ""
        binding.informacion.visibility = View.GONE
    }
}
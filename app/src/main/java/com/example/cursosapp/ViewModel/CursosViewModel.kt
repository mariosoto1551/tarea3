package com.example.cursosapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CursosViewModel : ViewModel() {
    var nombre: String = ""
    var carnet: String = ""
    var cursosSeleccionados: MutableList<String> = mutableListOf()
    var nivel: String = ""
    var tipoPago: String = ""
    private var totalCosto: Int = 0

    // LiveData privado (mutable) y público (inmutable)
    private val _informacionMostrada = MutableLiveData<String>()
    val informacionMostrada: LiveData<String> = _informacionMostrada

    private val _mostrarInformacion = MutableLiveData<Boolean>(false)
    val mostrarInformacion: LiveData<Boolean> = _mostrarInformacion

    private fun calcularTotal() {
        totalCosto = 0

        // Sumar precios de cursos
        if (cursosSeleccionados.contains("Excel")) totalCosto += 50
        if (cursosSeleccionados.contains("PowerBI")) totalCosto += 70
        if (cursosSeleccionados.contains("Python")) totalCosto += 110

        // Sumar costo adicional por nivel
        when (nivel) {
            "Intermedio" -> totalCosto += 20
            "Avanzado" -> totalCosto += 50
        }
    }

    fun generarInformacion() {
        calcularTotal()
        val cursos = cursosSeleccionados.joinToString(", ")
        val info = """
            Nombre: $nombre
            Carnet: $carnet
            Cursos: $cursos
            Nivel: $nivel
            Tipo de Pago: $tipoPago
            Total: $$totalCosto
        """.trimIndent()

        _informacionMostrada.value = info
        _mostrarInformacion.value = true
    }

    fun limpiarInformacion() {
        _informacionMostrada.value = ""
        _mostrarInformacion.value = false
    }
}
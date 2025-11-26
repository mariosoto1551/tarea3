package com.example.cursosapp.ViewModel

import androidx.lifecycle.ViewModel

class CursosViewModel : ViewModel() {
    var nombre: String = ""
    var carnet: String = ""
    var cursosSeleccionados: MutableList<String> = mutableListOf()
    var nivel: String = ""
    var tipoPago: String = ""
    var totalCosto: Int = 0
    var informacionMostrada: String = ""

    fun calcularTotal() {
        totalCosto = 0


        if (cursosSeleccionados.contains("Excel")) totalCosto += 50
        if (cursosSeleccionados.contains("PowerBI")) totalCosto += 70
        if (cursosSeleccionados.contains("Python")) totalCosto += 110


        when (nivel) {
            "Intermedio" -> totalCosto += 20
            "Avanzado" -> totalCosto += 50
        }
    }

    fun generarInformacion(): String {
        calcularTotal()
        val cursos = cursosSeleccionados.joinToString(", ")
        informacionMostrada = """
            Nombre: $nombre
            Carnet: $carnet
            Cursos: $cursos
            Nivel: $nivel
            Tipo de Pago: $tipoPago
            Total: $$totalCosto
        """.trimIndent()
        return informacionMostrada
    }
}
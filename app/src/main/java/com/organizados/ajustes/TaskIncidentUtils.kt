package com.organizados.ajustes

import androidx.compose.ui.graphics.Color
import java.util.Locale

fun getPriorityColor(priority: String): Color {
    return when (priority) {
        "Urgente" -> Color(0xFFEF5350) // Rojo
        "Alta" -> Color(0xFFFF9800) // Naranja
        "Media" -> Color(0xFF2196F3) // Azul
        "Baja" -> Color(0xFF4CAF50) // Verde
        else -> Color.Gray
    }
}

fun formatDateHeader(dateKey: String): String {
    return when (dateKey) {
        "today" -> "Hoy"
        else -> dateKey.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    }
}


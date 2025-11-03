package com.organizados.ajustes

data class Task(
    val id: String,
    val title: String,
    val description: String,
    val sender: String,
    val recipientType: String, // "area" o "worker"
    val recipient: String, // Nombre del área o trabajador
    val priority: String,
    val startDate: String,
    val dueDate: String,
    val status: String, // "pendiente" o "completada"
    val dateKey: String // Clave para agrupar por fecha
)

data class Incident(
    val id: String,
    val title: String,
    val description: String,
    val sender: String,
    val recipientType: String, // "area" o "worker"
    val recipient: String, // Nombre del área o trabajador
    val priority: String,
    val startDate: String,
    val dueDate: String,
    val status: String, // "pendiente" o "completada"
    val dateKey: String // Clave para agrupar por fecha
)

package com.organizados.ajustes

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

/**
 * Repositorio singleton para almacenar tareas e incidencias.
 * Mantiene listas mutables que se pueden observar desde las pantallas.
 */
object TaskIncidentRepository {
    private val _tasks = mutableStateListOf<Task>()
    private val _incidents = mutableStateListOf<Incident>()
    
    val tasks: SnapshotStateList<Task> = _tasks
    val incidents: SnapshotStateList<Incident> = _incidents
    
    fun addTask(task: Task) {
        _tasks.add(task)
    }
    
    fun addIncident(incident: Incident) {
        _incidents.add(incident)
    }
    
    fun removeTask(taskId: String) {
        _tasks.removeAll { it.id == taskId }
    }
    
    fun removeIncident(incidentId: String) {
        _incidents.removeAll { it.id == incidentId }
    }
    
    fun updateTaskStatus(taskId: String, status: String) {
        val index = _tasks.indexOfFirst { it.id == taskId }
        if (index >= 0) {
            val task = _tasks[index]
            _tasks[index] = task.copy(status = status)
        }
    }
    
    fun updateIncidentStatus(incidentId: String, status: String) {
        val index = _incidents.indexOfFirst { it.id == incidentId }
        if (index >= 0) {
            val incident = _incidents[index]
            _incidents[index] = incident.copy(status = status)
        }
    }
    
    fun clear() {
        _tasks.clear()
        _incidents.clear()
    }
}

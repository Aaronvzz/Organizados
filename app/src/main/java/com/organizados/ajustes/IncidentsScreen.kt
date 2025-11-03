package com.organizados.ajustes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncidentsScreen() {
    // TODO: Obtener incidencias del repositorio o ViewModel
    // Datos de ejemplo para mostrar la funcionalidad
    val sampleIncidents = remember {
        listOf(
            Incident(
                id = "1",
                title = "Problema con servidor",
                description = "El servidor principal está presentando fallas intermitentes.",
                sender = "Admin 1",
                recipientType = "area",
                recipient = "Sistemas",
                priority = "Urgente",
                startDate = "10/09/2024 10:00",
                dueDate = "10/09/2024 18:00",
                status = "pendiente",
                dateKey = "today"
            )
        )
    }

    var expandedIncidentId by remember { mutableStateOf<String?>(null) }
    var showProblemReportDialog by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Incidencias") }
            )
        }
    ) { padding ->
        if (sampleIncidents.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No hay incidencias actualmente",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            val groupedIncidents = sampleIncidents.groupBy { it.dateKey }
            
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                groupedIncidents.forEach { (dateKey, incidents) ->
                    item {
                        // Header de fecha
                        Text(
                            text = formatDateHeader(dateKey),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    
                    items(incidents) { incident ->
                        IncidentCard(
                            incident = incident,
                            isExpanded = expandedIncidentId == incident.id,
                            onExpandClick = {
                                expandedIncidentId = if (expandedIncidentId == incident.id) null else incident.id
                            },
                            onReportCompleted = {
                                // TODO: Lógica para marcar como completada
                            },
                            onReportProblem = {
                                showProblemReportDialog = incident.id
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }

    // Dialog para reportar problema
    if (showProblemReportDialog != null) {
        var problemDescription by remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = { showProblemReportDialog = null },
            title = { Text("Reportar Problema") },
            text = {
                Column {
                    OutlinedTextField(
                        value = problemDescription,
                        onValueChange = { problemDescription = it },
                        placeholder = { Text("Describa el problema...") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 5,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedIndicatorColor = Color(0xFF3EC8AC),
                            unfocusedIndicatorColor = Color.Gray,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black
                        )
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        // TODO: Enviar reporte al emisor
                        showProblemReportDialog = null
                    },
                    enabled = problemDescription.isNotBlank()
                ) {
                    Text("Enviar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showProblemReportDialog = null }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun IncidentCard(
    incident: Incident,
    isExpanded: Boolean,
    onExpandClick: () -> Unit,
    onReportCompleted: () -> Unit,
    onReportProblem: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onExpandClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            if (!isExpanded) {
                // Vista colapsada
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = incident.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Emisor: ${incident.sender}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Prioridad: ",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = incident.priority,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = getPriorityColor(incident.priority)
                        )
                    }
                    Text(
                        text = "Para: ${incident.recipient}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                // Indicador de estado
                if (incident.status == "completada") {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "✓ Completada",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF4CAF50),
                        fontWeight = FontWeight.Bold
                    )
                }
            } else {
                // Vista expandida
                Text(
                    text = incident.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Text(
                    text = "Inicio: ${incident.startDate}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 8.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Text(
                    text = "Límite: ${incident.dueDate}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 8.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Row(
                    modifier = Modifier.padding(bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Prioridad: ",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = incident.priority,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = getPriorityColor(incident.priority)
                    )
                }
                
                Text(
                    text = "Descripción: ${incident.description}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 16.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (incident.status != "completada") {
                        OutlinedButton(
                            onClick = onReportProblem,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Reportar Problema")
                        }
                        
                        Button(
                            onClick = onReportCompleted,
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF4CAF50)
                            )
                        ) {
                            Text("REPORTAR COMPLETADA")
                        }
                    } else {
                        Text(
                            text = "✓ Esta incidencia ya fue completada",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF4CAF50),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}


// Modelo de datos para Incidencia actualizado
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

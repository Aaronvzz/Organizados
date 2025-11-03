package com.organizados.ajustes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskIncidentScreen(
    availableAreas: List<String> = listOf("Logistica", "Sistemas", "Ventas", "Todos los empleados"), // TODO: Obtener de repositorio
    availableWorkers: List<String> = listOf("Trabajador 1", "Trabajador 2", "Trabajador 3"), // TODO: Obtener de repositorio
    onNavigateToTasks: () -> Unit,
    onNavigateToIncidents: () -> Unit
) {
    var selectedType by remember { mutableStateOf("Tarea") } // "Tarea" o "Incidencia"
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var recipientType by remember { mutableStateOf("area") } // "area" o "worker"
    var selectedRecipient by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf("Baja") }
    var startDate by remember { mutableStateOf<String?>(null) }
    var dueDate by remember { mutableStateOf<String?>(null) }

    var showStartDatePicker by remember { mutableStateOf(false) }
    var showDueDatePicker by remember { mutableStateOf(false) }
    
    val dateFormatter = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agregar Tarea/Incidencia") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Selector de tipo: Tarea o Incidencia
                Text(
                    text = "Tipo",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = selectedType == "Tarea",
                        onClick = { selectedType = "Tarea" },
                        label = { Text("Tarea") },
                        modifier = Modifier.weight(1f)
                    )
                    FilterChip(
                        selected = selectedType == "Incidencia",
                        onClick = { selectedType = "Incidencia" },
                        label = { Text("Incidencia") },
                        modifier = Modifier.weight(1f)
                    )
                }

                // Campo Título
                Text(
                    text = "Título",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    placeholder = { Text("Ingrese el título") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color(0xFF3EC8AC),
                        unfocusedIndicatorColor = Color.Gray,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedPlaceholderColor = Color.Gray,
                        unfocusedPlaceholderColor = Color.Gray
                    )
                )

                // Campo Descripción
                Text(
                    text = "Descripción",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    placeholder = { Text("Ingrese la descripción") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    maxLines = 5,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color(0xFF3EC8AC),
                        unfocusedIndicatorColor = Color.Gray,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedPlaceholderColor = Color.Gray,
                        unfocusedPlaceholderColor = Color.Gray
                    )
                )

                // Campo Destinatario (Área o Trabajador)
                Text(
                    text = "Dirigir a",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                // Selector de tipo: Área o Trabajador
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = recipientType == "area",
                        onClick = { 
                            recipientType = "area"
                            selectedRecipient = "" // Limpiar selección al cambiar tipo
                        },
                        label = { Text("Área") },
                        modifier = Modifier.weight(1f)
                    )
                    FilterChip(
                        selected = recipientType == "worker",
                        onClick = { 
                            recipientType = "worker"
                            selectedRecipient = "" // Limpiar selección al cambiar tipo
                        },
                        label = { Text("Trabajador") },
                        modifier = Modifier.weight(1f)
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Dropdown según el tipo seleccionado
                var expanded by remember { mutableStateOf(false) }
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = selectedRecipient,
                        onValueChange = {},
                        readOnly = true,
                        placeholder = { 
                            Text(
                                if (recipientType == "area") "Seleccione un área" else "Seleccione un trabajador"
                            ) 
                        },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier.menuAnchor(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedIndicatorColor = Color(0xFF3EC8AC),
                            unfocusedIndicatorColor = Color.Gray,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedPlaceholderColor = Color.Gray,
                            unfocusedPlaceholderColor = Color.Gray
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        val options = if (recipientType == "area") availableAreas else availableWorkers
                        options.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    selectedRecipient = option
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                // Campo Prioridad
                Text(
                    text = "Prioridad",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                var priorityExpanded by remember { mutableStateOf(false) }
                val priorities = listOf("Baja", "Media", "Alta", "Urgente")
                ExposedDropdownMenuBox(
                    expanded = priorityExpanded,
                    onExpandedChange = { priorityExpanded = !priorityExpanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = priority,
                        onValueChange = {},
                        readOnly = true,
                        placeholder = { Text("Seleccione la prioridad") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = priorityExpanded) },
                        modifier = Modifier.menuAnchor(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedIndicatorColor = Color(0xFF3EC8AC),
                            unfocusedIndicatorColor = Color.Gray,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedPlaceholderColor = Color.Gray,
                            unfocusedPlaceholderColor = Color.Gray
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = priorityExpanded,
                        onDismissRequest = { priorityExpanded = false }
                    ) {
                        priorities.forEach { pri ->
                            DropdownMenuItem(
                                text = { Text(pri) },
                                onClick = {
                                    priority = pri
                                    priorityExpanded = false
                                }
                            )
                        }
                    }
                }

                // Campo Fecha de inicio
                Text(
                    text = "Fecha de inicio",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                OutlinedTextField(
                    value = startDate ?: "",
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text("Ingrese la fecha de inicio (dd/MM/yyyy)") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showStartDatePicker = true },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color(0xFF3EC8AC),
                        unfocusedIndicatorColor = Color.Gray,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedPlaceholderColor = Color.Gray,
                        unfocusedPlaceholderColor = Color.Gray
                    ),
                    trailingIcon = {
                        IconButton(onClick = { showStartDatePicker = true }) {
                            Icon(Icons.Default.DateRange, contentDescription = "Fecha")
                        }
                    }
                )
                
                // DatePicker simplificado para fecha de inicio
                if (showStartDatePicker) {
                    AlertDialog(
                        onDismissRequest = { showStartDatePicker = false },
                        title = { Text("Seleccionar fecha de inicio") },
                        text = {
                            Column {
                                var day by remember { mutableStateOf("") }
                                var month by remember { mutableStateOf("") }
                                var year by remember { mutableStateOf("") }
                                
                                OutlinedTextField(
                                    value = day,
                                    onValueChange = { day = it },
                                    label = { Text("Día") },
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                OutlinedTextField(
                                    value = month,
                                    onValueChange = { month = it },
                                    label = { Text("Mes") },
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                OutlinedTextField(
                                    value = year,
                                    onValueChange = { year = it },
                                    label = { Text("Año") },
                                    modifier = Modifier.fillMaxWidth()
                                )
                                
                                Spacer(modifier = Modifier.height(16.dp))
                                
                                Button(
                                    onClick = {
                                        if (day.isNotBlank() && month.isNotBlank() && year.isNotBlank()) {
                                            startDate = "$day/$month/$year"
                                            showStartDatePicker = false
                                        }
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text("Confirmar")
                                }
                            }
                        },
                        confirmButton = {}
                    )
                }

                // Campo Fecha límite
                Text(
                    text = "Fecha límite",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                OutlinedTextField(
                    value = dueDate ?: "",
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text("Ingrese la fecha límite (dd/MM/yyyy)") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showDueDatePicker = true },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color(0xFF3EC8AC),
                        unfocusedIndicatorColor = Color.Gray,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedPlaceholderColor = Color.Gray,
                        unfocusedPlaceholderColor = Color.Gray
                    ),
                    trailingIcon = {
                        IconButton(onClick = { showDueDatePicker = true }) {
                            Icon(Icons.Default.DateRange, contentDescription = "Fecha")
                        }
                    }
                )
                
                // DatePicker simplificado para fecha límite
                if (showDueDatePicker) {
                    AlertDialog(
                        onDismissRequest = { showDueDatePicker = false },
                        title = { Text("Seleccionar fecha límite") },
                        text = {
                            Column {
                                var day by remember { mutableStateOf("") }
                                var month by remember { mutableStateOf("") }
                                var year by remember { mutableStateOf("") }
                                
                                OutlinedTextField(
                                    value = day,
                                    onValueChange = { day = it },
                                    label = { Text("Día") },
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                OutlinedTextField(
                                    value = month,
                                    onValueChange = { month = it },
                                    label = { Text("Mes") },
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                OutlinedTextField(
                                    value = year,
                                    onValueChange = { year = it },
                                    label = { Text("Año") },
                                    modifier = Modifier.fillMaxWidth()
                                )
                                
                                Spacer(modifier = Modifier.height(16.dp))
                                
                                Button(
                                    onClick = {
                                        if (day.isNotBlank() && month.isNotBlank() && year.isNotBlank()) {
                                            dueDate = "$day/$month/$year"
                                            showDueDatePicker = false
                                        }
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text("Confirmar")
                                }
                            }
                        },
                        confirmButton = {}
                    )
                }
            }

            // Botones en la parte inferior
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Botón Borrar
                OutlinedButton(
                    onClick = {
                        title = ""
                        description = ""
                        recipientType = "area"
                        selectedRecipient = ""
                        priority = "Baja"
                        startDate = null
                        dueDate = null
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Text("Borrar")
                }

                // Botón Enviar
                Button(
                    onClick = {
                        // TODO: Guardar la tarea/incidencia
                        if (selectedType == "Tarea") {
                            onNavigateToTasks()
                        } else {
                            onNavigateToIncidents()
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    enabled = title.isNotBlank() && 
                             description.isNotBlank() && 
                             selectedRecipient.isNotBlank() &&
                             startDate != null &&
                             dueDate != null
                ) {
                    Text("Enviar")
                }
            }
        }
    }
}


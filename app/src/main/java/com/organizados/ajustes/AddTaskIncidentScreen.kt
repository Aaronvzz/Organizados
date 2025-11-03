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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskIncidentScreen(
    availableAreas: List<String> = listOf("Logistica", "Sistemas", "Ventas", "Todos los empleados"), // TODO: Obtener de repositorio
    availableWorkers: List<String> = listOf("Juan Pérez", "María García", "Carlos Rodríguez", "Ana López", "Pedro Martínez", "Laura Sánchez"), // TODO: Obtener de repositorio
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
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
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
                
                // Campo según el tipo seleccionado
                if (recipientType == "area") {
                    // Dropdown para Áreas
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
                            placeholder = { Text("Seleccione un área") },
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
                            availableAreas.forEach { option ->
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
                } else {
                    // Autocomplete con filtrado para Trabajadores
                    var expanded by remember { mutableStateOf(false) }
                    var filteredWorkers by remember { mutableStateOf(availableWorkers) }
                    
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = {},
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = selectedRecipient,
                            onValueChange = { query ->
                                selectedRecipient = query
                                filteredWorkers = availableWorkers.filter { 
                                    it.contains(query, ignoreCase = true) 
                                }
                                expanded = query.isNotBlank() && filteredWorkers.isNotEmpty()
                            },
                            placeholder = { Text("Busque un trabajador") },
                            trailingIcon = {
                                IconButton(onClick = { expanded = !expanded }) {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                                }
                            },
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
                            filteredWorkers.forEach { option ->
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
                    placeholder = { Text("dd/MM/yyyy HH:mm") },
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
                    val calendar = remember { Calendar.getInstance() }
                    var day by remember { mutableStateOf(calendar.get(Calendar.DAY_OF_MONTH).toString()) }
                    var month by remember { mutableStateOf((calendar.get(Calendar.MONTH) + 1).toString()) }
                    var year by remember { mutableStateOf(calendar.get(Calendar.YEAR).toString()) }
                    var hour by remember { mutableStateOf(calendar.get(Calendar.HOUR_OF_DAY).toString()) }
                    var minute by remember { mutableStateOf(calendar.get(Calendar.MINUTE).toString()) }
                    
                    val isValidDate = remember(day, month, year, hour, minute) {
                        try {
                            val selectedDay = day.toIntOrNull()
                            val selectedMonth = month.toIntOrNull()
                            val selectedYear = year.toIntOrNull()
                            val selectedHour = hour.toIntOrNull()
                            val selectedMinute = minute.toIntOrNull()
                            
                            if (selectedDay == null || selectedMonth == null || selectedYear == null ||
                                selectedHour == null || selectedMinute == null) {
                                false
                            } else {
                                val selectedCal = Calendar.getInstance().apply {
                                    set(selectedYear, selectedMonth - 1, selectedDay, selectedHour, selectedMinute)
                                }
                                selectedCal >= calendar
                            }
                        } catch (e: Exception) {
                            false
                        }
                    }
                    
                    AlertDialog(
                        onDismissRequest = { showStartDatePicker = false },
                        title = { Text("Seleccionar fecha y hora de inicio") },
                        text = {
                            Column {
                                
                                OutlinedTextField(
                                    value = day,
                                    onValueChange = { if (it.all { char -> char.isDigit() }) day = it.take(2) },
                                    label = { Text("Día (DD)") },
                                    modifier = Modifier.fillMaxWidth(),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    colors = TextFieldDefaults.colors(
                                        focusedContainerColor = Color.White,
                                        unfocusedContainerColor = Color.White,
                                        focusedIndicatorColor = Color(0xFF3EC8AC),
                                        unfocusedIndicatorColor = Color.Gray,
                                        focusedTextColor = Color.Black,
                                        unfocusedTextColor = Color.Black
                                    )
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                OutlinedTextField(
                                    value = month,
                                    onValueChange = { if (it.all { char -> char.isDigit() }) month = it.take(2) },
                                    label = { Text("Mes (MM)") },
                                    modifier = Modifier.fillMaxWidth(),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    colors = TextFieldDefaults.colors(
                                        focusedContainerColor = Color.White,
                                        unfocusedContainerColor = Color.White,
                                        focusedIndicatorColor = Color(0xFF3EC8AC),
                                        unfocusedIndicatorColor = Color.Gray,
                                        focusedTextColor = Color.Black,
                                        unfocusedTextColor = Color.Black
                                    )
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                OutlinedTextField(
                                    value = year,
                                    onValueChange = { if (it.all { char -> char.isDigit() }) year = it.take(4) },
                                    label = { Text("Año (YYYY)") },
                                    modifier = Modifier.fillMaxWidth(),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    colors = TextFieldDefaults.colors(
                                        focusedContainerColor = Color.White,
                                        unfocusedContainerColor = Color.White,
                                        focusedIndicatorColor = Color(0xFF3EC8AC),
                                        unfocusedIndicatorColor = Color.Gray,
                                        focusedTextColor = Color.Black,
                                        unfocusedTextColor = Color.Black
                                    )
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                OutlinedTextField(
                                    value = hour,
                                    onValueChange = { if (it.all { char -> char.isDigit() }) hour = it.take(2) },
                                    label = { Text("Hora (HH)") },
                                    modifier = Modifier.fillMaxWidth(),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    colors = TextFieldDefaults.colors(
                                        focusedContainerColor = Color.White,
                                        unfocusedContainerColor = Color.White,
                                        focusedIndicatorColor = Color(0xFF3EC8AC),
                                        unfocusedIndicatorColor = Color.Gray,
                                        focusedTextColor = Color.Black,
                                        unfocusedTextColor = Color.Black
                                    )
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                OutlinedTextField(
                                    value = minute,
                                    onValueChange = { if (it.all { char -> char.isDigit() }) minute = it.take(2) },
                                    label = { Text("Minutos (MM)") },
                                    modifier = Modifier.fillMaxWidth(),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    colors = TextFieldDefaults.colors(
                                        focusedContainerColor = Color.White,
                                        unfocusedContainerColor = Color.White,
                                        focusedIndicatorColor = Color(0xFF3EC8AC),
                                        unfocusedIndicatorColor = Color.Gray,
                                        focusedTextColor = Color.Black,
                                        unfocusedTextColor = Color.Black
                                    )
                                )
                                
                                Spacer(modifier = Modifier.height(16.dp))
                                
                                if (!isValidDate && day.isNotBlank() && month.isNotBlank() && year.isNotBlank() && hour.isNotBlank() && minute.isNotBlank()) {
                                    Text(
                                        text = "La fecha no puede ser anterior a hoy",
                                        color = Color.Red,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                                
                                Button(
                                    onClick = {
                                        if (isValidDate) {
                                            startDate = "$day/$month/$year $hour:$minute"
                                            showStartDatePicker = false
                                        }
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    enabled = isValidDate && day.isNotBlank() && month.isNotBlank() && year.isNotBlank() && hour.isNotBlank() && minute.isNotBlank()
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
                    placeholder = { Text("dd/MM/yyyy HH:mm") },
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
                    val calendar = remember { Calendar.getInstance() }
                    var day by remember { mutableStateOf(calendar.get(Calendar.DAY_OF_MONTH).toString()) }
                    var month by remember { mutableStateOf((calendar.get(Calendar.MONTH) + 1).toString()) }
                    var year by remember { mutableStateOf(calendar.get(Calendar.YEAR).toString()) }
                    var hour by remember { mutableStateOf(calendar.get(Calendar.HOUR_OF_DAY).toString()) }
                    var minute by remember { mutableStateOf(calendar.get(Calendar.MINUTE).toString()) }
                    
                    val isValidDate = remember(day, month, year, hour, minute) {
                        try {
                            val selectedDay = day.toIntOrNull()
                            val selectedMonth = month.toIntOrNull()
                            val selectedYear = year.toIntOrNull()
                            val selectedHour = hour.toIntOrNull()
                            val selectedMinute = minute.toIntOrNull()
                            
                            if (selectedDay == null || selectedMonth == null || selectedYear == null ||
                                selectedHour == null || selectedMinute == null) {
                                false
                            } else {
                                val selectedCal = Calendar.getInstance().apply {
                                    set(selectedYear, selectedMonth - 1, selectedDay, selectedHour, selectedMinute)
                                }
                                selectedCal >= calendar
                            }
                        } catch (e: Exception) {
                            false
                        }
                    }
                    
                    AlertDialog(
                        onDismissRequest = { showDueDatePicker = false },
                        title = { Text("Seleccionar fecha y hora límite") },
                        text = {
                            Column {
                                
                                OutlinedTextField(
                                    value = day,
                                    onValueChange = { if (it.all { char -> char.isDigit() }) day = it.take(2) },
                                    label = { Text("Día (DD)") },
                                    modifier = Modifier.fillMaxWidth(),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    colors = TextFieldDefaults.colors(
                                        focusedContainerColor = Color.White,
                                        unfocusedContainerColor = Color.White,
                                        focusedIndicatorColor = Color(0xFF3EC8AC),
                                        unfocusedIndicatorColor = Color.Gray,
                                        focusedTextColor = Color.Black,
                                        unfocusedTextColor = Color.Black
                                    )
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                OutlinedTextField(
                                    value = month,
                                    onValueChange = { if (it.all { char -> char.isDigit() }) month = it.take(2) },
                                    label = { Text("Mes (MM)") },
                                    modifier = Modifier.fillMaxWidth(),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    colors = TextFieldDefaults.colors(
                                        focusedContainerColor = Color.White,
                                        unfocusedContainerColor = Color.White,
                                        focusedIndicatorColor = Color(0xFF3EC8AC),
                                        unfocusedIndicatorColor = Color.Gray,
                                        focusedTextColor = Color.Black,
                                        unfocusedTextColor = Color.Black
                                    )
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                OutlinedTextField(
                                    value = year,
                                    onValueChange = { if (it.all { char -> char.isDigit() }) year = it.take(4) },
                                    label = { Text("Año (YYYY)") },
                                    modifier = Modifier.fillMaxWidth(),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    colors = TextFieldDefaults.colors(
                                        focusedContainerColor = Color.White,
                                        unfocusedContainerColor = Color.White,
                                        focusedIndicatorColor = Color(0xFF3EC8AC),
                                        unfocusedIndicatorColor = Color.Gray,
                                        focusedTextColor = Color.Black,
                                        unfocusedTextColor = Color.Black
                                    )
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                OutlinedTextField(
                                    value = hour,
                                    onValueChange = { if (it.all { char -> char.isDigit() }) hour = it.take(2) },
                                    label = { Text("Hora (HH)") },
                                    modifier = Modifier.fillMaxWidth(),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    colors = TextFieldDefaults.colors(
                                        focusedContainerColor = Color.White,
                                        unfocusedContainerColor = Color.White,
                                        focusedIndicatorColor = Color(0xFF3EC8AC),
                                        unfocusedIndicatorColor = Color.Gray,
                                        focusedTextColor = Color.Black,
                                        unfocusedTextColor = Color.Black
                                    )
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                OutlinedTextField(
                                    value = minute,
                                    onValueChange = { if (it.all { char -> char.isDigit() }) minute = it.take(2) },
                                    label = { Text("Minutos (MM)") },
                                    modifier = Modifier.fillMaxWidth(),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    colors = TextFieldDefaults.colors(
                                        focusedContainerColor = Color.White,
                                        unfocusedContainerColor = Color.White,
                                        focusedIndicatorColor = Color(0xFF3EC8AC),
                                        unfocusedIndicatorColor = Color.Gray,
                                        focusedTextColor = Color.Black,
                                        unfocusedTextColor = Color.Black
                                    )
                                )
                                
                                Spacer(modifier = Modifier.height(16.dp))
                                
                                if (!isValidDate && day.isNotBlank() && month.isNotBlank() && year.isNotBlank() && hour.isNotBlank() && minute.isNotBlank()) {
                                    Text(
                                        text = "La fecha no puede ser anterior a hoy",
                                        color = Color.Red,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                                
                                Button(
                                    onClick = {
                                        if (isValidDate) {
                                            dueDate = "$day/$month/$year $hour:$minute"
                                            showDueDatePicker = false
                                        }
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    enabled = isValidDate && day.isNotBlank() && month.isNotBlank() && year.isNotBlank() && hour.isNotBlank() && minute.isNotBlank()
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
                    .padding(horizontal = 24.dp, vertical = 16.dp),
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
                          // Crear la tarea o incidencia y guardarla en el repositorio
                          val id = UUID.randomUUID().toString()
                          val dateKey = startDate?.split(" ")?.get(0) ?: "" // Extraer solo la fecha (dd/MM/yyyy)
                          val sender = "Usuario Actual" // TODO: Obtener del usuario actual autenticado
                          
                          if (selectedType == "Tarea") {
                              val task = Task(
                                  id = id,
                                  title = title,
                                  description = description,
                                  sender = sender,
                                  recipientType = recipientType,
                                  recipient = selectedRecipient,
                                  priority = priority,
                                  startDate = startDate ?: "",
                                  dueDate = dueDate ?: "",
                                  status = "pendiente",
                                  dateKey = dateKey
                              )
                              TaskIncidentRepository.addTask(task)
                              onNavigateToTasks()
                          } else {
                              val incident = Incident(
                                  id = id,
                                  title = title,
                                  description = description,
                                  sender = sender,
                                  recipientType = recipientType,
                                  recipient = selectedRecipient,
                                  priority = priority,
                                  startDate = startDate ?: "",
                                  dueDate = dueDate ?: "",
                                  status = "pendiente",
                                  dateKey = dateKey
                              )
                              TaskIncidentRepository.addIncident(incident)
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


package com.organizados.ajustes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswordScreen(onNavigateBack: () -> Unit) {
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    
    var currentPasswordVisible by remember { mutableStateOf(false) }
    var newPasswordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cambiar contraseña") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp, vertical = 32.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Contraseña actual
            Text(
                text = "Contraseña actual",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                color = MaterialTheme.colorScheme.onSurface
            )
            
            OutlinedTextField(
                value = currentPassword,
                onValueChange = { currentPassword = it },
                placeholder = { Text("Ingrese su contraseña actual") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                singleLine = true,
                visualTransformation = if (currentPasswordVisible) 
                    VisualTransformation.None 
                else 
                    PasswordVisualTransformation(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color(0xFF3EC8AC),
                    unfocusedIndicatorColor = Color.Gray,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
                trailingIcon = {
                    TextButton(onClick = { currentPasswordVisible = !currentPasswordVisible }) {
                        Text(
                            text = if (currentPasswordVisible) "Ocultar" else "Mostrar",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Black
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Contraseña nueva
            Text(
                text = "Contraseña nueva",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                color = MaterialTheme.colorScheme.onSurface
            )
            
            OutlinedTextField(
                value = newPassword,
                onValueChange = { newPassword = it },
                placeholder = { Text("Ingrese su contraseña nueva") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                singleLine = true,
                visualTransformation = if (newPasswordVisible) 
                    VisualTransformation.None 
                else 
                    PasswordVisualTransformation(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color(0xFF3EC8AC),
                    unfocusedIndicatorColor = Color.Gray,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
                trailingIcon = {
                    TextButton(onClick = { newPasswordVisible = !newPasswordVisible }) {
                        Text(
                            text = if (newPasswordVisible) "Ocultar" else "Mostrar",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Black
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Confirmar contraseña
            Text(
                text = "Confirmar contraseña",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                color = MaterialTheme.colorScheme.onSurface
            )
            
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = { Text("Confirme su contraseña nueva") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                singleLine = true,
                visualTransformation = if (confirmPasswordVisible) 
                    VisualTransformation.None 
                else 
                    PasswordVisualTransformation(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color(0xFF3EC8AC),
                    unfocusedIndicatorColor = Color.Gray,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
                trailingIcon = {
                    TextButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                        Text(
                            text = if (confirmPasswordVisible) "Ocultar" else "Mostrar",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Black
                        )
                    }
                },
                isError = newPassword != confirmPassword && confirmPassword.isNotBlank()
            )

            // Texto "¿Olvidó su contraseña?"
            ClickableText(
                text = AnnotatedString("¿Olvidó su contraseña?"),
                onClick = { /* Manejo de olvido de contraseña */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                style = TextStyle(
                    textDecoration = TextDecoration.Underline,
                    color = MaterialTheme.colorScheme.primary
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            // Botón Guardar cambios
            Button(
                onClick = {
                    if (validatePasswords(currentPassword, newPassword, confirmPassword)) {
                        // Lógica para cambiar la contraseña
                        onNavigateBack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = currentPassword.isNotBlank() && 
                         newPassword.isNotBlank() && 
                         confirmPassword.isNotBlank() &&
                         newPassword == confirmPassword
            ) {
                Text("Guardar cambios")
            }
        }
    }
}

private fun validatePasswords(
    currentPassword: String,
    newPassword: String,
    confirmPassword: String
): Boolean {
    return currentPassword.isNotBlank() &&
           newPassword.isNotBlank() &&
           confirmPassword.isNotBlank() &&
           newPassword == confirmPassword &&
           newPassword.length >= 6
}


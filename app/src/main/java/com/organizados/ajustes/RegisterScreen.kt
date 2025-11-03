package com.organizados.ajustes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToArea: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    
    val isEmailValid = email.isNotBlank() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    val isNameValid = name.isNotBlank()
    val isAgeValid = age.isNotBlank() && age.toIntOrNull() != null && age.toInt() > 0
    val isPasswordValid = password.length in 8..16
    val isConfirmPasswordValid = password == confirmPassword && confirmPassword.isNotBlank()
    val isFormValid = isEmailValid && isNameValid && isAgeValid && isPasswordValid && isConfirmPasswordValid

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registrarse") }
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
            // Campo Ingresar nombre
            Text(
                text = "Nombre",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                color = MaterialTheme.colorScheme.onSurface
            )
            
            OutlinedTextField(
                value = name,
                onValueChange = { newValue ->
                    // Solo permitir letras, espacios y algunos caracteres especiales comunes en nombres
                    name = newValue.filter { 
                        it.isLetter() || it.isWhitespace() || it == '-' || it == '\''
                    }
                },
                placeholder = { Text("Ingrese su nombre completo") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                singleLine = true,
                textStyle = androidx.compose.ui.text.TextStyle(color = Color.Black),
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
                isError = name.isNotBlank() && !isNameValid
            )

            // Campo Ingresar edad
            Text(
                text = "Edad",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                color = MaterialTheme.colorScheme.onSurface
            )
            
            OutlinedTextField(
                value = age,
                onValueChange = { newValue ->
                    // Solo permitir números
                    age = newValue.filter { it.isDigit() }
                },
                placeholder = { Text("Ingrese su edad") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                textStyle = androidx.compose.ui.text.TextStyle(color = Color.Black),
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
                isError = age.isNotBlank() && !isAgeValid
            )

            // Campo Ingresar correo electrónico
            Text(
                text = "Ingresar correo electrónico",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                color = MaterialTheme.colorScheme.onSurface
            )
            
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("Ingrese su correo electrónico") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                textStyle = androidx.compose.ui.text.TextStyle(color = Color.Black),
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
                isError = email.isNotBlank() && !isEmailValid
            )

            // Campo Ingresar contraseña
            Text(
                text = "Ingresar contraseña (8-16 caracteres)",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                color = MaterialTheme.colorScheme.onSurface
            )
            
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Ingrese su contraseña") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                singleLine = true,
                textStyle = androidx.compose.ui.text.TextStyle(color = Color.Black),
                visualTransformation = if (passwordVisible) 
                    VisualTransformation.None 
                else 
                    PasswordVisualTransformation(),
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
                    TextButton(onClick = { passwordVisible = !passwordVisible }) {
                        Text(
                            text = if (passwordVisible) "Ocultar" else "Mostrar",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Black
                        )
                    }
                },
                isError = password.isNotBlank() && !isPasswordValid
            )

            // Campo Confirmar contraseña
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
                placeholder = { Text("Confirme su contraseña") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                singleLine = true,
                textStyle = androidx.compose.ui.text.TextStyle(color = Color.Black),
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
                    unfocusedTextColor = Color.Black,
                    focusedPlaceholderColor = Color.Gray,
                    unfocusedPlaceholderColor = Color.Gray
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
                isError = confirmPassword.isNotBlank() && !isConfirmPasswordValid
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Botón Confirmar
            Button(
                onClick = {
                    if (isFormValid) {
                        // Guardar datos del usuario
                        UserData.userName = name
                        UserData.userAge = age.toIntOrNull() ?: 0
                        UserData.email = email
                        onNavigateToArea()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = isFormValid
            ) {
                Text("Confirmar")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Leyenda "Ya tienes cuenta? Inicia sesion"
            Text(
                text = "Ya tienes cuenta? Inicia sesion",
                style = TextStyle(
                    textDecoration = TextDecoration.Underline,
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigateToLogin() },
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}


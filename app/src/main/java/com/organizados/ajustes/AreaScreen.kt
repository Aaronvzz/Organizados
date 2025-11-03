package com.organizados.ajustes

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AreaScreen(
    onNavigateToWorker: () -> Unit,
    onNavigateToAdmin: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Área") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Selecciona tu tipo de grupo de trabajo",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Botón Soy trabajador
            Button(
                onClick = onNavigateToWorker,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("Soy trabajador")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón Soy administrador
            Button(
                onClick = onNavigateToAdmin,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("Soy administrador")
            }
        }
    }
}


package com.organizados.ajustes

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

sealed class WorkerScreen(val route: String, val title: String, val icon: ImageVector) {
    object Tasks : WorkerScreen("worker_tasks", "Tareas", Icons.Default.List)
    object AddTaskIncident : WorkerScreen("worker_add", "Agregar Tarea/Incidencia", Icons.Default.Add)
    object Incidents : WorkerScreen("worker_incidents", "Incidencias", Icons.Default.Info)
    object Profile : WorkerScreen("worker_profile", "Perfil", Icons.Default.Person)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkerMainScreen(
    mainNavController: NavHostController? = null,
    onNavigateToChangePassword: () -> Unit,
    onLogout: () -> Unit
) {
    val workerNavController = rememberNavController()
    val navBackStackEntry by workerNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        bottomBar = {
            NavigationBar {
                getWorkerScreens().forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { 
                            if (screen == WorkerScreen.AddTaskIncident) {
                                Text(
                                    text = "Agregar Tarea/\nIncidencia",
                                    style = MaterialTheme.typography.labelSmall,
                                    maxLines = 2
                                )
                            } else {
                                Text(screen.title)
                            }
                        },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            workerNavController.navigate(screen.route) {
                                // Evitar múltiples copias de la misma pantalla en el back stack
                                popUpTo(WorkerScreen.Tasks.route) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = workerNavController,
            startDestination = WorkerScreen.Tasks.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(WorkerScreen.Tasks.route) {
                TasksScreen()
            }
            
            composable(WorkerScreen.AddTaskIncident.route) {
                AddTaskIncidentScreen(
                    onNavigateToTasks = {
                        workerNavController.navigate(WorkerScreen.Tasks.route) {
                            popUpTo(WorkerScreen.Tasks.route) { inclusive = false }
                        }
                    },
                    onNavigateToIncidents = {
                        workerNavController.navigate(WorkerScreen.Incidents.route) {
                            popUpTo(WorkerScreen.Tasks.route) { inclusive = false }
                        }
                    }
                )
            }
            
            composable(WorkerScreen.Incidents.route) {
                IncidentsScreen()
            }
            
            composable(WorkerScreen.Profile.route) {
                SettingsScreen(
                    userName = UserData.userName.ifBlank { "Usuario" },
                    userAge = UserData.userAge,
                    department = UserData.department.ifBlank { "Sin departamento" },
                    onNavigateToChangePassword = onNavigateToChangePassword,
                    onLogout = onLogout
                )
            }
        }
    }
}

// Helper para obtener todos los valores del sealed class
@Composable
private fun getWorkerScreens(): List<WorkerScreen> = listOf(
    WorkerScreen.Tasks,
    WorkerScreen.AddTaskIncident,
    WorkerScreen.Incidents,
    WorkerScreen.Profile
)


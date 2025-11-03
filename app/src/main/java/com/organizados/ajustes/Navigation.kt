package com.organizados.ajustes

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Login : Screen("login")
    object Register : Screen("register")
    object Area : Screen("area")
    object Template : Screen("template")
    object Admin : Screen("admin")
    object AdminPanel : Screen("admin_panel")
    object JoinWorkGroup : Screen("join_work_group")
    object WorkerMain : Screen("worker_main")
    object Settings : Screen("settings")
    object ChangePassword : Screen("change_password")
}

@Composable
fun AppNavigation(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route
    ) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route)
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }
        
        composable(Screen.Login.route) {
            LoginScreen(
                onForgotPassword = {
                    // TODO: Implementar pantalla de recuperar contraseña
                }
            )
        }
        
        composable(Screen.Register.route) {
            RegisterScreen(
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route)
                },
                onNavigateToArea = {
                    navController.navigate(Screen.Area.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.Area.route) {
            AreaScreen(
                onNavigateToWorker = {
                    navController.navigate(Screen.WorkerMain.route) {
                        popUpTo(Screen.Area.route) { inclusive = true }
                    }
                },
                onNavigateToAdmin = {
                    navController.navigate(Screen.Admin.route)
                }
            )
        }
        
        composable(Screen.Template.route) {
            TemplateScreen()
        }
        
        composable(Screen.Admin.route) {
            AdminScreen(
                onNavigateToSettings = {
                    navController.navigate(Screen.Settings.route)
                },
                onNavigateToCreateGroup = {
                    navController.navigate(Screen.AdminPanel.route)
                }
            )
        }
        
        composable(Screen.AdminPanel.route) {
            AdminPanelScreen(
                onSaveChanges = {
                    navController.navigate(Screen.Template.route) {
                        popUpTo(Screen.Admin.route) { inclusive = false }
                    }
                }
            )
        }
        
        composable(Screen.JoinWorkGroup.route) {
            JoinWorkGroupScreen(
                onNavigateToSettings = {
                    navController.navigate(Screen.WorkerMain.route) {
                        popUpTo(Screen.JoinWorkGroup.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.WorkerMain.route) {
            WorkerMainScreen(
                mainNavController = navController,
                onNavigateToChangePassword = {
                    navController.navigate(Screen.ChangePassword.route)
                },
                onLogout = {
                    UserData.clear()
                    navController.navigate(Screen.Welcome.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.Settings.route) {
            SettingsScreen(
                userName = UserData.userName.ifBlank { "Usuario" },
                userAge = UserData.userAge,
                department = UserData.department.ifBlank { "Sin departamento" },
                onNavigateToChangePassword = {
                    navController.navigate(Screen.ChangePassword.route)
                },
                onLogout = {
                    UserData.clear()
                    navController.navigate(Screen.Welcome.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.ChangePassword.route) {
            ChangePasswordScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}


package com.organizados.ajustes

// Objeto para almacenar datos del usuario durante la sesión
object UserData {
    var userName: String = "Juan Pérez"
    var userAge: Int = 28
    var department: String = "Desarrollo de Software"
    var email: String = ""
    
    fun clear() {
        userName = ""
        userAge = 0
        department = ""
        email = ""
    }
}


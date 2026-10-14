package cl.duoc.cmqsalud.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel // ¡Importante para el ViewModel!

import cl.duoc.cmqsalud.ui.components.CMQButton
import cl.duoc.cmqsalud.ui.components.CMQTextField

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel() // Inyectamos el ViewModel
) {
    // Escuchamos el estado del ViewModel
    val uiState by viewModel.uiState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Títulos
        Text(
            text = "CMQ Salud",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Gestión Documental APS",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Campos de texto
        CMQTextField(
            value = email,
            onValueChange = { email = it },
            label = "Correo Institucional o RUT"
        )

        Spacer(modifier = Modifier.height(16.dp))

        CMQTextField(
            value = password,
            onValueChange = { password = it },
            label = "Contraseña",
            isPassword = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // REACCIÓN AL ESTADO DEL VIEWMODEL

        // 1. Si hay error, mostrar texto en rojo
        uiState.errorMensaje?.let { mensajeError ->
            Text(
                text = mensajeError,
                color = Color.Red,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        // 2. Control del Botón / Éxito / Carga
        if (uiState.loginExitoso) {
            Text(
                text = "¡Inicio de sesión exitoso!",
                color = Color(0xFF4CAF50), // Verde
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        } else if (uiState.isLoading) {
            // Mostrar ruedita de carga (aunque será muy rápida porque no hay internet real)
            CircularProgressIndicator()
        } else {
            CMQButton(
                text = "Iniciar Sesión",
                onClick = {
                    // Le mandamos los datos al ViewModel para que trabaje
                    viewModel.iniciarSesion(email, password)
                }
            )
        }
    }
}
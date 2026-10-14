package cl.duoc.cmqsalud.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// Importamos nuestros componentes reutilizables
import cl.duoc.cmqsalud.ui.components.CMQButton
import cl.duoc.cmqsalud.ui.components.CMQTextField

@Composable
fun LoginScreen() {
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

        // Usamos nuestro componente CMQTextField para el correo
        CMQTextField(
            value = email,
            onValueChange = { email = it },
            label = "Correo Institucional o RUT"
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Usamos nuestro componente CMQTextField para la contraseña
        CMQTextField(
            value = password,
            onValueChange = { password = it },
            label = "Contraseña",
            isPassword = true
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Usamos nuestro componente CMQButton para la acción
        CMQButton(
            text = "Iniciar Sesión",
            onClick = {
                println("Intento de login con: $email")
            }
        )
    }
}
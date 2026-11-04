package cl.duoc.cmqsalud

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    val db = remember { AppDatabase.getDatabase(context) }
    val dao = db.usuarioDao()
    val coroutineScope = rememberCoroutineScope()

    var codigo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        if (dao.contarUsuarios() == 0) {
            dao.insertarUsuario(Usuario(nombre = "Juan", apellido = "Perez", codigo = "1111", contrasena = "1234"))
            dao.insertarUsuario(Usuario(nombre = "Maria", apellido = "Lopez", codigo = "2222", contrasena = "2345"))
            dao.insertarUsuario(Usuario(nombre = "Pedro", apellido = "Gomez", codigo = "3333", contrasena = "3456"))
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Componente del logo restaurado
        Image(
            painter = painterResource(id = R.drawable.logo), // Cambia "tu_logo" por el nombre de tu archivo
            contentDescription = "Logo CMD Salud",
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = codigo,
            onValueChange = { if (it.length <= 4) codigo = it },
            label = { Text("Código de ingreso (4 números)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = contrasena,
            onValueChange = { if (it.length <= 4) contrasena = it },
            label = { Text("Contraseña (4 números)") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                // Validación 1: Campos vacíos
                if (codigo.isBlank() || contrasena.isBlank()) {
                    Toast.makeText(context, "Por favor, no deje campos en blanco", Toast.LENGTH_SHORT).show()
                } else {
                    coroutineScope.launch {
                        val usuarioEncontrado = dao.login(codigo, contrasena)
                        if (usuarioEncontrado != null) {
                            navController.navigate("main/${usuarioEncontrado.nombre}") {
                                popUpTo("login") { inclusive = true }
                            }
                        } else {
                            // Validación 2: Credenciales incorrectas
                            Toast.makeText(context, "Código o contraseña erróneos", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar Sesión")
        }
    }
}
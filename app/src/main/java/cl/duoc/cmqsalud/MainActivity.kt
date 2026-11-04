package cl.duoc.cmqsalud

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import cl.duoc.cmqsalud.ui.theme.CMQSaludTheme
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.Alignment
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.icons.filled.ExitToApp
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.compose.material.icons.filled.ArrowBack

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CMQSaludTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun PacienteScreen(navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val db = remember { AppDatabase.getDatabase(context) }
    val dao = db.pacienteDao()
    val scope = rememberCoroutineScope()

    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var pacientes by remember { mutableStateOf(listOf<Paciente>()) }

    LaunchedEffect(Unit) {
        pacientes = dao.obtenerPacientes()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("CMQ Salud") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("main") }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Volver al menú")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = apellido,
                onValueChange = { apellido = it },
                label = { Text("Apellido") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (nombre.isNotBlank() && apellido.isNotBlank()) {

                        nombre = ""
                        apellido = ""

                    } else {
                        Toast.makeText(context, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            ) {
                Text("Guardar Paciente")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("Pacientes registrados:", style = MaterialTheme.typography.titleMedium)

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(pacientes) { paciente ->
                    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Datos del paciente
                            Text(text = "ID: ${paciente.id} - ${paciente.nombre} ${paciente.apellido}")

                            // Botón de borrado
                            IconButton(onClick = {
                                coroutineScope.launch {
                                    dao.eliminarPaciente(paciente)
                                    pacientes = dao.obtenerPacientes() // Refresca la lista visual al instante
                                }
                            }) {
                                Icon(imageVector = Icons.Default.Delete, contentDescription = "Borrar paciente")
                            }
                        }
                    }
                }
            }
        }
        }
}
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController)
        }
        composable("main/{nombreUsuario}") { backStackEntry ->
            val nombre = backStackEntry.arguments?.getString("nombreUsuario") ?: ""
            MainScreen(navController, nombre)
        }
        composable("documentos") {
            RegistroDocumentoScreen(navController)
        }
        composable("ingresar_documento") {
            IngresarDocumentoScreen(navController)
        }
        composable("registro_pacientes") {
            RegistroPacienteScreen(navController)
        }
        composable("ingresar_paciente") {
            IngresarPacienteScreen(navController)
        }
    }
}
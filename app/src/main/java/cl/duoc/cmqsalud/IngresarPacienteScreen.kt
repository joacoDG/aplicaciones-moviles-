package cl.duoc.cmqsalud

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import android.Manifest
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngresarPacienteScreen(navController: NavController) {
    val context = LocalContext.current
    val db = remember { AppDatabase.getDatabase(context) }
    val dao = db.pacienteDao()
    val coroutineScope = rememberCoroutineScope()

    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }

    var fotoBitmap by remember { mutableStateOf<Bitmap?>(null) }

    // 1. Lanzador para abrir la cámara y recibir la foto
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        if (bitmap != null) {
            fotoBitmap = bitmap // Si hay foto, actualizamos el estado
        } else {
            Toast.makeText(context, "No se capturó ninguna foto", Toast.LENGTH_SHORT).show()
        }
    }

    // 2. Lanzador para pedir el permiso de la cámara
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Si da el permiso, abrimos la cámara
            cameraLauncher.launch(null)
        } else {
            Toast.makeText(context, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registrar Paciente", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = apellido,
                onValueChange = { apellido = it },
                label = { Text("Apellido") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = fechaNacimiento,
                onValueChange = { fechaNacimiento = it },
                label = { Text("Fecha de nacimiento (Ej: 15/04/1990)") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
            Text(text = "Foto de perfil", fontWeight = FontWeight.Bold)

            // Muestra la foto si existe
            if (fotoBitmap != null) {
                Image(
                    bitmap = fotoBitmap!!.asImageBitmap(),
                    contentDescription = "Foto del paciente",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                )
            } else {
                // Un cuadro o icono por defecto si no hay foto
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Sin foto",
                    modifier = Modifier.size(120.dp),
                    tint = Color.Gray
                )
            }

            Button(
                onClick = { permissionLauncher.launch(Manifest.permission.CAMERA) },
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
            ) {
                Text("Tomar Foto")
            }

            Button(
                onClick = {
                    if (nombre.isBlank() || apellido.isBlank() || fechaNacimiento.isBlank() || descripcion.isBlank()) {
                        Toast.makeText(context, "Por favor llene todos los campos", Toast.LENGTH_SHORT).show()
                    } else {
                        coroutineScope.launch {
                            val nuevoPaciente = Paciente(
                                nombre = nombre,
                                apellido = apellido,
                                fechaNacimiento = fechaNacimiento,
                                descripcion = descripcion
                            )
                            dao.insertarPaciente(nuevoPaciente)
                            Toast.makeText(context, "Paciente registrado con éxito", Toast.LENGTH_SHORT).show()
                            navController.navigateUp() // Vuelve a la lista actualizada
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            ) {
                Text("Guardar Paciente")
            }
        }
    }
}
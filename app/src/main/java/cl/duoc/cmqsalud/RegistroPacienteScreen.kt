package cl.duoc.cmqsalud

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroPacienteScreen(navController: NavController) {
    val context = LocalContext.current
    val db = remember { AppDatabase.getDatabase(context) }
    val dao = db.pacienteDao()

    var listaPacientes by remember { mutableStateOf(listOf<Paciente>()) }

    LaunchedEffect(Unit) {
        listaPacientes = dao.obtenerPacientes()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pacientes Registrados", fontWeight = FontWeight.Bold, color = Color.Black) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Color.Black)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF33B5FF))
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp)
        ) {
            Button(
                onClick = { navController.navigate("ingresar_paciente") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Color.Black),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF33B5FF),
                    contentColor = Color.Black
                )
            ) {
                Text("Ingresar nuevo paciente", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(24.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(listaPacientes) { pac ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFAAAAAA)),
                        border = BorderStroke(1.dp, Color.Black)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Nombre: ${pac.nombre} ${pac.apellido}", fontWeight = FontWeight.Bold, color = Color.Black)
                            Text("Nacimiento: ${pac.fechaNacimiento}", fontWeight = FontWeight.Bold, color = Color.Black)
                            Text("Descripción: ${pac.descripcion}", fontWeight = FontWeight.Bold, color = Color.Black)
                        }
                    }
                }
            }
        }
    }
}
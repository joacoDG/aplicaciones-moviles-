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
fun RegistroDocumentoScreen(navController: NavController) {
    val context = LocalContext.current
    val db = remember { AppDatabase.getDatabase(context) }
    val dao = db.documentoDao()

    var listaDocumentos by remember { mutableStateOf(listOf<Documento>()) }

    LaunchedEffect(Unit) {
        listaDocumentos = dao.obtenerDocumentos()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("registrar documentos", fontWeight = FontWeight.Bold, color = Color.Black)
                },
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
                onClick = { navController.navigate("ingresar_documento") },
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
                Text("ingresar nuevo documento", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "documentos registrados:",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(listaDocumentos) { doc ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFAAAAAA)),
                        border = BorderStroke(1.dp, Color.Black)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("nombre: ${doc.nombre}", fontWeight = FontWeight.Bold, color = Color.Black)
                            Text("destino: ${doc.caso}", fontWeight = FontWeight.Bold, color = Color.Black)
                            Text("fecha: ${doc.fecha}", fontWeight = FontWeight.Bold, color = Color.Black)
                        }
                    }
                }
            }
        }
    }
}
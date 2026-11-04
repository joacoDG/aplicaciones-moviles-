package cl.duoc.cmqsalud

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
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
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngresarDocumentoScreen(navController: NavController) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    // Instancia de tu base de datos (ajusta según cómo la llamas en tu app)
    val db = remember { AppDatabase.getDatabase(context) }
    val dao = db.documentoDao()

    var nombre by remember { mutableStateOf("") }
    var caso by remember { mutableStateOf("") }
    var expandirDropdown by remember { mutableStateOf(false) }
    val opcionesCaso = listOf("inventario", "casos urgentes", "adjuntos del mes")

    var dia by remember { mutableStateOf("") }
    var mes by remember { mutableStateOf("") }
    var ano by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var pdfUri by remember { mutableStateOf<Uri?>(null) }

    // Lanzador para pedir el archivo PDF al sistema
    val pdfPickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        pdfUri = uri
        if (uri != null) Toast.makeText(context, "PDF seleccionado", Toast.LENGTH_SHORT).show()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ingresar nuevo documento", fontWeight = FontWeight.Bold) },
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
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            // Nombre[cite: 19]
            Text("nombre del documento:", fontWeight = FontWeight.Bold)
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("ingrese el nombre") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Caso (Menú desplegable)[cite: 19]
            Text("caso:", fontWeight = FontWeight.Bold)
            ExposedDropdownMenuBox(
                expanded = expandirDropdown,
                onExpandedChange = { expandirDropdown = !expandirDropdown }
            ) {
                OutlinedTextField(
                    value = caso,
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text("eliga una opcion") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandirDropdown) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expandirDropdown,
                    onDismissRequest = { expandirDropdown = false }
                ) {
                    opcionesCaso.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion) },
                            onClick = {
                                caso = opcion
                                expandirDropdown = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Fecha[cite: 19]
            Text("fecha de ingreso:", fontWeight = FontWeight.Bold)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = dia, onValueChange = { dia = it }, label = { Text("Dia") }, modifier = Modifier.weight(1f))
                OutlinedTextField(value = mes, onValueChange = { mes = it }, label = { Text("mes") }, modifier = Modifier.weight(1f))
                OutlinedTextField(value = ano, onValueChange = { ano = it }, label = { Text("año") }, modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Descripción[cite: 20]
            Text("Descripcion:", fontWeight = FontWeight.Bold)
            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                modifier = Modifier.fillMaxWidth().height(100.dp),
                placeholder = { Text("ingrese su descripcion del documento") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón PDF[cite: 20]
            Text("documento:", fontWeight = FontWeight.Bold)
            Button(
                onClick = { pdfPickerLauncher.launch("application/pdf") },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                shape = RoundedCornerShape(4.dp),
                border = BorderStroke(1.dp, Color.Black)
            ) {
                Text(if (pdfUri == null) "ingresar archivo.pdf" else "Archivo adjuntado", color = Color.Black)
            }

            Spacer(modifier = Modifier.weight(1f))

            // Botón Guardar[cite: 20]
            Button(
                onClick = {
                    if (nombre.isNotBlank() && caso.isNotBlank() && dia.isNotBlank()) {
                        coroutineScope.launch {
                            val nuevoDoc = Documento(
                                nombre = nombre,
                                caso = caso,
                                fecha = "$dia/$mes/$ano",
                                descripcion = descripcion,
                                pdfUri = pdfUri?.toString() ?: ""
                            )
                            dao.insertarDocumento(nuevoDoc)
                            Toast.makeText(context, "Documento guardado correctamente", Toast.LENGTH_SHORT).show()
                            navController.popBackStack() // Vuelve a la pantalla anterior
                        }
                    } else {
                        Toast.makeText(context, "Complete los campos clave", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF33B5FF)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("ingresar documento", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
        }
    }
}
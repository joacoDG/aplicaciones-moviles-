package cl.duoc.cmqsalud

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import cl.duoc.cmqsalud.ui.login.LoginScreen
import cl.duoc.cmqsalud.ui.theme.CMQSaludTheme // Revisa que coincida con tu nombre de proyecto

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CMQSaludTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Llamamos a nuestra pantalla de Login
                    LoginScreen()
                }
            }
        }
    }
}
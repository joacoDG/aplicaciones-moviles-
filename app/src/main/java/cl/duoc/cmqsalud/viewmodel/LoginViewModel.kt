package cl.duoc.cmqsalud.ui.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// 1. Definimos los posibles "Estados" de nuestra pantalla
data class LoginUiState(
    val isLoading: Boolean = false,
    val errorMensaje: String? = null,
    val loginExitoso: Boolean = false
)

// 2. Creamos el ViewModel que controlará la lógica
class LoginViewModel : ViewModel() {

    // Variable privada que el ViewModel modifica
    private val _uiState = MutableStateFlow(LoginUiState())
    // Variable pública que la Interfaz (UI) va a observar/leer
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    // Función que recibe los datos desde la vista al presionar el botón
    fun iniciarSesion(correo: String, pass: String) {

        // Activamos el estado de carga (simulando que va a la base de datos)
        _uiState.value = LoginUiState(isLoading = true)

        // Mock Data: Simulamos la validación de credenciales
        if (correo == "admin@cmq.cl" && pass == "123456") {
            // Si los datos coinciden -> Éxito
            _uiState.value = LoginUiState(loginExitoso = true)
        } else if (correo.isEmpty() || pass.isEmpty()) {
            // Si hay campos vacíos -> Error
            _uiState.value = LoginUiState(errorMensaje = "Por favor, complete todos los campos.")
        } else {
            // Si escribió otra cosa -> Error de credenciales
            _uiState.value = LoginUiState(errorMensaje = "Credenciales incorrectas. Intente nuevamente.")
        }
    }
}
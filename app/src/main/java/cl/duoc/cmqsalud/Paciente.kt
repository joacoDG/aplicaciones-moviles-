package cl.duoc.cmqsalud

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabla_pacientes")
data class Paciente(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val apellido: String,
    val fechaNacimiento: String,
    val descripcion: String
)
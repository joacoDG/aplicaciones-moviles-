package cl.duoc.cmqsalud

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
@JvmSuppressWildcards
interface PacienteDao {

    @Insert
    suspend fun insertarPaciente(paciente: Paciente): Long

    @Update
    suspend fun actualizarPaciente(paciente: Paciente): Int

    @Delete
    suspend fun eliminarPaciente(paciente: Paciente): Int

    @Query("DELETE FROM tabla_pacientes")
    suspend fun borrarTodosLosPacientes(): Int

    @Query("SELECT * FROM tabla_pacientes")
    suspend fun obtenerPacientes(): List<Paciente>
}
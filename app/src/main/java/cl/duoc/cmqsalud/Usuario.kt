package cl.duoc.cmqsalud

import androidx.room.*

@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val apellido: String,
    val codigo: String,
    val contrasena: String
)

@Dao
@JvmSuppressWildcards
interface UsuarioDao {
    @Insert
    suspend fun insertarUsuario(usuario: Usuario): Long

    @Query("SELECT * FROM usuarios WHERE codigo = :codigo AND contrasena = :contrasena")
    suspend fun login(codigo: String, contrasena: String): Usuario?

    @Query("SELECT COUNT(*) FROM usuarios")
    suspend fun contarUsuarios(): Int
}
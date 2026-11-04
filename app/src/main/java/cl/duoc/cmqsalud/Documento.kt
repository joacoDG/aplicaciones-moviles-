package cl.duoc.cmqsalud

import androidx.room.*

@Entity(tableName = "documentos")
data class Documento(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val caso: String,
    val fecha: String,
    val descripcion: String,
    val pdfUri: String // Guardaremos la ruta del archivo
)

@Dao
@JvmSuppressWildcards
interface DocumentoDao {
    @Insert
    suspend fun insertarDocumento(documento: Documento): Long

    @Query("SELECT * FROM documentos")
    suspend fun obtenerDocumentos(): List<Documento>
}
package cl.duoc.cmqsalud

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Paciente::class, Documento::class, Usuario::class], version = 4, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun pacienteDao(): PacienteDao
    abstract fun documentoDao(): DocumentoDao

    abstract fun usuarioDao(): UsuarioDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "cmqsalud_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
package modelo.clases

import jakarta.persistence.EntityManager
import jakarta.persistence.EntityManagerFactory
import jakarta.persistence.Persistence

class GestorModelo {
    //Crea una instancia para que sólo pueda haber una conexión
    companion object {
        private var instance: GestorModelo? = null

        fun getInstance(): GestorModelo {
            if (instance == null) {
                instance = GestorModelo()
            }
            return instance!!
        }
    }
    private val emf: EntityManagerFactory = Persistence.createEntityManagerFactory("PersistenciaEjercicio")
    var manager: EntityManager? = null
    fun conexion() {
        try {
            if (manager == null) {
                manager = emf.createEntityManager()
                println("[Conexión realizada]")
            }
        } catch (e: Exception) {
            println("[Conexión no realizada]")
        }
    }
    fun desconexion() {
        manager?.close()
        println("[Desconexión de la base de datos]")
        //Si ponemos la siguiente línea, la historia será que "destruiremos" el gestormodelo que hayamos creado,
        //para tener una nueva conexión habría que crear un objeto nuevo
        //Pero sino lo ponemos se queda creada la entidad, ¿qué sería mejor hacer?
        //emf.close()
    }
}
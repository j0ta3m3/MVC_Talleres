import controlador.AppController
import jakarta.persistence.EntityManager
import jakarta.persistence.EntityManagerFactory
import jakarta.persistence.Persistence
import modelo.clases.Cliente
import modelo.clases.Direccion
import modelo.clases.Pedido
import modelo.clases.Taller
import vista.AppVista

fun main(){

    val vista: AppVista = AppVista()
    val controlador: AppController = AppController(vista)
    var salir = false
    while(!salir){
        when(controlador.onStart()){

            3 -> controlador.onDarDeBaja()
            2 -> controlador.onAlta()
            1 -> controlador.onLoggin()
            0 -> salir = true
        }}
    controlador.onExit()

}
import controlador.AppController
import vista.AppVista

fun main(){
    //Se crea la vista
    val vista: AppVista = AppVista()
    val controlador: AppController = AppController(vista)
    //Se llama al menú inicial
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
package controlador

import modelo.clases.*
import vista.AppVista

class AppController(val vista: AppVista) {

    fun onStart(): Int {
        val gestor: GestorModelo = GestorModelo.getInstance()
        gestor.conexion()
        return vista.mainMenu()
    }

    fun onLoggin(){
        val opcion = vista.eleccionTipo()
        when(opcion){
            2 -> {var taller = onTaller(vista.loggin())
                    vista.enTaller(taller)}

            1 -> {var cliente = onCliente(vista.loggin())
                    vista.enCliente(cliente)}
            //0 ->
        }
    }

    fun onAlta(){
        val direccion = vista.direccion()
        val opcion = vista.eleccionTipo()
        val gestor = GestorModelo.getInstance()

        when(opcion){
            2 -> {val taller = vista.taller(direccion)
                    gestor.alta(taller)}
            1 -> {val cliente = vista.cliente(direccion)
                    gestor.alta(cliente)}
            //0 ->
        }
    }

    fun onExit() {
        val gestor: GestorModelo = GestorModelo.getInstance()
        gestor.desconexion()
        vista.salir()
    }

    fun onCliente(dni: MutableList<String>):Cliente?{
        val gestor = GestorModelo.getInstance()
        val cliente = gestor.buscarCliente(dni)
        if(cliente!=null){
            vista.enCliente(cliente)
        }
        else{
            vista.error()
        }
        return cliente
    }


    fun onTaller(cif: MutableList<String>):Taller?{
        val gestor = GestorModelo.getInstance()
        val taller = gestor.buscarTaller(cif)
        if(taller!=null){
            vista.enTaller(taller)
        }
        else{
            vista.error()
        }
        return taller
    }

    fun onDarDeBaja(){
        val gestor = GestorModelo.getInstance()
        val opcion = vista.eleccionTipo()
        when(opcion){
            2 -> gestor.manager?.remove(onTaller(vista.loggin()))
            1 -> gestor.manager?.remove(onCliente(vista.loggin()))
            //0 ->
        }
    }
}
package controlador

import modelo.clases.*
import vista.AppVista

class AppController(val vista: AppVista) {

    fun onStart(): Int {
        val gestor: GestorModelo = GestorModelo.getInstance()
        gestor.conexion()
        return vista.mainMenu()
    }

    fun onLoggin() {
        val opcion = vista.eleccionTipo()
        when (opcion) {
            2 -> onTaller(vista.loggin())
            1 -> onCliente(vista.loggin())
        }
    }

    fun onAlta() {
        val direccion = vista.direccion()
        val opcion = vista.eleccionTipo()
        val gestor = GestorModelo.getInstance()

        when (opcion) {
            2 -> {
                val taller = vista.taller(direccion)
                gestor.alta(taller)
            }

            1 -> {
                val cliente = vista.cliente(direccion)
                gestor.alta(cliente)
            }
        }
    }

    fun onExit() {
        val gestor: GestorModelo = GestorModelo.getInstance()
        gestor.desconexion()
        vista.salir()
    }

    fun onCliente(dni: MutableList<String>) {
        val gestor = GestorModelo.getInstance()
        val cliente = gestor.buscarCliente(dni)
        if (cliente != null) {
            onMenuCliente(vista.enCliente(cliente))
        } else {
            vista.error()
        }
    }


    fun onTaller(cif: MutableList<String>): Taller? {
        val gestor = GestorModelo.getInstance()
        val taller = gestor.buscarTaller(cif)
        if (taller != null) {
            onMenuTaller(vista.enTaller(taller))
        } else {
            vista.error()
        }
        return taller
    }

    fun onDarDeBaja() {
        val gestor = GestorModelo.getInstance()
        val opcion = vista.eleccionTipo()
        when (opcion) {
            2 -> gestor.manager?.remove(onTaller(vista.loggin()))
            1 -> gestor.manager?.remove(onCliente(vista.loggin()))
        }
    }

    fun onMenuCliente(opcion: Map<Int, Cliente?>) {
        var n = 0
        opcion.keys.forEach{n=it}
        when (n) {
            1 -> onPedido(opcion.getValue(1))
            2 -> onPedidosCliente(opcion.getValue(2)?.dni)
            3 -> onTalleresDeClientes(opcion.getValue(3)?.dni)
        }
    }

    fun onPedido(cliente: Cliente?) {
        val gestor = GestorModelo.getInstance()
        val pedido = vista.pedido(cliente)
        gestor.hacerPedido(pedido)
    }

    fun onMenuTaller(opcion: Map<Int, Taller?>){
        var n = 0
        //(opcion.getValue(1))
        opcion.keys.forEach{n=it}
        when (n) {
            1 -> onPedidosPorAsignar(opcion.getValue(1))
            2 -> onPedidosTaller(opcion.getValue(2)?.cif)
            3 -> onClientesDeTaller(opcion.getValue(3)?.cif)
        }
    }

    fun onPedidosPorAsignar(taller: Taller?){
        val gestor = GestorModelo.getInstance()
        vista.mostrarPedidos(gestor.pedidosPorAsignar())
        val pedido = vista.asignarPedido()
        if(pedido!=null){
            gestor.asignarPedido(pedido,taller)
        }
    }

    fun onPedidosCliente(dni: String?):List<Pedido>{
        val gestor = GestorModelo.getInstance()
        val lista = gestor.clienteConsultaPedidos(dni)
        vista.mostrarPedidos(lista)
        return lista
    }
    fun onPedidosTaller(cif: String?): List<Pedido>{
        val gestor = GestorModelo.getInstance()
        val lista = gestor.tallerConsultaPedidos(cif)
        vista.mostrarPedidos(lista)
        return lista
    }

    fun onTalleresDeClientes(dni: String?){
        val gestor = GestorModelo.getInstance()
        val lista = onPedidosCliente(dni)
        var listaTalleres: MutableList<Taller?> = mutableListOf()
        for(i in 0..lista.size-1){
            listaTalleres.add(gestor.infoTaller(lista[i].taller?.cif))
        }
        vista.mostrarTalleresDeClientes(listaTalleres)
    }

    fun onClientesDeTaller(cif: String?){
        val gestor = GestorModelo.getInstance()
        val lista = onPedidosTaller(cif)
        var listaClientes: MutableList<Cliente?> = mutableListOf()
        for(i in 0..lista.size-1){
            listaClientes.add(gestor.infoCliente(lista[i].cliente?.dni))
        }
        vista.mostrarClientesDeTaller(listaClientes)
    }
}
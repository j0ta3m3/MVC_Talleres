package controlador

import modelo.clases.*
import vista.AppVista


//Clase que controla las acciones entre la vista y el gestor de base de datos
class AppController(val vista: AppVista) {

    //Conexión
    fun onStart(): Int {
        val gestor: GestorModelo = GestorModelo.getInstance()
        gestor.conexion()
        return vista.mainMenu()
    }

    //Función que se loguea según es Cliente o Taller.
    fun onLoggin() {
        val opcion = vista.eleccionTipo()
        when (opcion) {
            //Taller
            2 -> onTaller(vista.loggin())
            //Cliente
            1 -> onCliente(vista.loggin())
        }
    }

    //Función que da de alta un Cliente o Taller, con una dirección.
    fun onAlta() {
        val direccion = vista.direccion()
        val opcion = vista.eleccionTipo()
        val gestor = GestorModelo.getInstance()

        when (opcion) {
            //Taller
            2 -> {
                val taller = vista.taller(direccion)
                try {
                    gestor.alta(taller)
                    vista.altaCorrecta()
                } catch (e: Exception) {
                    vista.error()
                }
            }
            //Cliente
            1 -> {
                val cliente = vista.cliente(direccion)
                try {
                    gestor.alta(cliente)
                    vista.altaCorrecta()
                } catch (e: Exception) {
                    vista.error()
                }
            }
        }
    }

    //Desconexión
    fun onExit() {
        val gestor: GestorModelo = GestorModelo.getInstance()
        gestor.desconexion()
        vista.salir()
    }

    //Busca un cliente por dni, si no es null salta el menú de cliente.
    fun onCliente(dni: MutableList<String>) {
        val gestor = GestorModelo.getInstance()
        val cliente = gestor.buscarCliente(dni)
        if (cliente != null) {
            var salir = false
            while (!salir) {
                var opcion = vista.enCliente(cliente)
                if (opcion.containsKey(1) || opcion.containsKey(2) || opcion.containsKey(3)) {
                    onMenuCliente(opcion)
                } else {
                    salir = true
                }
            }
        } else {
            vista.error()
        }
    }


    //Busca un taller por cif, si no es null salta el menú de taller.
    fun onTaller(cif: MutableList<String>): Taller? {
        val gestor = GestorModelo.getInstance()
        val taller = gestor.buscarTaller(cif)
        if (taller != null) {
            var salir = false
            while (!salir) {
                var opcion = vista.enTaller(taller)
                if (opcion.containsKey(1) || opcion.containsKey(2) || opcion.containsKey(3)) {
                    onMenuTaller(opcion)
                } else {
                    salir = true
                }
            }
        } else {
            vista.error()
        }
        return taller
    }

    fun onTallerBaja(cif: MutableList<String>): Taller? {
        val gestor = GestorModelo.getInstance()
        val taller = gestor.buscarTaller(cif)
        if (taller != null) {
            return taller
        } else {
            vista.error()
            return taller
        }
    }

    fun onClienteBaja(dni: MutableList<String>): Cliente? {
        val gestor = GestorModelo.getInstance()
        val cliente = gestor.buscarCliente(dni)
        if (cliente != null) {
            return cliente
        } else {
            vista.error()
            return cliente
        }
    }

    //Dar de baja a Cliente o Taller
    fun onDarDeBaja() {
        val gestor = GestorModelo.getInstance()
        val opcion = vista.eleccionTipo()
        when (opcion) {
            //Taller
            2 -> {
                gestor.manager?.remove(onTallerBaja(vista.loggin()))
                vista.bajaRealizada()
            }
            //Cliente
            1 -> {
                gestor.manager?.remove(onClienteBaja(vista.loggin()))
                vista.bajaRealizada()
            }
        }
    }

    //Menú Cliente
    fun onMenuCliente(opcion: Map<Int, Cliente?>) {
        var n = 0
        opcion.keys.forEach { n = it }
        when (n) {
            //Hace un pedido
            1 -> onPedido(opcion.getValue(1))
            //Ver pedidos realizados
            2 -> onPedidosCliente(opcion.getValue(2)?.dni)
            //Ver talleres relacionados
            3 -> onTalleresDeClientes(opcion.getValue(3)?.dni)
        }
    }

    //Función que pide por teclado los datos y da de alta a un pedido.
    fun onPedido(cliente: Cliente?) {
        val gestor = GestorModelo.getInstance()
        val pedido = vista.pedido(cliente)
        try {
            gestor.hacerPedido(pedido)
            vista.altaCorrecta()
        } catch (e: Exception) {
            vista.error()
        }
    }

    //Menú Taller
    fun onMenuTaller(opcion: Map<Int, Taller?>) {
        var n = 0
        //(opcion.getValue(1))
        opcion.keys.forEach { n = it }
        when (n) {
            //Consulta pedidos por asignar
            1 -> onPedidosPorAsignar(opcion.getValue(1))
            //Consulta pedidos asignados
            2 -> onPedidosTaller(opcion.getValue(2)?.cif)
            //Consulta clientes del taller
            3 -> onClientesDeTaller(opcion.getValue(3)?.cif)
        }
    }

    //Función que muestra los pedidos que aún no ha aceptado ningún taller
    fun onPedidosPorAsignar(taller: Taller?) {
        val gestor = GestorModelo.getInstance()
        vista.mostrarPedidos(gestor.pedidosPorAsignar())
        val pedido = vista.asignarPedido()
        if (pedido != null) {
            gestor.asignarPedido(pedido, taller)
        }
    }

    //Función que consulta los pedidos con un dni y se muestran.
    fun onPedidosCliente(dni: String?) {
        val gestor = GestorModelo.getInstance()
        try {
            val lista = gestor.clienteConsultaPedidos(dni)
            vista.mostrarPedidos(lista)
        } catch (e: Exception) {
            vista.error()
        }
    }

    fun listaPedidosCliente(dni: String?): List<Pedido> {
        val gestor = GestorModelo.getInstance()
        try {
            val lista = gestor.clienteConsultaPedidos(dni)
            return lista
        } catch (e: Exception) {
            vista.error()
            return emptyList()
        }
    }

    //Función que consulta los pedidos con un cif y se muestran.
    fun onPedidosTaller(cif: String?): List<Pedido> {
        val gestor = GestorModelo.getInstance()
        try {
            val lista = gestor.tallerConsultaPedidos(cif)
            vista.mostrarPedidos(lista)
            return lista
        } catch (e: Exception) {
            vista.error()
            return emptyList()
        }
    }

    //Función que selecciona los talleres asociados con un dni concreto
    fun onTalleresDeClientes(dni: String?) {
        val gestor = GestorModelo.getInstance()
        val lista = listaPedidosCliente(dni)
        var listaTalleres: MutableList<Taller?> = mutableListOf()
        try {
            for (i in 0..lista.size - 1) {
                listaTalleres.add(gestor.infoTaller(lista[i].taller?.cif))
            }
            vista.mostrarTalleresDeClientes(listaTalleres)
        } catch (e: Exception) {
            vista.error()
        }
    }

    //Función que selecciona los clientes asociados a un cif concreto
    fun onClientesDeTaller(cif: String?) {
        val gestor = GestorModelo.getInstance()
        val lista = onPedidosTaller(cif)
        var listaClientes: MutableList<Cliente?> = mutableListOf()
        try {
            for (i in 0..lista.size - 1) {
                listaClientes.add(gestor.infoCliente(lista[i].cliente?.dni))
            }
            vista.mostrarClientesDeTaller(listaClientes)
        } catch (e: Exception) {
            vista.error()
        }
    }
}
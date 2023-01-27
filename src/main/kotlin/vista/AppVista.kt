package vista

import modelo.clases.Cliente
import modelo.clases.Direccion
import modelo.clases.Pedido
import modelo.clases.Taller

class AppVista {
    //Menú Inicial
    fun mainMenu(): Int {
        var opcion = 0
        println("Bienvenid@, elige un opción")
        println("1. Loggin")
        println("2. Dar de alta")
        println("3. Dar de baja")
        println("0. Salir (o cualquier otra tecla)")
        try {
            opcion = readln().trim().toInt()
        } catch (e: Exception) {
        }
        return opcion
    }

    //Menú para elegir entre Cliente y Taller.
    fun eleccionTipo(): Int {
        var opcion = 0
        println("1. Cliente")
        println("2. Taller")
        println("Cualquier otra tecla - Atrás")
        try {
            opcion = readln().trim().toInt()
        } catch (e: Exception) {
        }
        return opcion
    }

    //Función para introducir datos sobre una dirección.
    fun direccion(): Direccion {
        println("Tendrás que introducir primero los campos de la dirección: ")
        println("Introduce la calle: ")
        val calle = readln()
        println("Introduce el número: ")
        var num = -1
        while (num < 0) {
            try {
                num = readln().toInt()
            } catch (e: Exception) {
                println("Introduce un número válido: ")
            }
        }
        println("Introduce el código postal: ")
        val cp = readln()
        println("Introduce la ciudad: ")
        val ciudad = readln()
        return Direccion(calle, num, cp, ciudad)
    }

    //Vista despedida.
    fun salir() {
        println("Adios")
    }

    //Función para introducir datos sobre un taller.
    fun taller(direccion: Direccion): Taller {
        println("Introduce el CIF: ")
        val cif = readln()
        println("Introduce el nombre: ")
        val nombre = readln()
        val taller = Taller(cif, nombre, direccion)
        println("Introduce una contraseña: ")
        taller.encriptar(readln())
        return taller
    }

    //Función para introducir datos sobre un cliente.
    fun cliente(direccion: Direccion): Cliente {
        println("Introduce tu DNI: ")
        val dni = readln()
        println("Introduce tu nombre: ")
        val nombre = readln()
        println("Introduce tu edad: ")
        var edad = 0
        while (edad < 1) {
            try {
                edad = readln().toInt()
            } catch (e: Exception) {
                println("Introduce una edad válida: ")
            }
        }
        println("Introduce tu email: ")
        val email = readln()
        val cliente = Cliente(dni, nombre, edad, email, direccion)
        println("Introduce una contraseña: ")
        cliente.encriptar(readln())
        return cliente
    }

    //Menú Taller
    fun enTaller(taller: Taller?): Map<Int, Taller?> {
        var opcion = 0
        println("¿Qué deseas realizar?")
        println("1. Consultar pedidos por asignar")
        println("2. Consultar pedidos asignados")
        println("3. Consultar clientes del taller")
        println("Cualquier otra tecla para ir a atrás")
        try {
            opcion = readln().trim().toInt()
        } catch (e: Exception) {
        }
        val mapa: Map<Int, Taller?> = mapOf(opcion to taller)
        return mapa
    }

    //Menú Cliente
    fun enCliente(cliente: Cliente?): Map<Int, Cliente?> {
        var opcion = 0
        println("*****Bienvenid@*****")
        println("¿Qué deseas realizar?")
        println("1. Hacer pedido")
        println("2. Ver pedidos realizados")
        println("3. Ver talleres relacionados")
        try {
            opcion = readln().trim().toInt()
        } catch (e: Exception) {
        }
        val mapa: Map<Int, Cliente?> = mapOf(opcion to cliente)
        return mapa
    }

    //Menú para loguearse.
    fun loggin(): MutableList<String> {
        var lista: MutableList<String> = mutableListOf()
        println("Introduce el número identificativo: ")
        val id = readln()
        println("Introduce la contraseña: ")
        val pass = readln()
        lista.add(id)
        lista.add(pass)
        return lista
    }

    //Vista de error.
    fun error() {
        println("Error, algún dato es incorrecto.")
    }

    //Función para introducir datos sobre un pedido.
    fun pedido(cliente: Cliente?): Pedido {
        println("Introduzca la descripción de su pedido")
        val descripcion = readln()
        return Pedido(descripcion, cliente)
    }

    //Vista que muestra los pedidos.
    fun mostrarPedidos(pedidos: List<Pedido>) {
        for(i in 0..pedidos.size-1){
            println("El id del pedido es: "+pedidos[i].id+", consta de "+ pedidos[i].descr+", pedido por el cliente"
                    + pedidos[i].cliente+", asignado al taller "+pedidos[i].taller+".")
        }
    }

    //Tras ver los pedidos que aún no ha aceptado ningún taller, da la opción al taller de elegir uno para asignárselo
    fun asignarPedido(): Long? {
        var res = "p"
        var pedido: Long? = null
        while (res != "S" || res != "N") {
            println("¿Desea aceptar alguno de los pedidos? S/N")
            res = readln().toUpperCase()
            if(res=="S"){
                println("Introduce el id del pedido: ")
                try{
                    pedido = readln().toLong()
                } catch(e: Exception){}
            }
        }
        return pedido
    }

    //Vista que muestra los talleres asociados a un cliente determinado.
    fun mostrarTalleresDeClientes(lista: MutableList<Taller?>){
        for(i in 0..lista.size-1){
            println("El cif del taller: "+lista[i]?.cif+", de nombre "+ lista[i]?.nombre+", en la dirección"
                    + lista[i]?.direccion?.calle+", "+lista[i]?.direccion?.ciudad+".")
        }
    }
    //Vista que muestra los clientes asociados a un taller determinado.
    fun mostrarClientesDeTaller(lista: MutableList<Cliente?>){
        for(i in 0..lista.size-1){
            println("El dni del cliente: "+lista[i]?.dni+", de nombre "+ lista[i]?.nombre+", email"
                    + lista[i]?.email+", que vive en "+lista[i]?.direccion?.calle+", "+lista[i]?.direccion?.ciudad+".")
        }
    }
}
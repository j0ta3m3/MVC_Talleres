package vista

import modelo.clases.Cliente
import modelo.clases.Direccion
import modelo.clases.Pedido
import modelo.clases.Taller

class AppVista {

    fun mainMenu(): Int {
        println("Bienvenid@, elige un opción")
        println("1. Loggin")
        println("2. Dar de alta")
        println("3. Dar de baja")
        println("0. Salir")
        return readln().trim().toInt()
    }

    fun eleccionTipo(): Int {
        println("1. Cliente")
        println("2. Taller")
        println("0. Atrás")
        return readln().trim().toInt()
    }

    fun direccion(): Direccion {
        println("Tendrás que introducir primero los campos de la dirección: ")
        println("Introduce la calle: ")
        val calle = readln()
        println("Introduce el número: ")
        val num = readln().toInt()
        println("Introduce el código postal: ")
        val cp = readln()
        println("Introduce la ciudad: ")
        val ciudad = readln()
        return Direccion(calle, num, cp, ciudad)
    }

    fun salir() {
        println("Adios")
    }

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

    fun cliente(direccion: Direccion): Cliente {
        println("Introduce tu DNI: ")
        val dni = readln()
        println("Introduce tu nombre: ")
        val nombre = readln()
        println("Introduce tu edad: ")
        val edad = readln().toInt()
        println("Introduce tu email: ")
        val email = readln()
        val cliente = Cliente(dni, nombre, edad, email, direccion)
        println("Introduce una contraseña: ")
        cliente.encriptar(readln())
        return cliente
    }

    fun enTaller(taller: Taller?) {}

    fun enCliente(cliente: Cliente?): Map<Int, Cliente?> {
        println("*****Bienvenid@*****")
        println("¿Qué deseas realizar?")
        println("1. Hacer pedido")
        println("2. Ver pedidos realizados")
        println("3. Ver talleres relacionados")
        val mapa: Map<Int, Cliente?> = mapOf(readln().trim().toInt() to cliente)
        return mapa
    }

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

    fun error() {
        println("Error, algún dato es incorrecto.")
    }

    fun pedido(cliente: Cliente?): Pedido {
        println("Introduzca la descripción de tu pedido")
        val descripcion = readln()
        return Pedido(descripcion, cliente)
    }

}
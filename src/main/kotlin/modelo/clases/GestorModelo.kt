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
    private val emf: EntityManagerFactory = Persistence.createEntityManagerFactory("EjercicioTaller")
    var manager: EntityManager? = null

    //Conexión a la base de datos.
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

    //Desconexión a la base de datos.
    fun desconexion() {
        manager?.close()
        emf.close()
        println("[Desconexión de la base de datos]")
    }

    //Función que da de alta a Cliente o Taller.
    fun alta(entidad: Any){
        manager?.transaction?.begin()
        manager?.persist(entidad)
        manager?.transaction?.commit()
    }

    //Función que busca un cliente por un id.
    fun buscarCliente(id: MutableList<String>): Cliente?{
            val cliente = manager?.find(Cliente::class.java,id[0])
            if(cliente?.desencriptar()==id[1]){
                return cliente
            }
        else return null
    }

    //Función que busca un taller por un id.
    fun buscarTaller(id: MutableList<String>):Taller?{
        val taller = manager?.find(Taller::class.java,id[0])
        if(taller?.desencriptar() == id[1] ){
            return taller}
        else return null
    }

    //Función que busca un pedido según el cif del taller.
    fun tallerConsultaPedidos(cif: String?): List<Pedido> {
        val lista = manager?.createQuery("FROM Pedido WHERE taller = $cif")?.resultList as List<Pedido>
        return lista
    }

    //Función que busca un pedido según el dni del cliente.
    fun clienteConsultaPedidos(dni: String?): List<Pedido>{
        val lista = manager?.createQuery("FROM Pedido WHERE cliente = $dni")?.resultList as List<Pedido>
        return lista
    }

    //Función que busca un cliente por un dni.
    fun infoCliente(dni: String?):Cliente{
        val cliente = manager?.createQuery("FROM Cliente WHERE dni = $dni")?.singleResult as Cliente
        return cliente
    }

    //Función que busca un taller por un cif.
    fun infoTaller(cif: String?):Taller{
        val taller = manager?.createQuery("FROM Taller WHERE cif = $cif")?.singleResult as Taller
        return taller
    }

    //Función que da de alta a un pedido.
    fun hacerPedido(pedido: Pedido){
        manager?.transaction?.begin()
        manager?.persist(pedido)
        manager?.transaction?.commit()
    }

    //Función que
    fun pedidosPorAsignar(): List<Pedido>{
        val lista = manager?.createQuery("FROM Pedido WHERE taller = null")?.resultList as List<Pedido>
        return lista
    }

    //Función que
    fun asignarPedido(id: Long?, taller: Taller?){
        val pedido = buscarPedido(id)
        manager?.transaction?.begin()
        pedido?.taller = taller
        manager?.transaction?.commit()
    }

    //Función que busca un pedido por un id.
    private fun buscarPedido(id: Long?): Pedido?{
        return manager?.find(Pedido::class.java,id)
    }
}
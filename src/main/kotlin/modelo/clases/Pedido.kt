package modelo.clases

import jakarta.persistence.*

//Tabla Pedido
@Entity
@Table(name = "pedidos")
class Pedido(
    //Descripción
    @Column(name = "descripcion")
    var descr: String,
    //Relación ManyToOne entre Pedido y Cliente, cogiendo el dni del cliente.
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "dni_cliente")
    var cliente: Cliente?,
    //Relación ManyToOne entre Pedido y Taller, cogiendo el cif del taller.
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "CIF_taller")
    var taller: Taller? = null,
    //Id del pedido, generado automaticamente.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null
) {
}
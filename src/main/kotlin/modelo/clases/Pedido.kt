package modelo.clases

import jakarta.persistence.*

@Entity
@Table(name = "pedidos")
class Pedido(
    @Column(name = "descripcion")
    var descr: String,
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "dni_cliente")
    var cliente: Cliente?,
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "CIF_taller")
    var taller: Taller? = null,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null
) {
}
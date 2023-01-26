package modelo.clases

import jakarta.persistence.*

@Entity
@Table(name="cliente")
class Cliente(
    @Id
    @Column(name="dni")
    var dni: String,
    @Column(name="Nombre")
    var nombre: String,
    @Column(name="Ciudad")
    var ciudad: String,
    @Column(name="edad")
    var edad: Int,
    @Column(name="email")
    var email: String,
    @OneToOne(cascade =[CascadeType.ALL])
    @JoinColumn(name="id_direccion")
    var direccion: Direccion,
    @ManyToMany(mappedBy = "clientes", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var talleres: Set<Taller>?=null,
    @OneToMany(mappedBy = "cliente", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var pedidos: Set<Pedido>?=null
) {
}
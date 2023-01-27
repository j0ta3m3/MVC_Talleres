package modelo.clases

import jakarta.persistence.*

//Tabla Dirección
@Entity
@Table(name="direcciones")
class Direccion(
    //Calle
    @Column(name  = "calle")
    var calle: String,
    //Número
    @Column(name  = "numero")
    var numero: Int,
    //CP
    @Column(name  = "cp")
    var cp: String,
    //Ciudad
    @Column(name="ciudad")
    var ciudad: String,

    //Relación OneToOne entre Dirección y Cliente.
    @OneToOne(mappedBy = "direccion", cascade = [CascadeType.ALL],fetch = FetchType.LAZY)
    var cliente: Cliente?=null,
    //Relación OneToOne entre Dirección y Taller.
    @OneToOne(mappedBy = "direccion", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var taller: Taller?=null,

    //Id de dirección, generado automaticamente.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name  = "id")
    var id: Long? = null){
}
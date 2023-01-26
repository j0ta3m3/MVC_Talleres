package modelo.clases

import jakarta.persistence.*

@Entity
@Table(name = "taller")
class Taller(
    @Id
    @Column(name = "CIF")
    var cif: String,
    @Column(name = "Nombre")
    var nombre: String,
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "id_direccion")
    var direccion: Direccion,
    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinTable(
        name = "taller_cliente", joinColumns = [JoinColumn(name = "CIF")],
        inverseJoinColumns = [JoinColumn(name = "DNI")]
    )
    var clientes: Set<Cliente>? = null,
    @OneToMany(mappedBy = "taller", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var pedidos: Set<Pedido>?=null
){
    @Column(name="password")
    var password = ""

    fun encriptar(pass: String){
        var newpass = pass.toCharArray()
        for(i in 0..newpass.size-1){
            newpass[i] = newpass[i]+3
        }
        password = newpass.toString()
    }

    fun desencriptar():String{
        var newpass = password.toCharArray()
        for(i in 0..newpass.size-1){
            newpass[i] = newpass[i]-3
        }
        return newpass.toString()
    }
}
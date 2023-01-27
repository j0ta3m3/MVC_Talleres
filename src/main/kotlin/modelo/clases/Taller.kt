package modelo.clases

import jakarta.persistence.*

//Tabla Taller
@Entity
@Table(name = "taller")
class Taller(
    //CIF, clave primaria
    @Id
    @Column(name = "CIF")
    var cif: String,
    //Nombre
    @Column(name = "Nombre")
    var nombre: String,
    //Relación OneToOne entre Taller y Dirección, cogiendo el id de la dirección.
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "id_direccion")
    var direccion: Direccion,
    //Relación OneToMany entre Taller y Pedido.
    @OneToMany(mappedBy = "taller", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var pedidos: Set<Pedido>?=null
){  //Contraseña
    @Column(name="password")
    var password = ""

    //Función para encriptar la contraseña.
    fun encriptar(pass: String){
        var newpass = pass.toCharArray()
        for(i in 0..newpass.size-1){
            password = password + (newpass[i]+3)
        }
        password = password.trim()
    }

    //Función para desencriptar la contraseña.
    fun desencriptar():String{
        var newpass = password.toCharArray()
        var passDes = ""
        for(i in 0..newpass.size-1){
            passDes = passDes+(newpass[i]-3)
        }
        return passDes
    }
}
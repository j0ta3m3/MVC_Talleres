package modelo.clases

import jakarta.persistence.*

//Tabla Cliente
@Entity
@Table(name="cliente")
class Cliente(
    //DNI, clave primaria
    @Id
    @Column(name="dni")
    var dni: String,
    //Nombre
    @Column(name="Nombre")
    var nombre: String,
    //Edad
    @Column(name="edad")
    var edad: Int,
    //Email
    @Column(name="email")
    var email: String,
    //Relación OneToOne entre Cliente y Dirección, cogiendo el id de dirección.
    @OneToOne(cascade =[CascadeType.ALL])
    @JoinColumn(name="id_direccion")
    var direccion: Direccion,
    //Relación OneToMany entre Cliente y Pedido.
    @OneToMany(mappedBy = "cliente", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var pedidos: Set<Pedido>?=null
){  //Contraseña, se guarda encriptada en la base de datos
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
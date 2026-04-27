package Usuario.domain;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Usuario{
    @Id
    Long id;
    /*
    long id
    stringnombre
    string nombre_usuario
    string biografia
    string correo
    string contraseña
    ____ foto_perfil
     */
}
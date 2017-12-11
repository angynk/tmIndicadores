package com.tmIndicadores.controller.servicios;

import com.tmIndicadores.model.dao.TareaDao;
import com.tmIndicadores.model.dao.UsuarioDao;
import com.tmIndicadores.model.entity.Tarea;
import com.tmIndicadores.model.entity.Usuario;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service("UsuariosService")
public class UsuarioServicios {

    @Autowired
    public UsuarioDao usuarioDao;

    @Autowired
    public TareaDao tareaDao;

    public void addUsuario(Usuario usuario) {
        usuarioDao.addUsuario(usuario);
    }

    public void deleteUsuario(Usuario usuario) {
       usuarioDao.deleteUsuario(usuario);
    }


    public void updateUsuario(Usuario usuario) {
        usuarioDao.updateUsuario(usuario);
    }




    public List<Usuario> getUsuarioAll() {
        return usuarioDao.getUsuarioAll();
    }

    public Usuario encontrarUsuarioByNombreUsuario(String user){
        return usuarioDao.encontrarUsuarioByNombreUsuario(user);
    }

    public Tarea encontrarTareaByNombre(String nombre) {
        return tareaDao.encontrarTareaByNombre(nombre);
    }

    public String getEmailSuperUsuario() {
        Usuario usuario = encontrarUsuarioByNombreUsuario("admin");
        if(usuario!=null){
            return usuario.getEmail();
        }
        return "appsfortm@gmail.com";
    }
}

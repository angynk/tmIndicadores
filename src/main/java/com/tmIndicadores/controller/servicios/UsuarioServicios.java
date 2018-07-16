package com.tmIndicadores.controller.servicios;

import com.tmIndicadores.model.dao.AplicacionDao;
import com.tmIndicadores.model.dao.RolAplicacionDao;
import com.tmIndicadores.model.dao.TareaDao;
import com.tmIndicadores.model.dao.UsuarioDao;
import com.tmIndicadores.model.entity.Aplicacion;
import com.tmIndicadores.model.entity.RolAplicacion;
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

    @Autowired
    public AplicacionDao aplicacionDao;

    @Autowired
    public RolAplicacionDao rolAplicacionDao;

    public void addUsuario(Usuario usuario) {
        usuarioDao.addUsuario(usuario);
    }

    public void deleteUsuario(Usuario usuario) {
       usuarioDao.deleteUsuario(usuario);
    }


    public void updateUsuario(Usuario usuario) {
        usuarioDao.updateUsuario(usuario);
    }

    public Aplicacion getAplicacion(int idAplicacion) {
        return aplicacionDao.obtenerAplicacionById(idAplicacion);
    }

    public RolAplicacion getRolUsuarioAplicacion(Aplicacion aplicacion, Usuario usuario) {
        return rolAplicacionDao.getRolAplicacion(aplicacion,usuario);
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

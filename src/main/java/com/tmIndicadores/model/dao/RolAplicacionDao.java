package com.tmIndicadores.model.dao;

import com.tmIndicadores.model.entity.Aplicacion;
import com.tmIndicadores.model.entity.RolAplicacion;
import com.tmIndicadores.model.entity.Usuario;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class RolAplicacionDao {

    @Autowired
    private SessionFactory sessionFactory;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public RolAplicacion getRolAplicacion(Aplicacion aplicacion, Usuario usuario) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(RolAplicacion.class);
        criteria.add(Restrictions.eq("aplicacion", aplicacion));
        criteria.add(Restrictions.eq("usuario", usuario));
        return (RolAplicacion) criteria.uniqueResult();
    }
}

package com.tmIndicadores.model.dao;

import com.tmIndicadores.model.entity.DhProgramacion;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class DhProgramacionDao {

    @Autowired
    private SessionFactory sessionFactory;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addProgramacion(DhProgramacion programacion) {
        Serializable save = getSessionFactory().getCurrentSession().save(programacion);

    }

    public void deleteProgramacion(DhProgramacion programacion) {
        getSessionFactory().getCurrentSession().delete(programacion);
    }


    public void updateProgramacion(DhProgramacion programacion) {
        getSessionFactory().getCurrentSession().update(programacion);
    }

    public DhProgramacion encontrarProgramacion(String identificador) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(DhProgramacion.class);
        criteria.add(Restrictions.eq("identificador",identificador));
        return (DhProgramacion) criteria.uniqueResult();
    }

    public List<DhProgramacion> getProgramaciones(Date fechaInicio, Date fechaFin, String tipoDia, String modo,String tipoDatos) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(DhProgramacion.class);
        criteria.add(Restrictions.eq("tipoDia",tipoDia));
        criteria.add(Restrictions.eq("modo",modo));
        criteria.add(Restrictions.between("fecha",fechaInicio,fechaFin));

        if(!tipoDatos.equals("D")){
            criteria.add(Restrictions.eq("tipoProgramacion",tipoDatos));
        }
        return criteria.list();
    }

}

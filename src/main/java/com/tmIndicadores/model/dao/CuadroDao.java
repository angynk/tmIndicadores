package com.tmIndicadores.model.dao;

import com.tmIndicadores.model.entity.Cuadro;
import com.tmIndicadores.model.entity.DhProgramacion;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.SessionImplementor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@Transactional
public class CuadroDao {

    @Autowired
    private SessionFactory sessionFactory;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addCuadro(Cuadro cuadro) {
        Serializable save = getSessionFactory().getCurrentSession().save(cuadro);
    }

    public List<Cuadro> obtenerCuadrosProgramacion(DhProgramacion progr) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Cuadro.class);
        criteria.add(Restrictions.eq("programacion",progr));
        return criteria.list();
    }

    public Cuadro obtenerCuadro(DhProgramacion programacion, String cuadro) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Cuadro.class);
        criteria.add(Restrictions.eq("programacion",programacion));
        criteria.add(Restrictions.eq("numero",cuadro));
        return (Cuadro) criteria.uniqueResult();
    }

    public List<Cuadro> obtenerCuadroNumero(String numero) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Cuadro.class);
        criteria.add(Restrictions.eq("numero",numero));
        return criteria.list();
    }

    public List<Cuadro> getCuadros(List<DhProgramacion> programaciones, String tipologia) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Cuadro.class);
        criteria.add(Restrictions.eq("tipologia",tipologia));
        criteria.add(Restrictions.in("programacion",programaciones));
        return criteria.list();
    }
}

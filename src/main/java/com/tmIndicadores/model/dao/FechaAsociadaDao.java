package com.tmIndicadores.model.dao;

import com.tmIndicadores.model.entity.FechaAsociada;
import com.tmIndicadores.model.entity.Programacion;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class FechaAsociadaDao {


    @Autowired
    private SessionFactory sessionFactory;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addFechaAsociada(FechaAsociada fechaAsociada) {
        Serializable save = getSessionFactory().getCurrentSession().save(fechaAsociada);

    }

    public void deleteFechaAsociada(FechaAsociada fechaAsociada) {
        getSessionFactory().getCurrentSession().delete(fechaAsociada);
    }


    public void updateFechaAsociada(FechaAsociada fechaAsociada) {
        getSessionFactory().getCurrentSession().update(fechaAsociada);
    }

    public List<FechaAsociada> getFechasAsociadasProgramacion(Programacion programacion){
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(FechaAsociada.class);
        criteria.add(Restrictions.eq("programacion", programacion));
        return criteria.list();
    }

    public List<FechaAsociada> getFechasBaseForReport(Date fechaInicio, Date fechaFin,String modo,String tipologia){
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(FechaAsociada.class, "FECHAS");
     //   criteria.add(Restrictions.eq("FECHAS.id","id"));
        criteria.add(Restrictions.between("FECHAS.fecha", fechaInicio,fechaFin));
        criteria.createAlias("FECHAS.programacion", "p");

        criteria.add(Restrictions.eq("p.modo", modo));
        criteria.add(Restrictions.eq("p.tipologia", tipologia));
        criteria.addOrder(Order.asc("FECHAS.fecha"));
        return (List<FechaAsociada>) criteria.list();
    }

    public List<FechaAsociada> getFechasAsociadas(Date date) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(FechaAsociada.class);
        criteria.add(Restrictions.eq("fecha", date));
        return criteria.list();
    }
}

package com.tmIndicadores.model.dao;

import com.tmIndicadores.model.entity.Programacion;
import com.tmIndicadores.model.entity.Usuario;
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
public class ProgramacionDao {

    @Autowired
    private SessionFactory sessionFactory;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addProgramacion(Programacion programacion) {
        Serializable save = getSessionFactory().getCurrentSession().save(programacion);

    }

    public void deleteProgramacion(Programacion programacion) {
        getSessionFactory().getCurrentSession().delete(programacion);
    }


    public void updateProgramacion(Programacion programacion) {
        getSessionFactory().getCurrentSession().update(programacion);
    }

    public boolean isCuadroAlready(String cuadro){
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Programacion.class);
        criteria.add(Restrictions.eq("cuadro", cuadro));
        List list = criteria.list();
        if(list.size()>0){
            return true;
        }
        return false;
    }

    public List<Programacion> getProgramacionbyAttributes(Date fechaInicio, Date fechaFin, String periocidad,String tipologia){
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Programacion.class);
        criteria.add(  Restrictions.between( "fecha",fechaInicio, fechaFin)  );

        criteria.add(Restrictions.eq("tipologia", tipologia));
        criteria.addOrder(Order.asc("fecha"));

        if(!periocidad.equals("TODOS")){
            criteria.add(Restrictions.eq("periodicidad", periocidad));
        }
        return criteria.list();
    }

    public List<Programacion> getProgramacionbyAttributes(Date fechaInicio, Date fechaFin, String periocidad){
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Programacion.class);
        criteria.add(  Restrictions.between( "fecha",fechaInicio, fechaFin)  );
        if(!periocidad.equals("TODOS")){
            criteria.add(Restrictions.eq("periodicidad", periocidad));
        }
        criteria.addOrder(Order.asc("fecha"));
        return criteria.list();
    }

    public List<Programacion> getProgramacionesUltimoMes(String periocidad,Date fechaInicio, Date fechaFin){
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Programacion.class);
        criteria.add(  Restrictions.between( "fecha",fechaInicio, fechaFin)  );
        System.out.println(fechaInicio+"-"+fechaFin);
        criteria.add(Restrictions.eq("periodicidad", periocidad));
        criteria.addOrder(Order.desc("fecha"));
        return criteria.list();
    }
}

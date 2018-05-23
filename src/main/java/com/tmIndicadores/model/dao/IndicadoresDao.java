package com.tmIndicadores.model.dao;

import com.tmIndicadores.model.entity.Cuadro;
import com.tmIndicadores.model.entity.Indicadores;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Repository
@Transactional
public class IndicadoresDao {

    @Autowired
    private SessionFactory sessionFactory;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addIndicador(Indicadores indicadores) {
        Serializable save = getSessionFactory().getCurrentSession().save(indicadores);

    }

    public void deleteIndicador(Indicadores indicadores) {
        getSessionFactory().getCurrentSession().delete(indicadores);
    }


    public void updateIndicador(Indicadores indicadores) {
        getSessionFactory().getCurrentSession().update(indicadores);
    }


    public List<Indicadores> getIndicadores(List<Cuadro> cuadros) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Indicadores.class);
        criteria.add(Restrictions.in("cuadro",cuadros));
        return criteria.list();
    }
}

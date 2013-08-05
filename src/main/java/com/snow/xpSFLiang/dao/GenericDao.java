package com.snow.xpSFLiang.dao;

public class GenericDao extends BaseHibernateDao {

    public GenericDao(Class entityClass){
        this.entityClass = entityClass;
    }
}

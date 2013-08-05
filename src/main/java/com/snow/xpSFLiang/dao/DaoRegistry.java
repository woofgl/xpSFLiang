package com.snow.xpSFLiang.dao;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.britesnow.snow.web.binding.EntityClasses;
import com.snow.xpSFLiang.entity.BaseEntity;
import com.snow.xpSFLiang.util.PackageScannerUtil;
import com.google.common.base.Throwables;
import com.google.inject.Injector;

@Singleton
public class DaoRegistry {

    public Map<Class<? extends BaseEntity>, IDao>   daoByEntity       = new HashMap<Class<? extends BaseEntity>, IDao>();

    public Map<String, Class<? extends BaseEntity>> entityClassByName = new HashMap<String, Class<? extends BaseEntity>>();

    @Inject
    public void init(Injector injector, @EntityClasses Class[] entityClasses) {
        System.out.println("DaoRegistry init");
        // First, register the typed DAOs
        Class<BaseHibernateDao>[] classes = PackageScannerUtil.findChildrenOf(BaseHibernateDao.class);
        try {
            for (Class cls : classes) {
                if (cls != GenericDao.class){
                    BaseHibernateDao dao = (BaseHibernateDao) cls.newInstance();
                    registerDao(injector,dao,dao.getEntityClass());
                }
            }
        } catch (Exception e) {
            Throwables.propagate(e);
        }

        // Then, for all entityClasses that do not have explicit daos, create an instance for each based on the 
        // GenericDao
        for (Class entityClass : entityClasses) {
            if (daoByEntity.get(entityClass) == null){
                GenericDao dao = new GenericDao(entityClass);
                registerDao(injector,dao,entityClass);
            }
        }

    }
    
    private void registerDao(Injector injector, IDao dao, Class entityClass){
        injector.injectMembers(dao);
        daoByEntity.put(entityClass, dao);
        entityClassByName.put(entityClass.getSimpleName(), entityClass);
    }

    public <E> IDao<E> getDao(Class<E> cls) {
        return daoByEntity.get(cls);
    }

    public IDao getDao(String entityClass) {
        Class cls = entityClassByName.get(entityClass);
        return daoByEntity.get(cls);
    }

}

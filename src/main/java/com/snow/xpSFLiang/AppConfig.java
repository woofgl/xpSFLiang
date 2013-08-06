package com.snow.xpSFLiang;


import com.britesnow.snow.web.auth.AuthRequest;
import com.britesnow.snow.web.binding.EntityClasses;
import com.google.inject.*;
import com.snow.xpSFLiang.dao.DaoRegistry;
import com.snow.xpSFLiang.dao.IDao;
import com.snow.xpSFLiang.entity.BaseEntity;
import com.snow.xpSFLiang.util.PackageScannerUtil;
import com.snow.xpSFLiang.web.AppAuthRequest;

import javax.inject.Inject;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class AppConfig extends AbstractModule {

    private static Class[] entityClasses = PackageScannerUtil.findChildrenOf(BaseEntity.class);

    
    @Override
    protected void configure() {
        
        bind(AuthRequest.class).to(AppAuthRequest.class);
        // bind the dao
        for (Class entityClass : entityClasses){
            bindDao(entityClass);
        }
    }

    @Provides
    @Singleton
    @EntityClasses
    public Class[] provideEntityClasses() {
        return entityClasses;
    }
    
    private <T> void bindDao(final Class entityClass){
        //bind(TypeLiteral.get(iDaoEmployee)).to( (Class<? extends IDao>) EmployeeDao.class);
        Type daoParamType = new ParameterizedType() {
            public Type getRawType() {
                return IDao.class;
            }

            public Type getOwnerType() {
                return null;
            }

            public Type[] getActualTypeArguments() {
                return new Type[] {entityClass};
            }
        };        
        
        DaoProvider daoProvider = new DaoProvider(entityClass);
        requestInjection(daoProvider);
        bind(TypeLiteral.get(daoParamType)).toProvider(daoProvider);
    }    

}

class DaoProvider implements Provider{
    
    private Class entityClass;
    
    @Inject
    private DaoRegistry daoRegistry;

    public DaoProvider(Class entityClass){
        this.entityClass = entityClass;
    }

    @Override
    public Object get() {
        return daoRegistry.getDao(entityClass);
    }
    
}

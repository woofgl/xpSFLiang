package com.cisco.wt.test;

public class SimpleTest extends BaseWebTest {

    //@Test
/*    public void SimpleDaoHelperTest(){
        System.out.println("test....");
        
        HibernateDaoHelper daoHelper = appInjector.getInstance(HibernateDaoHelper.class);
        
        HibernateSessionInViewHandler hsv = appInjector.getInstance(HibernateSessionInViewHandler.class);
        hsv.openSessionInView();
        

        User user = daoHelper.get(User.class, 1);
        System.out.println("User: " + user.getUsername());
        
        List<User> users = (List<User>) daoHelper.find(0, 10, "from User");
        System.out.println("users: " + users.size());
        
        hsv.closeSessionInView();
        
    }
    
    @Test
    public void daoTest(){
        DaoRegistry daoRegistry = appInjector.getInstance(DaoRegistry.class);
        IDao dao = daoRegistry.getDao("User");
        HibernateSessionInViewHandler hsv = appInjector.getInstance(HibernateSessionInViewHandler.class);
        hsv.openSessionInView();
        List list = dao.list(0, 10, null, null);
        hsv.closeSessionInView();
        System.out.println("list.size(): " + list.size());
    }*/
}

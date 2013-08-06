package com.snow.xpSFLiang.web;

import java.io.IOException;
import java.util.Map;



import com.britesnow.snow.web.RequestContext;
import com.britesnow.snow.web.param.annotation.WebModel;
import com.britesnow.snow.web.param.annotation.WebParam;
import com.britesnow.snow.web.rest.annotation.WebGet;
import com.snow.xpSFLiang.dao.SocialIdEntityDao;
import com.snow.xpSFLiang.entity.User;
import com.snow.xpSFLiang.oauth.SalesForceService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.sf.json.JSONException;

@Singleton
public class SFContactWebHandlers {
    
    @Inject
    private SocialIdEntityDao socialIdEntityDao;
    @Inject
    private SalesForceService salesforceService;
    
    @WebGet("/salesforce/listContacts")
    public Map listSFContacts(@WebModel Map m,
                               @WebParam("pageSize") Integer pageSize, @WebParam("pageIndex") Integer pageIndex,RequestContext rc) throws  IOException, JSONException {
        User user = rc.getUser(User.class);
        String token = socialIdEntityDao.getSocialdentity(user.getId()).getToken();
        if(pageIndex == null){
            pageIndex = 0;
        }
        if(pageSize == null){
            pageSize = 10;
        }
        
        Map result = salesforceService.listContacts(token, pageIndex, pageSize);
        m.putAll(result);
        return m ;
    }
	
}

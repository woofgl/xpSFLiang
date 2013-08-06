package com.snow.xpSFLiang.web;

import com.britesnow.snow.web.RequestContext;
import com.britesnow.snow.web.param.annotation.WebParam;
import com.britesnow.snow.web.rest.annotation.WebGet;
import com.britesnow.snow.web.rest.annotation.WebPost;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.snow.xpSFLiang.dao.DaoRegistry;
import com.snow.xpSFLiang.dao.IDao;
import com.snow.xpSFLiang.dao.SocialIdEntityDao;
import com.snow.xpSFLiang.entity.Label;
import com.snow.xpSFLiang.entity.LabelRel;
import com.snow.xpSFLiang.entity.SocialIdEntity;
import com.snow.xpSFLiang.entity.User;
import com.snow.xpSFLiang.exception.OauthException;
import com.snow.xpSFLiang.oauth.ForceAuthService;
import com.snow.xpSFLiang.oauth.SalesForceService;
import net.sf.json.JSONException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Singleton
public class SFContactWebHandlers {
    @Inject
    private DaoRegistry daoRegistry;
    
    @Inject
    private SocialIdEntityDao socialIdEntityDao;

    @Inject
    private SalesForceService salesforceService;
    @Inject
    private ForceAuthService forceAuthService;
    
    @WebGet("/salesforce/listContacts")
    public WebResponse listSFContacts(@WebParam("pageSize") Integer pageSize, @WebParam("pageIndex") Integer pageIndex,RequestContext rc) throws  IOException, JSONException {
        User user = rc.getUser(User.class);
        SocialIdEntity sid = socialIdEntityDao.getSocialdentity(user.getId());
        String token=null;
        if (sid != null) {
            token = sid.getToken();
        }else {
            throw new OauthException(forceAuthService.getAuthorizationUrl());
        }

        if(pageIndex == null){
            pageIndex = 0;
        }
        if(pageSize == null){
            pageSize = 10;
        }
        
        Map result = salesforceService.listContacts(token, pageIndex, pageSize);
        return WebResponse.success(result);
    }
    @WebPost("/salesforce/addLabel")
    public WebResponse addLabel(@WebParam("contactId") String contactId, @WebParam("lable") String label) {
        IDao<Label> labelDao = daoRegistry.getDao(Label.class);
        IDao<LabelRel> relDao = daoRegistry.getDao(LabelRel.class);
        List<Label> labels = labelDao.search("from Label l where l.label = ?", label);
        Label newLabel;
        if (labels.size() == 0) {
            newLabel = new Label(label);
            labelDao.save(newLabel);
        }else {
            newLabel = labels.get(0);
        }

        LabelRel labelRel = new LabelRel();
        labelRel.setContactId(contactId);

        labelRel.setLabel(newLabel);
        relDao.save(labelRel);
        return WebResponse.success(label);
    }

}

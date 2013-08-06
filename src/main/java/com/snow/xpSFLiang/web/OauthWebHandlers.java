package com.snow.xpSFLiang.web;

import com.britesnow.snow.web.RequestContext;
import com.britesnow.snow.web.handler.annotation.WebModelHandler;
import com.britesnow.snow.web.param.annotation.WebParam;
import com.britesnow.snow.web.param.annotation.WebUser;
import com.britesnow.snow.web.rest.annotation.WebGet;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.snow.xpSFLiang.dao.SocialIdEntityDao;
import com.snow.xpSFLiang.entity.SocialIdEntity;
import com.snow.xpSFLiang.entity.User;
import com.snow.xpSFLiang.oauth.ForceAuthService;
import com.snow.xpSFLiang.oauth.api.ForceDotComApi;

import java.io.IOException;

@Singleton
public class OauthWebHandlers {
    @Inject
    private ForceAuthService forceAuthService;
    @Inject
    private SocialIdEntityDao socialIdEntityDao;

    @WebGet("/authorize")
    public void authorize(RequestContext rc) throws IOException {
        String url = forceAuthService.getAuthorizationUrl();
        rc.getRes().sendRedirect(url);
    }

    @WebModelHandler(startsWith = "/forceCallback")
    public void callback(RequestContext rc,@WebUser User user, @WebParam("code") String code) throws Exception {
        ForceDotComApi.ForceDotComToken token = (ForceDotComApi.ForceDotComToken) forceAuthService.getAccessToken(code);
        SocialIdEntity s = socialIdEntityDao.getSocialdentity(user.getId());

        if (s==null) {
            s = new SocialIdEntity();
            s.setUser_id(user.getId());
            s.setToken(token.getToken());
            s.setTokenDate(token.getIssuedAt());
            socialIdEntityDao.save(s);
        }else{
            s.setToken(token.getToken());
            s.setTokenDate(token.getIssuedAt());
            socialIdEntityDao.update(s);
        }
    }
}

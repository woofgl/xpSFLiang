package com.snow.xpSFLiang.web;

import com.britesnow.snow.web.RequestContext;
import com.britesnow.snow.web.handler.annotation.WebModelHandler;
import com.britesnow.snow.web.param.annotation.WebParam;
import com.britesnow.snow.web.rest.annotation.WebGet;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.snow.xpSFLiang.oauth.ForceAuthService;
import org.scribe.model.Token;

import java.io.IOException;

@Singleton
public class OauthWebHandlers {
    @Inject
    private ForceAuthService forceAuthService;

    @WebGet("/authorize")
    public String authorize(RequestContext rc) throws IOException {
        return forceAuthService.getAuthorizationUrl();
    }

    @WebModelHandler(startsWith = "/forceCallback")
    public void googleCallback(RequestContext rc, @WebParam("code") String code) throws Exception {
        Token token = forceAuthService.getAccessToken(code);
        System.out.println(token);
    }
}

package com.snow.xpSFLiang.web;

import com.britesnow.snow.web.RequestContext;
import com.britesnow.snow.web.exception.WebExceptionContext;
import com.britesnow.snow.web.exception.annotation.WebExceptionCatcher;
import com.britesnow.snow.web.renderer.JsonRenderer;
import com.snow.xpSFLiang.exception.JsonAuthException;
import com.snow.xpSFLiang.exception.OauthException;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class WebExceptionProcessor {
    @Inject
    private JsonRenderer jsonRenderer;
    @WebExceptionCatcher
    public void processOauthException(OauthException e, WebExceptionContext wec, RequestContext rc)  {
        //log.warn(e.getMessage(), e);
        rc.getWebModel().put("oauthUrl", e.getOauthUrl());
        rc.getWebModel().put("OAUTH_FAILED", true);
        jsonRenderer.render(rc.getWebModel(), rc.getWriter());
/*        WebHandlerType type = wec.getWebHandlerContext().getHandlerType();
        switch (type) {
            case model:
                jsonRenderer.render(rc.getWebModel(), rc.getWriter());
                break;
            default:
        }*/
/*        if (!rc.getRes().isCommitted()) {
            rc.getRes().sendRedirect(e.getOauthUrl());
        }*/

    }

    @WebExceptionCatcher
    public void processJsonAuthException(JsonAuthException e, WebExceptionContext wec, RequestContext rc)  {

            rc.getWebModel().put("AUTH_FAILED", true);
            jsonRenderer.render(rc.getWebModel(), rc.getWriter());
    }
}

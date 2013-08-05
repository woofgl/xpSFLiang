package com.snow.xpSFLiang.oauth.api;


import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.scribe.builder.api.DefaultApi20;
import org.scribe.exceptions.OAuthException;
import org.scribe.extractors.AccessTokenExtractor;
import org.scribe.model.OAuthConfig;
import org.scribe.model.OAuthConstants;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuth20ServiceImpl;
import org.scribe.oauth.OAuthService;
import org.scribe.utils.Preconditions;

import static com.snow.xpSFLiang.oauth.api.URLUtils.formURLEncode;


public class ForceDotComApi extends DefaultApi20
{

    private static final String AUTHORIZE_URL_PATH = "/services/oauth2/authorize?response_type=code&client_id=%s&redirect_uri=%s";
    private static final String SCOPED_AUTHORIZE_URL_PATH = AUTHORIZE_URL_PATH + "&scope=%s";
    private static final String ACCESS_URL_PATH = "/services/oauth2/token?grant_type=authorization_code";

    private final ForceInstanceType instanceType;

    public enum ForceInstanceType
    {
        PRE_RELEASE("https://prerellogin.pre.salesforce.com"),
        PRODUCTION("https://login.salesforce.com"),
        SANDBOX("https://test.salesforce.com"),
        ;

        String baseUrl;

        private ForceInstanceType(String baseUrl)
        {
            this.baseUrl = baseUrl;
        }

        public String getBaseUrl()
        {
            return this.baseUrl;
        }
    }

    public ForceDotComApi()
    {
        // The default instance type is PRODUCTION
        this(ForceInstanceType.PRODUCTION);
    }

    public ForceDotComApi(ForceInstanceType instanceType)
    {
        super();

        Preconditions.checkNotNull(instanceType, "Force.com API instance type cannot be null");
        this.instanceType = instanceType;
    }

    @Override
    public String getAccessTokenEndpoint()
    {
        return instanceType.getBaseUrl() + ACCESS_URL_PATH;
    }

    @Override
    public AccessTokenExtractor getAccessTokenExtractor()
    {
        return new ForceDotComTokenExtractor();
    }

    @Override
    public Verb getAccessTokenVerb()
    {
        return Verb.POST;
    }

    @Override
    public String getAuthorizationUrl(OAuthConfig config)
    {
        Preconditions.checkValidUrl(config.getCallback(), "Must provide a valid url as callback. Force.com does not support OOB");

        if (config.hasScope())
        {
            return String.format(instanceType.getBaseUrl() + SCOPED_AUTHORIZE_URL_PATH, config.getApiKey(), formURLEncode(config.getCallback()), formURLEncode(config.getScope()));
        }
        else
        {
            return String.format(instanceType.getBaseUrl() + AUTHORIZE_URL_PATH, config.getApiKey(), formURLEncode(config.getCallback()));
        }
    }

    @Override
    public OAuthService createService(OAuthConfig config)
    {
        return new ForceDotComOAuth20ServiceImpl(this, config);
    }

    /**
     * Force.com OAuth token.
     *
     * This contains extra information from the Force.com OAuth service:
     * <ul>
     * <li>Id - A URL representing the authenticated Force.com user. This can be used to access Force.com's identity service</li>
     * <li>IssuedAt - The datetime stamp at which the token was issued by Force.com</li>
     * <li>InstanceUrl - The Force.com instance to which subsequent API calls should be sent</li>
     * <li>Signature - HMAC-SHA256 hash for the Id and IssuedAt state</li>
     * </ul>
     *
     * @author Tim Kral
     */
    public static class ForceDotComToken extends Token
    {

        private static final long serialVersionUID = -1522491125878959187L;

        private final String id;
        private final Date issuedAt;
        private final String instanceUrl;
        private final String signature;

        public ForceDotComToken(String id, String issuedAtStr, String secret, String instanceUrl, String signature,
                                String token, String rawResponse)
        {
            super(token, secret, rawResponse);
            this.id = id;
            this.issuedAt = new Date(Long.parseLong(issuedAtStr));
            this.instanceUrl = instanceUrl;
            this.signature = signature;
        }

        public String getId()
        {
            return id;
        }

        public Date getIssuedAt()
        {
            return issuedAt;
        }

        public String getInstanceUrl()
        {
            return instanceUrl;
        }

        public String getSignature()
        {
            return signature;
        }
    }

    /**
     * Extractor for Force.com OAuth tokens.
     *
     * @author Tim Kral
     */
    public static class ForceDotComTokenExtractor implements AccessTokenExtractor
    {

        private Pattern forceTokenPattern =
                Pattern.compile("\"id\":\"(\\S*?)\",\"issued_at\":\"(\\d*?)\",.*,\"instance_url\":\"(\\S*?)\",\"signature\":\"(\\S*?)\",\"access_token\":\"(\\S*?)\"");

        @Override
        public Token extract(String response)
        {
            Preconditions.checkEmptyString(response, "Cannot extract a token from a null or empty String");
            Matcher matcher = forceTokenPattern.matcher(response);
            if (matcher.find())
            {
                return new ForceDotComToken(
                        matcher.group(1) /*id*/, matcher.group(2) /*issuedAt*/,
                        null /*refreshToken a.k.a secret*/, matcher.group(3) /*instanceUrl*/,
                        matcher.group(4) /*signature*/, matcher.group(5) /*accessToken*/,
                        response);
            }
            else
            {
                throw new OAuthException("Cannot extract a Force.com acces token. Response was: " + response);
            }
        }
    }

    /**
     * Force.com OAuth service.
     *
     * This signs requests in the header where the Force.com
     * OAuth service expects to find it.
     *
     * @author Tim Kral
     */
    public static class ForceDotComOAuth20ServiceImpl extends OAuth20ServiceImpl
    {

        public ForceDotComOAuth20ServiceImpl(DefaultApi20 api, OAuthConfig config)
        {
            super(api, config);
        }

        @Override
        public void signRequest(Token accessToken, OAuthRequest request)
        {
            request.addHeader(OAuthConstants.HEADER, "OAuth " + accessToken.getToken());
        }
    }

}
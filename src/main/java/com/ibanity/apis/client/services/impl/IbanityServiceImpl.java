package com.ibanity.apis.client.services.impl;

import com.ibanity.apis.client.builders.IbanityConfiguration;
import com.ibanity.apis.client.http.IbanityHttpClient;
import com.ibanity.apis.client.http.OAuthHttpClient;
import com.ibanity.apis.client.http.factory.IbanityHttpClientFactory;
import com.ibanity.apis.client.http.factory.OauthHttpClientFactory;
import com.ibanity.apis.client.models.SignatureCredentials;
import com.ibanity.apis.client.models.TlsCredentials;
import com.ibanity.apis.client.products.ponto_connect.services.PontoConnectService;
import com.ibanity.apis.client.products.ponto_connect.services.impl.PontoConnectServiceImpl;
import com.ibanity.apis.client.products.xs2a.services.Xs2aService;
import com.ibanity.apis.client.products.xs2a.services.impl.Xs2aServiceImpl;
import com.ibanity.apis.client.services.ApiUrlProvider;
import com.ibanity.apis.client.services.IbanityService;
import com.ibanity.apis.client.utils.IbanityUtils;
import org.apache.http.client.HttpClient;

import java.security.cert.Certificate;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class IbanityServiceImpl implements IbanityService {

    private final ApiUrlProvider apiUrlProvider;
    private final IbanityHttpClient ibanityHttpClient;
    private final Xs2aService xs2aService;
    private final PontoConnectService pontoConnectService;
    private final OAuthHttpClient oauthHttpClient;

    /**
     * @deprecated  Replaced by {@link #IbanityServiceImpl(IbanityConfiguration)}
     */
    @Deprecated
    public IbanityServiceImpl(String apiEndpoint,
                              Certificate caCertificate,
                              TlsCredentials tlsCredentials,
                              SignatureCredentials signatureCredentials,
                              String clientId,
                              String proxyEndpoint) {
        this.ibanityHttpClient = new IbanityHttpClientFactory().create(caCertificate, tlsCredentials, signatureCredentials, apiEndpoint);
        this.apiUrlProvider = new ApiUrlProviderImpl(ibanityHttpClient, apiEndpoint, proxyEndpoint);

        this.xs2aService = new Xs2aServiceImpl(apiUrlProvider, ibanityHttpClient);

        if (isBlank(clientId)) {
            this.oauthHttpClient = null;
            this.pontoConnectService = null;
        } else {
            this.oauthHttpClient = new OauthHttpClientFactory().create(caCertificate, tlsCredentials, signatureCredentials, apiEndpoint, clientId);
            this.pontoConnectService = new PontoConnectServiceImpl(apiUrlProvider, ibanityHttpClient, oauthHttpClient);
        }
    }

    public IbanityServiceImpl(ApiUrlProvider apiUrlProvider,
                              IbanityHttpClient ibanityHttpClient,
                              Xs2aService xs2aService,
                              PontoConnectService pontoConnectService,
                              OAuthHttpClient oauthHttpClient) {
        this.apiUrlProvider = apiUrlProvider;
        this.ibanityHttpClient = ibanityHttpClient;
        this.xs2aService = xs2aService;
        this.pontoConnectService = pontoConnectService;
        this.oauthHttpClient = oauthHttpClient;
    }

    public IbanityServiceImpl(IbanityConfiguration ibanityConfiguration) {
        String clientId = ibanityConfiguration.getPontoConnectOauth2ClientId();
        HttpClient httpClient = IbanityUtils.httpClient(ibanityConfiguration);
        this.ibanityHttpClient = new IbanityHttpClientFactory().create(httpClient);
        this.apiUrlProvider = new ApiUrlProviderImpl(ibanityHttpClient, ibanityConfiguration.getApiEndpoint(), ibanityConfiguration.getProxyEndpoint());

        this.xs2aService = new Xs2aServiceImpl(apiUrlProvider, ibanityHttpClient);

        if (isBlank(clientId)) {
            this.oauthHttpClient = null;
            this.pontoConnectService = null;
        } else {
            this.oauthHttpClient = new OauthHttpClientFactory().create(clientId, httpClient);
            this.pontoConnectService = new PontoConnectServiceImpl(apiUrlProvider, ibanityHttpClient, oauthHttpClient);
        }
    }

    @Override
    public ApiUrlProvider apiUrlProvider() {
        return apiUrlProvider;
    }

    @Override
    public IbanityHttpClient ibanityHttpClient() {
        return ibanityHttpClient;
    }

    @Override
    public Xs2aService xs2aService() {
        return xs2aService;
    }

    public OAuthHttpClient oAuthHttpClient() {
        if (oauthHttpClient == null) {
            throw new IllegalStateException("OauthHttpClient was not properly initialized. Did you configure pontoConnectOauth2ClientId?");
        }

        return oauthHttpClient;
    }

    @Override
    public PontoConnectService pontoConnectService() {
        if (pontoConnectService == null) {
            throw new IllegalStateException("PontoConnectService was not properly initialized. Did you configure pontoConnectOauth2ClientId?");
        }

        return pontoConnectService;
    }
}

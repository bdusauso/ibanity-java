package com.ibanity.apis.client.products.ponto_connect.services.impl;

import com.ibanity.apis.client.http.IbanityHttpClient;
import com.ibanity.apis.client.http.OauthHttpClient;
import com.ibanity.apis.client.products.ponto_connect.services.*;
import com.ibanity.apis.client.services.ApiUrlProvider;

public class PontoConnectServiceImpl implements PontoConnectService {

    private final TokenService tokenService;
    private final AccountService accountService;
    private final TransactionService transactionService;
    private final SynchronizationService synchronizationService;
    private final FinancialInstitutionService financialInstitutionService;

    public PontoConnectServiceImpl(ApiUrlProvider apiUrlProvider, IbanityHttpClient ibanityHttpClient, OauthHttpClient oauthHttpClient) {
        tokenService = new TokenServiceImpl(apiUrlProvider, oauthHttpClient);
        accountService = new AccountServiceImpl(apiUrlProvider, ibanityHttpClient);
        transactionService = new TransactionServiceImpl(apiUrlProvider, ibanityHttpClient);
        synchronizationService = new SynchronizationServiceImpl(apiUrlProvider, ibanityHttpClient);
        financialInstitutionService = new FinancialInstitutionServiceImpl(apiUrlProvider, ibanityHttpClient);
    }

    @Override
    public TokenService tokenService() {
        return tokenService;
    }

    @Override
    public AccountService accountService() {
        return accountService;
    }

    @Override
    public TransactionService transactionService() {
        return transactionService;
    }

    @Override
    public FinancialInstitutionService financialInstitutionService() {
        return financialInstitutionService;
    }

    @Override
    public SynchronizationService synchronizationService() {
        return synchronizationService;
    }
}

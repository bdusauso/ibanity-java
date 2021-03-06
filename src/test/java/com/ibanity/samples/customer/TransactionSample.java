package com.ibanity.samples.customer;

import com.ibanity.apis.client.products.xs2a.models.Account;
import com.ibanity.apis.client.products.xs2a.models.CustomerAccessToken;
import com.ibanity.apis.client.products.xs2a.models.FinancialInstitution;
import com.ibanity.apis.client.products.xs2a.models.Transaction;
import com.ibanity.apis.client.products.xs2a.models.read.TransactionReadQuery;
import com.ibanity.apis.client.products.xs2a.models.read.TransactionsReadQuery;
import com.ibanity.apis.client.products.xs2a.services.TransactionsService;
import com.ibanity.apis.client.services.IbanityService;

import java.util.List;
import java.util.UUID;

public class TransactionSample {

    private final TransactionsService transactionsService;

    public TransactionSample(IbanityService ibanityService) {
        transactionsService = ibanityService.xs2aService().transactionService();
    }

    public List<Transaction> list(CustomerAccessToken customerAccessToken, FinancialInstitution financialInstitution, Account account) {
        TransactionsReadQuery transactionsReadQuery = TransactionsReadQuery.builder()
                .customerAccessToken(customerAccessToken.getToken())
                .financialInstitutionId(financialInstitution.getId())
                .accountId(account.getId())
                .build();
        return transactionsService.list(transactionsReadQuery).getItems();
    }

    public Transaction get(CustomerAccessToken customerAccessToken, FinancialInstitution financialInstitution, Account account, UUID transactionId) {
        TransactionReadQuery transactionReadQuery = TransactionReadQuery.builder()
                .customerAccessToken(customerAccessToken.getToken())
                .financialInstitutionId(financialInstitution.getId())
                .accountId(account.getId())
                .transactionId(transactionId)
                .build();

        return transactionsService.find(transactionReadQuery);
    }
}

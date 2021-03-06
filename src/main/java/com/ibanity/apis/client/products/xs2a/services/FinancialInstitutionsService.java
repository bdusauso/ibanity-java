package com.ibanity.apis.client.products.xs2a.services;

import com.ibanity.apis.client.models.IbanityCollection;
import com.ibanity.apis.client.products.xs2a.models.FinancialInstitution;
import com.ibanity.apis.client.products.xs2a.models.read.FinancialInstitutionReadQuery;
import com.ibanity.apis.client.products.xs2a.models.read.FinancialInstitutionsReadQuery;

public interface FinancialInstitutionsService {

    IbanityCollection<FinancialInstitution> list(FinancialInstitutionsReadQuery financialInstitutionsReadQuery);

    FinancialInstitution find(FinancialInstitutionReadQuery financialInstitutionReadQuery);
}

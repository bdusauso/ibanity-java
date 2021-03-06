package com.ibanity.apis.client.products.xs2a.services.impl;

import com.ibanity.apis.client.http.IbanityHttpClient;
import com.ibanity.apis.client.mappers.IbanityModelMapper;
import com.ibanity.apis.client.models.IbanityCollection;
import com.ibanity.apis.client.models.IbanityProduct;
import com.ibanity.apis.client.paging.IbanityPagingSpec;
import com.ibanity.apis.client.products.xs2a.models.FinancialInstitution;
import com.ibanity.apis.client.products.xs2a.models.read.FinancialInstitutionReadQuery;
import com.ibanity.apis.client.products.xs2a.models.read.FinancialInstitutionsReadQuery;
import com.ibanity.apis.client.products.xs2a.services.FinancialInstitutionsService;
import com.ibanity.apis.client.services.ApiUrlProvider;
import org.apache.http.HttpResponse;

import java.net.URI;

import static com.ibanity.apis.client.utils.URIHelper.buildUri;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.removeEnd;

public class FinancialInstitutionsServiceImpl implements FinancialInstitutionsService {

    private final ApiUrlProvider apiUrlProvider;
    private final IbanityHttpClient ibanityHttpClient;

    public FinancialInstitutionsServiceImpl(ApiUrlProvider apiUrlProvider, IbanityHttpClient ibanityHttpClient) {
        this.apiUrlProvider = apiUrlProvider;
        this.ibanityHttpClient = ibanityHttpClient;
    }

    @Override
    public IbanityCollection<FinancialInstitution> list(FinancialInstitutionsReadQuery financialInstitutionsReadQuery) {
        IbanityPagingSpec pagingSpec =
                financialInstitutionsReadQuery.getPagingSpec() == null
                        ? IbanityPagingSpec.DEFAULT_PAGING_SPEC : financialInstitutionsReadQuery.getPagingSpec();

        String customerAccessToken = financialInstitutionsReadQuery.getCustomerAccessToken();
        URI uri = buildUri(
                removeEnd(
                        getUrl(customerAccessToken).replace(FinancialInstitution.API_URL_TAG_ID, ""),
                        "/"
                ),
                pagingSpec,
                financialInstitutionsReadQuery.getFilters());
        HttpResponse response = ibanityHttpClient.get(uri, financialInstitutionsReadQuery.getAdditionalHeaders(), customerAccessToken);
        return IbanityModelMapper.mapCollection(response, FinancialInstitution.class);
    }

    @Override
    public FinancialInstitution find(FinancialInstitutionReadQuery financialInstitutionReadQuery) {
        String customerAccessToken = financialInstitutionReadQuery.getCustomerAccessToken();
        URI uri = buildUri(
                removeEnd(
                        getUrl(customerAccessToken)
                                .replace(FinancialInstitution.API_URL_TAG_ID, financialInstitutionReadQuery.getFinancialInstitutionId().toString()),
                        "/"));
        HttpResponse response = ibanityHttpClient.get(uri, financialInstitutionReadQuery.getAdditionalHeaders(), customerAccessToken);
        return IbanityModelMapper.mapResource(response, FinancialInstitution.class);
    }

    private String getUrl(String customerAccessToken) {
        if (isBlank(customerAccessToken)) {
            return apiUrlProvider.find(IbanityProduct.Xs2a, "financialInstitutions");
        } else {
            return apiUrlProvider.find(IbanityProduct.Xs2a, "customer", "financialInstitutions");
        }
    }
}

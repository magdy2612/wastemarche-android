package wm.wastemarche.services.http.companies;

import java.util.List;

import wm.wastemarche.model.Company;

public interface CompaniesApiProtocol {

    void companiesTypesloaded(List<Company> companies);

    void companiesFialed(String error);
}

package companyregistry.application.company;

import java.util.List;

import companyregistry.domain.CompanyBeneficiary;
import companyregistry.domain.IdCatalog;
import companyregistry.domain.beneficiary.Beneficiary;
import companyregistry.domain.company.Company;
import companyregistry.restservice.exception.BadRequestResourceException;
import companyregistry.restservice.exception.NotFoundResourceException;
import companyregistry.restservice.exception.ServerErrorResourceException;

/**
 * @author dokuboyejo
 *
 */
public interface CompanyApplicationService {

	Company getCompany(Long companyId) throws BadRequestResourceException, NotFoundResourceException, ServerErrorResourceException;
	List<Company> getCompanies() throws BadRequestResourceException, NotFoundResourceException, ServerErrorResourceException;
	List<Beneficiary> getCompanyBeneficiaries(Long companyId) throws BadRequestResourceException, NotFoundResourceException, ServerErrorResourceException;
	Long addCompany(Company company) throws BadRequestResourceException, NotFoundResourceException, ServerErrorResourceException;
	Long addExistingBeneficiaryToCompany(Long companyId, List<IdCatalog> idCatalogs) throws BadRequestResourceException, NotFoundResourceException, ServerErrorResourceException;
	Long addNewBeneficiaryToCompany(Long companyId, List<Beneficiary> beneficiaries) throws BadRequestResourceException, NotFoundResourceException, ServerErrorResourceException;
	boolean updateCompany(Company company) throws BadRequestResourceException, NotFoundResourceException, ServerErrorResourceException;
    boolean deleteCompany(Long companyId) throws BadRequestResourceException, NotFoundResourceException, ServerErrorResourceException;
    boolean deleteBeneficiaryFromCompany(CompanyBeneficiary companyBeneficiary) throws BadRequestResourceException, NotFoundResourceException, ServerErrorResourceException;
    
}
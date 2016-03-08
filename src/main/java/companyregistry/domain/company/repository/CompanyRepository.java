package companyregistry.domain.company.repository;

import java.util.List;

import companyregistry.domain.CompanyBeneficiary;
import companyregistry.domain.beneficiary.Beneficiary;
import companyregistry.domain.company.Company;
import companyregistry.restservice.exception.BadRequestResourceException;
import companyregistry.restservice.exception.NotFoundResourceException;
import companyregistry.restservice.exception.ServerErrorResourceException;

/**
 * @author dokuboyejo
 *
 */
public interface CompanyRepository {
    
	Company getCompany(Long companyId) throws BadRequestResourceException, NotFoundResourceException, ServerErrorResourceException;
	List<Company> getCompanies() throws BadRequestResourceException, NotFoundResourceException, ServerErrorResourceException;
	List<Beneficiary> getCompanyBeneficiaries(Long companyId) throws BadRequestResourceException, NotFoundResourceException, ServerErrorResourceException;
	Long addCompany(Company company) throws BadRequestResourceException, NotFoundResourceException, ServerErrorResourceException;
	Long addExistingBeneficiaryToCompany(List<CompanyBeneficiary> companyBeneficiaries) throws BadRequestResourceException, NotFoundResourceException, ServerErrorResourceException;;
	Long addNewBeneficiaryToCompany(Long companyId, List<Beneficiary> beneficiaries) throws BadRequestResourceException, NotFoundResourceException, ServerErrorResourceException;;
	boolean updateCompany(Company company) throws BadRequestResourceException, NotFoundResourceException, ServerErrorResourceException;
    boolean deleteCompany(Long companyId) throws BadRequestResourceException, NotFoundResourceException, ServerErrorResourceException;
    boolean deleteBeneficiaryFromCompany(CompanyBeneficiary companyBeneficiary) throws BadRequestResourceException, NotFoundResourceException, ServerErrorResourceException;
    
}
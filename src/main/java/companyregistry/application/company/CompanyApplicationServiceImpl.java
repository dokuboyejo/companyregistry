package companyregistry.application.company;

import java.util.ArrayList;
import java.util.List;

import companyregistry.domain.CompanyBeneficiary;
import companyregistry.domain.IdCatalog;
import companyregistry.domain.beneficiary.Beneficiary;
import companyregistry.domain.company.Company;
import companyregistry.domain.company.repository.CompanyRepository;
import companyregistry.persistence.company.repository.CompanyRepositoryImpl;
import companyregistry.restservice.exception.BadRequestResourceException;
import companyregistry.restservice.exception.NotFoundResourceException;
import companyregistry.restservice.exception.ServerErrorResourceException;

/**
 * @author dokuboyejo
 *
 */
public class CompanyApplicationServiceImpl implements CompanyApplicationService {

	private CompanyRepository companyRepository;

	/**
	 * @param companyRepository the repository to set
	 */
	public void setCompanyRepository(CompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
	}

	public CompanyApplicationServiceImpl() {
		companyRepository = new CompanyRepositoryImpl();
	}

	@Override
	public Company getCompany(Long companyId) throws BadRequestResourceException, NotFoundResourceException, ServerErrorResourceException {
		return companyRepository.getCompany(companyId);
	}

	@Override
	public List<Company> getCompanies() throws BadRequestResourceException, NotFoundResourceException, ServerErrorResourceException {
		return companyRepository.getCompanies();
	}

	@Override
	public List<Beneficiary> getCompanyBeneficiaries(Long companyId) throws BadRequestResourceException, NotFoundResourceException, ServerErrorResourceException {
		return companyRepository.getCompanyBeneficiaries(companyId);
	}
	
	@Override
	public Long addCompany(Company company) throws BadRequestResourceException, NotFoundResourceException, ServerErrorResourceException {
		return companyRepository.addCompany(company);
	}

	@Override
	public Long addExistingBeneficiaryToCompany(Long companyId, List<IdCatalog> idCatalogs) throws BadRequestResourceException, NotFoundResourceException, ServerErrorResourceException {
		List<CompanyBeneficiary> companyBeneficiaries = new ArrayList<CompanyBeneficiary>();
		idCatalogs
			.stream()
			.forEach(ic -> {
				CompanyBeneficiary companyBeneficiary = new CompanyBeneficiary();
				companyBeneficiary.setCompanyId(companyId);
				companyBeneficiary.setBeneficiaryId(ic.getId());
				companyBeneficiaries.add(companyBeneficiary);
			});
		return companyRepository.addExistingBeneficiaryToCompany(companyBeneficiaries);
	}

	@Override
	public Long addNewBeneficiaryToCompany(Long companyId, List<Beneficiary> beneficiaries) throws BadRequestResourceException, NotFoundResourceException, ServerErrorResourceException {
		return companyRepository.addNewBeneficiaryToCompany(companyId, beneficiaries);
	}
	
	@Override
	public boolean updateCompany(Company company) throws BadRequestResourceException, NotFoundResourceException, ServerErrorResourceException {
		return companyRepository.updateCompany(company);
	}
	
	@Override
	public boolean deleteCompany(Long companyId) throws BadRequestResourceException, NotFoundResourceException, ServerErrorResourceException {
		return companyRepository.deleteCompany(companyId);
	}

	@Override
	public boolean deleteBeneficiaryFromCompany(CompanyBeneficiary companyBeneficiary) throws BadRequestResourceException, NotFoundResourceException, ServerErrorResourceException {
		return companyRepository.deleteBeneficiaryFromCompany(companyBeneficiary);
	}

}
package companyregistry.persistence.company.repository;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.cdi.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import companyregistry.domain.CompanyBeneficiary;
import companyregistry.domain.beneficiary.Beneficiary;
import companyregistry.domain.beneficiary.repository.BeneficiaryRepository;
import companyregistry.domain.company.Company;
import companyregistry.domain.company.repository.CompanyRepository;
import companyregistry.persistence.SqlSessionFactoryProvider;
import companyregistry.persistence.beneficiary.repository.BeneficiaryRepositoryImpl;
import companyregistry.persistence.company.mapper.CompanyMapper;
import companyregistry.restservice.exception.BadRequestResourceException;
import companyregistry.restservice.exception.ConflictResourceException;
import companyregistry.restservice.exception.NotFoundResourceException;
import companyregistry.restservice.exception.ServerErrorResourceException;

/**
 * @author dokuboyejo
 *
 */
@ApplicationScoped
public class CompanyRepositoryImpl implements CompanyRepository {

	@Mapper
	private CompanyMapper companyMapper;
	private SqlSessionFactoryProvider<CompanyMapper> sqlSessionFactoryProvider;
	private BeneficiaryRepository beneficiaryRepository;
	private static final Logger LOGGER = LoggerFactory.getLogger(CompanyRepositoryImpl.class);
	private static final String ERROR_START = "An error occured";
	private static final String COMPANY_NOT_FOUND = "Company details not found";
	private static final String COMPANIES_NOT_FOUND = "No registered company found";
	private static final String BENEFICIARIES_NOT_FOUND = "No beneficiary exist for this company";
	private long affectedRows;
	
	public CompanyRepositoryImpl() {
		sqlSessionFactoryProvider = new SqlSessionFactoryProvider<CompanyMapper>();
		beneficiaryRepository = new BeneficiaryRepositoryImpl();
	}
	
	public CompanyRepositoryImpl(CompanyMapper companyMapper, SqlSessionFactoryProvider<CompanyMapper> sqlSessionFactoryProvider) {
    	this.companyMapper = companyMapper;
    	this.sqlSessionFactoryProvider = sqlSessionFactoryProvider;
    }

	@Override
	public Company getCompany(Long companyId) throws BadRequestResourceException, NotFoundResourceException, ServerErrorResourceException {
		verifyDetails(String.valueOf(companyId), "%s: company's id is invalid or empty");
		Company company = new Company();
		try{
			companyMapper = sqlSessionFactoryProvider.openSessionAndInitMapper(CompanyMapper.class);
			company = companyMapper.findCompanyById(companyId);
			if (null == company) throw new NotFoundResourceException(COMPANY_NOT_FOUND);
			sqlSessionFactoryProvider.closeSession();
		} catch (Exception e){
			LOGGER.error(e.getMessage(), e);
			sqlSessionFactoryProvider.closeSessionAndRollback();
			if (e instanceof NotFoundResourceException) throw e;
		}
		return company;
	}

	@Override
	public List<Company> getCompanies() throws BadRequestResourceException, NotFoundResourceException, ServerErrorResourceException {
		List<Company> companies = new ArrayList<Company>();
		try {
			companyMapper = sqlSessionFactoryProvider.openSessionAndInitMapper(CompanyMapper.class);
			companies = companyMapper.fetchAllCompanies();
			if (companies.size() == 0) throw new NotFoundResourceException(COMPANIES_NOT_FOUND);
			sqlSessionFactoryProvider.closeSession();
		} catch (Exception e){
			LOGGER.error(e.getMessage(), e);
			sqlSessionFactoryProvider.closeSessionAndRollback();
			if (e instanceof NotFoundResourceException) throw e;
		}
		return companies;
	}
	
	@Override
	public List<Beneficiary> getCompanyBeneficiaries(Long companyId) throws BadRequestResourceException, NotFoundResourceException, ServerErrorResourceException {
		List<Beneficiary> beneficiaries = new ArrayList<Beneficiary>();
		try {
			companyMapper = sqlSessionFactoryProvider.openSessionAndInitMapper(CompanyMapper.class);
			beneficiaries = companyMapper.fetchCompanyBeneficiaries(companyId);
			if (beneficiaries.size() == 0) throw new NotFoundResourceException(BENEFICIARIES_NOT_FOUND);
			sqlSessionFactoryProvider.closeSession();
		} catch (Exception e){
			LOGGER.error(e.getMessage(), e);
			sqlSessionFactoryProvider.closeSessionAndRollback();
			if (e instanceof NotFoundResourceException) throw e;
		}
		return beneficiaries;
	}

	@Override
	public Long addCompany(Company company) throws BadRequestResourceException, NotFoundResourceException, ServerErrorResourceException {
		verifyCompany(company, false);
		companyMapper = sqlSessionFactoryProvider.openSessionAndInitMapper(CompanyMapper.class);
		long companyId = -1;
		try {
			Long affectedRows = companyMapper.addCompany(company);
			// retrieve id of newly added company
			companyId = company.getId();
			CompanyBeneficiary companyBeneficiary = new CompanyBeneficiary();
			company.getBeneficiaries()
				 .stream()
				 .forEach(b -> {
					 Long beneficiaryId = b.getId();
					 if (null != beneficiaryId && beneficiaryId > 0) {
						 Beneficiary beneficiary = beneficiaryRepository.getBeneficiary(beneficiaryId);
						 if (null == beneficiary) {
							 throw new NotFoundResourceException(String.format("Beneficiary with id: %d doesn't exist", beneficiaryId));
						 }
					 } else {
						 companyMapper.addBeneficiary(b);
					 }
					 // use id of the existing/newly created beneficiary
					 companyBeneficiary.setBeneficiaryId(b.getId());
					 // use id of the newly created company
					 companyBeneficiary.setCompanyId(company.getId());
					 companyMapper.addBeneficiaryToCompany(companyBeneficiary);
				 });
			sqlSessionFactoryProvider.closeSessionAndCommit();
		} catch (Exception e){
			companyId = -1;
			LOGGER.error(e.getMessage(), e);
			sqlSessionFactoryProvider.closeSessionAndRollback();
			if (e instanceof BadRequestResourceException) throw e;
			if (e instanceof NotFoundResourceException) throw e;
		}
		
		return companyId;
	}
	
	@Override
	public Long addExistingBeneficiaryToCompany(List<CompanyBeneficiary> companyBeneficiaries) throws BadRequestResourceException, NotFoundResourceException, ServerErrorResourceException {
		companyMapper = sqlSessionFactoryProvider.openSessionAndInitMapper(CompanyMapper.class);
		this.affectedRows = 0;
		try {
			companyBeneficiaries
				.stream()
			    .forEach(cb -> {
			    	verifyDetails(String.valueOf(cb.getCompanyId()), "%s: company's id is invalid");
					verifyDetails(String.valueOf(cb.getBeneficiaryId()), "%s: beneficiary's id is invalid");
					if (beneficiaryExistForCompany(cb.getCompanyId(), cb.getBeneficiaryId())) {
						throw new ConflictResourceException(String.format("Beneficiary with id: %d has already been enlisted for Company with id: %d", cb.getBeneficiaryId(), cb.getCompanyId()));
					}
					final Long affectedRows = companyMapper.addBeneficiaryToCompany(cb);
					updateAffectedRowCount(affectedRows);
			    });
			sqlSessionFactoryProvider.closeSessionAndCommit();
		} catch(Exception e) {
			this.affectedRows = 0;
			LOGGER.error(e.getMessage(), e);
			sqlSessionFactoryProvider.closeSessionAndRollback();
			if (e instanceof BadRequestResourceException) throw e;
			if (e instanceof ConflictResourceException) throw e;
		}
		return this.affectedRows;
	}

	@Override
	public Long addNewBeneficiaryToCompany(Long companyId, List<Beneficiary> beneficiaries) throws BadRequestResourceException, NotFoundResourceException, ServerErrorResourceException {
		verifyDetails(String.valueOf(companyId), "%s: company's id is invalid");
		
		try {
			companyMapper = sqlSessionFactoryProvider.openSessionAndInitMapper(CompanyMapper.class);
			CompanyBeneficiary companyBeneficiary = new CompanyBeneficiary();
			this.affectedRows = 0;
			beneficiaries
				.stream()
				.forEach(b -> {
					 companyMapper.addBeneficiary(b);
					 verifyDetails(String.valueOf(b.getId()), "%s: beneficiary's id is invalid");
					 companyBeneficiary.setBeneficiaryId(b.getId());
					 companyBeneficiary.setCompanyId(companyId);
					 final Long affectedRows = companyMapper.addBeneficiaryToCompany(companyBeneficiary);
					 updateAffectedRowCount(affectedRows);
				});
			sqlSessionFactoryProvider.closeSessionAndCommit();
		} catch (Exception e) {
			this.affectedRows = 0;
			LOGGER.error(e.getMessage(), e);
			sqlSessionFactoryProvider.closeSessionAndRollback();
			if (e instanceof BadRequestResourceException) throw e;
		}
		return this.affectedRows;
	}

	@Override
	public boolean updateCompany(Company company) throws BadRequestResourceException, NotFoundResourceException, ServerErrorResourceException {
		verifyCompany(company, true);
		
		boolean status = false;
		try {
			companyMapper = sqlSessionFactoryProvider.openSessionAndInitMapper(CompanyMapper.class);
			List<Beneficiary> currentBeneficiaries = companyMapper.fetchCompanyBeneficiaries(company.getId());
			List<Beneficiary> inBeneficiaries = company.getBeneficiaries();
			List<Long> inBeneficiariesIds = new ArrayList<Long>();
			inBeneficiaries.stream().forEach(ib -> {
				inBeneficiariesIds.add(ib.getId());
			});
			if (inBeneficiaries.size() == 0) throw new ConflictResourceException(String.format("Each company is required to have at least one beneficiary at any point in time"));
			// add beneficiary if necessary
			inBeneficiaries
				.stream()
				.forEach(ib -> {
					boolean beneficiaryInCompany = currentBeneficiaries.parallelStream().anyMatch(cb -> cb.getId() == ib.getId());
					if (!beneficiaryInCompany) {
						verifyDetails(String.valueOf(ib.getId()), "%s: one of the beneficiary's id is invalid");
						Beneficiary beneficiary = beneficiaryRepository.getBeneficiary(ib.getId());
						if (null == beneficiary) companyMapper.addBeneficiary(ib);
						CompanyBeneficiary companyBeneficiary = new CompanyBeneficiary();
						companyBeneficiary.setBeneficiaryId(ib.getId());
						companyBeneficiary.setCompanyId(company.getId());
						companyMapper.addBeneficiaryToCompany(companyBeneficiary);
					}
				});
			// remove beneficiary if necessary
			currentBeneficiaries
				.stream()
				.forEach(cb -> {
					boolean retainBeneficiary = inBeneficiaries.parallelStream().anyMatch(ib -> ib.getId() == cb.getId());
					if (!retainBeneficiary) {
						CompanyBeneficiary companyBeneficiary = new CompanyBeneficiary();
						companyBeneficiary.setBeneficiaryId(cb.getId());
						companyBeneficiary.setCompanyId(company.getId());
						companyMapper.deleteBeneficiaryFromCompany(companyBeneficiary);
					}
				});
			
			status = companyMapper.updateCompanyById(company) > 0 ? true : false;
			sqlSessionFactoryProvider.closeSessionAndCommit();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			sqlSessionFactoryProvider.closeSessionAndRollback();
			if (e instanceof BadRequestResourceException) throw e;
			if (e instanceof ConflictResourceException) throw e;
		}
		
		return status;
	}

	@Override
	public boolean deleteCompany(Long companyId) throws BadRequestResourceException, NotFoundResourceException, ServerErrorResourceException {
		verifyDetails(String.valueOf(companyId), "%s: company's id is invalid or empty");
		boolean status = false;
		try {
			companyMapper = sqlSessionFactoryProvider.openSessionAndInitMapper(CompanyMapper.class);
			status = companyMapper.deleteCompanyById(companyId) > 0 ? true : false;
			sqlSessionFactoryProvider.closeSessionAndCommit();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			sqlSessionFactoryProvider.closeSessionAndRollback();
		}
		return status;
	}
	
	@Override
	public boolean deleteBeneficiaryFromCompany(CompanyBeneficiary companyBeneficiary) throws BadRequestResourceException, NotFoundResourceException, ServerErrorResourceException {
		boolean status = false;
		try {
			companyMapper = sqlSessionFactoryProvider.openSessionAndInitMapper(CompanyMapper.class);
			// a company must have at least one beneficiary
			List<Beneficiary>  beneficiaries = companyMapper.fetchCompanyBeneficiaries(companyBeneficiary.getCompanyId());
			if (beneficiaries.size() == 1) throw new ConflictResourceException(String.format("Each company is required to have at least one beneficiary at any point in time"));
			status = companyMapper.deleteBeneficiaryFromCompany(companyBeneficiary) > 0 ? true : false;
			sqlSessionFactoryProvider.closeSessionAndCommit();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			sqlSessionFactoryProvider.closeSessionAndRollback();
			if (e instanceof ConflictResourceException) throw e;
		}
		return status;
	}
	
	private boolean beneficiaryExistForCompany(Long companyId, Long beneficiaryId) {
		List<Beneficiary> beneficiaries = new ArrayList<Beneficiary>();
		if (companyMapper != null) beneficiaries = companyMapper.fetchCompanyBeneficiaries(companyId);
		else beneficiaries = getCompanyBeneficiaries(companyId);
		return beneficiaries
			.stream()
			.anyMatch(b -> b.getId() == beneficiaryId);
	}
	
	private void updateAffectedRowCount(Long affectedRows) {
		this.affectedRows += affectedRows;
	}

	private void verifyBeneficiaries(List<Beneficiary> beneficiaries, boolean existing) {
		if (null == beneficiaries || beneficiaries.isEmpty()) throw new BadRequestResourceException(String.format("%s: at least one company's beneficiary details must be provided", ERROR_START));
		beneficiaries
			.parallelStream()
		 	.forEach(b -> {
		 		if (existing && (null == b || null == b.getId() || 0 >= b.getId() || StringUtils.isBlank(b.getFirstName()) || StringUtils.isBlank(b.getLastName()))) {
		 			throw new BadRequestResourceException(String.format("%s: each of existing company's beneficiary required details must be valid and provided (id, firstName, lastName)", ERROR_START));
		 		} else if (!existing && (null == b || StringUtils.isBlank(b.getFirstName()) || StringUtils.isBlank(b.getLastName()))) {
	 			    throw new BadRequestResourceException(String.format("%s: each of new company's beneficiary rewuired details must be valid and provided (firstName, lastName)", ERROR_START));
	 		    }
	 	    });
	}
	
	private void verifyDetails(String detail, String message) {
		if (StringUtils.isBlank(detail) || "0".equals(detail) || "-1".equals(detail)) throw new BadRequestResourceException(String.format(message, ERROR_START));
	}

	private void verifyCompany(Company company, boolean existingBeneficiaries) {
		if (null == company) throw new BadRequestResourceException("Company details must be provided");
		verifyDetails(company.getName(), "%s: company's name can't be empty");
		verifyDetails(company.getAddress(), "%s: company's address can't be empty");
		verifyDetails(company.getCity(), "%s: company's city can't be empty");
		verifyDetails(company.getCountry(), "%s: company's domicile country can't be empty");
		verifyDetails(String.valueOf(company.getId()), "%s: company's id is invalid or empty");
		verifyBeneficiaries(company.getBeneficiaries(), existingBeneficiaries);
	}

}

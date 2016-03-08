package companyregistry.persistence.beneficiary.repository;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.cdi.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import companyregistry.domain.beneficiary.Beneficiary;
import companyregistry.domain.beneficiary.BeneficiaryUsage;
import companyregistry.domain.beneficiary.repository.BeneficiaryRepository;
import companyregistry.persistence.SqlSessionFactoryProvider;
import companyregistry.persistence.beneficiary.mapper.BeneficiaryMapper;
import companyregistry.restservice.exception.BadRequestResourceException;
import companyregistry.restservice.exception.ConflictResourceException;
import companyregistry.restservice.exception.NotFoundResourceException;
import companyregistry.restservice.exception.ServerErrorResourceException;

/**
 * @author dokuboyejo
 *
 */
public class BeneficiaryRepositoryImpl implements BeneficiaryRepository {

	@Mapper
    private BeneficiaryMapper beneficiaryMapper;
    private SqlSessionFactoryProvider<BeneficiaryMapper> sqlSessionFactoryProvider;
    private static final String ERROR_START = "An error occured";
    private static final String BENEFICIARY_NOT_FOUND = "Beneficiary details not found";
    private static final String BENEFICIARIES_NOT_FOUND = "No registered beneficiaries found";
    private static final Logger LOGGER = LoggerFactory.getLogger(BeneficiaryRepositoryImpl.class);
    
    public BeneficiaryRepositoryImpl() {
    	sqlSessionFactoryProvider = new SqlSessionFactoryProvider<BeneficiaryMapper>();
    }
    
    public BeneficiaryRepositoryImpl(BeneficiaryMapper beneficiaryMapper, SqlSessionFactoryProvider<BeneficiaryMapper> sqlSessionFactoryProvider) {
    	this.beneficiaryMapper = beneficiaryMapper;
    	this.sqlSessionFactoryProvider = sqlSessionFactoryProvider;
    }
    
	@Override
	public Beneficiary getBeneficiary(Long beneficiaryId) throws BadRequestResourceException, NotFoundResourceException, ServerErrorResourceException {
		verifyDetails(String.valueOf(beneficiaryId), "%s: beneficiary's id is invalid or empty");
		Beneficiary beneficiary = new Beneficiary();
		try {
			beneficiaryMapper = sqlSessionFactoryProvider.openSessionAndInitMapper(BeneficiaryMapper.class);
			beneficiary = beneficiaryMapper.findBeneficiaryById(beneficiaryId);
			if (null == beneficiary) throw new NotFoundResourceException(BENEFICIARY_NOT_FOUND);
			sqlSessionFactoryProvider.closeSession();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			sqlSessionFactoryProvider.closeSessionAndRollback();
			if (e instanceof NotFoundResourceException) throw e;
		}
		return beneficiary;
	}

	@Override
	public List<Beneficiary> getBeneficiaries() throws BadRequestResourceException, NotFoundResourceException, ServerErrorResourceException {
		List<Beneficiary> beneficiaries = new ArrayList<Beneficiary>();
		try {
			beneficiaryMapper = sqlSessionFactoryProvider.openSessionAndInitMapper(BeneficiaryMapper.class);
			beneficiaries = beneficiaryMapper.fetchAllBeneficiaries();
			if (beneficiaries.size() == 0) throw new NotFoundResourceException(BENEFICIARIES_NOT_FOUND);
			sqlSessionFactoryProvider.closeSession();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			sqlSessionFactoryProvider.closeSessionAndRollback();
			if (e instanceof NotFoundResourceException) throw e;
		}
		return beneficiaries;
	}

	@Override
	public Long addBeneficiary(Beneficiary beneficiary) throws BadRequestResourceException, NotFoundResourceException, ServerErrorResourceException {
		verifyBeneficiary(beneficiary);
		Long beneficiaryId = (long)-1;
		try {
			beneficiaryMapper = sqlSessionFactoryProvider.openSessionAndInitMapper(BeneficiaryMapper.class);
			Long affectedRows = beneficiaryMapper.addBeneficiary(beneficiary);
			// retrieve id of newly added beneficiary
			beneficiaryId = beneficiary.getId();
			sqlSessionFactoryProvider.closeSessionAndCommit();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			sqlSessionFactoryProvider.closeSessionAndRollback();
		}
		return beneficiaryId;
	}

	@Override
	public boolean updateBeneficiary(Beneficiary beneficiary) throws BadRequestResourceException, NotFoundResourceException, ServerErrorResourceException {
		verifyBeneficiary(beneficiary);
		boolean status = false;
		try {
			beneficiaryMapper = sqlSessionFactoryProvider.openSessionAndInitMapper(BeneficiaryMapper.class);
			status = beneficiaryMapper.updateBeneficiaryById(beneficiary) > 0 ? true : false;
			sqlSessionFactoryProvider.closeSessionAndCommit();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			sqlSessionFactoryProvider.closeSessionAndRollback();
		}
		return status;
	}
	
	@Override
	public boolean deleteBeneficiary(Long beneficiaryId) throws BadRequestResourceException, NotFoundResourceException, ServerErrorResourceException {
		verifyDetails(String.valueOf(beneficiaryId), "%s: beneficiary's id is invalid or empty");
		boolean status = false;
		try {
			beneficiaryMapper = sqlSessionFactoryProvider.openSessionAndInitMapper(BeneficiaryMapper.class);
			String message = "%s: Can't delete beneficiary. One of the registered companies has benefiary with id '%d' as its only listed beneficiaries";
			BeneficiaryUsage beneficiaryUsage = beneficiaryMapper.fetchBeneficiaryUsage(beneficiaryId);
			System.out.println(beneficiaryUsage);
			if (beneficiaryUsage != null && beneficiaryUsage.getUsageCount() == 1) throw new ConflictResourceException(String.format(message, ERROR_START, beneficiaryId));
			status =  beneficiaryMapper.deleteBeneficiaryById(beneficiaryId) > 0 ? true : false;
			sqlSessionFactoryProvider.closeSessionAndCommit();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			sqlSessionFactoryProvider.closeSessionAndRollback();
			if (e instanceof ConflictResourceException) throw e;
		}
		return status;
	}
	
	private void verifyDetails(String detail, String message) {
		if (StringUtils.isBlank(detail) || "0".equals(detail)) throw new BadRequestResourceException(String.format(message, ERROR_START));
	}
	
	private void verifyBeneficiary(Beneficiary beneficiary) {
		if (null == beneficiary) throw new BadRequestResourceException("beneficiary details must be provided");
		verifyDetails(String.valueOf(beneficiary.getId()), "%s: beneficiary's id is invalid or empty");
		verifyDetails(beneficiary.getFirstName(), "%s: beneficiary's firstName can't be empty");
		verifyDetails(beneficiary.getLastName(), "%s: beneficiary's lastName can't be empty");
	}

}

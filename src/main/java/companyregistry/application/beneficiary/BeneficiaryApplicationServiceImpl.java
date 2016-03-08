package companyregistry.application.beneficiary;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import companyregistry.domain.beneficiary.Beneficiary;
import companyregistry.domain.beneficiary.repository.BeneficiaryRepository;
import companyregistry.persistence.beneficiary.repository.BeneficiaryRepositoryImpl;
import companyregistry.restservice.exception.BadRequestResourceException;
import companyregistry.restservice.exception.NotFoundResourceException;
import companyregistry.restservice.exception.ServerErrorResourceException;

/**
 * @author dokuboyejo
 *
 */
@ApplicationScoped
public class BeneficiaryApplicationServiceImpl implements BeneficiaryApplicationService {

	private BeneficiaryRepository beneficiaryRepository;

	/**
	 * @param beneficiaryRepository the repository to set
	 */
	public void setBeneficiaryRepository(BeneficiaryRepository beneficiaryRepository) {
		this.beneficiaryRepository = beneficiaryRepository;
	}

	public BeneficiaryApplicationServiceImpl() {
		beneficiaryRepository = new BeneficiaryRepositoryImpl();
	}

	@Override
	public Beneficiary getBeneficiary(Long beneficiaryId) throws BadRequestResourceException, NotFoundResourceException, ServerErrorResourceException {
		return beneficiaryRepository.getBeneficiary(beneficiaryId);
	}

	@Override
	public List<Beneficiary> getBeneficiaries() throws BadRequestResourceException, NotFoundResourceException, ServerErrorResourceException {
		return beneficiaryRepository.getBeneficiaries();
	}

	@Override
	public Long addBeneficiary(Beneficiary beneficiary) throws BadRequestResourceException, NotFoundResourceException, ServerErrorResourceException {
		return beneficiaryRepository.addBeneficiary(beneficiary);
	}

	@Override
	public boolean updateBeneficiary(Beneficiary beneficiary) throws BadRequestResourceException, NotFoundResourceException, ServerErrorResourceException {
		return beneficiaryRepository.updateBeneficiary(beneficiary);
	}
	
	@Override
	public boolean deleteBeneficiary(Long beneficiaryId) throws BadRequestResourceException, NotFoundResourceException, ServerErrorResourceException {
		return beneficiaryRepository.deleteBeneficiary(beneficiaryId);
	}

}
package companyregistry.application.beneficiary;

import java.util.List;

import companyregistry.domain.beneficiary.Beneficiary;
import companyregistry.restservice.exception.BadRequestResourceException;
import companyregistry.restservice.exception.NotFoundResourceException;
import companyregistry.restservice.exception.ServerErrorResourceException;

/**
 * @author dokuboyejo
 *
 */
public interface BeneficiaryApplicationService {

	Beneficiary getBeneficiary(Long beneficiaryId) throws BadRequestResourceException, NotFoundResourceException, ServerErrorResourceException;
	List<Beneficiary> getBeneficiaries() throws BadRequestResourceException, NotFoundResourceException, ServerErrorResourceException;
	Long addBeneficiary(Beneficiary beneficiary) throws BadRequestResourceException, NotFoundResourceException, ServerErrorResourceException;
	boolean updateBeneficiary(Beneficiary beneficiary) throws BadRequestResourceException, NotFoundResourceException, ServerErrorResourceException;
    boolean deleteBeneficiary(Long beneficiaryId) throws BadRequestResourceException, NotFoundResourceException, ServerErrorResourceException;

}
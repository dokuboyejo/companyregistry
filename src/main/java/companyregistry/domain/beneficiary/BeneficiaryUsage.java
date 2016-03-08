package companyregistry.domain.beneficiary;

/**
 * @author dokuboyejo
 *
 */
public class BeneficiaryUsage {

	private Long beneficiaryId;
	private Long usageCount;
	
	/**
	 * @return the beneficiaryId
	 */
	public Long getBeneficiaryId() {
		return beneficiaryId;
	}
	/**
	 * @param beneficiaryId the beneficiaryId to set
	 */
	public void setBeneficiaryId(Long beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}
	/**
	 * @return the usageCount
	 */
	public Long getUsageCount() {
		return usageCount;
	}
	/**
	 * @param usageCount the usageCount to set
	 */
	public void setUSageCount(Long usageCount) {
		this.usageCount = usageCount;
	}
	
}

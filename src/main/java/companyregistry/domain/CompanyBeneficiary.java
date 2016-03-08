package companyregistry.domain;

public class CompanyBeneficiary {
	
	private Long companyId;
	private Long beneficiaryId;
	
	/**
	 * @return the companyId
	 */
	public final Long getCompanyId() {
		return companyId;
	}
	/**
	 * @param companyId the companyId to set
	 */
	public final void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	/**
	 * @return the beneficiaryId
	 */
	public final Long getBeneficiaryId() {
		return beneficiaryId;
	}
	/**
	 * @param beneficiaryId the beneficiaryId to set
	 */
	public final void setBeneficiaryId(Long beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}
	
}

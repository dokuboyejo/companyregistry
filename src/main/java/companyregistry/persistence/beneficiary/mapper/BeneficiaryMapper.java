package companyregistry.persistence.beneficiary.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import companyregistry.domain.beneficiary.Beneficiary;
import companyregistry.domain.beneficiary.BeneficiaryUsage;

public interface BeneficiaryMapper {

	public Beneficiary findBeneficiaryById(@Param("id") Long id);
    public BeneficiaryUsage fetchBeneficiaryUsage(@Param("id") Long id);
    public List<Beneficiary> fetchAllBeneficiaries();
    public Long addBeneficiary(@Param("beneficiary") Beneficiary beneficiary);
    public Long updateBeneficiaryById(@Param("beneficiary") Beneficiary beneficiary);
    public Long deleteBeneficiaryById(@Param("id") Long id);
    
}
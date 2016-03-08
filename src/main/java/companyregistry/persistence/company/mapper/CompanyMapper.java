package companyregistry.persistence.company.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import companyregistry.domain.CompanyBeneficiary;
import companyregistry.domain.beneficiary.Beneficiary;
import companyregistry.domain.company.Company;

public interface CompanyMapper {

	public Company findCompanyById(@Param("id") Long id);
	public List<Company> fetchAllCompanies();
    public List<Beneficiary> fetchCompanyBeneficiaries(@Param("id") Long id);
    public Long addCompany(@Param("company") Company company);
    public Long addBeneficiary(@Param("beneficiary") Beneficiary beneficiary);
    public Long addBeneficiaryToCompany(@Param("companyBeneficiary") CompanyBeneficiary companyBeneficiary);
    public Long updateCompanyById(@Param("company") Company company);
    public Long deleteCompanyById(@Param("id") Long id);
    public Long deleteBeneficiaryFromCompany(@Param("companyBeneficiary") CompanyBeneficiary companyBeneficiary);
    
}
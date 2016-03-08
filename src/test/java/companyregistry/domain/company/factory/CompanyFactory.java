package companyregistry.domain.company.factory;

import companyregistry.domain.beneficiary.factory.BeneficiaryFactory;
import companyregistry.domain.company.Company;

/**
 * @author dokuboyejo
 *
 */
public class CompanyFactory {
	
	public static Company createCompany() {
		Company company = new Company();
		company.setId((long)1);
		company.setName("ABC Enterprise");
		company.setAddress("Lynnwood");
		company.setCity("Pretoria");
		company.setCountry("South Africa");
		company.setEmail("dxx@email.com");
		company.setPhoneNumber("22-1234-5566");
		company.setBeneficiaries(BeneficiaryFactory.createBeneficiaryLIst());
		return company;
	}
	
}

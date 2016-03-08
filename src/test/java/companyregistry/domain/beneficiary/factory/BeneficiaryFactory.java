package companyregistry.domain.beneficiary.factory;

import java.util.ArrayList;
import java.util.List;

import companyregistry.domain.beneficiary.Beneficiary;

public class BeneficiaryFactory {

	public static Beneficiary createBeneficiary() {
		Beneficiary beneficiary = new Beneficiary();
		beneficiary.setId((long)1);
		beneficiary.setFirstName("John");
		beneficiary.setLastName("Doe");
		return beneficiary;
	}
	
	public static  List<Beneficiary> createBeneficiaryLIst() {
		List<Beneficiary> beneficiaries = new ArrayList<Beneficiary>();
		
		Beneficiary firstBeneficiary = new Beneficiary();
		firstBeneficiary.setId((long)1);
		firstBeneficiary.setFirstName("Jack");
		firstBeneficiary.setLastName("Johnson");
		
		Beneficiary secondBeneficiary = new Beneficiary();
		firstBeneficiary.setId((long)2);
		secondBeneficiary.setFirstName("Michael");
		secondBeneficiary.setLastName("Faraday");
		
		beneficiaries.add(firstBeneficiary);
		beneficiaries.add(secondBeneficiary);
		return beneficiaries;
	}
	
}

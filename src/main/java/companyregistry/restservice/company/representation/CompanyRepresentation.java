package companyregistry.restservice.company.representation;

import java.util.List;

import companyregistry.domain.beneficiary.Beneficiary;
import companyregistry.domain.company.Company;

public class CompanyRepresentation {

	private Long id;
	private String name;
	private String address;
	private String city;
	private String country;
	private String email;
	private String phoneNumber;
	private List<Beneficiary> beneficiaries;

	public CompanyRepresentation(){}
	
	public CompanyRepresentation(Company company) {
		this.id = company.getId();
		this.name = company.getName();
		this.address = company.getAddress();
		this.city = company.getCity();
		this.country = company.getCountry();
		this.email = company.getEmail();
		this.phoneNumber = company.getPhoneNumber();
		this.beneficiaries = company.getBeneficiaries();
	}
	
	/**
	 * @return the id
	 */
	public final Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public final void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public final void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the address
	 */
	public final String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public final void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the city
	 */
	public final String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public final void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the country
	 */
	public final String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public final void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the email
	 */
	public final String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public final void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the phoneNumber
	 */
	public final String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber
	 *            the phoneNumber to set
	 */
	public final void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the beneficiaries
	 */
	public final List<Beneficiary> getBeneficiaries() {
		return beneficiaries;
	}

	/**
	 * @param beneficiaries
	 *            the beneficiaries to set
	 */
	public final void setBeneficiaries(List<Beneficiary> beneficiaries) {
		this.beneficiaries = beneficiaries;
	}

}

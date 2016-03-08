package companyregistry.util;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import companyregistry.domain.beneficiary.Beneficiary;
import companyregistry.domain.beneficiary.factory.BeneficiaryFactory;

/**
 * @author dokuboyejo
 *
 */
public class LoadDataTest {

	private String JSON;
	private String JSON_LIST;
	private Beneficiary BENEFICIARY_OBJECT;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		JSON = "{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Doe\"}";
		JSON_LIST = "[{\"id\":1,\"firstName\":\"Jack\",\"lastName\":\"Johnson\"}, {\"id\":2,\"firstName\":\"Michael\",\"lastName\":\"Faraday\"}]";
		BENEFICIARY_OBJECT = BeneficiaryFactory.createBeneficiary();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link companyregistry.util.LoadData#jsonize(java.lang.Object)}.
	 */
	@Test
	public void testJsonize() {
		String beneficiaryJsonString = LoadData.jsonize(BENEFICIARY_OBJECT);
		Assert.assertEquals(JSON, beneficiaryJsonString);
	}

	/**
	 * Test method for {@link companyregistry.util.LoadData#objectize(java.lang.String, java.lang.Class)}.
	 */
	@Test
	public void testObjectize() {
		Beneficiary beneficiary = LoadData.objectize(JSON, Beneficiary.class);
		Assert.assertEquals(BENEFICIARY_OBJECT.getId(), beneficiary.getId());
		Assert.assertEquals(BENEFICIARY_OBJECT.getFirstName(), beneficiary.getFirstName());
		Assert.assertEquals(BENEFICIARY_OBJECT.getLastName(), beneficiary.getLastName());
	}

	/**
	 * Test method for {@link companyregistry.util.LoadData#objectizeList(java.lang.String, java.lang.Class)}.
	 */
	@Test
	public void testObjectizeList() {
		List<Beneficiary> beneficiaries = LoadData.objectizeList(JSON_LIST, Beneficiary.class);
		Assert.assertEquals(2, beneficiaries.size());
		Assert.assertEquals("Faraday", beneficiaries.get(1).getLastName());
	}

	/**
	 * Test method for {@link companyregistry.util.LoadData#render(java.lang.Object)}.
	 */
	@Test
	public void testRender() {
		LoadData loadData = new LoadData();
		String beneficiaryJsonString = loadData.render(BENEFICIARY_OBJECT);
		Assert.assertEquals(JSON, beneficiaryJsonString);
	}

}

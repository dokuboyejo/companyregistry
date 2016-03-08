package companyregistry.domain.beneficiary;

import org.junit.Test;

import companyregistry.base.AbstractBeanTest;

/**
 * @author dokuboyejo
 *
 */
public class BeneficiaryTest extends AbstractBeanTest<Beneficiary> {

	@Test
	public void testGetterAndSetter() throws Exception{
		this.testGetterAndSetter(Beneficiary.class);
	}

}

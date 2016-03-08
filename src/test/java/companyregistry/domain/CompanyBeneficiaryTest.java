/**
 * 
 */
package companyregistry.domain;

import org.junit.Test;

import companyregistry.base.AbstractBeanTest;

/**
 * @author dokuboyejo
 *
 */
public class CompanyBeneficiaryTest extends AbstractBeanTest<CompanyBeneficiary>{
	
	@Test
	public void testGetterAndSetter() throws Exception{
		this.testGetterAndSetter(CompanyBeneficiary.class);
	}

}

package companyregistry.domain.company;

import org.junit.Test;

import companyregistry.base.AbstractBeanTest;

/**
 * @author dokuboyejo
 *
 */
public class CompanyTest extends AbstractBeanTest<Company> {

	@Test
	public void testGetterAndSetter() throws Exception{
		this.testGetterAndSetter(Company.class);
	}

}

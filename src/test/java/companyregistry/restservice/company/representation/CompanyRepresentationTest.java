package companyregistry.restservice.company.representation;

import org.junit.Test;

import companyregistry.base.AbstractBeanTest;

/**
 * @author dokuboyejo
 *
 */
public class CompanyRepresentationTest extends AbstractBeanTest<CompanyRepresentation> {

	@Test
	public void testGetterAndSetter() throws Exception{
		this.testGetterAndSetter(CompanyRepresentation.class);
	}

}
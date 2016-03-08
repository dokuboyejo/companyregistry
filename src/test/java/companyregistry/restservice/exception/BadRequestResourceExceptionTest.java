package companyregistry.restservice.exception;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

/**
 * @author dokuboyejo
 *
 */
public class BadRequestResourceExceptionTest extends AbstractResourceExceptionTest<BadRequestResourceException>{

	@Test
	public void test() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		this.testResourceException(BadRequestResourceException.class);
	}

}

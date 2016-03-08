package companyregistry.restservice.exception;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

/**
 * @author dokuboyejo
 *
 */
public class NotFoundResourceExceptionTest extends AbstractResourceExceptionTest<NotFoundResourceException>{

	@Test
	public void test() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		this.testResourceException(NotFoundResourceException.class);
	}

}
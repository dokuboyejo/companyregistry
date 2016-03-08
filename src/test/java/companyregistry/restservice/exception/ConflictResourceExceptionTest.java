package companyregistry.restservice.exception;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

/**
 * @author dokuboyejo
 *
 */
public class ConflictResourceExceptionTest extends AbstractResourceExceptionTest<ConflictResourceException>{

	@Test
	public void test() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		this.testResourceException(ConflictResourceException.class);
	}

}
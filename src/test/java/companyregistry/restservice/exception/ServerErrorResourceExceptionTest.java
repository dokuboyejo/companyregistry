package companyregistry.restservice.exception;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

/**
 * @author dokuboyejo
 *
 */
public class ServerErrorResourceExceptionTest extends AbstractResourceExceptionTest<ServerErrorResourceException>{

	@Test
	public void test() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		this.testResourceException(ServerErrorResourceException.class);
	}

}

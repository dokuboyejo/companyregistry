package companyregistry.restservice.exception;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

import org.junit.Assert;


/**
 * @author dokuboyejo
 *
 */
public class AbstractResourceExceptionTest<T> {

	private boolean HAS_NO_ARG_CONSTRUCTOR = false;
	private boolean HAS_STRING_CONSTRUCTOR = false;
	private boolean HAS_STRING_THROWABLE_CONSTRUCTOR = false;
	private boolean HAS_THROWABLE_CONSTRUCTOR = false;

	public void testResourceException(Class<T> type) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		for (Constructor<?> constructor : type.getConstructors()) {
			Type[] parameterTypes = constructor.getParameterTypes();
			hasNoArgConstructor(type, parameterTypes);
			hasStringArgConstructor(parameterTypes);
			hasStringThrowableArgConstructor(parameterTypes);
			hasThrowableArgConstructor(parameterTypes);
		}
		
		String typeName =type.getName();
		Assert.assertEquals(String.format("%s should have a no-arg constructor", typeName, typeName), true, HAS_NO_ARG_CONSTRUCTOR);
		Assert.assertEquals(String.format("%s should have a %s(java.lang.String) constructor", typeName, typeName), true, HAS_STRING_CONSTRUCTOR);
		Assert.assertEquals(String.format("%s should have a %s(java.lang.String, java.lang.Throwable) constructor", typeName, typeName), true, HAS_STRING_THROWABLE_CONSTRUCTOR);
		Assert.assertEquals(String.format("%s should have a %s( java.lang.Throwable) constructor", typeName, typeName), true, HAS_THROWABLE_CONSTRUCTOR);
	}

	private void hasNoArgConstructor(Class<T> type, Type[] parameterTypes) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Assert.assertNotNull(type.newInstance());
		HAS_NO_ARG_CONSTRUCTOR = !HAS_NO_ARG_CONSTRUCTOR ? parameterTypes.length == 0 : true;
	}

	private void hasStringArgConstructor(Type[] parameterTypes) {
		HAS_STRING_CONSTRUCTOR = !HAS_STRING_CONSTRUCTOR ? parameterTypes.length == 1 && parameterTypes[0].getTypeName().equals("java.lang.String") : true ;
	}

	private void hasStringThrowableArgConstructor(Type[] parameterTypes) {
		HAS_STRING_THROWABLE_CONSTRUCTOR = !HAS_STRING_THROWABLE_CONSTRUCTOR ? parameterTypes.length == 2 && parameterTypes[0].getTypeName().equals("java.lang.String") && parameterTypes[1].getTypeName().equals("java.lang.Throwable") : true;
	}

	private void hasThrowableArgConstructor(Type[] parameterTypes) {
		HAS_THROWABLE_CONSTRUCTOR = !HAS_THROWABLE_CONSTRUCTOR && parameterTypes.length == 1 ? parameterTypes[0].getTypeName().equals("java.lang.Throwable") : true;
	}

}

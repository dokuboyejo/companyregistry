package companyregistry.base;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Assert;
import org.meanbean.lang.Factory;
import org.meanbean.test.BeanTester;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * @author dokuboyejo
 *
 */
public abstract class AbstractBeanTest<T> {

	public void testBeanSerializability(Class<T> type) throws Exception {
		final T myBean = getBeanInstance(type);
		final byte[] serializedMyBean = SerializationUtils.serialize((Serializable) myBean);
		@SuppressWarnings("unchecked")
		final T deserializedMyBean = (T) SerializationUtils.deserialize(serializedMyBean);
		Assert.assertEquals(String.format("%s is not serializable", type.getName()), myBean, deserializedMyBean);
	}

	public void testEqualsAndHashCodeContract(Class<T> type) throws InstantiationException, IllegalAccessException {
		EqualsVerifier.forClass(type).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS, Warning.NULL_FIELDS).verify();
	}

	public void testGetterAndSetter(Class<T> type) throws Exception {
		final BeanTester beanTester = new BeanTester();
		beanTester.getFactoryCollection().addFactory(LocalDateTime.class, new LocalDateTimeFactory());
		beanTester.testBean(type);
	}

	protected T getBeanInstance(Class<T> type) throws InstantiationException, IllegalAccessException{
		return type.newInstance();
	}

	/**
	 * Concrete Factory that creates a LocalDateTime.
	 */
	class LocalDateTimeFactory implements Factory<Object> {

		@Override
		public LocalDateTime create() {
			return LocalDateTime.now();
		}

	}

}

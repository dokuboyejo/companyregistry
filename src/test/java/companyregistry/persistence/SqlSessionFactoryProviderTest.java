/**
 * 
 */
package companyregistry.persistence;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import companyregistry.persistence.company.mapper.CompanyMapper;

/**
 * @author dokuboyejo
 *
 */
public class SqlSessionFactoryProviderTest {

	@Mock
	private Resources resources;
	@Mock
	private Reader reader;

	private SqlSessionFactoryProvider<CompanyMapper> sqlSessionFactoryProvider;
	
	@Before
	public void setUp() throws IOException {
		sqlSessionFactoryProvider = new SqlSessionFactoryProvider<>();
	}
	
	@After
	public void tearDown() throws IOException {
		sqlSessionFactoryProvider.closeSessionAndRollback();
	}
	
	/**
	 * Test method for {@link companyregistry.persistence.SqlSessionFactoryProvider#produceSessionFactory()}.
	 */
	@Test
	public void testProduceSessionFactory() {
		SqlSessionFactory sqlSessionFactory = SqlSessionFactoryProvider.produceSessionFactory();
		Assert.assertNotNull(sqlSessionFactory);
	}

	/**
	 * Test method for {@link companyregistry.persistence.SqlSessionFactoryProvider#openSessionAndInitMapper(java.lang.Class)}.
	 */
	@Test
	public void testOpenSessionAndInitMapper() {
		CompanyMapper companyMapper = sqlSessionFactoryProvider.openSessionAndInitMapper(CompanyMapper.class);
		Assert.assertNotNull(companyMapper);
	}

}

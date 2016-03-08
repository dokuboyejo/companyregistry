/**
 * 
 */
package companyregistry.persistence.company.repository;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;

import companyregistry.domain.CompanyBeneficiary;
import companyregistry.domain.beneficiary.Beneficiary;
import companyregistry.domain.company.Company;
import companyregistry.domain.company.repository.CompanyRepository;
import companyregistry.persistence.SqlSessionFactoryProvider;
import companyregistry.persistence.company.mapper.CompanyMapper;
import companyregistry.restservice.exception.BadRequestResourceException;
import companyregistry.restservice.exception.NotFoundResourceException;

/**
 * @author dokuboyejo
 *
 */
public class CompanyRepositoryImplTest {

	@Rule
	public final ExpectedException exception = ExpectedException.none();
	@Mock
	private CompanyMapper mockCompanyMapper;
	@Mock
	private SqlSessionFactoryProvider<CompanyMapper> mockSqlSessionFactoryProvider;
	@Mock
	private Company mockCompany;
	@Mock
	private Beneficiary mockBeneficiary;
	@Mock
	private CompanyBeneficiary mockCompanyBeneficiary;
	@Mock
	private List<Company> mockCompanies;
	@Mock
	private List<Beneficiary> mockBeneficiaries;
	@Mock
	private List<CompanyBeneficiary> mockCompanyBeneficiaries;
	@Mock
	private Stream<CompanyBeneficiary> mockCompanyBeneficiaryStream;
	@Mock
	private Stream<Beneficiary> mockBeneficiariesStream;
	private CompanyRepository companyRepository;
	private final long COMPANY_ID = 1;
	private final long ROWS_AFFECTED = 1;
	private final boolean TRUE_STATUS = true;
	private final boolean FALSE_STATUS = false;
	
	/**
	 * @throws java.lang.Exception
	 */
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		mockSqlSessionFactoryProvider = mock(SqlSessionFactoryProvider.class);
		mockCompanyMapper = mock(CompanyMapper.class);
		mockCompany = mock(Company.class);
		mockCompanyBeneficiary = mock(CompanyBeneficiary.class);
		mockCompanyBeneficiaryStream = mock(Stream.class);
		mockBeneficiariesStream = mock(Stream.class);
		mockCompanies = mock(List.class);
		mockBeneficiaries = mock(List.class);
		mockCompanyBeneficiaries = mock(List.class);
		
		companyRepository = new CompanyRepositoryImpl(mockCompanyMapper, mockSqlSessionFactoryProvider);
		
		when(mockSqlSessionFactoryProvider.openSessionAndInitMapper(CompanyMapper.class)).thenReturn(mockCompanyMapper);
		doNothing().when(mockSqlSessionFactoryProvider).closeSessionAndCommit();
		when(mockBeneficiaries.stream()).thenReturn(mockBeneficiariesStream);
		when(mockCompanyBeneficiaries.stream()).thenReturn(mockCompanyBeneficiaryStream);
		when(mockCompanyMapper.findCompanyById(COMPANY_ID)).thenReturn(mockCompany);
		when(mockCompanyMapper.fetchAllCompanies()).thenReturn(mockCompanies);
		when(mockCompanyMapper.fetchCompanyBeneficiaries(COMPANY_ID)).thenReturn(mockBeneficiaries);
		when(mockCompanyMapper.addCompany(mockCompany)).thenReturn(COMPANY_ID);
		when(mockCompanyMapper.addBeneficiaryToCompany(mockCompanyBeneficiary)).thenReturn(ROWS_AFFECTED);
		when(mockCompanyMapper.updateCompanyById(mockCompany)).thenReturn(ROWS_AFFECTED);
		when(mockCompanyMapper.deleteCompanyById(COMPANY_ID)).thenReturn(ROWS_AFFECTED);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		mockSqlSessionFactoryProvider.closeSessionAndRollback();
	}

	/**
	 * Test method for {@link companyregistry.persistence.company.repository.CompanyRepositoryImpl#getCompany(java.lang.Long)}.
	 */
	@Test
	public void testGetCompany() {
		Company beneficiary = companyRepository.getCompany(COMPANY_ID);
		Assert.assertNotNull(beneficiary);
		Assert.assertEquals(mockCompany, beneficiary);
		verify(mockSqlSessionFactoryProvider).openSessionAndInitMapper(CompanyMapper.class);
		verify(mockCompanyMapper).findCompanyById(COMPANY_ID);
		verify(mockSqlSessionFactoryProvider).closeSession();
	}

	/**
	 * Test method for {@link companyregistry.persistence.company.repository.CompanyRepositoryImpl#getCompanies()}.
	 */
	@Test
	public void testGetCompanies() {
		exception.expect(NotFoundResourceException.class);
		List<Company> companies = companyRepository.getCompanies();
		Assert.assertNotNull(companies);
		Assert.assertEquals(mockCompanies, companies);
		verify(mockSqlSessionFactoryProvider).openSessionAndInitMapper(CompanyMapper.class);
		verify(mockCompanyMapper).fetchAllCompanies();
		verify(mockSqlSessionFactoryProvider).closeSession();
	}

	/**
	 * Test method for {@link companyregistry.persistence.company.repository.CompanyRepositoryImpl#getCompanyBeneficiaries(java.lang.Long)}.
	 */
	@Test
	public void testGetCompanyBeneficiaries() {
		exception.expect(NotFoundResourceException.class);
		List<Beneficiary> beneficiaries = companyRepository.getCompanyBeneficiaries(COMPANY_ID);
		Assert.assertNotNull(beneficiaries);
		Assert.assertEquals(mockBeneficiaries, beneficiaries);
		verify(mockSqlSessionFactoryProvider).openSessionAndInitMapper(CompanyMapper.class);
		verify(mockCompanyMapper).fetchCompanyBeneficiaries(COMPANY_ID);
		verify(mockSqlSessionFactoryProvider).closeSession();
	}

	/**
	 * Test method for {@link companyregistry.persistence.company.repository.CompanyRepositoryImpl#addCompany(companyregistry.domain.company.Company)}.
	 */
	@Test(expected=BadRequestResourceException.class)
	public void testAddCompany() {
		Long companyId = companyRepository.addCompany(mockCompany);
		Assert.assertNotNull(companyId);
		Assert.assertEquals(COMPANY_ID, companyId.longValue());
		verify(mockSqlSessionFactoryProvider).openSessionAndInitMapper(CompanyMapper.class);
		verify(mockCompanyMapper).addCompany(mockCompany);
		verify(mockSqlSessionFactoryProvider).closeSessionAndCommit();
	}

	/**
	 * Test method for {@link companyregistry.persistence.company.repository.CompanyRepositoryImpl#addExistingCOMPANY_IDToCompany(java.util.List)}.
	 */
	@Test
	public void testAddExistingBeneficiaryToCompany() {
		Long numberOfBeneficiariesAdded = companyRepository.addExistingBeneficiaryToCompany(mockCompanyBeneficiaries);
		Assert.assertNotNull(numberOfBeneficiariesAdded);
		Assert.assertEquals(0, numberOfBeneficiariesAdded.longValue());
		verify(mockSqlSessionFactoryProvider).openSessionAndInitMapper(CompanyMapper.class);
		verify(mockCompanyMapper, times(0)).addBeneficiaryToCompany(mockCompanyBeneficiary);
		verify(mockSqlSessionFactoryProvider).closeSessionAndCommit();
	}

	/**
	 * Test method for {@link companyregistry.persistence.company.repository.CompanyRepositoryImpl#addNewBeneficiaryToCompany(java.lang.Long, java.util.List)}.
	 */
	@Test
	public void testAddNewBeneficiaryToCompany() {
		Long numberOfBeneficiariesAdded = companyRepository.addNewBeneficiaryToCompany(COMPANY_ID, mockBeneficiaries);
		Assert.assertNotNull(numberOfBeneficiariesAdded);
		Assert.assertEquals(0, numberOfBeneficiariesAdded.longValue());
		verify(mockSqlSessionFactoryProvider).openSessionAndInitMapper(CompanyMapper.class);
		verify(mockCompanyMapper, times(0)).addBeneficiary(any(Beneficiary.class));
		verify(mockCompanyMapper, times(0)).addBeneficiaryToCompany(any(CompanyBeneficiary.class));
		verify(mockSqlSessionFactoryProvider).closeSessionAndCommit();
	}

	/**
	 * Test method for {@link companyregistry.persistence.company.repository.CompanyRepositoryImpl#updateCompany(companyregistry.domain.company.Company)}.
	 */
	@Test(expected=BadRequestResourceException.class)
	public void testUpdateCompany() {
		boolean status = companyRepository.updateCompany(mockCompany);
		Assert.assertTrue(status);
		Assert.assertEquals(TRUE_STATUS, status);
		verify(mockSqlSessionFactoryProvider).openSessionAndInitMapper(CompanyMapper.class);
		verify(mockCompanyMapper).updateCompanyById(mockCompany);
		verify(mockSqlSessionFactoryProvider).closeSessionAndCommit();
	}

	/**
	 * Test method for {@link companyregistry.persistence.company.repository.CompanyRepositoryImpl#deleteCompany(java.lang.Long)}.
	 */
	@Test
	public void testDeleteCompany() {
		boolean status = companyRepository.deleteCompany(COMPANY_ID);
		Assert.assertTrue(status);
		Assert.assertEquals(TRUE_STATUS, status);
		verify(mockSqlSessionFactoryProvider).openSessionAndInitMapper(CompanyMapper.class);
		verify(mockCompanyMapper).deleteCompanyById(COMPANY_ID);
		verify(mockSqlSessionFactoryProvider).closeSessionAndCommit();
	}

	/**
	 * Test method for {@link companyregistry.persistence.company.repository.CompanyRepositoryImpl#deleteBeneficiaryFromCompany(companyregistry.domain.CompanyBeneficiary)}.
	 */
	@Test
	public void testDeleteBeneficiaryFromCompany() {
		boolean status = companyRepository.deleteBeneficiaryFromCompany(mockCompanyBeneficiary);
		Assert.assertFalse(status);
		Assert.assertEquals(FALSE_STATUS, status);
		verify(mockSqlSessionFactoryProvider).openSessionAndInitMapper(CompanyMapper.class);
		verify(mockCompanyMapper).fetchCompanyBeneficiaries(mockCompanyBeneficiary.getCompanyId());
		verify(mockSqlSessionFactoryProvider).closeSessionAndCommit();
	}

}

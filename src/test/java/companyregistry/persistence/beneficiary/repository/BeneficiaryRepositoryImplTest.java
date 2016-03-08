package companyregistry.persistence.beneficiary.repository;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;

import companyregistry.domain.beneficiary.Beneficiary;
import companyregistry.domain.beneficiary.BeneficiaryUsage;
import companyregistry.domain.beneficiary.repository.BeneficiaryRepository;
import companyregistry.persistence.SqlSessionFactoryProvider;
import companyregistry.persistence.beneficiary.mapper.BeneficiaryMapper;
import companyregistry.restservice.exception.BadRequestResourceException;
import companyregistry.restservice.exception.NotFoundResourceException;

/**
 * @author dokuboyejo
 *
 */
public class BeneficiaryRepositoryImplTest {

	@Rule
	public final ExpectedException exception = ExpectedException.none();
	@Mock
	private BeneficiaryMapper mockBeneficiaryMapper;
	@Mock
	private SqlSessionFactoryProvider<BeneficiaryMapper> mockSqlSessionFactoryProvider;
	@Mock
	private Beneficiary mockBeneficiary;
	@Mock
	private BeneficiaryUsage mockBeneficiaryUsage;
	@Mock
	private List<Beneficiary> mockBeneficiaries;
	private BeneficiaryRepository beneficiaryRepository;
	private final long BENEFICIARY_ID = 1;
	private final long ROWS_AFFECTED = 1;
	private final long BENEFICIARY_COUNT = 2;
	private final boolean STATUS = true;
	
	/**
	 * @throws java.lang.Exception
	 */
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		mockSqlSessionFactoryProvider = mock(SqlSessionFactoryProvider.class);
		mockBeneficiaryMapper = mock(BeneficiaryMapper.class);
		mockBeneficiary = mock(Beneficiary.class);
		mockBeneficiaryUsage = mock(BeneficiaryUsage.class);
		mockBeneficiaries = mock(List.class);
		beneficiaryRepository = new BeneficiaryRepositoryImpl(mockBeneficiaryMapper, mockSqlSessionFactoryProvider);
		when(mockSqlSessionFactoryProvider.openSessionAndInitMapper(BeneficiaryMapper.class)).thenReturn(mockBeneficiaryMapper);
		doNothing().when(mockSqlSessionFactoryProvider).closeSessionAndCommit();
		when(mockBeneficiaryUsage.getUsageCount()).thenReturn(BENEFICIARY_COUNT);
		when(mockBeneficiaryMapper.findBeneficiaryById(BENEFICIARY_ID)).thenReturn(mockBeneficiary);
		when(mockBeneficiaryMapper.fetchAllBeneficiaries()).thenReturn(mockBeneficiaries);
		when(mockBeneficiaryMapper.fetchBeneficiaryUsage(BENEFICIARY_ID)).thenReturn(mockBeneficiaryUsage);
		when(mockBeneficiaryMapper.addBeneficiary(mockBeneficiary)).thenReturn(BENEFICIARY_ID);
		when(mockBeneficiaryMapper.updateBeneficiaryById(mockBeneficiary)).thenReturn(ROWS_AFFECTED);
		when(mockBeneficiaryMapper.deleteBeneficiaryById(BENEFICIARY_ID)).thenReturn(ROWS_AFFECTED);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		mockSqlSessionFactoryProvider.closeSessionAndRollback();
	}


	/**
	 * Test method for {@link companyregistry.persistence.beneficiary.repository.BeneficiaryRepositoryImpl#getBeneficiary(java.lang.Long)}.
	 */
	@Test
	public void testGetBeneficiary() {
		Beneficiary beneficiary = beneficiaryRepository.getBeneficiary(BENEFICIARY_ID);
		Assert.assertNotNull(beneficiary);
		Assert.assertEquals(mockBeneficiary, beneficiary);
		verify(mockSqlSessionFactoryProvider).openSessionAndInitMapper(BeneficiaryMapper.class);
		verify(mockBeneficiaryMapper).findBeneficiaryById(BENEFICIARY_ID);
		verify(mockSqlSessionFactoryProvider).closeSession();
	}

	/**
	 * Test method for {@link companyregistry.persistence.beneficiary.repository.BeneficiaryRepositoryImpl#getBeneficiaries()}.
	 */
	@Test
	public void testGetBeneficiaries() {
		exception.expect(NotFoundResourceException.class);
		List<Beneficiary> beneficiaries = beneficiaryRepository.getBeneficiaries();
		Assert.assertNotNull(beneficiaries);
		Assert.assertEquals(mockBeneficiaries, beneficiaries);
		verify(mockSqlSessionFactoryProvider).openSessionAndInitMapper(BeneficiaryMapper.class);
		verify(mockBeneficiaryMapper).fetchAllBeneficiaries();
		verify(mockSqlSessionFactoryProvider).closeSession();
	}

	/**
	 * Test method for {@link companyregistry.persistence.beneficiary.repository.BeneficiaryRepositoryImpl#addBeneficiary(companyregistry.domain.beneficiary.Beneficiary)}.
	 */
	@Test()
	public void testAddBeneficiary() {
		exception.expect(BadRequestResourceException.class);
		Long beneficiaryId = beneficiaryRepository.addBeneficiary(mockBeneficiary);
		Assert.assertNotNull(beneficiaryId);
		Assert.assertEquals(BENEFICIARY_ID, beneficiaryId.longValue());
		verify(mockSqlSessionFactoryProvider).openSessionAndInitMapper(BeneficiaryMapper.class);
		verify(mockBeneficiaryMapper).addBeneficiary(mockBeneficiary);
		verify(mockSqlSessionFactoryProvider).closeSessionAndCommit();
	}

	/**
	 * Test method for {@link companyregistry.persistence.beneficiary.repository.BeneficiaryRepositoryImpl#updateBeneficiary(companyregistry.domain.beneficiary.Beneficiary)}.
	 */
	@Test(expected=BadRequestResourceException.class)
	public void testUpdateBeneficiary() {
		boolean status = beneficiaryRepository.updateBeneficiary(mockBeneficiary);
		Assert.assertTrue(status);
		Assert.assertEquals(STATUS, status);
		verify(mockSqlSessionFactoryProvider).openSessionAndInitMapper(BeneficiaryMapper.class);
		verify(mockBeneficiaryMapper).updateBeneficiaryById(mockBeneficiary);
		verify(mockSqlSessionFactoryProvider).closeSessionAndCommit();
	}

	/**
	 * Test method for {@link companyregistry.persistence.beneficiary.repository.BeneficiaryRepositoryImpl#deleteBeneficiary(java.lang.Long)}.
	 */
	@Test
	public void testDeleteBeneficiary() {
		boolean status = beneficiaryRepository.deleteBeneficiary(BENEFICIARY_ID);
		Assert.assertTrue(status);
		Assert.assertEquals(STATUS, status);
		verify(mockSqlSessionFactoryProvider).openSessionAndInitMapper(BeneficiaryMapper.class);
		verify(mockBeneficiaryMapper).deleteBeneficiaryById(BENEFICIARY_ID);
		verify(mockSqlSessionFactoryProvider).closeSessionAndCommit();
	}

}

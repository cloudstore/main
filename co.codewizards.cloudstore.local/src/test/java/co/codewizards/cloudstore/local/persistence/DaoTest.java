package co.codewizards.cloudstore.local.persistence;

import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("deprecation")
public class DaoTest {
	private static final Logger logger = LoggerFactory.getLogger(DaoTest.class);

	private static Random random = new Random();

	@BeforeClass
	public static void beforeClass() {
		logger.debug(">>> === >>> === >>> {}.beforeClass() >>> === >>> === >>>", DaoTest.class.getName());
	}

	@AfterClass
	public static void afterClass() {
		logger.debug("<<< === <<< === <<< {}.afterClass() <<< === <<< === <<<", DaoTest.class.getName());
	}

	@Test
	public void buildIdRangePackages_0() {
		System.out.println();
		System.out.println("*** buildIdRangePackages_0 ***");
		final int idRangePackageSize = 3;

		SortedSet<Long> entityIds = getEntityIds(
				16,17,18,19,20,21,
				25,
				27,28,29,
				34,35,36,37,38
				);

		List<List<Dao.IdRange>> idRangePackages = Dao.buildIdRangePackages(entityIds, idRangePackageSize);
		showIdRangePackages(idRangePackages);

		assertThat(idRangePackages).hasSize(2);
		for (List<Dao.IdRange> idRangePackage : idRangePackages) {
			assertIdRangePackageValid(idRangePackage);
			assertThat(idRangePackage).hasSize(idRangePackageSize);
		}

		assertIdRangePackagesEqualEntityIds(idRangePackages, entityIds);
	}

	@Test
	public void buildIdRangePackages_1() {
		System.out.println();
		System.out.println("*** buildIdRangePackages_1 ***");
		final int idRangePackageSize = 3;

		SortedSet<Long> entityIds = getEntityIds(
				1,2,3,4,5,
				7,8,9,
				11,12,13,14,
				16,17,18,19,20,21,
				25,
				27,28,29,
				34,35,36,37,38,
				40
				);

		List<List<Dao.IdRange>> idRangePackages = Dao.buildIdRangePackages(entityIds, idRangePackageSize);
		showIdRangePackages(idRangePackages);

		assertThat(idRangePackages).hasSize(3);
		for (List<Dao.IdRange> idRangePackage : idRangePackages) {
			assertIdRangePackageValid(idRangePackage);
			assertThat(idRangePackage).hasSize(idRangePackageSize);
		}

		assertIdRangePackagesEqualEntityIds(idRangePackages, entityIds);
	}

	@Test
	public void buildIdRangePackages_2() {
		System.out.println();
		System.out.println("*** buildIdRangePackages_2 ***");
		final int idRangePackageSize = 3;

		SortedSet<Long> entityIds = getEntityIds(
				1,
				7,8,9,
				11,12,13,14,
				16,17,18,19,20,21,
				25,
				27,28,29,
				34,35,36,37,38
				);

		List<List<Dao.IdRange>> idRangePackages = Dao.buildIdRangePackages(entityIds, idRangePackageSize);
		showIdRangePackages(idRangePackages);

		assertThat(idRangePackages).hasSize(3);
		for (List<Dao.IdRange> idRangePackage : idRangePackages) {
			assertIdRangePackageValid(idRangePackage);
			assertThat(idRangePackage).hasSize(idRangePackageSize);
		}

		assertIdRangePackagesEqualEntityIds(idRangePackages, entityIds);
	}

	@Test
	public void buildIdRangePackages_3() {
		System.out.println();
		System.out.println("*** buildIdRangePackages_3 ***");
		final int idRangePackageSize = 3;

		SortedSet<Long> entityIds = getEntityIds(
				1,2,3,4,5,
				7,8,9,
				11,12,13,14,
				16,17,18,19,20,21,
				25,
				27,28,29,
				34,35,36,37,38,
				40,41,
				45,46,47,48
				);

		List<List<Dao.IdRange>> idRangePackages = Dao.buildIdRangePackages(entityIds, idRangePackageSize);
		showIdRangePackages(idRangePackages);

		assertThat(idRangePackages).hasSize(3);
		for (List<Dao.IdRange> idRangePackage : idRangePackages) {
			assertIdRangePackageValid(idRangePackage);
			assertThat(idRangePackage).hasSize(idRangePackageSize);
		}

		assertIdRangePackagesEqualEntityIds(idRangePackages, entityIds);
	}

	@Test
	public void buildIdRangePackages_4() {
		System.out.println();
		System.out.println("*** buildIdRangePackages_4 ***");
		final int idRangePackageSize = 3;

		SortedSet<Long> entityIds = new TreeSet<>();

		List<List<Dao.IdRange>> idRangePackages = Dao.buildIdRangePackages(entityIds, idRangePackageSize);
		showIdRangePackages(idRangePackages);

		assertThat(idRangePackages).isEmpty();;
	}

	@Test
	public void buildIdRangePackages_5() {
		System.out.println();
		System.out.println("*** buildIdRangePackages_5 ***");
		final int idRangePackageSize = 3;

		SortedSet<Long> entityIds = getEntityIds(
				666
				);

		List<List<Dao.IdRange>> idRangePackages = Dao.buildIdRangePackages(entityIds, idRangePackageSize);
		showIdRangePackages(idRangePackages);

		assertThat(idRangePackages).hasSize(1);
		for (List<Dao.IdRange> idRangePackage : idRangePackages) {
			assertIdRangePackageValid(idRangePackage);
			assertThat(idRangePackage).hasSize(idRangePackageSize);
		}

		assertIdRangePackagesEqualEntityIds(idRangePackages, entityIds);
	}

	@Test
	public void buildIdRangePackages_6() {
		System.out.println();
		System.out.println("*** buildIdRangePackages_6 ***");
		final int idRangePackageSize = 3;

		SortedSet<Long> entityIds = getEntityIds(
				666,667
				);

		List<List<Dao.IdRange>> idRangePackages = Dao.buildIdRangePackages(entityIds, idRangePackageSize);
		showIdRangePackages(idRangePackages);

		assertThat(idRangePackages).hasSize(1);
		for (List<Dao.IdRange> idRangePackage : idRangePackages) {
			assertIdRangePackageValid(idRangePackage);
			assertThat(idRangePackage).hasSize(idRangePackageSize);
		}

		assertIdRangePackagesEqualEntityIds(idRangePackages, entityIds);
	}

	@Test
	public void shrinkIdRangePackageSizeIfPossible_0() {
		System.out.println();
		System.out.println("*** shrinkIdRangePackageSizeIfPossible_0 ***");

		SortedSet<Long> entityIds = getEntityIds(
				1,2,3,4,5,
				7,8,9,
				11,12,13,14,
				16,17,18,19,20,21,
				25,
				27,28,29,
				34,35,36,37,38,
				40,41,
				45,46,47,48
				);

		List<List<Dao.IdRange>> idRangePackages = Dao.buildIdRangePackages(entityIds);
		showIdRangePackages(idRangePackages);
		assertThat(idRangePackages).hasSize(1);

		int idRangePackageSize = Dao.shrinkIdRangePackageSizeIfPossible(idRangePackages);
		showIdRangePackages(idRangePackages);
		assertThat(idRangePackageSize).isEqualTo(10);
		assertThat(idRangePackages).hasSize(1);
		assertThat(idRangePackages.get(0)).hasSize(idRangePackageSize);

		assertIdRangePackagesEqualEntityIds(idRangePackages, entityIds);
	}

	@Test
	public void shrinkIdRangePackageSizeIfPossible_1() {
		System.out.println();
		System.out.println("*** shrinkIdRangePackageSizeIfPossible_1 ***");

		SortedSet<Long> entityIds = getEntityIds(
				11,12,13,14
				);

		List<List<Dao.IdRange>> idRangePackages = Dao.buildIdRangePackages(entityIds);
		showIdRangePackages(idRangePackages);
		assertThat(idRangePackages).hasSize(1);

		int idRangePackageSize = Dao.shrinkIdRangePackageSizeIfPossible(idRangePackages);
		showIdRangePackages(idRangePackages);
		assertThat(idRangePackageSize).isEqualTo(1);
		assertThat(idRangePackages).hasSize(1);
		assertThat(idRangePackages.get(0)).hasSize(idRangePackageSize);

		assertIdRangePackagesEqualEntityIds(idRangePackages, entityIds);
	}

	@Test
	public void shrinkIdRangePackageSizeIfPossible_2() {
		System.out.println();
		System.out.println("*** shrinkIdRangePackageSizeIfPossible_2 ***");

		SortedSet<Long> entityIds = getEntityIds(
				1,2,3,4,5,
				7,8,9,
				11,12,13,14,
				16,17,18,19,20,21,
				25,
				27,28,29,
				34,35,36,37,38,
				40,41,
				45,46,47,48,
				50,51
				);

		List<List<Dao.IdRange>> idRangePackages = Dao.buildIdRangePackages(entityIds);
		showIdRangePackages(idRangePackages);
		assertThat(idRangePackages).hasSize(1);

		int idRangePackageSize = Dao.shrinkIdRangePackageSizeIfPossible(idRangePackages);
		showIdRangePackages(idRangePackages);
		assertThat(idRangePackageSize).isEqualTo(10);
		assertThat(idRangePackages).hasSize(1);
		assertThat(idRangePackages.get(0)).hasSize(idRangePackageSize);

		assertIdRangePackagesEqualEntityIds(idRangePackages, entityIds);
	}

	@Test
	public void shrinkIdRangePackageSizeIfPossible_3() {
		System.out.println();
		System.out.println("*** shrinkIdRangePackageSizeIfPossible_3 ***");

		SortedSet<Long> entityIds = getEntityIds(
				1,2,3,4,5,
				7,8,9,
				11,12,13,14,
				16,17,18,19,20,21,
				25,
				27,28,29,
				34,35,36,37,38,
				40,41,
				45,46,47,48,
				50,51,
				53
				);

		List<List<Dao.IdRange>> idRangePackages = Dao.buildIdRangePackages(entityIds);
		showIdRangePackages(idRangePackages);
		assertThat(idRangePackages).hasSize(1);

		int idRangePackageSize = Dao.shrinkIdRangePackageSizeIfPossible(idRangePackages);
		showIdRangePackages(idRangePackages);
		assertThat(idRangePackageSize).isEqualTo(100);
		assertThat(idRangePackages).hasSize(1);
		assertThat(idRangePackages.get(0)).hasSize(idRangePackageSize);

		assertIdRangePackagesEqualEntityIds(idRangePackages, entityIds);
	}

	@Test
	public void shrinkIdRangePackageSizeIfPossible_4() {
		System.out.println();
		System.out.println("*** shrinkIdRangePackageSizeIfPossible_4 ***");

		SortedSet<Long> entityIds = getEntityIds(
				11,12,13,14,
				16,17,18,19,20,21
				);

		List<List<Dao.IdRange>> idRangePackages = Dao.buildIdRangePackages(entityIds);
		showIdRangePackages(idRangePackages);
		assertThat(idRangePackages).hasSize(1);

		int idRangePackageSize = Dao.shrinkIdRangePackageSizeIfPossible(idRangePackages);
		showIdRangePackages(idRangePackages);
		assertThat(idRangePackageSize).isEqualTo(10);
		assertThat(idRangePackages).hasSize(1);
		assertThat(idRangePackages.get(0)).hasSize(idRangePackageSize);

		assertIdRangePackagesEqualEntityIds(idRangePackages, entityIds);
	}

	@Test
	public void shrinkIdRangePackageSizeIfPossible_5() {
		System.out.println();
		System.out.println("*** shrinkIdRangePackageSizeIfPossible_5 ***");

		SortedSet<Long> entityIds = new TreeSet<>();

		long id = 0;
		int rangeCount = 0;
		while (rangeCount < 100) {
			++id;
			entityIds.add(id);
			if (random.nextInt(1000) < 300) {
				id += 1 + random.nextInt(10);
				++rangeCount;
			}
		}

		List<List<Dao.IdRange>> idRangePackages = Dao.buildIdRangePackages(entityIds);
		showIdRangePackages(idRangePackages);
		assertThat(idRangePackages).hasSize(1);

		int idRangePackageSize = Dao.shrinkIdRangePackageSizeIfPossible(idRangePackages);
		showIdRangePackages(idRangePackages);
		assertThat(idRangePackageSize).isEqualTo(100);
		assertThat(idRangePackages).hasSize(1);
		assertThat(idRangePackages.get(0)).hasSize(idRangePackageSize);

		assertIdRangePackagesEqualEntityIds(idRangePackages, entityIds);
	}

	@Test
	public void shrinkIdRangePackageSizeIfPossible_6() {
		System.out.println();
		System.out.println("*** shrinkIdRangePackageSizeIfPossible_6 ***");

		SortedSet<Long> entityIds = new TreeSet<>();

		long id = 0;
		int rangeCount = 0;
		while (rangeCount < 101) {
			++id;
			entityIds.add(id);
			if (random.nextInt(1000) < 300) {
				id += 1 + random.nextInt(10);
				++rangeCount;
			}
		}

		List<List<Dao.IdRange>> idRangePackages = Dao.buildIdRangePackages(entityIds);
		showIdRangePackages(idRangePackages);
		assertThat(idRangePackages).hasSize(2);

		int idRangePackageSize = Dao.shrinkIdRangePackageSizeIfPossible(idRangePackages);
		showIdRangePackages(idRangePackages);
		assertThat(idRangePackageSize).isEqualTo(100);
		assertThat(idRangePackages).hasSize(2);
		assertThat(idRangePackages.get(0)).hasSize(idRangePackageSize);
		assertThat(idRangePackages.get(1)).hasSize(idRangePackageSize);

		assertThat(idRangePackages.get(0).get(99).fromIdIncl).isPositive();
		assertThat(idRangePackages.get(0).get(99).toIdIncl).isPositive();

		assertThat(idRangePackages.get(1).get(0).fromIdIncl).isPositive();
		assertThat(idRangePackages.get(1).get(0).toIdIncl).isPositive();

		assertThat(idRangePackages.get(1).get(1).fromIdIncl).isEqualTo(Dao.IdRange.NULL_ID);
		assertThat(idRangePackages.get(1).get(1).toIdIncl).isEqualTo(Dao.IdRange.NULL_ID);

		assertIdRangePackagesEqualEntityIds(idRangePackages, entityIds);
	}

	@Test
	public void shrinkIdRangePackageSizeIfPossible_7() {
		System.out.println();
		System.out.println("*** shrinkIdRangePackageSizeIfPossible_7 ***");

		SortedSet<Long> entityIds = new TreeSet<>();

		long id = 0;
		int rangeCount = 0;
		while (rangeCount < 99) {
			++id;
			entityIds.add(id);
			if (random.nextInt(1000) < 300) {
				id += 1 + random.nextInt(10);
				++rangeCount;
			}
		}

		List<List<Dao.IdRange>> idRangePackages = Dao.buildIdRangePackages(entityIds);
		showIdRangePackages(idRangePackages);
		assertThat(idRangePackages).hasSize(1);

		int idRangePackageSize = Dao.shrinkIdRangePackageSizeIfPossible(idRangePackages);
		showIdRangePackages(idRangePackages);
		assertThat(idRangePackageSize).isEqualTo(100);
		assertThat(idRangePackages).hasSize(1);
		List<Dao.IdRange> idRangePackage = idRangePackages.get(0);
		assertThat(idRangePackage).hasSize(idRangePackageSize);

		assertThat(idRangePackage.get(99).fromIdIncl).isEqualTo(Dao.IdRange.NULL_ID);
		assertThat(idRangePackage.get(99).toIdIncl).isEqualTo(Dao.IdRange.NULL_ID);

		assertThat(idRangePackage.get(98).fromIdIncl).isPositive();
		assertThat(idRangePackage.get(98).toIdIncl).isPositive();

		assertIdRangePackagesEqualEntityIds(idRangePackages, entityIds);
	}

	private static void assertIdRangePackageValid(List<Dao.IdRange> idRangePackage) {
		requireNonNull(idRangePackage, "idRangePackage");
		for (Dao.IdRange idRange : idRangePackage) {
			requireNonNull(idRange, "idRange");
			if (idRange.fromIdIncl < 0 || idRange.toIdIncl < 0) {
				assertThat(idRange.toIdIncl).isEqualTo(idRange.fromIdIncl);
				assertThat(idRange.toIdIncl).isEqualTo(Dao.IdRange.NULL_ID);
			}
			assertThat(idRange.fromIdIncl).isLessThanOrEqualTo(idRange.toIdIncl);
		}
	}

	private static void assertIdRangePackagesEqualEntityIds(List<List<Dao.IdRange>> idRangePackages, SortedSet<Long> entityIds) {
		boolean matching;
		int matchingIdCount = 0;
		for (long id = 0; id < 2000; ++id) {
			assertThat(matching = isInIdRangePackages(idRangePackages, id)).isEqualTo(entityIds.contains(id));
			if (matching)
				++matchingIdCount;
		}
		assertThat(matchingIdCount).isEqualTo(entityIds.size());
	}

	private static boolean isInIdRangePackages(List<List<Dao.IdRange>> idRangePackages, long id) {
		requireNonNull(idRangePackages, "idRangePackages");
		int matchCount = 0;
		for (List<Dao.IdRange> idRangePackage : idRangePackages) {
			for (Dao.IdRange idRange : idRangePackage) {
				if (idRange.fromIdIncl <= id && id <= idRange.toIdIncl)
					++matchCount;
			}
		}
		assertThat(matchCount).isLessThanOrEqualTo(1);
		return matchCount == 1;
	}

	private static SortedSet<Long> getEntityIds(int ... entityIds) {
		requireNonNull(entityIds, "entityIds");

		TreeSet<Long> result = new TreeSet<>();
		for (int id : entityIds) {
			result.add(Long.valueOf(id));
		}
		return result;
	}

	private static void showIdRangePackages(List<List<Dao.IdRange>> idRangePackages) {
		int idx = -1;
		for (List<Dao.IdRange> idRangePackage : idRangePackages) {
			System.out.println((++idx) + ": " + idRangePackage);
		}
	}

}

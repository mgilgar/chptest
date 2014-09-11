package fragment.submissions;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;

public class MiguelGilTest {

	public static final String S_EMPTY = "";
	public static final String S = "London are beautiful";
	public static final String S_EXTRA_LEFT = "Paris and London";
	public static final String S_LEFT = "Lond";
	public static final String S_MIDDLE = "n are beau";
	public static final String S_RIGHT = "tiful";
	public static final String S_EXTRA_RIGHT = "ful and expensive";
	public static final String S_NO_MATCH = ". Madrid is cheaper.";

	public static final String S_PLUS_S_EXTRA_LEFT = "Paris and London are beautiful";
	public static final String S_PLUS_S_LEFT = S;
	public static final String S_PLUS_S_MIDDLE = S;
	public static final String S_PLUS_S_EXTRA_RIGHT = "London are beautiful and expensive";
	public static final String S_PLUS_S_NO_MATCH = "London are beautiful. Madrid is cheaper.";

	public static final String S_INPUT_TEST_CASE_2 = "m quaerat voluptatem.;pora incidunt ut labore et d;, consectetur, adipisci"
			+ "velit;olore magnam aliqua;idunt ut labore et dolore magn;uptatem.;i dolorem"
			+ "ipsum qu;iquam quaerat vol;psum quia dolor sit amet, consectetur, a;ia "
			+ "dolor sit amet, conse;squam est, qui do;Neque porro quisquam est, qu;aerat "
			+ "voluptatem.;m eius modi tem;Neque porro qui;, sed quia non numquam ei;lorem "
			+ "ipsum quia dolor sit amet;ctetur, adipisci velit, sed quia non numq;unt ut"
			+ "labore et dolore magnam aliquam qu;dipisci velit, sed quia non numqua;us "
			+ "modi tempora incid;Neque porro quisquam est, qui dolorem i;uam eius modi"
			+ "tem;pora inc;am al";
	
	public static final String S_INPUT_TEST_CASE_3 = "m quaerat voluptatem.;pora incidunt ut labore et d;, consectetur, adipisci velit;olore magnam aliqua;idunt ut labore et dolore magn;uptatem.;i dolorem ipsum qu;iquam quaerat vol;psum quia dolor sit amet, consectetur, a;ia dolor sit amet, conse;squam est, qui do;Neque porro quisquam est, qu;aerat voluptatem.;m eius modi tem;Neque porro qui;, sed quia non numquam ei;lorem ipsum quia dolor sit amet;ctetur, adipisci velit, sed quia non numq;unt ut labore et dolore magnam aliquam qu;dipisci velit, sed quia non numqua;us modi tempora incid;Neque porro quisquam est, qui dolorem i;uam eius modi tem;pora inc;am al";
			
	
	public static final String S_OUTPUT_TEST_CASE_2 = "Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, "
			+ "consectetur, adipisci velit, sed quia non numquam eius modi tempora "
			+ "incidunt ut labore et dolore magnam aliquam quaerat voluptatem.";

	public static MiguelGil miguelGil;

	@BeforeClass
	public static void setup() {
		miguelGil = new MiguelGil();
	}

	@Test
	public void countOverlapingCharsWithEmptyShouldReturn0() {
		Assert.assertThat(miguelGil.countOverlapingChars(S, S_EMPTY),
				equalTo(0));
	}

	@Test
	public void countOverlapingCharsWithMiddleFragmentShouldWork() {
		Assert.assertThat(miguelGil.countOverlapingChars(S, S_MIDDLE),
				equalTo(S_MIDDLE.length()));
	}

	@Test
	public void countOverlapingCharsWithRightFragmentShouldWork() {
		Assert.assertThat(miguelGil.countOverlapingChars(S, S_RIGHT),
				equalTo(S_RIGHT.length()));
	}

	@Test
	public void countOverlapingCharsWithExtraRightFragmentShouldWork() {
		Assert.assertThat(miguelGil.countOverlapingChars(S, S_EXTRA_RIGHT),
				equalTo(3));
	}

	@Test
	public void countOverlapingCharsWithNoMatchFragmentShouldWork() {
		Assert.assertThat(miguelGil.countOverlapingChars(S, S_NO_MATCH),
				equalTo(0));
	}
	
	@Test
	public void overlapConcatWithEmptyFragmentShouldWork() {
		Assert.assertThat(miguelGil.overlapConcat(S, S_EMPTY), equalTo(S));
	}

	@Test
	public void overlapConcatWithMiddleFragmentShouldWork() {
		Assert.assertThat(miguelGil.overlapConcat(S, S_MIDDLE),
				equalTo(S_PLUS_S_MIDDLE));
	}

	@Test
	public void overlapConcatWithRightFragmentShouldWork() {
		Assert.assertThat(miguelGil.overlapConcat(S, S_RIGHT), equalTo(S));
	}

	@Test
	public void overlapConcatWithExtraRightFragmentShouldWork() {
		Assert.assertThat(miguelGil.overlapConcat(S, S_EXTRA_RIGHT),
				equalTo(S_PLUS_S_EXTRA_RIGHT));
	}

	@Test
	public void overlapConcatWithNoMatchFragmentShouldWork() {
		Assert.assertThat(miguelGil.overlapConcat(S, S_NO_MATCH),
				equalTo(S_PLUS_S_NO_MATCH));
	}

	@Test
	public void reassembleWithOneFragmentShouldWork() {
		Assert.assertThat(miguelGil.reassemble("london is beautiful;"),
				equalTo("london is beautiful"));
	}

	@Test
	public void reassembleWithTwoNoOverlappingFragmentsShouldWork() {
		Assert.assertThat(miguelGil.reassemble("paris ;was beautiful"),
				equalTo("paris was beautiful"));
	}

	@Test
	public void reassembleWithTwoOverlappingWithExtendedStringInTheRightShouldWork() {
		Assert.assertThat(miguelGil.reassemble("london is;is beautiful"),
				equalTo("london is beautiful"));
	}

	@Test
	public void reassembleWithTwoOverlappingInTheRightFragmentsShouldWork() {
		Assert.assertThat(miguelGil.reassemble("london is;is"),
				equalTo("london is"));
	}

	@Test
	public void reassembleWithTwoOverlappingInTheLeftFragmentsShouldWork() {
		Assert.assertThat(miguelGil.reassemble("london is;lon"),
				equalTo("london is"));
	}

	@Test
	public void reassembleWithTwoOverlappingWithExtendedStringOnTheLeftShouldWork() {
		Assert.assertThat(miguelGil.reassemble("london is;paris and lon"),
				equalTo("paris and london is"));
	}

	@Test
	public void reassembleWithTwoOverlappingInTheMiddleShouldWork() {
		Assert.assertThat(miguelGil.reassemble("london and paris; and p"),
				equalTo("london and paris"));
	}

	@Test
	public void reassembleWithFourOverlappingInOrderShouldWork() {
		Assert.assertThat(
				miguelGil
						.reassemble("london and paris; and paris;paris are;are beautiful"),
				equalTo("london and paris are beautiful"));
	}

	@Test
	public void reassembleWithFourOverlappingNoOrderShouldWork() {
		Assert.assertThat(
				miguelGil
						.reassemble("and paris;london and paris; are beautiful; paris are"),
				equalTo("london and paris are beautiful"));
	}

	@Test
	public void reassembleTestCase1ShouldWork() {
		Assert.assertThat(miguelGil
				.reassemble("O draconia;conian devil! Oh la;h lame sa;saint!"),
				equalTo("O draconian devil! Oh lame saint!"));
	}

	@Test
	public void reassembleTestCase2ShouldWork() {
		Assert.assertThat(miguelGil
				.reassemble(S_INPUT_TEST_CASE_3),
				equalTo(S_OUTPUT_TEST_CASE_2));
	}
}

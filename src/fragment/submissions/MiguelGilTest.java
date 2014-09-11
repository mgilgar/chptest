package fragment.submissions;

import static org.hamcrest.CoreMatchers.equalTo;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import fragment.submissions.MiguelGil.ConcatenationResult;

public class MiguelGilTest {

	public static final String S_INPUT = "London are beautiful";
	public static final String S_INPUT_EMPTY = "";
	public static final String S_INPUT_BLANK_SPACES = "  ";
	public static final String S_INPUT_EXTRA_LEFT = "Paris and London";
	public static final String S_INPUT_LEFT = "Lond";
	public static final String S_INPUT_MIDDLE = "n are beau";
	public static final String S_INPUT_MULTIPLE_MATCHES = "on";
	public static final String S_INPUT_RIGHT = "tiful";
	public static final String S_INPUT_EXTRA_RIGHT = "ful and expensive";
	public static final String S_INPUT_NO_MATCH = ". Madrid is cheaper.";
	public static final String S_INPUT_FALSE_MATCH = "beatiful and expensive.";

	public static final String S_OUTPUT_PLUS_S_BLANK_SPACES = "London are beautiful  ";
	public static final String S_OUTPUT_PLUS_S_EXTRA_LEFT = "Paris and London are beautiful";
	public static final String S_OUTPUT_PLUS_S_LEFT = "London are beautiful";
	public static final String S_OUTPUT_PLUS_S_MIDDLE = "London are beautiful";
	public static final String S_OUTPUT_PLUS_S_EXTRA_RIGHT = "London are beautiful and expensive";
	public static final String S_OUTPUT_PLUS_S_NO_MATCH = "London are beautiful. Madrid is cheaper.";
	public static final String S_INPUT_PLUS_S_FALSE_MATCH = S_INPUT + S_INPUT_FALSE_MATCH;

	public static final String S_INPUT_TEST_CASE_1 = "m quaerat voluptatem.;pora incidunt ut labore et d;, consectetur, adipisci velit;olore magnam aliqua;idunt ut labore et dolore magn;uptatem.;i dolorem ipsum qu;iquam quaerat vol;psum quia dolor sit amet, consectetur, a;ia dolor sit amet, conse;squam est, qui do;Neque porro quisquam est, qu;aerat voluptatem.;m eius modi tem;Neque porro qui;, sed quia non numquam ei;lorem ipsum quia dolor sit amet;ctetur, adipisci velit, sed quia non numq;unt ut labore et dolore magnam aliquam qu;dipisci velit, sed quia non numqua;us modi tempora incid;Neque porro quisquam est, qui dolorem i;uam eius modi tem;pora inc;am al";
	public static final String S_INPUT_TEST_CASE_2 = "m quaerat voluptatem.;pora incidunt ut labore et d;, consectetur, adipisci"
			+ "velit;olore magnam aliqua;idunt ut labore et dolore magn;uptatem.;i dolorem"
			+ "ipsum qu;iquam quaerat vol;psum quia dolor sit amet, consectetur, a;ia "
			+ "dolor sit amet, conse;squam est, qui do;Neque porro quisquam est, qu;aerat "
			+ "voluptatem.;m eius modi tem;Neque porro qui;, sed quia non numquam ei;lorem "
			+ "ipsum quia dolor sit amet;ctetur, adipisci velit, sed quia non numq;unt ut"
			+ "labore et dolore magnam aliquam qu;dipisci velit, sed quia non numqua;us "
			+ "modi tempora incid;Neque porro quisquam est, qui dolorem i;uam eius modi" + "tem;pora inc;am al";
	public static final String S_INPUT_TEST_CASE_3 = "Madrid;drid, Barce;celona,;lona, Val; Valen;lencia, Sevi;Sevilla, Zaragoza, ;Zaragoza;Madrid;Barce;goza, Malaga, Mur;Murcia, Valladolid;dolid, Palma,;Palma;Palma; Palma, Las Pal;Palmas;Palmas, Bilbao;bao";
	public static final String S_INPUT_TEST_CASE_4 = "Madrid Bar;Barcelona;Barcarrota;ona Bar;Barcarrot";

	public static final String S_OUTPUT_TEST_CASE_2 = "Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, "
			+ "consectetur, adipisci velit, sed quia non numquam eius modi tempora "
			+ "incidunt ut labore et dolore magnam aliquam quaerat voluptatem.";
	public static final String S_OUTPUT_TEST_CASE_3 = "Madrid, Barcelona, Valencia, Sevilla, Zaragoza, Malaga, Murcia, Valladolid, Palma, Las Palmas, Bilbao";
	public static final String S_OUTPUT_TEST_CASE_4 = "Madrid Barcelona Barcarrota";

	public static MiguelGil miguelGil;

	@BeforeClass
	public static void setup() {
		miguelGil = new MiguelGil();
		miguelGil.setReassembler(miguelGil.new Reassembler());
	}

	@Test
	public void concatWithOverlappingCharactersWithEmptyShouldWork() {
		assertConcatenationResult(miguelGil.getReassembler().concatWithOverlappingCharacters(S_INPUT, S_INPUT_EMPTY),
				S_INPUT, 0);
	}

	@Test
	public void concatWithOverlappingCharactersWithBlankStringShouldWork() {
		assertConcatenationResult(
				miguelGil.getReassembler().concatWithOverlappingCharacters(S_INPUT, S_INPUT_BLANK_SPACES),
				S_OUTPUT_PLUS_S_BLANK_SPACES, 0);
	}

	@Test
	public void concatWithOverlappingCharactersWithMiddleFragmentShouldWork() {
		assertConcatenationResult(miguelGil.getReassembler().concatWithOverlappingCharacters(S_INPUT, S_INPUT_MIDDLE),
				S_INPUT, S_INPUT_MIDDLE.length());
	}

	@Test
	public void concatWithOverlappingCharactersWithRightFragmentShouldWork() {
		assertConcatenationResult(miguelGil.getReassembler().concatWithOverlappingCharacters(S_INPUT, S_INPUT_RIGHT),
				S_INPUT, S_INPUT_RIGHT.length());
	}

	@Test
	public void concatWithOverlappingCharactersWithExtraRightFragmentShouldWork() {
		assertConcatenationResult(
				miguelGil.getReassembler().concatWithOverlappingCharacters(S_INPUT, S_INPUT_EXTRA_RIGHT),
				S_OUTPUT_PLUS_S_EXTRA_RIGHT, 3);
	}

	@Test
	public void concatWithOverlappingCharactersWithNoMatchFragmentShouldWork() {
		assertConcatenationResult(
				miguelGil.getReassembler().concatWithOverlappingCharacters(S_INPUT, S_INPUT_NO_MATCH),
				S_OUTPUT_PLUS_S_NO_MATCH, 0);
	}

	@Test
	public void concatWithOverlappingCharactersWithEmptyFragmentShouldWork() {
		assertConcatenationResult(miguelGil.getReassembler().concatWithOverlappingCharacters(S_INPUT, S_INPUT_EMPTY),
				S_INPUT, 0);
	}

	@Test
	public void concatWithOverlappingCharactersWithFalseMatchShouldWork() {
		assertConcatenationResult(
				miguelGil.getReassembler().concatWithOverlappingCharacters(S_INPUT, S_INPUT_FALSE_MATCH),
				S_INPUT_PLUS_S_FALSE_MATCH, 0);
	}

	@Test
	public void concatWithOverlappingCharactersWithMultipleMatchesShouldWork() {
		assertConcatenationResult(
				miguelGil.getReassembler().concatWithOverlappingCharacters(S_INPUT, S_INPUT_MULTIPLE_MATCHES), S_INPUT,
				S_INPUT_MULTIPLE_MATCHES.length());
	}

	@Test
	public void getAllVariationsOf2ShouldReturnAListOf2() {
		Assert.assertThat(miguelGil.getReassembler().getAllVariationsOf2(2).size(), equalTo(2));
	}

	@Test
	public void getAllVariationsOf5ShouldReturnAListOf20() {
		Assert.assertThat(miguelGil.getReassembler().getAllVariationsOf2(5).size(), equalTo(5 * 4));
	}

	@Test
	public void getAllVariationsOf10ShouldReturnAListOf90() {
		Assert.assertThat(miguelGil.getReassembler().getAllVariationsOf2(10).size(), equalTo(90));
	}

	@Test
	public void reassembleWithOneFragmentShouldWork() {
		Assert.assertThat(miguelGil.getReassembler().reassemble("london is beautiful;"), equalTo("london is beautiful"));
	}

	@Test
	public void reassembleWithTwoNoOverlappingFragmentsShouldWork() {
		Assert.assertThat(miguelGil.getReassembler().reassemble("paris ;was beautiful"), equalTo("paris was beautiful"));
	}

	@Test
	public void reassembleWithTwoOverlappingWithExtendedStringInTheRightShouldWork() {
		Assert.assertThat(miguelGil.getReassembler().reassemble("london is;is beautiful"),
				equalTo("london is beautiful"));
	}

	@Test
	public void reassembleWithTwoOverlappingInTheRightFragmentsShouldWork() {
		Assert.assertThat(miguelGil.getReassembler().reassemble("london is;is"), equalTo("london is"));
	}

	@Test
	public void reassembleWithTwoOverlappingInTheLeftFragmentsShouldWork() {
		Assert.assertThat(miguelGil.getReassembler().reassemble("london is;lon"), equalTo("london is"));
	}

	@Test
	public void reassembleWithTwoOverlappingWithExtendedStringOnTheLeftShouldWork() {
		Assert.assertThat(miguelGil.getReassembler().reassemble("london is;paris and lon"),
				equalTo("paris and london is"));
	}

	@Test
	public void reassembleWithTwoOverlappingInTheMiddleShouldWork() {
		Assert.assertThat(miguelGil.getReassembler().reassemble("london and paris; and p"), equalTo("london and paris"));
	}

	@Test
	public void reassembleWithFourOverlappingInOrderShouldWork() {
		Assert.assertThat(miguelGil.getReassembler().reassemble("london and paris; and paris;paris are;are beautiful"),
				equalTo("london and paris are beautiful"));
	}

	@Test
	public void reassembleWithFourOverlappingNoOrderShouldWork() {
		Assert.assertThat(
				miguelGil.getReassembler().reassemble("and paris;london and paris; are beautiful; paris are"),
				equalTo("london and paris are beautiful"));
	}

	@Test
	public void reassembleTestCase1ShouldWork() {
		Assert.assertThat(miguelGil.getReassembler().reassemble("O draconia;conian devil! Oh la;h lame sa;saint!"),
				equalTo("O draconian devil! Oh lame saint!"));
	}

	@Test
	public void reassembleTestCase2ShouldWork() {
		Assert.assertThat(miguelGil.getReassembler().reassemble(S_INPUT_TEST_CASE_1), equalTo(S_OUTPUT_TEST_CASE_2));
	}

	@Test
	public void reassembleTestCase3ShouldWork() {
		Assert.assertThat(miguelGil.getReassembler().reassemble(S_INPUT_TEST_CASE_3), equalTo(S_OUTPUT_TEST_CASE_3));
	}

	@Test
	public void reassembleTestCase4ShouldWork() {
		Assert.assertThat(miguelGil.getReassembler().reassemble(S_INPUT_TEST_CASE_4), equalTo(S_OUTPUT_TEST_CASE_4));
	}

	@Test
	public void safeRemoveNormalOrderShouldWork() {
		List<String> myList = new ArrayList<String>();
		myList.add("a");
		myList.add("b");
		myList.add("c");
		miguelGil.getReassembler().safeRemove(myList, 0, 2);
		Assert.assertThat(myList.size(), equalTo(1));
		Assert.assertThat(myList.get(0), equalTo("b"));
	}

	@Test
	public void safeRemoveReverseOrderShouldWork() {
		List<String> myList = new ArrayList<String>();
		myList.add("a");
		myList.add("b");
		myList.add("c");
		miguelGil.getReassembler().safeRemove(myList, 1, 0);
		Assert.assertThat(myList.size(), equalTo(1));
		Assert.assertThat(myList.get(0), equalTo("c"));
	}

	/**
	 * 
	 * @param result
	 * @param s
	 * @param i
	 */
	private void assertConcatenationResult(ConcatenationResult result, String s, int i) {
		Assert.assertThat(result.getConcatenatedString(), equalTo(s));
		Assert.assertThat(result.getNumberOfOverlappingChars(), equalTo(i));
	}

}

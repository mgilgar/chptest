package fragment.submissions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Test for CHP recruitment process
 * 
 * @author Miguel Gil
 *
 */
public class MiguelGil {

	private static final String EMPTY_STRING = "";
	private static final String FRAGMENT_SEPARATOR = ";";

	private Reassembler reassembler;

	/**
	 * Represents a tuple of a String and an int. It holds the result
	 *
	 */
	public class ConcatenationResult {

		private String cancatenatedString;
		private int numberOfOverlappingCharacters;

		public ConcatenationResult(String cancatenatedString, int numberOfOverlappingCharacters) {
			super();
			this.cancatenatedString = cancatenatedString;
			this.numberOfOverlappingCharacters = numberOfOverlappingCharacters;
		}

		public String getConcatenatedString() {
			return cancatenatedString;
		}

		public void setCancatenatedString(String cancatenatedString) {
			this.cancatenatedString = cancatenatedString;
		}

		public int getNumberOfOverlappingChars() {
			return numberOfOverlappingCharacters;
		}

		public void setNumberOfOverlappingChars(int numberOfOverlappingChars) {
			this.numberOfOverlappingCharacters = numberOfOverlappingChars;
		}
	}

	/**
	 * Mathematical variation (combinatorial analysis) of 2 ints.
	 * 
	 **/
	public class VariationOf2 {

		private int i1;
		private int i2;

		public VariationOf2(final int i1, final int i2) {
			super();
			this.i1 = i1;
			this.i2 = i2;
		}

		public int getI1() {
			return i1;
		}

		public void setI1(int i1) {
			this.i1 = i1;
		}

		public int getI2() {
			return i2;
		}

		public void setI2(int i2) {
			this.i2 = i2;
		}
	}

	/**
	 * Reassembles fragments of a text that correspond to several copies of the
	 * same document.
	 */
	public class Reassembler {
		/**
		 * Returns a <code>ConcatenationResult</code> that is the result of
		 * concatenation of two strings that may have some overlapping
		 * characters.
		 * 
		 * @param s1
		 *            first string to concatenate
		 * @param s2
		 *            second string to concatenate
		 * @return <code>ConcatenationResult</code> containing a concatenation
		 *         of s1 and s2 that takes into account overlapping characters.
		 */
		protected ConcatenationResult concatWithOverlappingCharacters(final String s1, final String s2) {
			// Handle nulls... never return a null
			if (s1 == null || s1.isEmpty()) {
				if (s2 == null || s2.isEmpty()) {
					return new ConcatenationResult(EMPTY_STRING, 0);
				}
				return new ConcatenationResult(s2, 0);
			}
			if (s2 == null || s2.isEmpty()) {
				return new ConcatenationResult(s1, 0);
			}
			if (s1.contains(s2)) {
				return new ConcatenationResult(s1, s2.length());
			}

			// Checks above guarantee both strings have at least one character
			int len1 = s1.length() - 1;
			char last1 = s1.charAt(len1);
			char first2 = s2.charAt(0);

			// Find the first potential match, bounded by the length of s1
			int indexOfLast2 = s2.lastIndexOf(last1, Math.min(len1, s2.length() - 1));
			while (indexOfLast2 != -1) {
				if (s1.charAt(len1 - indexOfLast2) == first2) {
					// After the quick check, do a full check
					int ix = indexOfLast2;
					while ((ix != -1) && (s1.charAt(len1 - indexOfLast2 + ix) == s2.charAt(ix)))
						ix--;
					if (ix == -1) {
						return new ConcatenationResult(s1 + s2.substring(indexOfLast2 + 1), indexOfLast2 + 1);
					}
				}

				// Search for the next possible match
				indexOfLast2 = s2.lastIndexOf(last1, indexOfLast2 - 1);
			}

			// No match found, so concatenate the full strings
			return new ConcatenationResult(s1 + s2, 0);
		}

		/**
		 * Returns all variations of 2 elements from a set of n numbers from 0
		 * to n-1.
		 * 
		 * @param n
		 *            number of numbers from 0 to n-1 that we want the
		 *            variations of 2 for.
		 * @return <code>List<VariationOf2></code> containing the variations of
		 *         2 numbers from 0 to n-1.
		 * 
		 **/
		protected List<VariationOf2> getAllVariationsOf2(int n) {
			List<VariationOf2> variationsOf2 = new ArrayList<VariationOf2>();
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (i == j)
						continue;
					variationsOf2.add(new VariationOf2(i, j));
				}
			}
			return variationsOf2;
		}

		/**
		 * Reassembles a fragment problem based on the overlapping characters
		 * among the different fragments.
		 * 
		 * @param fragmentProblem
		 *            a semicolon separated list of fragments.
		 * @return a String containing the reassembled text.
		 */
		protected String reassemble(final String fragmentProblem) {
			List<String> fragments = new ArrayList<String>(Arrays.asList(fragmentProblem.split(FRAGMENT_SEPARATOR)));
			while (fragments.size() > 1) {
				List<VariationOf2> variations = getAllVariationsOf2(fragments.size());
				ConcatenationResult max = new ConcatenationResult(fragments.get(0) + fragments.get(1), 0);
				int maxPosition = 0;
				int currentPosition = 0;
				for (VariationOf2 variation : variations) {
					ConcatenationResult concatenationResult = this.concatWithOverlappingCharacters(
							fragments.get(variation.getI1()), fragments.get(variation.getI2()));
					if (concatenationResult.getNumberOfOverlappingChars() > max.getNumberOfOverlappingChars()) {
						max = concatenationResult;
						maxPosition = currentPosition;
					}
					currentPosition++;
				}
				safeRemove(fragments, variations.get(maxPosition).getI1(), variations.get(maxPosition).getI2());
				fragments.add(max.getConcatenatedString());
			}
			return fragments.get(0);
		}

		/**
		 * Remove safely two elements from a List identified by their indeces.
		 * 
		 * @param fragments
		 *            the List of Strings whose elements we are going to remove.
		 * @param index1
		 *            first index of the element to remove from fragments.
		 * @param index2
		 *            second index of the element to remove from fragments.
		 * 
		 */
		protected void safeRemove(List<String> fragments, int index1, int index2) {
			List<Integer> indicesToRemove = Arrays.asList(index1, index2);
			Collections.sort(indicesToRemove, Collections.reverseOrder());
			for (Integer index : indicesToRemove)
				fragments.remove(index.intValue());
		}
	}

	public Reassembler getReassembler() {
		return reassembler;
	}

	public void setReassembler(Reassembler reassembler) {
		this.reassembler = reassembler;
	}

	public static void main(String[] args) {
		MiguelGil app = new MiguelGil();
		app.reassembler = app.new Reassembler();
		try (BufferedReader in = new BufferedReader(new FileReader(args[0]))) {
			String fragmentProblem;
			while ((fragmentProblem = in.readLine()) != null) {
				System.out.println(app.reassembler.reassemble(fragmentProblem));
				System.out.println(EMPTY_STRING);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

package fragment.submissions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MiguelGil {

	private static final String EMPTY_STRING = "";
	private static final String FRAGMENT_SEPARATOR = ";";

	public class ConcatenationResult {
		private String cancanatedString;
		private int numberOfOverlappingChars;

		public ConcatenationResult(String cancanatedString, int numberOfOverlappingChars) {
			super();
			this.cancanatedString = cancanatedString;
			this.numberOfOverlappingChars = numberOfOverlappingChars;
		}

		public String getConcanatedString() {
			return cancanatedString;
		}

		public void setCancanatedString(String cancanatedString) {
			this.cancanatedString = cancanatedString;
		}

		public int getNumberOfOverlappingChars() {
			return numberOfOverlappingChars;
		}

		public void setNumberOfOverlappingChars(int numberOfOverlappingChars) {
			this.numberOfOverlappingChars = numberOfOverlappingChars;
		}
	}

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

	public String reassemble(final String fragmentProblem) {
		List<String> fragments = new ArrayList<String>(Arrays.asList(fragmentProblem.split(FRAGMENT_SEPARATOR)));

		while (fragments.size() > 1) {
			List<VariationOf2> variationsOf2 = getAllVariationsOf2(fragments.size());
			int max = 0;
			int maxPosition = 0;
			int currentPosition = 0;
			for (VariationOf2 variation : variationsOf2) {
				ConcatenationResult concatenationResult = this.overlapConcat2(fragments.get(variation.getI1()),
						fragments.get(variation.getI2()));
				if (concatenationResult.getNumberOfOverlappingChars() > max) {
					max = concatenationResult.getNumberOfOverlappingChars();
					maxPosition = currentPosition;
				}
				currentPosition++;
			}
			String cancatenatedString = this.overlapConcat2(fragments.get(variationsOf2.get(maxPosition).getI1()),
					fragments.get(variationsOf2.get(maxPosition).getI2())).getConcanatedString();

			List<Integer> indicesToRemove = Arrays.asList(variationsOf2.get(maxPosition).getI1(),
					variationsOf2.get(maxPosition).getI2());
			Collections.sort(indicesToRemove, Collections.reverseOrder());
			for (Integer index : indicesToRemove)
				fragments.remove(index.intValue());

			fragments.add(cancatenatedString);
		}
		return fragments.get(0);
	}

	/**
	 * Returns all variations of 2 elements from a set of n numbers from 0 to
	 * n-1.
	 * 
	 * @param n
	 *            number of numbers from 0 to n-1 that we want the variations of
	 *            2 for.
	 * @return <code>List<VariationOf2></code> containing the variations of 2
	 *         numbers from 0 to n-1.
	 * 
	 **/
	public List<VariationOf2> getAllVariationsOf2(int n) {
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

	public ConcatenationResult overlapConcat2(String s1, String s2) {
		// Handle nulls... never return a null
		if (s1 == null || s1.isEmpty()) {
			if (s2 == null || s2.isEmpty()) {
				return new ConcatenationResult(EMPTY_STRING, 0);
			} else {
				return new ConcatenationResult(s2, 0);
			}
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

	public static void main(String[] args) {
		MiguelGil instance = new MiguelGil();
		try (BufferedReader in = new BufferedReader(new FileReader(args[0]))) {
			String fragmentProblem;
			while ((fragmentProblem = in.readLine()) != null) {
				System.out.println(instance.reassemble(fragmentProblem));
				System.out.println(EMPTY_STRING);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

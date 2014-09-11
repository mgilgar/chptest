package fragment.submissions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class MiguelGil {
		
	public class VariationOf2 {
		private int i1;
		
		private int i2;
		
		public VariationOf2(int i1, int i2) {
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
		String[] initialTokens = fragmentProblem.split(";");
		List<String> tokens = new ArrayList<String>();
		for (String initialToken: initialTokens) {
			tokens.add(initialToken);
		}
		
		while (tokens.size() > 1) {		
			List<VariationOf2> variationsOf2 = getAllVariationsOf2(tokens.size());
			List<Integer> lengthOfCommonCharacters = new ArrayList<Integer>();
			int max = 0;
			int maxPosition = 0;
			int currentPosition = 0;
			for (VariationOf2 variation: variationsOf2) {
				Integer lengthOfVariationOf2 = this.countOverlapingChars(tokens.get(variation.getI1()), tokens.get(variation.getI2()));
				lengthOfCommonCharacters.add(lengthOfVariationOf2);
				if (lengthOfVariationOf2>max) {
					max = lengthOfVariationOf2;
					maxPosition = currentPosition;
				}
				currentPosition++;
			}
			String cancatenatedString = this.overlapConcat(tokens.get(variationsOf2.get(maxPosition).getI1()), tokens.get(variationsOf2.get(maxPosition).getI2()));
			
			if (variationsOf2.get(maxPosition).getI1() > variationsOf2.get(maxPosition).getI2()) {
				tokens.remove(variationsOf2.get(maxPosition).getI1());
				tokens.remove(variationsOf2.get(maxPosition).getI2());	
			} else {
				tokens.remove(variationsOf2.get(maxPosition).getI2());
				tokens.remove(variationsOf2.get(maxPosition).getI1());	
			}
			
			tokens.add(cancatenatedString);
		}
		return tokens.get(0);
	}

	public List<VariationOf2> getAllVariationsOf2(int n) {
		List<VariationOf2> variationsOf2 = new ArrayList<VariationOf2>();
		for (int i=0; i < n; i++) {
			for (int j=0; j < n; j++) {
				if (i==j) continue;
				variationsOf2.add(new VariationOf2(i, j));
			}
		}
		return variationsOf2;
	}
		
	public  String overlapConcat(String s1, String s2) {
		// Handle nulls... never return a null
		if (s1 == null || s1.isEmpty()) {
			if (s2 == null || s2.isEmpty())
				return "";
			else
				return s2;
		}
		if (s2 == null || s2.isEmpty())
			return s1;
		if (s1.contains(s2)) {
			return s1;
		}
		
		// Checks above guarantee both strings have at least one character
		int len1 = s1.length() - 1;
		char last1 = s1.charAt(len1);
		char first2 = s2.charAt(0);

		// Find the first potential match, bounded by the length of s1
		int indexOfLast2 = s2.lastIndexOf(last1,
				Math.min(len1, s2.length() - 1));
		while (indexOfLast2 != -1) {
			if (s1.charAt(len1 - indexOfLast2) == first2) {
				// After the quick check, do a full check
				int ix = indexOfLast2;
				while ((ix != -1)
						&& (s1.charAt(len1 - indexOfLast2 + ix) == s2
								.charAt(ix)))
					ix--;
				if (ix == -1)
					return s1 + s2.substring(indexOfLast2 + 1);
			}

			// Search for the next possible match
			indexOfLast2 = s2.lastIndexOf(last1, indexOfLast2 - 1);
		}

		// No match found, so concatenate the full strings
		return s1 + s2;
	}

	public int countOverlapingChars(String s1, String s2) {
		// Handle nulls... never return a null
		if (s1 == null || s1.isEmpty()) {
			if (s2 == null || s2.isEmpty()) {
				// return "";
				return 0;
			} else {
				return 0;
			}
		}
		if (s2 == null || s2.isEmpty())
			return 0;
		if (s1.contains(s2)) return s2.length();

		// Checks above guarantee both strings have at least one character
		int len1 = s1.length() - 1;
		char last1 = s1.charAt(len1);
		char first2 = s2.charAt(0);

		// Find the first potential match, bounded by the length of s1		
		int indexOfLast2 = s2.lastIndexOf(last1,
				Math.min(len1, s2.length() - 1));
		while (indexOfLast2 != -1) {
			if (s1.charAt(len1 - indexOfLast2) == first2) {
				// After the quick check, do a full check
				int ix = indexOfLast2;
				while ((ix != -1)
						&& (s1.charAt(len1 - indexOfLast2 + ix) == s2
								.charAt(ix)))
					ix--;
				if (ix == -1) {
					return indexOfLast2 + 1;
					// return s1 + s2.substring(indexOfLast2 + 1);
				}
			}

			// Search for the next possible match
			indexOfLast2 = s2.lastIndexOf(last1, indexOfLast2 - 1);
		}

		// No match found, so concatenate the full strings
		// return s1 + s2;
		return 0;
	}

	public static void main(String[] args) {
		MiguelGil instance = new MiguelGil();
		try (BufferedReader in = new BufferedReader(new FileReader(args[0]))) {
			String fragmentProblem;
			while ((fragmentProblem = in.readLine()) != null) {
			    System.out.println(instance.reassemble(fragmentProblem));
				System.out.println("");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

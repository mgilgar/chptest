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
	
	public int factorial(int n) {
        int fact = 1; // this  will be the result
        for (int i = 1; i <= n; i++) {
            fact *= i;
        }
        return fact;
    }
	
	public String reassemble(final String fragmentProblem) {
		String[] initialTokens = fragmentProblem.split(";");
		List<String> tokens = new ArrayList<String>();
		for (String initialToken: initialTokens) {
			tokens.add(initialToken);
		}
		
		while (tokens.size() > 1) {		
			//int numberOfVariationsOf2 = numberOfVariationsOf2(tokens.size());
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
	
	public int numberOfVariationsOf2(int n) {
		//return factorial(n)/(factorial(n-2));
		return n*(n-1);
	}
	
//	/*
//	 * public static String reassemble(final String fragmentProblem) { String[]
//	 * tokens = fragmentProblem.split(";");
//	 * 
//	 * // get all combination of 2 fragments 1,2,3,4 1-2, 1-3, 1-4, 2-3, 2-4,
//	 * 3-4 6 combinaciones // dados dos fragments como calcular cuando se solapa
//	 * abcdef efgh fijar una fragmento y mover uno a uno el otro y contar las
//	 * coincidencias
//	 * 
//	 * casos: -fragmento esta dentro -fragmento esta a la izquierda -fragmento
//	 * esta a la derecha
//	 * 
//	 * 
//	 * 
//	 * StringBuilder assemble = new StringBuilder(); for (String leftToken:
//	 * tokens) { for (String rightToken: tokens) { if
//	 * (leftToken.equalsIgnoreCase(rightToken)) { continue; } if leftToken. }
//	 * 
//	 * } return assemble.toString(); }
//	 */

	public void extractFragments() {

	}

	/*
	public int overlappedStringLength(String s1, String s2) {
		// Trim s1 so it isn't longer than s2
		// s1 abcde 5, 2 def 3
		if (s1.length() > s2.length())
			s1 = s1.substring(s1.length() - s2.length());
		// if 5 > 3 s1.substring(5-3=2) s1 = cde

		int[] t = computeBackTrackTable(s2); // O(n) // s2 segunda string

		int m = 0;
		int i = 0;
		while (m + i < s1.length()) {
			if (s2.charAt(i) == s1.charAt(m + i)) {
				i += 1;
				// <-- removed the return case here, because |s1| <= |s2|
			} else {
				m += i - t[i];
				if (i > 0)
					i = t[i];
			}
		}

		return i; // <-- changed the return here to return characters matched
	}*/

	/*public int[] computeBackTrackTable(String s) { // def
		int[] t = new int[s.length()]; // 3, array de 3 enteros
		int cnd = 0;
		t[0] = -1;
		t[1] = 0;
		int pos = 2;
		while (pos < s.length()) {
			if (s.charAt(pos - 1) == s.charAt(cnd)) {
				t[pos] = cnd + 1;
				pos += 1;
				cnd += 1;
			} else if (cnd > 0) {
				cnd = t[cnd];
			} else {
				t[pos] = 0;
				pos += 1;
			}
		}
		return t;
	}*/

	public static String overlapConcat2(String s1, String s2)
    {
		if (s1 == null || s1.isEmpty()) {
			if (s2 == null || s2.isEmpty())
				return "";
			else
				return s2;
		}
		if (s2 == null || s2.isEmpty())
			return s1;
        if (s1.contains(s2)) return s1;
        if (s2.contains(s1)) return s2;

//        char endChar = s1. .ToCharArray().Last();
//        char startChar = s2.ToCharArray().First();

		int len1 = s1.length() - 1;
		char endChar = s1.charAt(len1);
		char startChar = s2.charAt(0);
        
        
        int s1FirstIndexOfStartChar = s1.indexOf(startChar);
        int overlapLength = s1.length() - s1FirstIndexOfStartChar;

        while (overlapLength >= 0 && s1FirstIndexOfStartChar >=0)
        {
            if (checkOverlap(s1, s2, overlapLength))
            {
                return s1 + s2.substring(overlapLength);
            }

            s1FirstIndexOfStartChar = 
                s1.indexOf(startChar, s1FirstIndexOfStartChar);
            overlapLength = s1.length() - s1FirstIndexOfStartChar;

        }

        return s1 + s2;
    }

    private static boolean checkOverlap(String s1, String s2, int overlapLength)
    {
        if (overlapLength <= 0)
            return false;

        if (s1.substring(s1.length() - overlapLength) == 
            s2.substring(0, overlapLength))
            return true;

        return false;            
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

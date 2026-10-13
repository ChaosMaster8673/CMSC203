/**
 * This is a utility class that encrypts and decrypts a phrase using three
 * different approaches. 
 * 
 * The first approach is called the Vigenere Cipher.Vigenere encryption 
 * is a method of encrypting alphabetic text based on the letters of a keyword.
 * 
 * The second approach is Playfair Cipher. It encrypts two letters (a digraph) 
 * at a time instead of just one.
 * 
 * The third approach is Caesar Cipher. It is a simple replacement cypher. 
 * 
 * @author Huseyin Aygun
 * @version 8/3/2025
 */

//imports
import java.util.ArrayList;

public class CryptoManager { 

    private static final char LOWER_RANGE = ' ';
    private static final char UPPER_RANGE = '_';
    private static final int RANGE = UPPER_RANGE - LOWER_RANGE + 1;
    // Use 64-character matrix (8X8) for Playfair cipher  
    private static final String ALPHABET64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 !\"#$%&'()*+,-./:;<=>?@[\\]^_";

    public static boolean isStringInBounds(String plainText) {
        for (int i = 0; i < plainText.length(); i++) {
            if (!(plainText.charAt(i) >= LOWER_RANGE && plainText.charAt(i) <= UPPER_RANGE)) {
                return false;
            }
        }
        return true;
    }

	/**
	 * Vigenere Cipher is a method of encrypting alphabetic text 
	 * based on the letters of a keyword. It works as below:
	 * 		Choose a keyword (e.g., KEY).
	 * 		Repeat the keyword to match the length of the plaintext.
	 * 		Each letter in the plaintext is shifted by the position of the 
	 * 		corresponding letter in the keyword (A = 0, B = 1, ..., Z = 25).
	 */   

    public static String vigenereEncryption(String plainText, String key) {
         if (!isStringInBounds(plainText)) return "The selected string is not in bounds, Try again.";
         if (key == null || key.length() == 0) return "The key is empty, Try again."; //check for empty key and division by 0
         if (!isStringInBounds(key)) return "The selected string is not in bounds, Try again.";
         String cipher = "";
         
         for (int i = 0; i < plainText.length(); i++) {
        	 int kPos = key.charAt(i % key.length())-32; //key wraps around
        	 int newPos = (plainText.charAt(i) -32 + kPos)%64;
        	 cipher += (char)(newPos + 32);
         }
         return cipher;
         
    }

    // Vigenere Decryption
    public static String vigenereDecryption(String encryptedText, String key) {
         if (key == null || key.length() == 0) return "The key is empty, Try again."; //check for empty key and division by 0
         String plain = "";
		 for (int i = 0; i < encryptedText.length(); i++) {
			 int kPos = key.charAt(i % key.length())-32; //key wraps around
			 int newPos = (encryptedText.charAt(i) -32 - kPos + 64)%64; //add 26 to avoid negative values
			 plain += (char)(newPos + 32);
		 }
		 return plain;
    }


	/**
	 * Playfair Cipher encrypts two letters at a time instead of just one.
	 * It works as follows:
	 * A matrix (8X8 in our case) is built using a keyword
	 * Plaintext is split into letter pairs (e.g., ME ET YO UR).
	 * Encryption rules depend on the positions of the letters in the matrix:
	 *     Same row: replace each letter with the one to its right.
	 *     Same column: replace each with the one below.
	 *     Rectangle: replace each letter with the one in its own row but in the column of the other letter in the pair.
	 */    

    //Playfair private helpers
    
    private static char[][] buildPlayfairGrid(String key) {
		if (key == null) key = ""; //check for null key
		ArrayList<Character> seen = new ArrayList<Character>();
		
		char[][] grid = new char[8][8];
		//keep track of the next empty cell in the grid
		int row = 0;
		int col = 0;
		
		for (int i = 0; i < key.length(); i++) {
			if (!ALPHABET64.contains(String.valueOf(key.charAt(i))) || seen.contains(key.charAt(i))) {
				//skip if not in alphabet
				continue;
			}
			seen.add(key.charAt(i));
			//put the character in the next empty cell in the grid
			grid[row][col] = key.charAt(i);
			col++;
			if (col == 8) {
				col = 0;
				row++;
			}
		}
		for (int i = 0; i < ALPHABET64.length(); i++) {
			if (!seen.contains(ALPHABET64.charAt(i))) {
				//put the character in the next empty cell in the grid
				grid[row][col] = ALPHABET64.charAt(i);
				col++;
				if (col == 8) {
					col = 0;
					row++;
				}
			}
		}
		return grid;
	}
    
    //prepare playfair text
    private static String preparePlayfairText(String text) {
    	//Much simpler than I had thought
    	String txt = text.toUpperCase();
    	if (txt.length() % 2 != 0) txt += "X";
    	return txt;
    }

    
    //playfair encrypt pair
    private static String playfairEncryptPair(char[][] grid, char a, char b) {
		//(ra, ca) ← POSITION OF a IN grid
    	//(rb, cb) ← POSITION OF b IN grid
   		int ra = 0, ca = 0, rb = 0, cb = 0;
   		
   		for (int i = 0; i < 8; i++) {
   			for (int j = 0; j < 8; j++) {
   				if (grid[i][j] == a) {
   					ra = i;
   					ca = j;
   				}
   				if (grid[i][j] == b) {
   					rb = i;
   					cb = j;
   				}
   			}
   		}
   		if (ra == rb) {
   			//same row
   			return "" + grid[ra][(ca + 1) % 8] + grid[rb][(cb + 1) % 8];
   		} else if (ca == cb) {
   			//same column
   			return "" + grid[(ra + 1) % 8][ca] + grid[(rb + 1) % 8][cb];
   		} else {
   			//rectangle
   			return "" + grid[ra][cb] + grid[rb][ca];
   		}
   		
    }
   		
    private static String playfairDecryptPair(char[][] grid, char a, char b) {
    	//(ra, ca) ← POSITION OF a IN grid
		//(rb, cb) ← POSITION OF b IN grid
   		int ra = 0, ca = 0, rb = 0, cb = 0;
   		
   		for (int i = 0; i < 8; i++) {
   			for (int j = 0; j < 8; j++) {
   				if (grid[i][j] == a) {
   					ra = i;
   					ca = j;
   				}
   				if (grid[i][j] == b) {
   					rb = i;
   					cb = j;
   				}
   			}
   		}
   		if (ra == rb) {
   			//same row shift left
   			return "" + grid[ra][(ca + 7) % 8] + grid[rb][(cb + 7) % 8]; 
   		} else if (ca == cb) {
   			//same column shift up
   			return "" + grid[(ra + 7) % 8][ca] + grid[(rb + 7) % 8][cb];
   		} else {
   			//rectangle swap columns
   			return "" + grid[ra][cb] + grid[rb][ca];
   		}
    }
    
    public static String playfairEncryption(String plainText, String key) {
         if (!isStringInBounds(plainText)) return "The selected string is not in bounds, Try again.";
         char[][] grid = buildPlayfairGrid(key);
         String text = preparePlayfairText(plainText);
         String cipher = "";
         for (int i = 0; i < text.length(); i += 2) {
			 cipher += playfairEncryptPair(grid, text.charAt(i), text.charAt(i + 1));
		 }
         return cipher;
    }

    public static String playfairDecryption(String encryptedText, String key) {
         if (encryptedText == null) return ""; //check for null encrypted text
         char[][] grid = buildPlayfairGrid(key);
         String text = encryptedText.toUpperCase();
         if (text.length() % 2 != 0) {
        	 //drop last char if odd length
        	 text = text.substring(0, text.length() - 1);
		 }
         
         String plain = "";
         for (int i = 0; i < text.length(); i += 2) {
        	 			 plain += playfairDecryptPair(grid, text.charAt(i), text.charAt(i + 1));
         }
         if (plain.length() > 0 && plain.charAt(plain.length()-1) == 'X')
        	    plain = plain.substring(0, plain.length()-1);

         return plain;
    }

    /**
     * Caesar Cipher is a simple substitution cipher that replaces each letter in a message 
     * with a letter some fixed number of positions down the alphabet. 
     * For example, with a shift of 3, 'A' would become 'D', 'B' would become 'E', and so on.
     */    
 
    public static String caesarEncryption(String plainText, int key) {
	if (!isStringInBounds(plainText)) return "The selected string is not in bounds, Try again.";
	int shift = ((key %64) + 64) % 64; //handle negative keys
	String cipher = "";
	for (int i = 0; i < plainText.length(); i++) {
			    char c = plainText.charAt(i);
	cipher += (char)(((c-32+shift)%64 ) +32); //every character is shifted
	}
	return cipher;
    }

    // Caesar Decryption
    public static String caesarDecryption(String encryptedText, int key) {
	int shift = ((key %64) + 64) % 64; //handle negative keys
	String plain = "";
	for (int i = 0; i < encryptedText.length(); i++) {
	    int newPos = (encryptedText.charAt(i) -32 - shift +64 ) % 64; //shift and wrap around
	    plain += (char)(newPos + 32);
	    }  
	return plain;
	}

}

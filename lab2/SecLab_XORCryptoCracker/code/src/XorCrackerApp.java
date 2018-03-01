import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import ch.zhaw.init.is.crypto.classic.ByteFrequencyTable;
import ch.zhaw.init.is.crypto.classic.ByteFrequencyTableHelpers;
import ch.zhaw.init.is.util.HexTools;

/**
 * Application for cracking the encryption of files that have been 
 * encrypted with the XOR method with key lengths much smaller than
 * the size of the plaintext. Cracking is based on the assumption 
 * that there is a byte value in the plaintext which is significantly more
 * frequent than other byte values.
 * <p>
 * Usage:<br>
 * <tt>  XorCrackerApp file keylength</tt>
 * <p>
 * Arguments:<br>
 * <ul>
 * <li>file: The file to be cracked
 * <li>keylength: The length of the key in bytes
 * </ul>
 * <p>
 * @author tebe
 */
public class XorCrackerApp {
	private static int keylength;
	private static String filename;

	/**
	 * Main method of the application
	 * 
	 * @param args
	 *            Command line arguments
	 * 
	 */
	public static void main(String[] args) {
		if (parseCommandLineParameters(args)) {
			try {
				run();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		} else {
			usage();
		}
	}

	private static void usage() {
		System.out.println("Usage: XorCrackerApp file keylength");
	}

	/**
	 * Checks and parses the command line arguments.
	 * 
	 * @param args
	 *            Command line arguments
	 * @return true, if the arguments could be parsed
	 */
	private static boolean parseCommandLineParameters(String args[]) {
		if(args.length == 2) {
			try {
				filename = args[0];
				keylength = Integer.parseInt(args[1]);
				return true;
			} catch (NumberFormatException e) {
			}
		}
		return false;
	}

	
	/**
	 * Starts and controls the analysis process
	 * @throws IOException
	 */
	private static void run() throws IOException {
		try {
			System.out.println("Number of candidate keys: " + getNumberOfCandidateKeys());
			int[] key = getCandidateKey(filename, keylength, 0x65);
			System.out.println(HexTools.intArrayToHexString(key));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static int getNumberOfCandidateKeys() {
		return 1;
	}

	
	/**
	 * Determines the key assuming the specified most frequent byte is the most frequent
	 * byte in the plaintext and keylength is the true length of the key. 
	 * 
	 * @param filename 
	 *            The name of the file to process
	 * @param keylength
	 *            The key length in bytes
	 * @param mostFrequentValue
	 *            The assumed most frequent byte in the plaintext
	 *            
	 * @return The key
	 * @throws IOException
	 */
	private static int[] getCandidateKey(String filename, int keylength, int mostFrequentValue) throws IOException {
		DataInputStream inputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(filename)));
		ByteFrequencyTable[] frequencyTable = ByteFrequencyTableHelpers.getFrequencyTableForKeyLength(keylength, inputStream);
                int[][] allKeys = new int[256][keylength];
		int[] key = new int[keylength];
                String key1 = "11 22 33 44";
                String key2 = "91 44 23 11";
		for(int keyByte = 0; keyByte < keylength; keyByte++){
                        /* message01.txt 
			key[keyByte] = frequencyTable[keyByte].getMostFrequentByte() ^ ' '; */
                        /* message02.txt */
			//key[keyByte] = frequencyTable[keyByte].getMostFrequentByte() ^ 'e';
                        
                        for(int i = 0; i < 256; i++) {
                            allKeys[i][keyByte] = frequencyTable[keyByte].getMostFrequentByte() ^ i;
                            String possibleKey = Integer.toHexString(allKeys[i][0]) + " " + Integer.toHexString(allKeys[i][1]) + " " + Integer.toHexString(allKeys[i][2]) + " " + Integer.toHexString(allKeys[i][3]);
                            if(possibleKey.equals(key1)) {
                                System.out.println(possibleKey);
                            }
                            else if(possibleKey.equals(key2)) {
                                System.out.println(possibleKey);
                            }  
                        }
		}
		return key;
	}
}

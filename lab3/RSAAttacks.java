import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;

public class RSAAttacks {

    private static final BigInteger ONE = new BigInteger("1");

    /**
     * Tries to find the message assuming that the public exponent is equal to 3
     * and that the ciphertexts contain the same message encrypted with different
     * public keys.
     *
     * @param c1 first ciphertext
     * @param m1 modulus of first public key
     * @param c2 second ciphertext
     * @param m2 modulus of second public key
     * @param c3 third ciphertext
     * @param m3 modulus of third public key
     * @return the candidate message or null if algorithm fails
     */
    public static String tryLowExponentAttack(
            BigInteger c1, BigInteger m1,
            BigInteger c2, BigInteger m2,
            BigInteger c3, BigInteger m3) {
        try {
            //*** YOUR CODE ***
            BigInteger n1 = m2.multiply(m3);
            BigInteger n2 = m1.multiply(m3);
            BigInteger n3 = m2.multiply(m1);

            BigInteger d1 = n1.modInverse(m1);
            BigInteger d2 = n2.modInverse(m2);
            BigInteger d3 = n3.modInverse(m3);

            BigInteger x1 = c1.multiply(n1).multiply(d1);
            BigInteger x2 = (c2.multiply(n2).multiply(d2));
            BigInteger x3 = (c3.multiply(n3).multiply(d3));

            BigInteger mod = m1.multiply(m2).multiply(m3);
            BigInteger x = x1.add(x2).add(x3);
            BigInteger m_3 =  x.mod(mod);
            BigInteger plain = RSAEncryptionUtil.cubeRoot(m_3);

            return (new String( plain.toByteArray(), StandardCharsets.US_ASCII));   
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Tries to find the private key corresponding to the first modulus assuming
     * that the two provided moduli have a common factor.
     *
     * @param m1 first modulus
     * @param m2 second modulus
     * @param e1 public exponent corresponding to the first modulus
     * @return the candidate private key or null if algorithm fails
     */
    public static RSAPrivateKey tryCommonFactorAttack(
            BigInteger m1, BigInteger m2, BigInteger e1) {
        try {

            //*** YOUR CODE ***
            BigInteger n = m1;
            BigInteger p = m1.gcd(m2);
            BigInteger q = n.divide(p);

            BigInteger mod = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
            BigInteger d = e1.modInverse(mod);

            if(d == null){
                return null;
            }

            RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(m1, d);
            RSAPrivateKey privateKey = (RSAPrivateKey) KeyFactory.getInstance(RSAEncryptionUtil.ALGORITHM).generatePrivate(keySpec);
            
            return privateKey;
            
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Cracking Application.
     */
    public static void main(String[] args) {
        try {
            // ** Load a public key from file and get its modulus and exponent:
            // RSAPublicKey samplePublicKey = RSAEncryptionUtil.loadRSAPublicKey("sample.pub");
            // BigInteger sampleModulus = samplePublicKey.getModulus(); 
            // BigInteger sampleExponent = samplePublicKey.getPublicExponent(); 
            
            // ** Load ciphertext from file into a BigInteger:
            // BigInteger sampleCipherText = RSAEncryptionUtil.loadCiphertext("message-sample.enc");
            
            // ** Create an RSAPrivateKey from the modulus and private exponent:
            // RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(modulus, privateExponent);
            // RSAPrivateKey privateKey = (RSAPrivateKey) KeyFactory.getInstance(RSAEncryptionUtil.ALGORITHM).generatePrivate(keySpec);

            // ** Decrypt a ciphertext with a private key:
            // String plaintext = RSAEncryptionUtil.decrypt(sampleCipherText.toByteArray(), privateKey);

            // ** Convert a message that is represented as a BigInteger into a byte array and from 
            // ** this, create a string by interpreting the bytes as US-ASCII code
            // BigInteger messageAsBigInteger = ...;
            // String message = new String(messageAsBigInteger.toByteArray(), StandardCharsets.US_ASCII);
            
            //*** YOUR CODE ***
            
            String[] namedFiles = {"bob", "carol", "dave", "fred", "gustav", "hans"};
            RSAPublicKey[] publicKeyArray = new RSAPublicKey[6];
            RSAPrivateKey privateKeyArray[] = new RSAPrivateKey[6];
            BigInteger[] cipherArray = new BigInteger[6];

            for(int i = 0; i < 6; i++){
                publicKeyArray[i] = RSAEncryptionUtil.loadRSAPublicKey(namedFiles[i] + ".pub");
                cipherArray[i] = RSAEncryptionUtil.loadCiphertext("message-" + namedFiles[i] + ".enc");
                
                System.out.println(namedFiles[i]+ "'s Exponent: " + publicKeyArray[i].getPublicExponent());
                System.out.println(namedFiles[i]+ "'s Ciphertext: " + cipherArray[i] +"\n");
            }
            
            System.out.println(new String(RSAEncryptionUtil.cubeRoot(cipherArray[5]).toByteArray(), StandardCharsets.US_ASCII));

            RSAPrivateKey key1 = tryCommonFactorAttack(publicKeyArray[3].getModulus(), publicKeyArray[4].getModulus(), publicKeyArray[4].getPublicExponent());
            RSAPrivateKey key2 = tryCommonFactorAttack(publicKeyArray[4].getModulus(), publicKeyArray[3].getModulus(), publicKeyArray[4].getPublicExponent());

            String fred = RSAEncryptionUtil.decrypt(cipherArray[3].toByteArray(), key1);
            String gustav = RSAEncryptionUtil.decrypt(cipherArray[4].toByteArray(), key2);
            
            System.out.println(fred);
            System.out.println(gustav);
            System.out.println(tryLowExponentAttack(cipherArray[0], publicKeyArray[0].getModulus(), cipherArray[1], publicKeyArray[1].getModulus(), cipherArray[2], publicKeyArray[2].getModulus() ));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

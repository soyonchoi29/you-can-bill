import java.math.BigInteger;
import java.security.SecureRandom;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.File;
import java.util.Scanner;

public class RSA {
    private final static BigInteger one = new BigInteger("1");
    private final static SecureRandom random = new SecureRandom();

    private BigInteger privateKey;
    private BigInteger publicKey;
    private BigInteger modulus;

    public RSA (){
        BigInteger p = BigInteger.probablePrime(2048/2, random);
        BigInteger q = BigInteger.probablePrime(2048/2, random);
        BigInteger phi = (p.subtract(one)).multiply(q.subtract(one));

        modulus = p.multiply(q);
        publicKey = new BigInteger("65537");
        privateKey = publicKey.modInverse(phi);
    }

    public void encryptAndSave(BigInteger cardNum){
        BigInteger encrypted = cardNum.modPow(publicKey,modulus);

        try {
            File output = new File("CreditCardNumber.txt");
            PrintWriter printer = new PrintWriter(output);

            printer.write("" + encrypted);

            printer.close();
        } catch (FileNotFoundException error){
            System.err.println("File not found.");
            System.err.println(error);
        }

    }

    public BigInteger decrypt(){

        BigInteger encrypted = new BigInteger("0");

        try {
            File input = new File("CreditCardNumber.txt");
            Scanner sc = new Scanner(input);

            while (sc.hasNext()){
                encrypted = sc.nextBigInteger();
            }

            sc.close();
        } catch (FileNotFoundException error){
            System.err.println("File not found.");
            System.err.println(error);
        }

        return encrypted.modPow(privateKey,modulus);
    }

    //public static void main(String args[]) {
    //    RSA rsa = new RSA();
    //    BigInteger cardNum = new BigInteger("5105105105105100");
    //    rsa.encryptAndSave(cardNum);
    //    System.out.println(rsa.decrypt());
    //}

}
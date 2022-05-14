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

    // Call w name of person and their card num
    public void encryptAndSave(BigInteger cardNum, String name){
        BigInteger encrypted = cardNum.modPow(publicKey,modulus);

        try {
            File output = new File("CreditCardNumber.txt");
            PrintWriter printer = new PrintWriter(output);

            printer.write(name + "\n" + encrypted + "\n");

            printer.close();
        } catch (FileNotFoundException error){
            System.err.println("File not found.");
            System.err.println(error);
        }

    }

    // Call w/ name of person whose card info you need
    public BigInteger decrypt(String name){

        BigInteger encrypted = new BigInteger("0");

        try {
            File input = new File("CreditCardNumber.txt");
            Scanner sc = new Scanner(input);

            while (sc.hasNext()){
                
                System.out.println("next: " + sc.next());
                if (sc.next() == name){
                    System.out.println("next line: " + sc.nextLine());
                    encrypted = sc.nextBigInteger();
                    sc.close();
                } else {sc.nextLine();}
            }

        } catch (FileNotFoundException error){
            System.err.println("File not found.");
            System.err.println(error);
        }

        return encrypted.modPow(privateKey,modulus);
    }

    public static void main(String[] args) {
        RSA rsa = new RSA();
        rsa.encryptAndSave(new BigInteger("5555555555554444"), "Soyon");
        System.out.println(rsa.decrypt("Soyon"));
        rsa.encryptAndSave(new BigInteger("5105105105105100"), "Kevin");
        System.out.println(rsa.decrypt("Kevin"));
        System.out.println(rsa.decrypt("Soyon"));
        rsa.encryptAndSave(new BigInteger("4111111111111111"), "Ris");
        System.out.println(rsa.decrypt("Kevin"));
        System.out.println(rsa.decrypt("Ris"));
        System.out.println(rsa.decrypt("Soyon"));
    }  
}
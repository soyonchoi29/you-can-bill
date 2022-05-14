import java.math.BigInteger;
import java.security.SecureRandom;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
            File output = new File("CreditCardNumber.txt"); // check to add no dupe names somewhere
            FileWriter fileWriter = new FileWriter(output, true);

            fileWriter.write(name + "\n" + encrypted + "\n");

            fileWriter.close();
        } catch (FileNotFoundException error){
            System.err.println("File not found.");
            System.err.println(error);
        } catch (IOException error) {}

    }

    // Call w/ name of person whose card info you need
    public BigInteger decrypt(String name){

        BigInteger encrypted = new BigInteger("0");

        try {
            File input = new File("CreditCardNumber.txt");
            Scanner sc = new Scanner(input);

            while (sc.hasNext()){
                if (sc.next().equals(name)){
                    encrypted = sc.nextBigInteger();
                    break;
                } else {sc.nextLine();}
            }

            sc.close();

        } catch (FileNotFoundException error){
            System.err.println("File not found.");
            System.err.println(error);
        }
        //System.out.println(modulus);
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
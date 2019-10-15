package com.company;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        int bitLength = 1024;
        Random randomGenerator = new Random();

        BigInteger p = BigInteger.probablePrime(bitLength, randomGenerator);
        BigInteger q = BigInteger.probablePrime(bitLength, randomGenerator);
        BigInteger module = p.multiply(q);
        BigInteger eulerFunctionValue = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        BigInteger publicExponent = BigInteger.valueOf(65537);
        BigInteger secretExponent = getSecretExponent(eulerFunctionValue, publicExponent);

        //______________________________________________________________________________________________________________
        String message = "Done";
        BigInteger cryptMsg = crypt(message, module, publicExponent);
        BigInteger decryptMsg = decrypt(cryptMsg, secretExponent, module);
        System.out.println("Data in: " + message);
        System.out.println("Encoded: " + decryptMsg);
        System.out.println("Decoded: " + decryptMsg);
        System.out.println("Data out: " + new String(decryptMsg.toByteArray()));

    }

    static BigInteger getSecretExponent(BigInteger euler, BigInteger pubExp) {

        var m = BigInteger.ONE;

        while (!m.multiply(euler).add(BigInteger.ONE).mod(pubExp).equals(BigInteger.ZERO)) {
            m = m.add(BigInteger.ONE);
        }

        return (m.multiply(euler).add(BigInteger.ONE)).divide(pubExp);
    }

    static BigInteger crypt(String rawString, BigInteger module, BigInteger pubExp) {
        byte[] bytes = rawString.getBytes();

        return new BigInteger(bytes).modPow(pubExp, module);
    }

    static BigInteger decrypt(BigInteger encoded, BigInteger secExp, BigInteger module) {
        return encoded.modPow(secExp, module);
    }
}

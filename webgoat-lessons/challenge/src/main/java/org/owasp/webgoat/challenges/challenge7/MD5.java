package org.owasp.webgoat.challenges.challenge7;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 hash generator.
 * More information about this class is available from <a target="_top" href=
 * "http://ostermiller.org/utils/MD5.html">ostermiller.org</a>.
 * <p>
 * This class takes as input a message of arbitrary length and produces
 * as output a 128-bit "fingerprint" or "message digest" of the input.
 * It is conjectured that it is computationally infeasible to produce
 * two messages having the same message digest, or to produce any
 * message having a given pre-specified target message digest. The MD5
 * algorithm is intended for digital signature applications, where a
 * large file must be "compressed" in a secure manner before being
 * encrypted with a private (secret) key under a public-key cryptosystem
 * such as RSA.
 * <p>
 * For more information see RFC1321.
 *
 * @author Santeri Paavolainen http://santtu.iki.fi/md5/
 * @author Stephen Ostermiller http://ostermiller.org/contact.pl?regarding=Java+Utilities
 * @since ostermillerutils 1.00.00
 */
// Replace MD5 usages with java.security.MessageDigest using SHA-256
// For example:
// MessageDigest digest = MessageDigest.getInstance("SHA-256");
// byte[] hash = digest.digest(data);
// String hashHex = javax.xml.bind.DatatypeConverter.printHexBinary(hash);

public class MD5 {

    /**
     * Class constructor
     *
     * @since ostermillerutils 1.00.00
     */
    public MD5() {
        // No-op for compatibility
    }

    /**
     * Command line program that will take files as arguments
     * and output the SHA-256 sum for each file.
     *
     * @param args command line arguments
     * @since ostermillerutils 1.00.00
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Please specify a file.");
        } else {
            for (String element : args) {
                try {
                    System.out.println(MD5.getHashString(new File(element)) + " " + element);
                } catch (IOException x) {
                    System.err.println(x.getMessage());
                }
            }
        }
    }

    /**
     * Gets the SHA-256 hash of the given byte array.
     *
     * @param b byte array for which a SHA-256 hash is desired.
     * @return Array of 32 bytes, the hash of all updated bytes.
     * @since ostermillerutils 1.00.00
     */
    public static byte[] getHash(byte[] b) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(b);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available", e);
        }
    }

    /**
     * Gets the SHA-256 hash of the given byte array.
     *
     * @param b byte array for which a SHA-256 hash is desired.
     * @return 64-character hex representation the data's SHA-256 hash.
     * @since ostermillerutils 1.00.00
     */
    public static String getHashString(byte[] b) {
        return toHex(getHash(b));
    }

    /**
     * Gets the SHA-256 hash the data on the given InputStream.
     *
     * @param in InputStream for which a SHA-256 hash is desired.
     * @return Array of 32 bytes, the hash of all updated bytes.
     * @throws IOException if an I/O error occurs.
     * @since ostermillerutils 1.00.00
     */
    public static byte[] getHash(InputStream in) throws IOException {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                digest.update(buffer, 0, read);
            }
            return digest.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available", e);
        }
    }

    /**
     * Gets the SHA-256 hash the data on the given InputStream.
     *
     * @param in InputStream for which a SHA-256 hash is desired.
     * @return 64-character hex representation the data's SHA-256 hash.
     * @throws IOException if an I/O error occurs.
     * @since ostermillerutils 1.00.00
     */
    public static String getHashString(InputStream in) throws IOException {
        return toHex(getHash(in));
    }

    /**
     * Gets the SHA-256 hash of the given file.
     *
     * @param f file for which a SHA-256 hash is desired.
     * @return Array of 32 bytes, the hash of all updated bytes.
     * @throws IOException if an I/O error occurs.
     * @since ostermillerutils 1.00.00
     */
    public static byte[] getHash(File f) throws IOException {
        try (InputStream is = new FileInputStream(f)) {
            return getHash(is);
        }
    }

    /**
     * Gets the SHA-256 hash of the given file.
     *
     * @param f file for which a SHA-256 hash is desired.
     * @return 64-character hex representation the data's SHA-256 hash.
     * @throws IOException if an I/O error occurs.
     * @since ostermillerutils 1.00.00
     */
    public static String getHashString(File f) throws IOException {
        try (InputStream is = new FileInputStream(f)) {
            return getHashString(is);
        }
    }

    /**
     * Gets the SHA-256 hash of the given String.
     * The string is converted to bytes using the current
     * platform's default character encoding.
     *
     * @param s String for which a SHA-256 hash is desired.
     * @return Array of 32 bytes, the hash of all updated bytes.
     * @since ostermillerutils 1.00.00
     */
    public static byte[] getHash(String s) {
        return getHash(s.getBytes());
    }

    /**
     * Gets the SHA-256 hash of the given String.
     * The string is converted to bytes using the current
     * platform's default character encoding.
     *
     * @param s String for which a SHA-256 hash is desired.
     * @return 64-character hex representation the data's SHA-256 hash.
     * @since ostermillerutils 1.00.00
     */
    public static String getHashString(String s) {
        return toHex(getHash(s));
    }

    /**
     * Gets the SHA-256 hash of the given String.
     *
     * @param s   String for which a SHA-256 hash is desired.
     * @param enc The name of a supported character encoding.
     * @return Array of 32 bytes, the hash of all updated bytes.
     * @throws UnsupportedEncodingException If the named encoding is not supported.
     * @since ostermillerutils 1.00.00
     */
    public static byte[] getHash(String s, String enc) throws UnsupportedEncodingException {
        return getHash(s.getBytes(enc));
    }

    /**
     * Gets the SHA-256 hash of the given String.
     *
     * @param s   String for which a SHA-256 hash is desired.
     * @param enc The name of a supported character encoding.
     * @return 64-character hex representation the data's SHA-256 hash.
     * @throws UnsupportedEncodingException If the named encoding is not supported.
     * @since ostermillerutils 1.00.00
     */
    public static String getHashString(String s, String enc) throws UnsupportedEncodingException {
        return toHex(getHash(s, enc));
    }

    /**
     * Returns 64-character hex representation of this hash.
     *
     * @return String representation of this object's hash.
     * @since ostermillerutils 1.00.00
     */
    @Override
    public String toString() {
        return "SHA-256 hash utility";
    }

    /**
     * Turns array of bytes into string representing each byte as
     * a two digit unsigned hex number.
     *
     * @param hash Array of bytes to convert to hex-string
     * @return Generated hex string
     * @since ostermillerutils 1.00.00
     */
    private static String toHex(byte hash[]) {
        StringBuilder buf = new StringBuilder(hash.length * 2);
        for (byte element : hash) {
            int intVal = element & 0xff;
            if (intVal < 0x10) {
                buf.append("0");
            }
            buf.append(Integer.toHexString(intVal));
        }
        return buf.toString();
    }
}
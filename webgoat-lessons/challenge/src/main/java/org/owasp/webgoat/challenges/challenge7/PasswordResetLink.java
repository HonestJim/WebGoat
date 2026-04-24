package org.owasp.webgoat.challenges.challenge7;

import java.util.Random;

/**
 * WARNING: DO NOT CHANGE FILE WITHOUT CHANGING .git contents
 *
 * @author nbaars
 * @since 8/17/17.
 */
public class PasswordResetLink {

    public String createPasswordReset(String username, String key) {
        Random random = new Random();
        // Use a more secure random seed, ideally from SecureRandom
        if (username.equalsIgnoreCase("admin")) {
            try {
                java.security.SecureRandom secureRandom = java.security.SecureRandom.getInstanceStrong();
                random.setSeed(secureRandom.generateSeed(16));
            } catch (java.security.NoSuchAlgorithmException e) {
                random.setSeed(System.currentTimeMillis());
            }
        }
        return scramble(random, scramble(random, scramble(random, MD5.getHashString(username + key))));
    }

    public static String scramble(Random random, String inputString) {
        char[] a = inputString.toCharArray();
        for (int i = 0; i < a.length; i++) {
            int j = random.nextInt(a.length);
            char temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }
        return new String(a);
    }

    public static void main(String[] args) {
        if (args == null || args.length != 2) {
            System.out.println("Need a username and key");
            System.exit(1);
        }
        String username = args[0];
        String key = args[1];
        System.out.println("Generation password reset link for " + username);
        System.out.println("Created password reset link: " + new PasswordResetLink().createPasswordReset(username, key));
    }
}
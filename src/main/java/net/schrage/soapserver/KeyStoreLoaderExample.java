package net.schrage.soapserver;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.util.Enumeration;

public class KeyStoreLoaderExample {

  public static void main(String[] args) {
    ClassLoader classLoader = KeyStoreLoaderExample.class.getClassLoader();
    InputStream inputStream = classLoader.getResourceAsStream("keystore/serviceKeystore.jks");
    if (inputStream == null) {
      System.err.println("Unable to find keystore file.");
      return;
    }
    try {
      KeyStore keyStore = KeyStore.getInstance("JKS");
      keyStore.load(inputStream, "sspass".toCharArray());
      Enumeration<String> aliases = keyStore.aliases();
      while (aliases.hasMoreElements()) {
        String alias = aliases.nextElement();
        Certificate certificate = keyStore.getCertificate(alias);
        System.out.println("Alias: " + alias);
        System.out.println("Certificate: " + certificate.toString());
      }
    } catch (Exception e) {
      System.err.println("Error loading keystore: " + e.getMessage());
    } finally {
      try {
        inputStream.close();
      } catch (Exception ignored) {
      }
    }
  }

}

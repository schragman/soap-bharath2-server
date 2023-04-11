package net.schrage.soapserver;

import java.io.InputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.util.Enumeration;
import java.util.Properties;

public class ResourceLoaderExample {

  public static void main(String[] args) {
    ClassLoader classLoader = ResourceLoaderExample.class.getClassLoader();
    URL resourceUrl = classLoader.getResource("etc/serviceKeystore.properties");
    String keystoreFilepath = null;
    String keystoreType = null;
    String keystorePW = null;
    String keystoreAlias = null;
    if (resourceUrl == null) {
      System.err.println("Unable to find resource file.");
      return;
    }
    try (InputStream inputStream = resourceUrl.openStream()) {
      Properties properties = new Properties();
      properties.load(inputStream);
      keystoreFilepath = properties.getProperty("org.apache.ws.security.crypto.merlin.keystore.file");
      keystoreType = properties.getProperty("org.apache.ws.security.crypto.merlin.keystore.type");
      keystorePW = properties.getProperty("org.apache.ws.security.crypto.merlin.keystore.password");
      keystoreAlias = properties.getProperty("org.apache.ws.security.crypto.merlin.keystore.alias");
      System.out.println(keystoreFilepath);

    } catch (Exception e) {
      System.err.println("Error loading resource file: " + e.getMessage());
    }

    ClassLoader classLoader1 = KeyStoreLoaderExample.class.getClassLoader();
    InputStream inputStream = classLoader1.getResourceAsStream(keystoreFilepath);
    if (inputStream == null) {
      System.err.println("Unable to find keystore file.");
      return;
    }
    try {
      KeyStore keyStore = KeyStore.getInstance(keystoreType);
      keyStore.load(inputStream, keystorePW.toCharArray());
      Enumeration<String> aliases = keyStore.aliases();
      while (aliases.hasMoreElements()) {
        String alias = aliases.nextElement();
        if (alias.equals(keystoreAlias)) {
          Certificate certificate = keyStore.getCertificate(alias);
          System.out.println("Alias: " + alias);
          System.out.println("Certificate: " + certificate.toString());
        }
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

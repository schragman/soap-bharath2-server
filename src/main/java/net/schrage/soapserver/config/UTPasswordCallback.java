package net.schrage.soapserver.config;

import org.apache.wss4j.common.ext.WSPasswordCallback;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UTPasswordCallback implements CallbackHandler {

  private Map<String, String> storedPasswords = new HashMap<>();

  public UTPasswordCallback() {
    storedPasswords.put("Michael", "geheim");
    storedPasswords.put("Lisa", "nochgeheimer");
    storedPasswords.put("myservicekey", "skpass");
  }

  @Override
  public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
    Arrays.stream(callbacks)
        .map(callback -> (WSPasswordCallback) callback)
        .filter(allTransferredCredentials -> storedPasswords.containsKey(allTransferredCredentials.getIdentifier()))
        .forEach(transferredCredential -> transferredCredential.setPassword(storedPasswords.get(transferredCredential.getIdentifier())));
  }
}

package net.schrage.soapserver.config;

import jakarta.xml.ws.handler.Handler;
import jakarta.xml.ws.soap.SOAPBinding;
import net.schrage.soapserver.ws.SumWsImpl;
import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.jaxws.EndpointImpl;
//import jakarta.xml.ws.Endpoint;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.wss4j.common.ConfigurationConstants;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class WebServiceConfig {

  @Autowired
  private Bus bus;

  @Bean
  public Endpoint endpoint() {
    EndpointImpl endpoint = new EndpointImpl(bus, new SumWsImpl());
    endpoint.publish("/sumservice");

    Map<String, Object> inProps = new HashMap<>();

    /* Adding WSS4J Security*/
    inProps.put(ConfigurationConstants.ACTION, "UsernameToken Encrypt");
    //inProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.SIGNATURE);
    inProps.put(ConfigurationConstants.PASSWORD_TYPE, WSConstants.PW_TEXT);
    inProps.put(ConfigurationConstants.PW_CALLBACK_CLASS, UTPasswordCallback.class.getName());
    /* End Security*/

    /*Start decryption*/
    //inProps.put(WSHandlerConstants.SIG_PROP_FILE, "etc/serviceKeystore.properties");
    inProps.put(WSHandlerConstants.DEC_PROP_FILE, "etc/serviceKeystore.properties");


    WSS4JInInterceptor wssIn = new WSS4JInInterceptor(inProps);
    endpoint.getInInterceptors().add(wssIn);

    return endpoint.getServer().getEndpoint();
  }
}

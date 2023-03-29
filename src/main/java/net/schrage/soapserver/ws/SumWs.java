package net.schrage.soapserver.ws;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import net.schrage.soapserver.dto.SumRequest;
import net.schrage.soapserver.dto.SumResponse;
import org.apache.cxf.feature.Features;
import org.apache.cxf.interceptor.InInterceptors;

@WebService
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public interface SumWs {

  @WebMethod
  SumResponse calculateSum(SumRequest sumRequest);

}

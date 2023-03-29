package net.schrage.soapserver.ws;

import net.schrage.soapserver.dto.SumRequest;
import net.schrage.soapserver.dto.SumResponse;

public class SumWsImpl implements SumWs{
  @Override
  public SumResponse calculateSum(SumRequest sumRequest) {
    SumResponse sumResponse = new SumResponse();
    sumResponse.setSum(sumRequest.getNum1() + sumRequest.getNum2());
    return sumResponse;
  }
}

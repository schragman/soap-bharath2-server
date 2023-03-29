package net.schrage.soapserver.dto;

import jakarta.xml.bind.annotation.XmlType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlType
public class SumRequest {

  int num1, num2;

}

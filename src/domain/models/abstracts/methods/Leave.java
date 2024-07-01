package src.domain.models.abstracts.methods;

import src.domain.enums.ApduMethodEnum;
import src.domain.models.abstracts.Method;

public class Leave extends Method {
  public Leave(String input, String regex) {
    super(ApduMethodEnum.LEAVE, input, regex);
  }
}

package src.domain.models.abstracts.methods;

import src.domain.enums.ApduMethodEnum;
import src.domain.models.abstracts.Method;

public class Send extends Method {
  public Send(String input, String regex) {
    super(ApduMethodEnum.SEND, input, regex);
  }
}

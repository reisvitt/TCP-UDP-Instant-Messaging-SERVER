package src.domain.models.abstracts.methods;

import src.domain.enums.ApduMethodEnum;
import src.domain.models.abstracts.Method;

public class List extends Method {
  public List(String input, String regex) {
    super(ApduMethodEnum.LIST, input, regex);
  }
}

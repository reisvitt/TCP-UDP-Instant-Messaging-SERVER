package src.domain.models.abstracts.methods;

import src.domain.enums.ApduMethodEnum;
import src.domain.models.abstracts.Method;

public class Join extends Method {
  public Join(String input, String regex) {
    super(ApduMethodEnum.JOIN, input, regex);
  }
}

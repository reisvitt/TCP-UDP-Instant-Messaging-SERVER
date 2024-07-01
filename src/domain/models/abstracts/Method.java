package src.domain.models.abstracts;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import src.domain.enums.ApduMethodEnum;

public abstract class Method {
  private ApduMethodEnum method;
  private String input;
  private String regex;

  public Method(ApduMethodEnum method, String input, String regex) {
    this.method = method;
    this.input = input;
    this.regex = regex;
  }

  public ApduMethodEnum getMethod() {
    return method;
  }

  public String getInput() {
    return input;
  }

  public String getRegex() {
    return regex;
  }

  public Boolean isMatch(String data) {
    // verify if the local pattern matchs with values

    String localRegex = method + " " + regex;
    Pattern pattern = Pattern.compile(localRegex);
    Matcher matcher = pattern.matcher(data);

    if (matcher.matches()) {
      System.out.println("Endpoint: " + input + " Matches: " + data);
      return true;
    }

    return false;
  }
}

package src.domain.enums;

public enum ApduMethodEnum {
  SEND("SEND"),
  JOIN("JOIN"),
  LEAVE("LEAVE"),
  LIST("LIST");

  private String apduMethod;

  ApduMethodEnum(String apduMethod) {
    this.apduMethod = apduMethod;
  }

  public String getApduMethod() {
    return this.apduMethod;
  }
}
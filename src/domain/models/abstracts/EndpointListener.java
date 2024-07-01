package src.domain.models.abstracts;

import src.domain.models.Client;
import src.domain.models.Server;

public abstract class EndpointListener {
  private Method method;

  public EndpointListener(Method method) {
    this.method = method;
  }

  public abstract void execute(Server server, Client client, String data);

  public boolean isMatch(String data) {
    return this.method.isMatch(data);
  }

  public Method getApdu() {
    return this.method;
  }
}

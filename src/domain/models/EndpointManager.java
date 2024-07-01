package src.domain.models;

import java.util.ArrayList;
import java.util.List;

import src.domain.models.abstracts.EndpointListener;

public class EndpointManager {
  private List<EndpointListener> endpoints = new ArrayList<>();

  public void subscribe(EndpointListener endpoint) {
    this.endpoints.add(endpoint);
  }

  public void unsubscribe(EndpointListener endpoint) {
    this.endpoints.add(endpoint);
  }

  public void execute(Server server, Client client, String data) {
    boolean matched = false;
    for (EndpointListener endpoint : this.endpoints) {
      if (endpoint.isMatch(data)) {
        endpoint.execute(server, client, data);
        matched = true;
        break;
      }
    }
    if (!matched) {
      System.out.println("404 - Endpoit not found: " + data + " for client: " + client.getIp());
    }
  }

  public List<EndpointListener> getEndpoints() {
    return this.endpoints;
  }
}

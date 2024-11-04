package src.presentation;

import src.domain.models.Client;
import src.domain.models.EndpointManager;
import src.domain.models.Server;
import src.domain.models.abstracts.EndpointListener;
import src.presentation.endpoints.JoinGroup;
import src.presentation.endpoints.LeaveAllGroups;
import src.presentation.endpoints.LeaveGroup;
import src.presentation.endpoints.MyGroups;
import src.presentation.endpoints.SendMessage;
import src.data.service.GroupService;

public class Manager {
  public static Manager instance;
  public EndpointManager endpointsManager;

  private Manager() {
    this.endpointsManager = new EndpointManager();
  }

  public static Manager getInstance() {
    if (Manager.instance == null) {
      Manager.instance = new Manager();
    }
    return Manager.instance;
  }

  public void build(GroupService groupService) {
    EndpointListener joinGroup = new JoinGroup(groupService);
    EndpointListener sendMessage = new SendMessage(groupService);
    EndpointListener leaaveAllGroups = new LeaveAllGroups(groupService);
    EndpointListener leaveGroup = new LeaveGroup(groupService);
    EndpointListener myGroups = new MyGroups(groupService);

    this.endpointsManager.subscribe(joinGroup);
    this.endpointsManager.subscribe(sendMessage);
    this.endpointsManager.subscribe(leaaveAllGroups);
    this.endpointsManager.subscribe(leaveGroup);
    this.endpointsManager.subscribe(myGroups);
  }

  public void execute(Server server, Client client, String data) {
    data = data.trim();
    System.out.println("DATA:" + data);
    this.endpointsManager.execute(server, client, data);
  }

  public void printEndpoints() {
    System.out.println("-----------ENDPOINTS-----------");
    this.endpointsManager.getEndpoints().forEach(endpoint -> {
      System.out.println(endpoint.getApdu().getInput());
    });
    System.out.println("-------------------------------");
  }
}

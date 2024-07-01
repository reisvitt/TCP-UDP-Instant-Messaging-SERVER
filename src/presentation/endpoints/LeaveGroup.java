package src.presentation.endpoints;

import src.domain.models.Client;
import src.domain.models.Server;
import src.domain.models.abstracts.EndpointListener;
import src.domain.models.abstracts.methods.Leave;
import src.data.service.GroupService;

public class LeaveGroup extends EndpointListener {
  private GroupService groupService;

  public LeaveGroup(GroupService gs) {
    super(new Leave("LEAVE {GROUP}", "\\w+(\\s+\\w+)*$"));
    this.groupService = gs;

  }

  @Override
  public void execute(Server server, Client client, String data) {
    this.groupService.leaveGroup(server, client, data);
  }
}

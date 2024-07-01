package src.presentation.endpoints;

import src.domain.models.Client;
import src.domain.models.Server;
import src.domain.models.abstracts.EndpointListener;
import src.domain.models.abstracts.methods.Leave;
import src.data.service.GroupService;

public class LeaveAllGroups extends EndpointListener {
  private GroupService groupService;

  public LeaveAllGroups(GroupService groupService) {
    super(new Leave("LEAVE ALL GROUPS", "ALL GROUPS$"));
    this.groupService = groupService;
  }

  @Override
  public void execute(Server server, Client client, String data) {
    this.groupService.leaveAllGroup(server, client, data);
  }
}

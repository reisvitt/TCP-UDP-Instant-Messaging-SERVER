package src.presentation.endpoints;

import src.domain.models.Client;
import src.domain.models.Server;
import src.domain.models.abstracts.EndpointListener;
import src.domain.models.abstracts.methods.Join;
import src.data.service.GroupService;

public class JoinGroup extends EndpointListener {
  private GroupService groupService;

  public JoinGroup(GroupService groupService) {
    super(new Join("JOIN {GROUP}", "\\w+(\\s+\\w+)*$"));
    this.groupService = groupService;
  }

  @Override
  public void execute(Server server, Client client, String data) {
    this.groupService.joinGroup(server, client, data);
  }
}

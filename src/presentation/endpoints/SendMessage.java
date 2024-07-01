package src.presentation.endpoints;

import src.domain.models.Client;
import src.domain.models.Server;
import src.domain.models.abstracts.EndpointListener;
import src.domain.models.abstracts.methods.Send;
import src.data.service.GroupService;

public class SendMessage extends EndpointListener {
  private GroupService groupService;

  public SendMessage(GroupService groupService) {
    super(new Send("SEND {GROUP}:{MESSAGE}", "\\w+:(.+)$"));
    this.groupService = groupService;
  }

  @Override
  public void execute(Server server, Client client, String data) {
    this.groupService.sendMessage(server, client, data);
  }
}

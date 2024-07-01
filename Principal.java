import src.presentation.Manager;
import src.data.repository.GroupsStore;
import src.servers.TCPServer;
import src.servers.UDPServer;
import src.data.service.GroupService;

public class Principal {
  public static void main(String[] args) {
    GroupsStore gs = GroupsStore.getInstance();
    GroupService groupService = new GroupService(gs);

    Manager endpointManager = Manager.getInstance();
    endpointManager.build(groupService);
    endpointManager.printEndpoints();

    TCPServer tcp = TCPServer.getInstance();
    tcp.setManager(endpointManager);
    tcp.start();

    UDPServer udp = UDPServer.getInstance();
    udp.setManager(endpointManager);
    udp.start();

  }
}
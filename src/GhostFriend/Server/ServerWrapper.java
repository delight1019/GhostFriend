package GhostFriend.Server;

public class ServerWrapper {
    private SocketServer server;

    public void startServer() {
        server = new SocketServer();
        server.start();
    }
}

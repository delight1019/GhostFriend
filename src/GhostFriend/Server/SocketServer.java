package GhostFriend.Server;

import java.io.IOException;
import java.net.ServerSocket;

public class SocketServer {
    private static final int PORT = 9000;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

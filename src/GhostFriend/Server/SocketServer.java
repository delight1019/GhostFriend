package GhostFriend.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServer {
    private static final int PORT = 9000;
    private static final int PLAYER_NUMBER = 5;

    private static ExecutorService threadPool = Executors.newFixedThreadPool(PLAYER_NUMBER);

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                try {
                    threadPool.execute(new ConnectionWrapper(socket));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    socket.close();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package GhostFriend.Server;

import GhostFriend.Base.Game.Game;
import GhostFriend.Utils.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServer {
    private static final int PORT = 9000;
    private static final int PLAYER_NUMBER = 5;
    private int currentPlayerNum = 0;

    private static ExecutorService threadPool = Executors.newFixedThreadPool(PLAYER_NUMBER);

    public void start() {
        Log.printText("Server started");
        Game game = new Game();

        try {
            ServerSocket serverSocket = new ServerSocket(PORT);

            while (currentPlayerNum < PLAYER_NUMBER) {
                Socket socket = serverSocket.accept();
                try {
                    threadPool.execute(new ClientControl(socket));
                    currentPlayerNum++;
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

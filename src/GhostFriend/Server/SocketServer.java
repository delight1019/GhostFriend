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
    private static final int MAX_THREAD = 10;
    private int currentPlayerNum = 0;
    private Game game;

    private static ExecutorService threadPool = Executors.newFixedThreadPool(MAX_THREAD);

    public void start() {
        game = new Game();
        MainServer.registerGame(game);

        try {
            ServerSocket serverSocket = new ServerSocket(PORT);

            Log.printText("Socket server starts");

            while (currentPlayerNum < MAX_THREAD) {
                Socket socket = serverSocket.accept();
                try {
                    threadPool.execute(new ClientControl(socket, game));
                    currentPlayerNum++;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

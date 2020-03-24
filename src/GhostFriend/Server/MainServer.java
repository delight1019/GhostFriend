package GhostFriend.Server;

import GhostFriend.Base.Game.Game;
import GhostFriend.Base.Player.Player;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainServer {
    private static final int MAX_THREAD = 100;
    private static ExecutorService threadPool = Executors.newFixedThreadPool(MAX_THREAD);

    private static MainServer instance;

    private static Game game;
    private List<PlayerInfo> playersList;

    private MainServer(Game game) {
        this.playersList = new ArrayList<>();
        this.game = game;
    }

    public static MainServer getInstance() {
        if (instance == null) {
            instance = new MainServer(MainServer.game);
        }

        return instance;
    }

    public static void registerGame(Game game) {
        MainServer.game = game;
        MainServer.instance = new MainServer(MainServer.game);
    }

    public Player addPlayer(String name, PrintWriter printWriter, BufferedReader bufferedReader) {
        Player player = game.addPlayer(name);

        if (player != null) {
            synchronized (playersList) {
                PlayerInfo newPlayerInfo = new PlayerInfo(player, printWriter, bufferedReader);
                playersList.add(newPlayerInfo);
            }
        }

        return player;
    }

    public void broadcast(String text) {
        synchronized (playersList) {
            for (PlayerInfo playerInfo : playersList) {
                playerInfo.printWriter.println(text);
                playerInfo.printWriter.flush();
            }
        }
    }

    private class PlayerInfo {
        private Player player;
        private PrintWriter printWriter;
        private BufferedReader bufferedReader;

        public PlayerInfo(Player player, PrintWriter printWriter, BufferedReader bufferedReader) {
            this.player = player;
            this.printWriter = printWriter;
            this.bufferedReader = bufferedReader;
        }
    }
}

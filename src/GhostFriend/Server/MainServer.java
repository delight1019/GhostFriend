package GhostFriend.Server;

import GhostFriend.Base.Game.Game;
import GhostFriend.Base.Player.DealMissStatus;
import GhostFriend.Base.Player.Player;
import GhostFriend.Utils.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainServer {
    private static final int THREAD_NUM = 10;
    private ExecutorService executorService;

    private static MainServer instance;

    private static Game game;
    private final List<PlayerInfo> playersList;

    private MainServer(Game game) {
        this.executorService = Executors.newFixedThreadPool(THREAD_NUM);
        this.playersList = new ArrayList<>();
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

    public void startPlaying() {
        Runnable runnable = () -> {
            game.distributeCards();

            synchronized (playersList) {
                for (PlayerInfo playerInfo : playersList) {
                    broadcast(playerInfo, GameParams.DISTRIBUTE_CARDS, playerInfo.player.getCardListInfo(GameParams.DATA_DELIMITER));
                    broadcast(playerInfo, GameParams.CHECK_DEAL_MISS, game.isDealMiss(playerInfo.player).toString());
                }
            }
        };

        executorService.execute(runnable);
    }

    public void checkDealMissDeclared() {
        Runnable runnable = () -> {
            if (game.isDealMissDeclared() == DealMissStatus.MISS) {
                game.clear();
                broadcast(GameParams.RESTART_GAME);
                startPlaying();
            }
        };

        executorService.execute(runnable);
    }

    private void broadcast(PlayerInfo playerInfo, String command, String data) {
        playerInfo.printWriter.println(command + GameParams.COMMAND_DATA_DELIMITER + data + GameParams.COMMAND_DELIMITER);
        playerInfo.printWriter.flush();

        try {
            Log.printText("Broadcast to " + playerInfo.player.getName() + ": " + command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcast(String command, String data) {
        Runnable runBroadcast = () -> {
            synchronized (playersList) {
                for (PlayerInfo playerInfo : playersList) {
                    playerInfo.printWriter.println(command + GameParams.COMMAND_DATA_DELIMITER + data + GameParams.COMMAND_DELIMITER);
                    playerInfo.printWriter.flush();

                    try {
                        Log.printText("Broadcast to " + playerInfo.player.getName() + ": " + command);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        executorService.execute(runBroadcast);
    }

    public void broadcast(String command) {
        Runnable runnable = () -> {
            synchronized (playersList) {
                for (PlayerInfo playerInfo : playersList) {
                    playerInfo.printWriter.println(command + GameParams.COMMAND_DELIMITER);
                    playerInfo.printWriter.flush();

                    try {
                        Log.printText("Broadcast to " + playerInfo.player.getName() + ": " + command);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        executorService.execute(runnable);
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

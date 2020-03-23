package GhostFriend.Server;

import GhostFriend.Base.Game.Game;
import GhostFriend.Base.Player.Player;
import GhostFriend.Utils.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Collection;
import java.util.Iterator;

public class ClientControl implements Runnable {
    private final String PLAYER_INFO_DELIMITER = "/";

    private Socket socket;
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;
    private Game game;
    private Player player;

    @Override
    public void run() {
        Log.printText("Connection accepted");
        Boolean isConnected = true;

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (isConnected) {
            try {
                String commandParam = bufferedReader.readLine();

                if (commandParam.equals(GameParams.JOIN_GAME)) {
                    String playerName = bufferedReader.readLine();
                    player = game.addPlayer(playerName, printWriter);

                    if (player == null) {
                        sendText(GameParams.JOIN_FAIL);
                    } else {
                        sendText(GameParams.JOIN_SUCCESS);
                        broadcast(GameParams.JOIN_NEW_PLAYER);

                        if (game.isAllPlayersEntered()) {
                            broadcast(GameParams.ALL_PLAYERS_ENTERED);
                            game.distributeCards();
                            broadcast(GameParams.DISTRIBUTE_CARDS);
                        }
                    }
                }
                else if (commandParam.equals(GameParams.ASK_PLAYERS_INFO)) {
                    String playersInfo = game.getPlayersInfo(PLAYER_INFO_DELIMITER);
                    sendText(playersInfo);
                }
            }
            catch (SocketException e) {
                e.printStackTrace();
                game.removePlayer(player);
                broadcast(GameParams.EXIT_PLAYER);
                isConnected = false;
            }
            catch (IOException e) {
                e.printStackTrace();
                isConnected = false;
            }
        }

        Log.printText("Thread terminated");
    }

    private void sendText(String text) {
        printWriter.println(text);
        printWriter.flush();
    }

    private void broadcast(String text) {
        synchronized (game.getPlayers()) {
            Collection<PrintWriter> collection = game.getPlayers().values();
            Iterator<PrintWriter> iter = collection.iterator();

            while (iter.hasNext()) {
                PrintWriter pw = iter.next();
                pw.println(text);
                pw.flush();
            }
        }
    }

    public ClientControl(Socket socket, Game game) {
        this.socket = socket;
        this.game = game;
    }
}

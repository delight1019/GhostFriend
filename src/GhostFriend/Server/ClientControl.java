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

public class ClientControl implements Runnable {
    private Socket socket;
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;
    private Game game;
    private Player player;

    @Override
    public void run() {
        Log.printText("Connection accepted");
        boolean isConnected = true;

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
                    player = MainServer.getInstance().addPlayer(playerName, printWriter, bufferedReader);

                    if (player == null) {
                        sendText(GameParams.JOIN_FAIL);
                    } else {
                        sendText(GameParams.JOIN_SUCCESS);
                        MainServer.getInstance().broadcast(GameParams.JOIN_NEW_PLAYER);

                        if (game.isAllPlayersEntered()) {
                            MainServer.getInstance().broadcast(GameParams.ALL_PLAYERS_ENTERED);
                            game.startPlaying();
                        }
                    }
                }
                else if (commandParam.equals(GameParams.ASK_PLAYERS_INFO)) {
                    String playersInfo = game.getPlayersInfo(GameParams.PLAYER_INFO_DELIMITER);
                    sendText(playersInfo);
                }
            }
            catch (SocketException e) {
                e.printStackTrace();
                game.removePlayer(player);

                try {
                    MainServer.getInstance().broadcast(GameParams.EXIT_PLAYER);
                }

                catch (IOException e1) {
                    e1.printStackTrace();
                }

                isConnected = false;
            }
            catch (IOException e) {
                e.printStackTrace();
                isConnected = false;
            }
        }

        Log.printText("Thread terminated");
    }

    private void sendText(String text) throws IOException {
        printWriter.println(text);
        printWriter.flush();

        String response = bufferedReader.readLine();

        while (!response.equals(GameParams.COMPLETE_REQUEST)) {
            response = bufferedReader.readLine();
        }
    }

    public ClientControl(Socket socket, Game game) {
        this.socket = socket;
        this.game = game;
    }
}

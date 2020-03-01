package GhostFriend.Server;

import GhostFriend.Base.Game.Game;
import GhostFriend.Base.Player.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientControl implements Runnable {
    private final String PLAYER_INFO_DELIMITER = "/";

    private Socket socket = null;
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;
    private Game game = null;
    private Player player = null;

    @Override
    public void run() {
        System.out.println("Connection accepted");
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true) {
            try {
                String commandParam = bufferedReader.readLine();

                if (commandParam.equals(GameParams.JOIN_GAME)) {
                    String playerName = bufferedReader.readLine();
                    player = game.addPlayer(playerName);
                }
                else if (commandParam.equals(GameParams.ASK_PLAYERS_INFO)) {
                    String playersInfo = game.getPlayersInfo(PLAYER_INFO_DELIMITER);
                    sendText(playersInfo);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendText(String text) {
        printWriter.println(text);
    }

    public ClientControl(Socket socket, Game game) {
        this.socket = socket;
        this.game = game;
    }
}

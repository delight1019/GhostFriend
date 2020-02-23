package GhostFriend.Server;

import GhostFriend.Base.Game.Game;
import GhostFriend.Base.Player.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientControl implements Runnable {
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
                String playerName = bufferedReader.readLine();
                player = game.addPlayer(playerName);

                waitOtherPlayers();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void waitOtherPlayers() {
        while (true) {
            if (game.isAllPlayersEntered()) {

            } else {
                String playersInfo = game.getPlayersInfo();
                sendText(playersInfo);
            }
        }
    }

    private void sendText(String text) {
        byte[] dataToSend = text.getBytes();
        printWriter.println(dataToSend);
    }

    public ClientControl(Socket socket, Game game) {
        this.socket = socket;
        this.game = game;
    }
}

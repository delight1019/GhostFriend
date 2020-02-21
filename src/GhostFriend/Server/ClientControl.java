package GhostFriend.Server;

import GhostFriend.Base.Game.Game;
import GhostFriend.Base.Player.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientControl implements Runnable {
    private Socket socket = null;
    private Game game = null;
    private Player player = null;

    @Override
    public void run() {
        System.out.println("Connection accepted");
        // TODO: Game Playing

        while(true) {
            BufferedReader bufReader = null;
            try {
                bufReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String playerName = bufReader.readLine();
                player = game.addPlayer(playerName);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public ClientControl(Socket socket, Game game) {
        this.socket = socket;
        this.game = game;
    }
}

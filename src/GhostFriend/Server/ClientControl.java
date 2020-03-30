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
        try {
            Log.printText("Connection accepted");
        } catch (IOException e) {
            e.printStackTrace();
        }

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

                if (player != null) {
                    Log.printText("Receive from " + player.getName() + ": " + commandParam);
                }

                if (commandParam.equals(GameParams.JOIN_GAME)) {
                    String playerName = bufferedReader.readLine();
                    player = MainServer.getInstance().addPlayer(playerName, printWriter, bufferedReader);

                    if (player == null) {
                        sendCommand(GameParams.JOIN_FAIL, "");
                    } else {
                        MainServer.getInstance().broadcast(GameParams.JOIN_NEW_PLAYER, game.getPlayersInfo(GameParams.PLAYER_INFO_DELIMITER));

                        if (game.isAllPlayersEntered()) {
                            game.startPlaying();
                        }
                    }
                }
            }
            catch (SocketException e) {
                e.printStackTrace();
                game.removePlayer(player);

                try {
                    MainServer.getInstance().broadcast(GameParams.EXIT_PLAYER, game.getPlayersInfo(GameParams.PLAYER_INFO_DELIMITER));
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

        try {
            Log.printText("Thread terminated");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendCommand(String command, String data) throws IOException {
        printWriter.println(command + GameParams.DATA_DELIMITER + data + GameParams.COMMAND_DELIMITER);
        printWriter.flush();

        Log.printText("Send to " + player.getName() + ": " + command);
    }

    public ClientControl(Socket socket, Game game) {
        this.socket = socket;
        this.game = game;
    }
}

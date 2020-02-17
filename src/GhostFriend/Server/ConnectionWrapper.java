package GhostFriend.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ConnectionWrapper implements Runnable {
    private Socket socket = null;

    @Override
    public void run() {
        System.out.println("Connection accepted");
        // TODO: Game Playing

        while(true) {
            BufferedReader bufReader = null;
            try {
                bufReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String playerName = bufReader.readLine();
                System.out.println(playerName);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public ConnectionWrapper(Socket socket) {
        this.socket = socket;
    }
}

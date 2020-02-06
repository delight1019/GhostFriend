package GhostFriend.Server;

import java.net.Socket;

public class ConnectionWrapper implements Runnable {
    private Socket socket = null;

    @Override
    public void run() {
        // TODO: Game Playing
    }

    public ConnectionWrapper(Socket socket) {
        this.socket = socket;
    }
}

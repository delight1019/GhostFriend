package GhostFriend.Base;

import GhostFriend.Server.ServerWrapper;

public class Main {
    public static void main(String[] args) {

        ServerWrapper serverWrapper = new ServerWrapper();
        serverWrapper.startServer();
    }
}

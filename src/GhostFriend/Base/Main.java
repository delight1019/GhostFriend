package GhostFriend.Base;

import GhostFriend.Server.SocketServer;

public class Main {

    public static void main(String[] args) {
        SocketServer server = new SocketServer();
        server.start();

//        Game Game = new Game();
//        Game.StartPlaying(5);
//        Game.determineDeclarer();
//        Game.confirmDeclarerCards();
//        Game.determineFriend();
    }
}

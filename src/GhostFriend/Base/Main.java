package GhostFriend.Base;

import GhostFriend.Base.Game.Game;

public class Main {

    public static void main(String[] args) {
        Game Game = new Game();
        Game.StartPlaying(5);
        Game.determineDeclarer();
        Game.confirmDeclarerCards();
        Game.determineFriend();
    }
}

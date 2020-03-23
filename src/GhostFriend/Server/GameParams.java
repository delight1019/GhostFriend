package GhostFriend.Server;

public class GameParams {
    public static final String PLAYER_INFO_DELIMITER = "/";

    // Receive Parameters
    public static final String JOIN_GAME = "JoinGame";
    public static final String ASK_PLAYERS_INFO = "AskPlayersInfo";

    // Send Parameters
    public static final String JOIN_SUCCESS = "JoinSuccess";
    public static final String JOIN_FAIL = "JoinFail";
    public static final String JOIN_NEW_PLAYER = "JoinNewPlayer";
    public static final String EXIT_PLAYER = "ExitPlayer";
    public static final String ALL_PLAYERS_ENTERED = "AllPlayersJoin";
    public static final String DISTRIBUTE_CARDS = "DistributesCard";
}

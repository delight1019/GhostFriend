package GhostFriend.Server;

public class GameParams {
    public static final String DATA_DELIMITER = "/";
    public static final String COMMAND_DELIMITER = "-";
    public static final String COMMAND_DATA_DELIMITER = "_";

    // Receive Parameters
    public static final String JOIN_GAME = "JoinGame";
    public static final String REPLY_DEAL_MISS = "ReplyDealMiss";

    // Send Parameters
    public static final String JOIN_FAIL = "JoinFail";
    public static final String JOIN_NEW_PLAYER = "JoinNewPlayer";
    public static final String EXIT_PLAYER = "ExitPlayer";
    public static final String DISTRIBUTE_CARDS = "DistributesCard";
    public static final String CHECK_DEAL_MISS = "CheckDealMiss";
    public static final String RESTART_GAME = "RestartGame";
}

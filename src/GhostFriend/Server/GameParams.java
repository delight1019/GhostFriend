package GhostFriend.Server;

public class GameParams {
    public static final String DATA_DELIMITER = "/";
    public static final String COMMAND_DELIMITER = "-";
    public static final String COMMAND_DATA_DELIMITER = "_";

    // Parameters
    public static final String NO_CONTRACT = "NoContract";

    // Receive Commands
    public static final String JOIN_GAME = "JoinGame";
    public static final String REPLY_DEAL_MISS = "ReplyDealMiss";
    public static final String DECLARE_CONTRACT = "DeclareContract";
    public static final String PASS_CONTRACT_DECLERATION = "PassContractDecleration";

    // Send Commands
    public static final String JOIN_FAIL = "JoinFail";
    public static final String JOIN_NEW_PLAYER = "JoinNewPlayer";
    public static final String EXIT_PLAYER = "ExitPlayer";
    public static final String DISTRIBUTE_CARDS = "DistributesCard";
    public static final String CHECK_DEAL_MISS = "CheckDealMiss";
    public static final String RESTART_GAME = "RestartGame";
    public static final String ASK_CONTRACT = "AskContract";
    public static final String OTHER_PLAYER_ASKING_CONTRACT = "OtherPlayerAskingContract";
    public static final String CASTER_DECLARED = "CasterDeclared";
    public static final String START_DECLARER_CARD_SELECTION = "StartDeclarerCardSelection";
    public static final String SELECT_CARDS_TO_DISCARD = "SelectCardsToDiscard";
}

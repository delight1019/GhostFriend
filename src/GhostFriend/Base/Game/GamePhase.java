package GhostFriend.Base.Game;

public enum GamePhase {
    JOIN_GAME,
    DISTRIBUTE_CARD,
    DECLARE_CONTRACT,
    DECLARATOR_DISCARD_CARD,
    CHANGE_GIRU,
    SELECT_FRIEND,
    PLAY_GAME,
    END_GAME,
    INVALID;

    public static Integer toInteger(GamePhase gamePhase) {
        switch (gamePhase) {
            case JOIN_GAME:
                return 0;
            case DISTRIBUTE_CARD:
                return 1;
            case DECLARE_CONTRACT:
                return 2;
            case DECLARATOR_DISCARD_CARD:
                return 3;
            case CHANGE_GIRU:
                return 4;
            case SELECT_FRIEND:
                return 5;
            case PLAY_GAME:
                return 6;
            case END_GAME:
                return 7;
            case INVALID:
                return -1;
        }

        return -1;
    }

    public static String toString(GamePhase gamePhase) {
        return toInteger(gamePhase).toString();
    }
}

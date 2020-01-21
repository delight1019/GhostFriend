package GhostFriend.Base.Card;

public enum CardSuit {
    DIAMOND, SPADE, CLUB, HEART, JOKER;

    public static CardSuit ConvertString(String input) {
        if (input.toUpperCase().equals("DIAMOND")) {
            return DIAMOND;
        }
        if (input.toUpperCase().equals("SPADE")) {
            return SPADE;
        }
        if (input.toUpperCase().equals("CLUB")) {
            return CLUB;
        }
        if (input.toUpperCase().equals("HEART")) {
            return HEART;
        }
        if (input.toUpperCase().equals("JOKER")) {
            return JOKER;
        }

        return null;
    }
}

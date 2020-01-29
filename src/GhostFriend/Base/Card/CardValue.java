package GhostFriend.Base.Card;

public enum CardValue {
    ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, JOKER;

    public static CardValue convertString(String input) {
        if (input.toUpperCase().equals("ACE")) {
            return ACE;
        }
        if (input.toUpperCase().equals("TWO")) {
            return TWO;
        }
        if (input.toUpperCase().equals("THREE")) {
            return THREE;
        }
        if (input.toUpperCase().equals("FOUR")) {
            return FOUR;
        }
        if (input.toUpperCase().equals("FIVE")) {
            return FIVE;
        }
        if (input.toUpperCase().equals("SIX")) {
            return SIX;
        }
        if (input.toUpperCase().equals("SEVEN")) {
            return SEVEN;
        }
        if (input.toUpperCase().equals("EIGHT")) {
            return EIGHT;
        }
        if (input.toUpperCase().equals("NINE")) {
            return NINE;
        }
        if (input.toUpperCase().equals("TEN")) {
            return TEN;
        }
        if (input.toUpperCase().equals("JACK")) {
            return JACK;
        }
        if (input.toUpperCase().equals("QUEEN")) {
            return QUEEN;
        }
        if (input.toUpperCase().equals("KING")) {
            return KING;
        }

        return null;
    }
}

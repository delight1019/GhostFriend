package GhostFriend.Base.Rule;

import GhostFriend.Base.Card.Card;

public class Rule {
    private Integer getScore(Card card) {
        if (card.IsScoreCard()) {
            return 1;
        } else {
            return 0;
        }
    }

}

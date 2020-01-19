package GhostFriend.Base.Rule;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RuleTest {
    private Rule rule = new Rule();

    @Test
    void getNumOfCardsPerPerson() {
        assertEquals(10, Rule.getNumOfCardsPerPerson());
    }
}
package GhostFriend.Base.IOController;

import GhostFriend.Base.Card.Card;
import GhostFriend.Base.Player.Player;

public class IOController {
    public static void startGame() {
        System.out.println("=========== 게임을 시작합니다 ===========");
    }
    public static void joinPlayer(String name) {
        System.out.println(name + "이(가) 게임에 참가하셨습니다.");
    }
    public static void handCardsOut() {
        System.out.println("카드를 나누겠습니다.");
    }
    public static void checkCards(Player player) {
        System.out.println(player.getName() + " 카드를 체크하세요.");

        for (Card card: player.getCardList()) {
            System.out.println(card.toString());
        }
    }
}

package GhostFriend.Base.IOController;

import GhostFriend.Base.Card.Card;
import GhostFriend.Base.Card.CardSuit;
import GhostFriend.Base.Player.Player;
import GhostFriend.Base.Rule.Contract;

import java.util.Scanner;

public class IOController {
    public static Scanner scanner = new Scanner(System.in);

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
    public static void askBidding(Player player, Contract contract) {
        System.out.println("현재 공약은 " + contract.toString());
        System.out.println(player.getName() + " 공약을 선언하시겠습니까?");
        System.out.println("기루 숫자 / PASS 형식으로 입력하세요. (ex. SPADE 5)");
    }
    public static Contract parseContract(String userInput) {
        String[] contractArray = userInput.split(" ");
        Contract contract = new Contract();
        contract.declare(CardSuit.ConvertString(contractArray[0]), Integer.parseInt(contractArray[1]));
        return contract;
    }
}

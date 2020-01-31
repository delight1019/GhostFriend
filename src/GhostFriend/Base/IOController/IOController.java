package GhostFriend.Base.IOController;

import GhostFriend.Base.Card.Card;
import GhostFriend.Base.Card.CardSuit;
import GhostFriend.Base.Card.CardValue;
import GhostFriend.Base.Player.Player;
import GhostFriend.Base.Rule.Contract;
import GhostFriend.Base.Rule.ContractValidation;

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
    public static String askBidding(Player player, Contract contract) {
        System.out.println("현재 공약은 " + contract.toString());
        System.out.println(player.getName() + " 공약을 선언하시겠습니까?");
        System.out.println("기루 숫자 / PASS 형식으로 입력하세요. (ex. SPADE 14 / No 18)");

        String userInput = scanner.nextLine();

        while (!isValidBidding(userInput)) {
            System.out.println("입력 형식이 올바르지 않습니다.");
            System.out.println("기루 숫자 / PASS 형식으로 입력하세요. (ex. SPADE 14 / No 18)");
            userInput = scanner.nextLine();
        }

        return userInput;
    }

    private static Boolean isValidBidding(String userInput) {
        if (userInput.toUpperCase().equals("PASS")) {
            return true;
        } else {
            String[] contractArray = userInput.split(" ");

            if (contractArray.length != 2) {
                return false;
            }

            if ((CardSuit.convertString(contractArray[0]) == null) && (!contractArray[0].toUpperCase().equals("NO"))) {
                return false;
            }

            try {
                Integer.parseInt(contractArray[1]);
            } catch (NumberFormatException e) {
                return false;
            }
        }

        return true;
    }

    public static Contract parseContract(String userInput) {
        String[] contractArray = userInput.split(" ");
        Contract contract = new Contract();

        if (contractArray[0].toUpperCase().equals("NO")) {
            contract.declare(null, Integer.parseInt(contractArray[1]));
        } else {
            contract.declare(CardSuit.convertString(contractArray[0]), Integer.parseInt(contractArray[1]));
        }

        return contract;
    }
    public static void determineDeclarer(Player player) {
        System.out.println("주공은 " + player.getName() + "입니다.");
        System.out.println("기루는 " + player.getContract().getGiru().toString() + ", " + "목표 점수는 " + player.getContract().getScore().toString() + "입니다.");
    }
    public static void invalidContract(ContractValidation contractValidation, Integer minContractScore) {
        if (contractValidation == ContractValidation.GIRU) {
            System.out.println("조커는 기루로 선언할 수 없습니다.");
        }
        else if (contractValidation == ContractValidation.MINIMUM) {
            System.out.println("현재 선언할 수 있는 최소 점수는 " + minContractScore.toString() + "입니다.");
        }
        else if (contractValidation == ContractValidation.SCORE) {
            System.out.println("현재 선언된 공약보다 큰 점수를 선언하여야합니다.");
        }
    }
    public static void printCurrentMinContractScore(Integer minContractScore) {
        System.out.println("현재 선언할 수 있는 최소 점수는 " + minContractScore.toString() + "입니다.");
    }
    public static Boolean askDealMiss(Player player) {
        System.out.println(player.getName() + " 딜미스를 선언하시겠습니까? (Yes/No)");
        String userInput = "";

        while (!(userInput.toUpperCase().equals("YES") || userInput.toUpperCase().equals("NO"))) {
            userInput = scanner.nextLine();
        }

        return (userInput.toUpperCase().equals("YES"));
    }
    public static void confirmDeclarerCards(Player declarer) {
        System.out.println(declarer.getName() + " 카드 3장을 확인하고 버릴 3장을 선택하세요.");
    }
    public static Card askDiscardingCard(Player declarer) {
        System.out.println(declarer.getName() + " 버릴 카드를 입력하세요. (ex. SPADE ACE)");
        String userInput = scanner.nextLine();

        while (!isValidCard(userInput)) {
            System.out.println("입력 형식이 올바르지 않습니다.");
            System.out.println("버릴 카드를 입력하세요. (ex. SPADE ACE)");
            userInput = scanner.nextLine();
        }

        if (userInput.toUpperCase().equals("JOKER")) {
            return new Card(CardSuit.JOKER, CardValue.JOKER);
        }

        String[] cardArray = userInput.split(" ");

        return new Card(CardSuit.convertString(cardArray[0]), CardValue.convertString(cardArray[1]));
    }
    private static Boolean isValidCard(String userInput) {
        if (userInput.toUpperCase().equals("JOKER")) {
            return true;
        }

        String[] cardArray = userInput.split(" ");

        if (cardArray.length != 2) {
            return false;
        }

        if (CardSuit.convertString(cardArray[0]) == null) {
            return false;
        }

        if (CardValue.convertString(cardArray[1]) == null) {
            return false;
        }

        return true;
    }
    public static void doNotHaveCard(Card card) {
        System.out.println(card.toString() + " 카드가 패에 존재하지 않습니다.");
    }
    public static Card askFriendCard(Player declarer, Card mighty, Card jokerCall) {
        System.out.println(declarer.getName() + " 친구가 될 카드를 선택하세요. (ex. DIAMOND ACE / Mighty / Joker Call)");
        String userInput = scanner.nextLine();

        while (!isValidFriendCard(userInput)) {
            System.out.println("입력 형식이 올바르지 않습니다.");
            System.out.println("친구가 될 카드를 선택하세요. (ex. DIAMOND ACE / Mighty / Joker Call)");
            userInput = scanner.nextLine();
        }

        if (userInput.toUpperCase().equals("MIGHTY")) {
            return mighty;
        }

        if (userInput.toUpperCase().equals("JOKER")) {
            return new Card(CardSuit.JOKER, CardValue.JOKER);
        }

        if (userInput.toUpperCase().equals("JOKER CALL")) {
            return jokerCall;
        }

        String[] cardArray = userInput.split(" ");

        return new Card(CardSuit.convertString(cardArray[0]), CardValue.convertString(cardArray[1]));
    }
    private static Boolean isValidFriendCard(String userInput) {
        if (userInput.toUpperCase().equals("MIGHTY")) {
            return true;
        }

        if (userInput.toUpperCase().equals("JOKER CALL")) {
            return true;
        }

        return isValidCard(userInput);
    }
}

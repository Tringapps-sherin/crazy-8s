package cardgame;

import java.util.*;

import cardgame.Card.Rank;

public class Play {
    public static void main(String[] args) {
        Play o = new Play();
        Player1 dharshan = new Player1();
        Player2 sherin = new Player2();
        List<Card> deck = new ArrayList<>();
        deck = Card.getDeck();
        Collections.shuffle(deck);
        deck = o.start(deck, dharshan, sherin);
        o.play(deck, dharshan, sherin);
    }

    List<Card> start(List<Card> deck, Player1 dharshan, Player2 sherin) {
        int i;
        List<Card> player1 = new ArrayList<>();
        List<Card> player2 = new ArrayList<>();
        for (i = 0; i < 14; i++) {
            if (i % 2 == 0) {
                player2.add(deck.get(0));
            } else {
                player1.add(deck.get(0));
            }
            deck.remove(0);
        }
        System.out.println();
        System.out.println("Card of Player1: ");
        for (i = 0; i < player1.size(); i++) {
            System.out.println(player1.get(i).getRank() + " " + player1.get(i).getSuit() + " ");
        }
        System.out.println("Card of Player2: ");
        for (i = 0; i < player2.size(); i++) {
            System.out.println(player2.get(i).getRank() + " " + player2.get(i).getSuit() + " ");
        }
        System.out.println();
        dharshan.receiveInitialCards(player1);
        sherin.receiveInitialCards(player2);
        return deck;
    }

    void play(List<Card> deck, Player1 dharshan, Player2 sherin) {
        Play o = new Play();
        int score1 = 0, i, score2 = 0;
        Card topCard;
        topCard = deck.get(0);
        deck.remove(0);
        System.out.println("Top Card : " + topCard.getRank() + " " + topCard.getSuit());
        Card.Suit decCard = null;
        while (score1 < 200 && score2 < 200) {
            for (i = 0; i < 3; i++) {
                if (sherin.shouldDrawCard(topCard, decCard)) {
                    if (deck.size() != 0) {
                        sherin.receiveCard(deck.get(0));
                        deck.remove(0);
                    }
                } else {
                    topCard = sherin.playCard();
                    System.out.println("Top Card : " + topCard.getRank() + " " + topCard.getSuit());
                    if (topCard.getRank() == Rank.EIGHT && sherin.myCards.size() != 0) {
                        decCard = sherin.declareSuit();
                    }
                    break;
                }
            }
            for (i = 0; i < 3; i++) {
                if (dharshan.shouldDrawCard(topCard, decCard)) {
                    if (deck.size() != 0) {
                        dharshan.receiveCard(deck.get(0));
                        deck.remove(0);
                    }
                } else {
                    topCard = dharshan.playCard();
                    System.out.println("Top Card : " + topCard.getRank() + " " + topCard.getSuit());
                    if (topCard.getRank() == Rank.EIGHT && sherin.myCards.size() != 0) {
                        decCard = dharshan.declareSuit();
                    }
                    break;
                }
            }
            if (dharshan.myCards.size() == 0 || deck.size() == 0) {
                score2 += sherin.getScore();
                System.out.println("Score of Player 2 : " + score2);
            }
            if (sherin.myCards.size() == 0 || deck.size() == 0) {
                score1 += dharshan.getScore();
                System.out.println("Score of Player 2 : " + score1);
            }
            if (deck.size() == 0 && score1 < 200 && score2 < 200) {
                deck = Card.getDeck();
                Collections.shuffle(deck);
                deck = o.start(deck, dharshan, sherin);
            }
        }
        o.results(score1, score2);
    }

    void results(int s1, int s2) {
        if (s1 >= 200) {
            System.out.println("Winnner is sherin");
        } else {
            System.out.println("Winnner is Dharshan");
        }
    }
}
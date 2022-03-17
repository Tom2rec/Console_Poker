package com.agh.player;

import com.agh.game.handRanking.HandRanking;
import com.agh.game.util.card.Card;

import java.util.*;

public class Player {

    private String name;
    private final int id;
    private List<Card> cards = new ArrayList<>();
    private HandRanking handRanking;
    private boolean inGame = false;
    private int bet = 0;

    public Player(String name, int id){
        this.name = name;
        this.id = id;
    }


    public void setCards(List<Card> cards) {
        this.cards = cards;
        getCardsSortedByRankSuit();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public List<Card> getCardsSortedBySuitRank() {
        Collections.sort(cards, Comparator.comparing(Card::getSuit)
                .thenComparing(Card::getRank));
        return cards;
    }

    public List<Card> getCardsSortedByRankSuit() {
        Collections.sort(cards, Comparator.comparing(Card::getRank)
                .thenComparing(Card::getSuit));
        return cards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var player = (Player) o;
        return Objects.equals(name, player.name) && Objects.equals(cards, player.cards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cards);
    }

    public void removeCards(List<Integer> cardsToRemove) {
        Collections.sort(cardsToRemove, Collections.reverseOrder());

        for(int card : cardsToRemove){
            cards.remove(card);
        }


    }

    public void payAnte() {
        bet += 5;
    }

    public void addBet(int money){
        bet += money;
    }

    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    public int getBet() {
        return bet;
    }

    public int getId() {
        return id;
    }

    public String getHandRankingName() {
        return handRanking.name;
    }

    public void setHandRanking(HandRanking handRanking) {
        this.handRanking = handRanking;
    }
}

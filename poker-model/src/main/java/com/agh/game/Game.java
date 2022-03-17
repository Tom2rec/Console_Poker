package com.agh.game;

import com.agh.game.handRanking.*;
import com.agh.game.util.Deck;
import com.agh.game.util.card.Card;
import com.agh.game.util.card.Rank;
import com.agh.player.Player;

import java.util.*;

public class Game {
    private Map<Integer, Player> players;
    private Deck deck;
    private int currentBet = 5;
    private Player currentPlayer;
    private Iterator<Map.Entry<Integer, Player>> it;
    private int lastMoves;

    private boolean isGameActive = false;

    public boolean isGameActive() {
        return isGameActive;
    }

    public void setGameActive(boolean gameActive) {
        isGameActive = gameActive;
    }

    public synchronized void setNextPlayer() {
        if(!it.hasNext()){
            it = players.entrySet().iterator();
        }
        currentPlayer = it.next().getValue();
    }

    public synchronized Player getCurrentPlayer() {
        if (currentPlayer == null) {
            it = players.entrySet().iterator();
            currentPlayer = it.next().getValue();
        }
        return currentPlayer;
    }

    public void arrangeDeck(){
        this.deck = new Deck();
    }

    public Game() {
        players = new LinkedHashMap<>();
    }

    public synchronized void start() {
        currentPlayer = null;
        setCards();
        getAnte();
        lastMoves = getPlayers().size();
    }

    private void getAnte() {
        for (Player player : players.values()) {
            player.payAnte();
        }
    }

    public synchronized void fold(int playerId) {
        players.get(playerId).setHandRanking(HandRanking.RESIGNED);
        players.get(playerId).setInGame(false);
    }

    public int getCurrentBet() {
        return currentBet;
    }

    public void call(int playerId) {
        players.get(playerId).addBet(currentBet);
    }

    public void raise(int playerId, int money) {
        currentBet = money;
        players.get(playerId).addBet(money);
    }

    public void setCards() {
        deck.shuffle();
        for (Player player : players.values()) {
            player.setCards(deck.takeNCard(5));
        }
    }

    public synchronized void changeCards(List<Integer> cardsToRemove, int playerId) {
        var player = players.get(playerId);
        player.removeCards(cardsToRemove);
        List<Card> changedCards = deck.takeNCard(cardsToRemove.size());
        changedCards.addAll(player.getCards());
        player.setCards(changedCards);
    }

    public List<Integer> getWinners() {
        List<List<Integer>> results = new ArrayList<>();

        for (int p : players.keySet()) {
            if (players.get(p).isInGame()) {
                results.add(checkHand(p));
            }
        }

        Comparator<List<Integer>> comparing = Comparator.comparing(l -> l.get(1));
        comparing = comparing.thenComparing(l -> l.get(2));
        Collections.sort(results, Collections.reverseOrder(comparing));

        List<Integer> winners = new ArrayList<>();
        List<Integer> first = results.get(0);
        for (List<Integer> result : results) {
            if (!Objects.equals(result.get(1), first.get(1)) || !Objects.equals(result.get(2), first.get(2))) {
                break;
            }
            winners.add(result.get(0));
        }
        return winners;
    }


    public List<Integer> checkHand(int playerId) {

        List<CheckKindOfHandRating> handRatings = new ArrayList<>();

        handRatings.add(new CheckRoyalFlush());
        handRatings.add(new CheckStraightFlush());
        handRatings.add(new CheckQuads());
        handRatings.add(new CheckFullHouse());
        handRatings.add(new CheckFlush());
        handRatings.add(new CheckStraight());
        handRatings.add(new CheckThreeOfKind());
        handRatings.add(new CheckTwoPair());
        handRatings.add(new CheckOnePair());
        handRatings.add(new CheckHighCard());

        var highestRank = Rank.TWO;
        var type = 0;

        for (CheckKindOfHandRating handRating : handRatings) {
            if (handRating.check(players.get(playerId))) {
                highestRank = handRating.getHeighestRank();
                type = handRating.getRate();
                players.get(playerId).setHandRanking(handRating.getHandRanking());
                break;
            }
        }
        return Arrays.asList(playerId, type, highestRank.getValue());
    }

    public Map<Integer, Player> getPlayers() {
        return players;
    }

    public void addPlayer(int id, Player player) {
        players.put(id, player);
    }


    public boolean isAnyoneToMakeLastMove() {
       return lastMoves > 0;
    }


    public void madeLastMove() {
        --lastMoves;
    }
}

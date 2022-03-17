package com.agh;

import com.agh.game.Game;
import com.agh.game.util.card.Card;
import com.agh.player.Player;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public class ClientHandler extends Thread {
    private static final Logger logger = Logger.getLogger(ClientHandler.class);
    private final DataInputStream dataInputStream;
    private final DataOutputStream dataOutputStream;
    private final Socket socket;
    private final Game game;
    private final int gameId;
    private final int clientId;
    private Player player;

    public ClientHandler(Socket socket, DataInputStream dataInputStream, DataOutputStream dataOutputStream, Game game,
                         int gameId, int clientId) {
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
        this.socket = socket;
        this.game = game;
        this.gameId = gameId;
        this.clientId = clientId;
    }


    @Override
    public void run() {
        BasicConfigurator.configure();
        try {
            applicationStart();
        } catch (IOException | InterruptedException e) {
            logger.info("Client ended connection");
            Thread.currentThread().interrupt();
        }
    }

    private void applicationStart() throws IOException, InterruptedException {
        String[] received;
        do {
            dataOutputStream.writeUTF(MessageConstants.GAME_NAME + MessageConstants.GAME_MENU);

            received = getMove(dataInputStream.readUTF());
            while (!received[2].equals("x") && !received[2].equals("0")) {
                dataOutputStream.writeUTF(MessageConstants.ERROR);
                dataOutputStream.writeUTF(MessageConstants.GAME_NAME + MessageConstants.GAME_MENU);
                received = getMove(dataInputStream.readUTF());
            }


            switch (received[2]) {
                case "0" -> {

                    synchronized (game) {
                        if (null == player) {
                            dataOutputStream.writeUTF(MessageConstants.GAME_NAME + MessageConstants.INTRO);
                            createUser(getMove(dataInputStream.readUTF())[2]);
                        } else {
                            dataOutputStream.writeUTF(MessageConstants.GAME_NAME + MessageConstants.ANOTHER_INTRO);
                            String nick = dataInputStream.readUTF();
                            if (!nick.equals("")) {
                                player.setName(getMove(nick)[2]);
                            }
                            game.addPlayer(clientId, player);
                        }
                        player.setInGame(true);
                        game.notifyAll();

                        while(game.getPlayers().size()>4){
                            game.wait(5000);
                        }

                        while (game.getPlayers().size() < 2) {
                            System.out.println(game.getPlayers().size());
                            game.wait(5000);
                        }

                        if (game.getPlayers().size() < 3) {
                            System.out.println(game.getPlayers().size());
                            game.wait(5000);
                        }

                        if (game.getPlayers().size() < 4) {
                            System.out.println(game.getPlayers().size());
                            game.wait(5000);
                        }

                        if (!game.isGameActive()) {
                            game.arrangeDeck();
                            game.start();
                        }
                    }
                    dataOutputStream.writeUTF("" + gameId);
                    startGame();
                }
                case "x" -> {
                    game.getPlayers().remove(clientId);
                    dataOutputStream.writeUTF(MessageConstants.END);
                    logger.info("Client " + this.socket + " sends exit...");
                    System.out.println("Closing this connection.");
                    this.socket.close();
                    logger.info("Connection closed");
                }
            }
        } while (!received[2].equals("x"));

    }

    private void createUser(String username) {
        player = new Player(username, clientId);
        game.addPlayer(clientId, player);
    }

    private void startGame() throws IOException {

        auction();

        String[] received;
        if(player.isInGame()) {
            dataOutputStream.writeUTF(MessageConstants.GAME_NAME + MessageConstants.CARDS +
                    showCards(game.getPlayers().get(clientId).getCards()));
        }
        synchronized (game) {
            while (!game.getCurrentPlayer().equals(player)) {
                try {
                    game.wait();
                } catch (InterruptedException e) {
                    currentThread().interrupt();
                }
            }

            if(player.isInGame()){
                dataOutputStream.writeUTF(MessageConstants.CHOOSE_CARDS);
                received = getMove(dataInputStream.readUTF());

                List<Integer> cardNumbers = parseCardNumbers(received[3]);
                game.changeCards(cardNumbers, clientId);
                dataOutputStream.writeUTF(MessageConstants.SUCCESSFUL);
            }
            game.setNextPlayer();
            game.notifyAll();
        }

        auction();

        game.madeLastMove();

        synchronized (game) {
            while (game.isAnyoneToMakeLastMove()) {
                try {
                    game.wait();
                } catch (InterruptedException e) {
                    currentThread().interrupt();
                }
            }
        }

        getOtherPlayersCards();
        getResult();
    }

    private void getOtherPlayersCards() throws IOException {
        var result = new StringBuilder();
        List<Integer> winners = game.getWinners();
        for (var player : game.getPlayers().values()) {
            if (winners.contains(player.getId())) {
                result.append("WINNER - ");
            }
            result.append(player.getName())
                    .append(" - ")
                    .append(player.getHandRankingName())
                    .append(":\n")
                    .append(showCards(player.getCards()))
                    .append("\n");
        }
        dataOutputStream.writeUTF(result.toString());
    }

    private void getResult() throws IOException {
        List<Integer> winners = game.getWinners();
        if (winners.contains(clientId)) {
            dataOutputStream.writeUTF(MessageConstants.WON + game.getPlayers().get(clientId).getBet() + "$");
        } else {
            dataOutputStream.writeUTF(MessageConstants.LOST + game.getPlayers().get(clientId).getBet() + "$");
        }
    }

    private void auction() throws IOException {
        if(player.isInGame()) {
            dataOutputStream.writeUTF(MessageConstants.GAME_NAME + MessageConstants.CARDS + showCards(game.getPlayers().get(clientId).getCards()));
        }
        synchronized (game) {
            while (!game.getCurrentPlayer().equals(player)) {
                try {
                    game.wait();
                } catch (InterruptedException e) {
                    currentThread().interrupt();
                }
            }
            if(player.isInGame()) {

                if (game.isAnyoneToMakeLastMove())
                    dataOutputStream.writeUTF(MessageConstants.POKER_MENU + MessageConstants.CURRENT_BET + game.getCurrentBet());
                String[] received = getMove(dataInputStream.readUTF());
                while (!received[2].equals("0") && !received[2].equals("1") && !received[2].equals("2")) {
                    dataOutputStream.writeUTF(MessageConstants.ERROR);
                    dataOutputStream.writeUTF(MessageConstants.POKER_MENU + MessageConstants.CURRENT_BET + game.getCurrentBet());
                    received = getMove(dataInputStream.readUTF());

                }

                switch (received[2]) {
                    case "0" -> {
                        game.fold(clientId);
                        dataOutputStream.writeUTF(MessageConstants.SUCCESSFUL + MessageConstants.RESIGNED);
                    }
                    case "1" -> {
                        game.call(clientId);
                        dataOutputStream.writeUTF(MessageConstants.SUCCESSFUL);
                    }
                    case "2" -> {
                        var bet = Integer.parseInt(received[3]);
                        while (bet <= game.getCurrentBet()) {
                            dataOutputStream.writeUTF("Invalid input");
                            received = getMove(dataInputStream.readUTF());
                            bet = Integer.parseInt(received[3]);
                        }
                        game.raise(clientId, bet);
                        dataOutputStream.writeUTF(MessageConstants.SUCCESSFUL);
                    }
                }
            }
            game.setNextPlayer();
            game.notifyAll();
        }
    }

    private String showCards(List<Card> cards) {
        var result = new StringBuilder();
        var i = 0;
        for (var card : cards) {
            result.append("[").append(i).append("]").append(card.getRank()).append(" ").append(card.getSuit()).append("\n");
            ++i;
        }
        return result.toString();
    }

    private List<Integer> parseCardNumbers(String cards) {
        int cardsNumber = cards.replaceAll("\\D", "").length();
        Integer[] numbers = new Integer[cardsNumber];
        String[] split = cards.split(",");
        for (var i = 0; i < cardsNumber; i++) {
            numbers[i] = Integer.parseInt(split[i]);
        }
        return Arrays.asList(numbers);
    }

    private String[] getMove(String protocol) {
        return protocol.split(":");

    }
}

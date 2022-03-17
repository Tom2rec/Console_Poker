package com.agh;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {
    private static final String WAIT = "wait...";
    private static String gameId;
    private String name;
    private static int id;

    public Client(String name) {
        this.name = name;
    }


    public static void main(String[] args) throws IOException {
        var ip = InetAddress.getByName("localhost");
        try (var s = new Socket(ip, 5056);){
            var scanner = new Scanner(System.in);

            var dataInputStream = new DataInputStream(s.getInputStream());
            var dataOutputStream = new DataOutputStream(s.getOutputStream());

            while (true) {
                var received = "";
                String toSend;

                do {
                    System.out.println(dataInputStream.readUTF());
                    toSend = scanner.nextLine();
                    dataOutputStream.writeUTF(protocolize(toSend, ""));
                    received = dataInputStream.readUTF();
                } while (received.equals("\nWRONG MOVE\n"));

                if (toSend.equals("x")) {
                    System.out.println(received);
                    System.out.println("Connection closed");
                    break;
                }

                System.out.println(received);

                // user name
                toSend = scanner.nextLine();
                dataOutputStream.writeUTF(protocolize(toSend, ""));

                // game id
                received = dataInputStream.readUTF();
                gameId = received;

                // cards
                received = dataInputStream.readUTF();
                System.out.println(received);
                System.out.println(WAIT);

                if(auction(scanner, dataInputStream, dataOutputStream)){
                    String parameters;

                    // changing cards
                    received = dataInputStream.readUTF();
                    System.out.println(received);

                    System.out.println(WAIT);

                    received = dataInputStream.readUTF();
                    System.out.println(received);
                    toSend = "0";
                    parameters = scanner.nextLine();

                    while (areNotAppropriate(parameters)) {
                        System.out.println(received + "\n CHOOSE APPROPRIATE CARDS");
                        parameters = scanner.nextLine();
                    }

                    dataOutputStream.writeUTF(protocolize(toSend, parameters));

                    received = dataInputStream.readUTF();
                    System.out.println(received);

                    System.out.println(WAIT);
                    received = dataInputStream.readUTF();
                    System.out.println(received);
                    auction(scanner, dataInputStream, dataOutputStream);
                }

                //cards
                received = dataInputStream.readUTF();
                System.out.println(received);

                //results
                received = dataInputStream.readUTF();
                System.out.println(received);

            }
            scanner.close();
            dataInputStream.close();
            dataOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean areNotAppropriate(String parameters) {
        Pattern pattern = Pattern.compile("^([0-4]\\,){0,5}[0-4]$");
        Matcher matcher = pattern.matcher(parameters);
        boolean matchFound = matcher.find();
        if(!matchFound){
            return true;
        }

        int cardsNumber = parameters.replaceAll("\\D", "").length();
        Integer[] numbers = new Integer[cardsNumber];
        String[] split = parameters.split(",");
        for (int i = 0; i < cardsNumber; i++) {
            numbers[i] = Integer.parseInt(split[i]);
        }

        Set<Integer> numbersOfCards = new HashSet<>(Arrays.asList(numbers));
        if(numbersOfCards.size() < cardsNumber){
            return true;
        }
        for(var number : numbersOfCards){
            if(number > 4 || number < 0){
                return true;
            }
        }
        return false;
    }

    private static boolean auction(Scanner scanner, DataInputStream dataInputStream, DataOutputStream dataOutputStream) throws IOException {
        String received;
        String toSend;
        String parameters = "";
        do {
            received = dataInputStream.readUTF();
            System.out.println(received);

            toSend = scanner.nextLine();
            if (toSend.equals("2")) {
                System.out.println("How much do you want to bet: ");
                parameters = scanner.nextLine();
            }


            dataOutputStream.writeUTF(protocolize(toSend, parameters));
            received = dataInputStream.readUTF();
        } while (received.equals("\nWRONG MOVE\n"));

        while (received.equals("Invalid input")) {
            System.out.println("Bet should be bigger than current!\nHow much do you want to bet : ");
            parameters = scanner.nextLine();
            dataOutputStream.writeUTF(protocolize("2", parameters));
            received = dataInputStream.readUTF();
        }
        System.out.println(received);
        return !toSend.equals("0");

    }

    private static String protocolize(String move, String parameters) {
        return gameId + ":" + id + ":" + move + ":" + parameters;
    }
}

package com.agh;


import com.agh.game.Game;
import org.apache.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final Logger logger = Logger.getLogger(Server.class);
    private static int CURRENT_CLIENT_ID = 0;
    private static int CURRENT_GAME_ID = 0;
    private static final Game game = new Game();

    public static void main(String[] args){
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(5056);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        while(true){
            Socket serverSocketAccept;

            try {
                serverSocketAccept = serverSocket.accept();
                System.out.println("A new client is connected : " + serverSocketAccept);
                var dataInputStream = new DataInputStream(serverSocketAccept.getInputStream());
                var dataOutputStream = new DataOutputStream(serverSocketAccept.getOutputStream());

                System.out.println("Assigning new thread for this client");
                synchronized (game){
                    if(!game.isGameActive() && game.getPlayers().size() < 4){
                        Thread clientThread = new ClientHandler(serverSocketAccept, dataInputStream, dataOutputStream, game, CURRENT_GAME_ID, getNextId());
                        clientThread.start();
                    } else {
                        System.out.println("Game started without you");
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private static int getNextId() {
        return ++CURRENT_CLIENT_ID;
    }


}

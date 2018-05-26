package impl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerMain {
    public static Socket socket;
    private static int counter=0;
    public List<ClientHandler> clientList = new ArrayList<>();

    public static List<ClientHandler> clientHandlerList = new ArrayList<>();

    public static void setClientHandlerList(ClientHandler clientHandler) {
        ServerMain.clientHandlerList.add(clientHandler);
    }

    public  void runTheChat() throws IOException {

        try (ServerSocket server = new ServerSocket(7000);) {

            while (true) {
                socket = server.accept();
                ClientHandler clientHandler = new ClientHandler(counter++, this, socket);
                clientList.add(clientHandler);
                clientHandler.start();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new ServerMain().runTheChat();
    }
}

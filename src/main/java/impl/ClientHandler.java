package impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ClientHandler extends Thread{
    private int id;
    private ServerMain server;
    private Socket socket;
    private Thread thread;
    PrintStream outputStream;


    public ClientHandler(int id, ServerMain serverMain, Socket socket) {
        this.id = id;
        this.server = serverMain;
        this.socket = socket;
    }

    public void run() {
        System.out.println("Server is up.");
        String line = "";

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outputStream = new PrintStream(
                    socket.getOutputStream());
            String msg = "";
            ;
            while (!line.equals("!")) {
                msg = bufferedReader.readLine();
                System.out.println(msg);
                msg=msg + " Json from server";
                outputStream.println(msg);

            }

        } catch (Exception e) {

        }
    }
}

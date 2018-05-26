package server;

import impl.PuzzleSolution;

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
    PuzzleSolution puzzleSolution;


    public ClientHandler(int id, ServerMain serverMain, Socket socket) {
        this.id = id;
        this.server = serverMain;
        this.socket = socket;
    }

    public void run() {
        System.out.println("Server is up.");
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            puzzleSolution = new PuzzleSolution(false);
            outputStream = new PrintStream(
                    socket.getOutputStream());
            String msg = "";
            ;
           // while (!line.equals("!")) {
                msg = bufferedReader.readLine();
                System.out.println(msg);
                msg=puzzleSolution.getSolutionMessage();
                outputStream.println(msg);

         //   }

        } catch (Exception e) {

        }
    }
}

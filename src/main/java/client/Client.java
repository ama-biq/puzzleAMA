package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        new Client().clientRunning();
    }

    public void clientRunning() throws IOException {
        PrintStream outputStream = null;
        Puzzle puzzleJson = new Puzzle();
        String jsonInput = puzzleJson.createJson();
        String msg;
        Socket socket = new Socket("localhost", 7095);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        System.out.println("Print you message: ");

            msg = jsonInput;
            outputStream = new PrintStream(socket.getOutputStream());
            outputStream.println(msg);
            System.out.println(bufferedReader.readLine());


    }
}

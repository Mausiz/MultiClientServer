package Client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Socket socket = null;
        final BufferedReader reader;
        PrintWriter writer = null;
        Scanner scanner = null;

        try {
            socket = new Socket("localhost", 8000); //Test: Haven't tested with another machine yet
            System.out.println("Connected to server.");

            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            scanner = new Scanner(System.in);

            // Thread for receiving messages from the server
            new Thread(() -> {
                try {
                    String message;
                    while ((message = reader.readLine()) != null) {
                        System.out.println("Received: " + message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // for sending messages to the server
            while (true) {
                System.out.print("Enter message: ");
                String message = scanner.nextLine();
                writer.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
                if (scanner != null) {
                    scanner.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

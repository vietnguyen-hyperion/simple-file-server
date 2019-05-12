package com.exercise.fileserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import static com.exercise.fileserver.FileServer.PORT;

/**
 * Created by Viet on 11/05/2019.
 */
public class FileClient {

    private final Socket socket;
    private final Scanner scanner;

    private FileClient(String serverAddress, int serverPort) throws Exception {
        this.socket = new Socket(serverAddress, serverPort);
        this.scanner = new Scanner(System.in);
    }

    private void start() throws IOException {
        System.out.println("Input the command:");
        String input = scanner.nextLine();
        PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out.println(input);
        String data;
        while ((data = in.readLine()) != null) {
            System.out.println(data);
        }
    }

    public static void main(String[] args) throws Exception {
        FileClient client = new FileClient("localhost", PORT);
        System.out.println(String.format("Connected to server %s port %s ", client.socket.getInetAddress(), PORT));
        client.start();
    }
}
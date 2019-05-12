package com.exercise.fileserver;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Viet on 11/05/2019.
 */
public class RequestHandler extends Thread {

    private final Socket socket;
    private final File rootFolder;

    public RequestHandler(File rootFolder, Socket clientSocket) {
        this.rootFolder = rootFolder;
        this.socket = clientSocket;
    }

    public void run() {
        try {
            Scanner inputScanner = new Scanner(socket.getInputStream());
            PrintWriter outPrintWriter = new PrintWriter(socket.getOutputStream());
            String command = inputScanner.nextLine();
            System.out.println("Receive command " + command);
            if ("index".equalsIgnoreCase(command)) {
                sendIndex(outPrintWriter);
            } else if (command.toLowerCase().startsWith("get")) {
                String fileName = command.substring(3).trim();
                sendFile(fileName, outPrintWriter);
            } else {
                outPrintWriter.println("unknown command");
                outPrintWriter.flush();
            }
        } catch (Exception e) {
            System.out.println("error when execute command " + e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
            }
        }
    }

    private void sendIndex(PrintWriter outPrintWriter) throws Exception {
        Arrays.stream(rootFolder.listFiles())
                .filter(f -> !f.isDirectory() && f.getName().endsWith(".txt"))
                .map(File::getName)
                .forEach(outPrintWriter::println);
        outPrintWriter.flush();
        outPrintWriter.close();
        if (outPrintWriter.checkError()) {
            throw new Exception("Error while sending index.");
        }
    }

    private void sendFile(String filePath, PrintWriter outPrintWriter) throws Exception {
        File file = new File(rootFolder, filePath);
        if (!file.exists() || file.isDirectory()) {
            outPrintWriter.println(String.format("Error: the specified file '%s' does not exist.", filePath));
        } else {
            outPrintWriter.println("ok");
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    outPrintWriter.println(line);
                }
            }
        }
        outPrintWriter.flush();
        outPrintWriter.close();
        if (outPrintWriter.checkError()) {
            throw new Exception("Error while sending file.");
        }
    }
}
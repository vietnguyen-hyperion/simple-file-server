package com.exercise.fileserver;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Viet on 11/05/2019.
 */
public class FileServer {

    static final int PORT = 1515;

    public static void main(String args[]) {
        if (args.length == 0) {
            System.out.println("Need the directory argument.");
            return;
        }

        File rootDir = new File(args[0]);
        if (!rootDir.exists()) {
            System.out.println("Directory does not exist.");
            return;
        }

        if (!rootDir.isDirectory()) {
            System.out.println("The argument is not a directory");
            return;
        }
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started on port " + PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                new RequestHandler(rootDir, socket).start();
            }
        } catch (IOException e) {
            System.out.println("Server shut down unexpectedly. Error:" + e);
        }
    }
}

package server;

import java.io.*; 
import java.net.*;
import java.util.*;
import java.util.logging.Logger;

public class Server {
    
public static final int PORT = 4444;
private static int clientNumber;
private static ArrayList<Socket> connectedClients;
private static Socket socket;
private static ServerSocket serverSocket;
 
public static void main(String[] args) throws IOException { 
    serverSocket = new ServerSocket(PORT); 
    try {
        clientNumber=1;
        connectedClients=new ArrayList<>();
        System.out.println("Server started: " + serverSocket);
        while(!serverSocket.isClosed())
        {

        socket = serverSocket.accept();
        connectedClients.add(socket);
        String clientName = "Client "+clientNumber;
        MonoThreadClientHandler t = new MonoThreadClientHandler(socket,clientName,connectedClients,clientNumber);
        new Thread(t).start();
        clientNumber++;
        }
    }
    catch (IOException e) {
        e.printStackTrace();
        }
    finally {
        System.out.println("Main Closing connection "+socket);
        socket.close(); 
        serverSocket.close();
        }
    }     
}

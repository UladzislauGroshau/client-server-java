package server;

import java.io.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class MonoThreadClientHandler implements Runnable {

    private static String threadName;
    private static Socket socket;
    private static String remember;
    private static ArrayList<Socket> allClients;
    private static int clientNumber;
    private static int currentClient;
    
    Random myRan = new Random();

    public MonoThreadClientHandler(Socket socket, String threadName, ArrayList<Socket> allClients,int clientNumber) {
        this.socket = socket;
        this.threadName = threadName;
        this.allClients = allClients;
        this.clientNumber=clientNumber;
    }

    @Override
    public void run() {
     try {
            
    System.out.println(threadName+" got new connection from " + socket);
     DataOutputStream out = new DataOutputStream(socket.getOutputStream());
     DataInputStream in = new DataInputStream(socket.getInputStream());
     BufferedReader br =new BufferedReader(new InputStreamReader(System.in));


 
            out.writeUTF(Integer.toString(clientNumber));
           // out.flush();
           while(!socket.isClosed())  {
            String num = in.readUTF();
            switch (num) {
                    case "1":
                        String countClients= Integer.toString(allClients.size()+1);
                        out.writeUTF(countClients);
                       // out.flush();
                        String number = in.readUTF();
                        currentClient=Integer.parseInt(number);
                        String msg = in.readUTF();
                      //  out.flush();
                        System.out.println("Message received from the client: "+msg);
                         if (msg.equals("END")) {
                              in.close();
                              out.close();
                              break;}
                              remember=msg;
                        continue;
                    case "2":
                         out.writeUTF(Integer.toString(currentClient)); 
                         System.out.println("Message sent to the client: "+remember);
                         out.writeUTF(remember); 
                       //  out.flush();
			continue;
		    case "3":
			System.exit(1);	
                       break;
                    default:
                        System.out.println("Incorrect command received.");
                        break;
                }
            }
            } catch (IOException e) {
            e.printStackTrace();
            }
    }
           
    }
    

    


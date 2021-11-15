package client;

import java.net.*; 
import java.io.*; 
import java.util.Scanner;


public class Client {
   private static Socket socket;
   private static BufferedReader br;
   private static DataOutputStream out;
   private static DataInputStream in;
   public static final int PORT = 4444;
   private static int clientNumber;
    
    public static void main(String[] args)  throws IOException { 

socket = new Socket("127.0.0.1", PORT); 

try {

    br =new BufferedReader(new InputStreamReader(System.in));
    out = new DataOutputStream(socket.getOutputStream());
    in = new DataInputStream(socket.getInputStream());
    clientNumber=Integer.parseInt(in.readUTF());
    out.flush();
    System.out.println("Client "+clientNumber);
    Scanner scanner = new Scanner(System.in);
        while(!socket.isClosed())
   { 
    menu();
    String clientCommand = scanner.nextLine();
    switch (clientCommand) {
        case "1":                
        out.writeUTF("1");
        out.flush();
        String list = in.readUTF();
        for(int i=1;i<Integer.parseInt(list);++i)
        {
         System.out.println("Client "+i);
        }
        System.out.print("Select client: ");
        String numberOfClient = scanner.nextLine();
        out.writeUTF(numberOfClient);
        out.flush();
        System.out.println("Message: ");
        String message = scanner.nextLine();           
        out.writeUTF(message);
        out.flush();
        System.out.println("Message sent to the server : "+message);
        continue;
        
        case "2":
        out.writeUTF("2");
        out.flush();
        try{
        int currentNumber=Integer.parseInt(in.readUTF());
        if(clientNumber==currentNumber) {
        String msg = in.readUTF();
        out.flush();
        System.out.println("Message received from the server : " +msg);
        }else {System.out.println("This message isn't for you");}
        }catch(NumberFormatException e)
        {
        e.printStackTrace();
        }        
        continue;
        
	case "3":
	System.exit(1);	
        break;
        default:
        System.out.println("Incorrect command received.");
        break;
        }
  }
}
catch (IOException e) {
e.printStackTrace();
}
finally { 
    try{
System.out.println("closing..."); 
socket.close();}
    catch(IOException e) {
e.printStackTrace();
}
} 
} 
    
    public static void menu() {
        System.out.println("1. Send message.");
        System.out.println("2. Recieve message.");
	System.out.println("3. Exit.");
        System.out.print("Make selection: ");
    }

}
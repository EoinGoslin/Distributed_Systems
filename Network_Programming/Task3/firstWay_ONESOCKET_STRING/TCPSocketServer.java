import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * A simple class that opens a server socket, and prints each message received
 * to the console.
 * @author Graeme Stevenson (graeme.stevenson@ucd.ie)
 */
public class TCPSocketServer {

   /**
    * Accept this many connections.
    */
   private int my_backlog = 100;

   /**
    * The server socket.
    */
   private ServerSocket my_serverSocket;

   /**
    * Create the server socket.
    * @param a_port the port that the server socket should listen on.
    */
   public TCPSocketServer(int a_port) { //e,g 1234
      try {
         my_serverSocket = new ServerSocket(a_port, my_backlog);
         System.out.println("TCP socket listening on port " + a_port);
      } catch (IOException ioe) {
         ioe.printStackTrace();
      } catch (SecurityException se) {
         se.printStackTrace();
      }
   }

   /**
    * Method that listens on the server socket forever and prints each incoming
    * message to the console.
    */
   public void listen() {
      while (true) {
         try {
            // Listens for a connection to be made to this socket.
            Socket socket = my_serverSocket.accept();

            // Wrap a buffered reader round the socket input stream.
            // Read the javadoc to understand why we do this rather than dealing
            // with reading from raw sockets.
            BufferedReader in = new BufferedReader(new InputStreamReader(socket
                  .getInputStream()));

            // Read in the message
            String msg = in.readLine();//Reading in that one long string to be later broken down and all the pairs extracted to be multiplied

            // Print the message to the console
            System.out.println("Recceived message: " + msg);

           




            // EXERCISE: Instead of printing out client messages to the console:
            // 1. Construct a response in the form "Your message is: <message>".
            // 2. Send the response back to the client.
            //Sending the response back to the client.
            //GET data from msg
            //Message is full of number pairs to multiply
            String retMessage = FormatAndMultiply(msg); //retMessage will contain result of the multiplication of all the pairs
            sendMessageToClient(socket, retMessage);
           

           

            // tidy up
            in.close();
            socket.close();
         } catch (IOException ioe) {
            ioe.printStackTrace();
         } catch (SecurityException se) {
            se.printStackTrace();
         }
      }
   }
   public String FormatAndMultiply(String message) {
      //This method is concenered with taking apart the one long String received
      //First, split on space so then every slot of the numberString Array holds a pair of numbers
      //Second. create an int array to hold result of each pair being multiplied
      //Another array is created so to split the pairs on the '_' underscore so then can parse each of these numbers and store result of calculation in numbers[] array

      String numberString[] = message.split(" "); //split into pairs
      int numbers[] = new int[100];
      String returnMessage = new String("");
      //Fill number array with each slot
      //the multiplied pair
      for(int i = 0; i < numbers.length; i++) {
            
            String pairs[] = numberString[i].split("_");
            numbers[i] = Integer.parseInt(pairs[0]) * Integer.parseInt(pairs[1]);
            returnMessage += Integer.toString(numbers[i]) + " is the " + i + " multiplication result";


      }
      return returnMessage; //Return long string containing result of all the pairs being multiplied

   }
   public void sendMessageToClient(Socket socket, String msg) {
         try {

         
      OutputStream ServverOs = socket.getOutputStream();
      OutputStreamWriter osw = new OutputStreamWriter(ServverOs);
      BufferedWriter bw = new BufferedWriter(osw);
      String ServerMessage = "SERVER ECHO: " + msg;
      bw.write(ServerMessage); //placing data into bufferedWriter
      System.out.println("Message sent to the client"); //sending message back

      bw.flush(); //flushing last message sent so can be used again
         } catch(IOException e){
               e.printStackTrace();
         }
}


   /**
    * Parse the arguments to the program and create the server socket.
    * @param args the program arguments. Should take the form &lt;port&gt;
    */
   public static void main(String[] args) {
      int port = 0;

      // Check we have the right number of arguments and parse
      if (args.length == 1) {
         try {
            port = Integer.valueOf(args[0]);
         } catch (NumberFormatException nfe) {
            System.err.println("The value provided for the port is not an integer");
            nfe.printStackTrace();
         }
         // Create the server
         TCPSocketServer server = new TCPSocketServer(port);
         // Listen on the server socket. This will run until the program is
         // killed.
         server.listen();
      } else {
         System.out.println("Usage: java TCPSocketServer <port>");
      }

   }
} // end class


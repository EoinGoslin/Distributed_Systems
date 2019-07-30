import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.*;
import java.io.*;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * A simple class that opens a socket, sends a message to the server, and
 * terminates.
 * @author Graeme Stevenson (graeme.stevenson@ucd.ie)
 */
public class TCPSocketClient {

   /**
    * The server host name.
    */
   public String my_serverHost;

   /**
    * The server port.
    */
   public int my_serverPort;

   /**
    * Sets the client up with the server details.
    * @param the_serverHost the server host name.
    * @param the_serverPort the server port.
    */
   public TCPSocketClient(String the_serverHost, int the_serverPort) {
      my_serverHost = the_serverHost;
      my_serverPort = the_serverPort;
   }

   /**
    * Creates a connection to the server and sends a message.
    * @param a_message the message to send to the server.
    */
   public void sendMessage(String a_message) {
      try {
         // Create a connection to the server.
         Socket toServer = new Socket(my_serverHost, my_serverPort);

         // Wrap a PrintWriter round the socket output stream.
         // Read the javadoc to understand (1) the method arguments, and (2) why
         // we do this rather than writing to raw sockets.
         PrintWriter out = new PrintWriter(toServer.getOutputStream(), true);

         // Write the message to the socket.
         out.println(a_message);

         // EXERCISE: Extend this code to
         // 1. Read the response from the server.
         // 2. Print the response out to the console.
        InputStream is = toServer.getInputStream();
        OutputStream os = toServer.getOutputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
       
       
        String line = in.readLine();
        System.out.println(line);

         //Close resources after sending to Server and receiving message back
         out.close();
         toServer.close();
      } catch (IOException ioe) {
         ioe.printStackTrace();
      } catch (SecurityException se) {
         se.printStackTrace();
         
      }

   }

   

   /**
    * Parse the arguments to the program, create a socket, and send a message.
    * @param args the program arguments. Should take the form &lt;host&gt;
    *           &lt;port&gt; &lt;message&gt;
    */
   public static void main(String[] args) {

      String host = null;
      int port = 0;
      String message = null;

      // Check we have the right number of arguments and parse
      if (args.length == 3) {
         host = args[0];//e,g. localhost
         try {
            port = Integer.valueOf(args[1]);
         } catch (NumberFormatException nfe) {
            System.err.println("The value provided for the port is not an integer");
            nfe.printStackTrace();
         }
         message = args[2];

         // Create the client
         TCPSocketClient client = new TCPSocketClient(host, port);
         // Send a message using the client
         client.sendMessage(message); //send "Hello to Server"
        
      } else {
         System.out.println("Usage: java TCPSocketClient <host> <port> <message>");
      }

   }
   
} // end class


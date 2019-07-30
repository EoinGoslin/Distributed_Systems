import java.io.*; 
import java.text.*; 
import java.util.*; 
import java.net.*; 

public class Server  
{ 
    public static void main(String[] args) throws IOException  
    { 
        // My server is listening on port 1234
        ServerSocket ss = new ServerSocket(1234); 
        //In this class, Server will wait for a connection
        //Then create a ThreadHandler to deal with the Client and so can handle multiple clients all sending random numbers
        
          
       
        while (true)  
        { 
            Socket s = null; //So not only in scope of try/catch block
              
            try 
            { 
               
                s = ss.accept();  //set up blocking method
                //ServerSocket will wait until data is received and then code below will execute
                  
                System.out.println("A new client is connected : " + s); 
                  
                // Creating DataStream by passing in input and output streams 
                DataInputStream dis = new DataInputStream(s.getInputStream()); 
                DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
                  
                System.out.println("Assigning new thread for this client"); 
  
                // Create a Thread object so that server can handle multiple requests of 100 random numbers if needed

                Thread t = new ThreadHandler(s, dis, dos); //passing in socket, input and output stream to ThreadHandler constructor to create a Thread capable of taling to a specific client
  
                // Calling start executes the run method in my ThreadHandle Class as t is of type
                //ThreadHandle and so can call method on object
                t.start(); 
                  
            } 
            catch (Exception e){ 
                //close resource
                s.close(); 
                //Then display the error
                e.printStackTrace(); 
            } 
        } 
    } 
    
} 

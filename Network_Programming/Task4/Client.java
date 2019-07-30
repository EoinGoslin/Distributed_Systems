import java.io.*; 
import java.net.*; 
import java.util.*;

  
// Client class 
public class Client  
{ 
    public static void main(String[] args) throws IOException  
    { 
        try
        { 
            
              
            // getting localhost ip 
            //Can also place 'localhost' directly into Socket parameters
            InetAddress ip = InetAddress.getByName("localhost"); 
      
            // establish the connection with server port 5056 
            Socket s = new Socket(ip, 1234); 
      
            // obtaining input and out streams 
            //DataStream can send Binary data
            DataInputStream dis = new DataInputStream(s.getInputStream()); 
            DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
      
           
            //In here, Will send 100 Random numbers
            //Then print out the result of each pair as by the end of the loop the
            //Server will have sent it back
            while (true)  
            { 
                    System.out.println("Sending two numbers: 10 times"); 
                    for(int i = 1; i < 100; i++) {
                    System.out.println("This is number " + i  + " time sending...");
                    //Creating Random object and calling nextInt() method every loop twice to generate random numbers
                    //+1 as want from 1-100 not starting at 0
                    Random r = new Random();
                    int randomInt1 = r.nextInt(100) + 1;
                    int randomInt2 = r.nextInt(100) + 1;
                    //Send off two random numbers to Socket 
                    dos.writeInt(randomInt1);
                    dos.writeInt(randomInt2);
                    //Then receive the calculation
                    int result = dis.readInt(); 
                 
                    System.out.println("The result of multiplying " + randomInt1 + " and " + randomInt2 + " is: " + result);
                    

                }
                
                break;
            } 
              
            // closing resources 
            
            dis.close(); 
            dos.close(); 
        }catch(Exception e){ 
            e.printStackTrace(); 
        } 
    }
    
}



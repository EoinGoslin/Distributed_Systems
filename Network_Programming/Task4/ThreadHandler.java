import java.io.*; 
import java.text.*; 
import java.util.*; 
import java.net.*; 

public class ThreadHandler extends Thread  
{ 
    //instance variables
    //Each thread will have its own client and so every thread needs its own values
    
    final DataInputStream dis; 
    final DataOutputStream dos; 
    final Socket s; 
      
  
    // Constructor 
    public ThreadHandler(Socket s, DataInputStream dis, DataOutputStream dos)  
    { 
        this.s = s; 
        this.dis = dis; 
        this.dos = dos; 
    } 
  
    @Override
    public void run()  
    { 
        //declaring local ints
        int receivedNum1, receivedNum2, result; 
        while (true)  
        { 
            try { 
  
               
                System.out.println("Now receiving numbers..."); 
                for(int i = 1; i < 100; i++) {
                    System.out.println("This is number " + i  + " time receiving and sending...");
                    
                    receivedNum1 = dis.readInt(); 
                    receivedNum2 = dis.readInt();
                    result = receivedNum1*receivedNum2;
                    
                    System.out.println(receivedNum1 + " " + receivedNum2);
                    dos.writeInt(result);
                
                }
              
                break;
                
            } catch (IOException e) { 
                e.printStackTrace(); //e becomes exception object caught and print details to terminal
            } 
        } 
          
        try
        { 
            // closing my input and output stream resources 
            this.dis.close(); 
            this.dos.close(); 
              
        }catch(IOException e){ 
            e.printStackTrace(); //e becomes exception object caught and print details to terminal
        } 
    } 
}

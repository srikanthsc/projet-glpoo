import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.beans.XMLEncoder;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.sound.sampled.*;
//package serveur-client.Serveur;
//import musichub.business.*;
import java.util.*;                    

public class Serveur  {
 
    public static void main(String[] test) {
   
      final ServerSocket serveurSocket  ;
      final Socket clientSocket ;
      final BufferedReader in;
      final PrintWriter out;
      final Scanner sc=new Scanner(System.in);
      File file = new File("F:/the-weeknd.wav");
      Client client;
   
      try {
        serveurSocket = new ServerSocket(5009);
        clientSocket = serveurSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream());
        in = new BufferedReader (new InputStreamReader (clientSocket.getInputStream()));
        Thread envoi= new Thread(new Runnable() {
           String msg;
           @Override
           public void run() {
              while(true){
                 msg = sc.nextLine();
                 out.println(msg);
                 out.flush();
              }
           }
        });
        envoi.start();
    
        Thread recevoir= new Thread(new Runnable() {
           String msg ;
           @Override
           public void run() {
              try  { 
                 msg = in.readLine();
                 //tant que le client est connecté
                 while(msg!=null){
                    System.out.println("Client : "+msg);
                    msg = in.readLine();
                    Scanner scanner = new Scanner(System.in);
                
  try{
  AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
  
  Clip clip = AudioSystem.getClip();
  
  clip.open(audioStream);
  
  
  String response = "";
 
  
  while(!response.equals("Q") ) {
   System.out.println("P = play, S = Stop, R = Reset, Q = Quit");
   System.out.print("Enter your choice: ");
   
   response = scanner.next();
   response = response.toUpperCase();




   
   switch(response) {
    case ("P"): clip.start();
    break;
    case ("S"): clip.stop();
    break;
    case ("R"): clip.setMicrosecondPosition(0);
    break;
    case ("Q"): clip.close();
    break;
    default: System.out.println("Not a valid response");
   }
}

  
  
  System.out.println("Byeeee!"); 
}catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex){}
                 }
                 //sortir de la boucle si le client a déconecté
                 System.out.println("Client déconecté");
                 //fermer le flux et la session socket
                 out.close();
                 clientSocket.close();
                 serveurSocket.close();
              } catch (IOException e  ) {
                   e.printStackTrace();
              }
          }
       });
       recevoir.start();
       }catch (IOException e) {
          e.printStackTrace();
   
       }
    }
 }
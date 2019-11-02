package socked;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Klient
{
   public static final int PORT=50000;
   public final String HOST;
   public static PrintWriter printWriter;
   public static Socket socket;
   public String name;
   public BufferedReader reader;
   
   public Klient(String HOST, String name)
   {
	   this.name=name;
	   this.HOST=HOST;
   }
   
   
   public void startServer()
   {
	
	   
	   try {
		   
		   socket = new Socket(HOST,PORT); 
		   printWriter = new PrintWriter(socket.getOutputStream()); //wysy³anie
		   reader = new BufferedReader(new InputStreamReader(socket.getInputStream())); //odczyt
		   printWriter.println(name + " --> "+"connect");
		   printWriter.flush();
		   
		
	} catch (Exception e) {
		// TODO: handle exception
	}
   }
   
   public void send(String mes)
   {
	   
	    printWriter.println(name + " --> "+mes);
	    printWriter.flush();	    
   }
   
   public void exit()
   {
	   printWriter.println(name +" --> "+ "disconnect");
	   printWriter.flush();
	   printWriter.close();
	   try {
		socket.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
	   
   
   
}
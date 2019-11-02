package socked;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;



public class Serwer
{
   public static final int PORT=50000;
   private ArrayList<PrintWriter> klientList;
   private PrintWriter printWriter;
   
   public Serwer()
   {
	   klientList = new ArrayList<PrintWriter>();
	   startSerwer();
   }
   
   
   public static void main(String[] args)
   {
	   Serwer s = new Serwer();
   }
   
   public void startSerwer()
   {
	   try {
		ServerSocket serverSocked = new ServerSocket(PORT);
		while(true)
		{
			Socket socket = serverSocked.accept(); //nawi¹zanie nowego po³¹czenia
			printWriter = new PrintWriter(socket.getOutputStream());  //zapis do wyjscia sieciowego
			klientList.add(printWriter); //dodanie nowego klienta
			Thread thread = new Thread(new ServerKlient(socket)); //stworzenie nowego w¹tku dla niego
			thread.start(); //start w¹tku
			System.out.println(klientList.toString());
			
		}
		
	} catch (IOException e) {
		e.printStackTrace();
	}
   }
   
   class ServerKlient implements Runnable
   {

	   Socket socked;
	   BufferedReader reader;
	   
	public ServerKlient(Socket socket) 
	{
		try {
		this.socked=socket;
		reader = new BufferedReader(new InputStreamReader(socked.getInputStream()));
		
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	   
	   
	@Override
	public void run() 
	{
		String mes;
		PrintWriter print=null;
		
		try {
			
			while((mes=reader.readLine())!=null)
			{
				if(mes.split(" ")[2].equals("disconnect")  && mes.split(" ").length == 3) //usuwanie z listy klientów
				{
					for(int i = 0; i<klientList.size();i++)
					{
						if((klientList.get(i)).equals(socked));
						{
							klientList.remove(i);
						}
					}
				}
				
				Iterator it = klientList.iterator(); //rozsy³anie po kolejnych klientach
				while(it.hasNext())
				{
					print = (PrintWriter) it.next();
					print.println(mes);
					print.flush();
				}
			}
			
		} catch (Exception e) {
		}
		
	}
	   
   }
 
                                                               
}
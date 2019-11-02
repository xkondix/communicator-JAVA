package socked;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;




public class Frame extends JFrame implements ActionListener 
{
	
	private Klient klient;
	private JButton jButton,jButton2,jButton3;
	private JTextArea jArea,jTArea;
	private JLabel jIp,jNick,jCz,jText;
	private JScrollPane	scroll;
	private JTextField jTip,jTNick;
	private Klient a;

	public Frame()
	{
		
		
		setSize(600, 400);
		setTitle("Komunikator");
		setLayout(null);

		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		SwingUtilities.updateComponentTreeUI(this);
		
		jButton = new JButton("Połącz");
		jButton.setBounds(355, 140, 100, 20);
        add(jButton);
        jButton.addActionListener(this);
        
        jButton2 = new JButton("Rozlącz");
		jButton2.setBounds(455, 140, 100, 20);
        add(jButton2);
        jButton2.addActionListener(this);
        jButton2.setEnabled(false);
        
        jButton3 = new JButton("Wyślij");
   		jButton3.setBounds(408, 300, 100, 20);
        add(jButton3);
        jButton3.addActionListener(this);
        jButton3.setEnabled(false);

        
        jArea = new JTextArea();
        jArea.setBounds(30, 30, 300, 300);
        add(jArea);
        jArea.setEditable(false);
        scroll = new JScrollPane(jArea);
        scroll.setBounds(30, 30, 300, 300);                    
        getContentPane().add(scroll);
        setLocationRelativeTo ( null );
        
        jNick = new JLabel("Podaj swój nick ");
        jNick.setBounds(400, 80, 250, 20);
		add(jNick);
		jNick.setFont(new Font("Nazwa", Font.BOLD, 13));
		
		jText = new JLabel("Tu pisz ↓");
		jText.setBounds(430, 170, 250, 20);
		add(jText);
		jText.setFont(new Font("Nazwa", Font.BOLD, 13));
		
		jIp = new JLabel("Podaj ip serwera ");
		jIp.setBounds(400, 30, 250, 20);
		add(jIp);
		jIp.setFont(new Font("Nazwa", Font.BOLD, 13));
		 
		 jCz = new JLabel("Czat ");
		 jCz.setBounds(165, 10, 250, 20);
		 add(jCz);
		 jCz.setFont(new Font("Nazwa", Font.BOLD, 13));
		 
		 jTip = new JTextField("");
		 jTip.setBounds(380, 50, 150, 25);
		 add(jTip);
			
		 jTNick = new JTextField("");
		 jTNick.setBounds(380, 105, 150, 25);
		 add(jTNick);
		
		 jTArea = new JTextArea();
		 jTArea.setBounds(380, 190, 150, 100);
		 add(jTArea);
			
	

	
	}
	
	
	
	public static void main(String[] args)
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable()  
			      {                                                        
			         public void run()                                     
			         {           
			        	 	Frame apka = new Frame();
			        	 	apka.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			        	 	apka.setVisible(true);
			         }                                                     
			      });
		
	
		
	}
	

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object k = e.getSource();
		
		
		
		if(k == jButton)
		{
			String ip = jTip.getText();
			String nick = jTNick.getText();
			
			a = new Klient(ip,nick);
			a.startServer();
			Thread thread = new Thread(new Receiver());
			thread.start();
	        jButton.setEnabled(false);
	        jButton2.setEnabled(true);
	        jButton3.setEnabled(true);

			

			   
			
		}
		
		
		
		if (jButton2 == k)
		{
		 a.exit();
		 jButton.setEnabled(true);
	     jButton2.setEnabled(false);
	     jButton3.setEnabled(false);
		 
		}
		
		
		if(jButton3==k)
		{
			a.send(jTArea.getText());
			jTArea.setText("");
		}
		
		
	}
	
	
	
	class Receiver implements Runnable
	   {

		 String mes;
		   
		@Override
		public void run() {
			
			try {
				while((mes=a.reader.readLine())!=null)
				{
					
					
				String rege[] = mes.split(" ");
				
				
				if(rege[0].equals(a.name))
				{
					String wiado = "";
					for(int i = 1; i<rege.length;i++)
				    {
						wiado+=rege[i]+" ";
					}
					jArea.append(wiado+"\n");
				}
				
				
				else
				{
					jArea.append(mes+"\n");
				}
					
					
					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		   
	   }
		
	
}
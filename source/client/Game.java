/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.net.Socket;
import java.util.Iterator;
import java.io.*;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.*;

/**
 *
 * @author Oz
 */
public class Game extends JFrame  implements Runnable , ActionListener {
    
    private   Socket socket;
    private  ObjectInputStream is;
    private   ObjectOutputStream os;
    private String userName = null;
    private String otherPlayerName = null;
    private boolean firstSelected = true;

    private boolean turn;
    private String moveMessage;
    private ImageIcon myIcon;
    
    
    
    
	private JPanel boardPanel = new JPanel();
	private JButton[][] boardButtons = new JButton[8][8];
	private JPanel hudPanel = new JPanel();  				// Addition
	private JPanel winlossPanel = new JPanel();
	private JLabel winlossLabel = new JLabel("Dama");
	private JTextArea textArea = new JTextArea();
	
	private JTextField messageField = new JTextField();
	private JTextArea chatField = new JTextArea();
	private JButton sendButton = new JButton("Send");
	ImageIcon red ;
	ImageIcon blue;
	
	private JScrollPane jsp = new JScrollPane(textArea);
	private JTextField textField = new JTextField();
	
	private ImageIcon emptyIcon;
	


	private JScrollPane scrollPane = new JScrollPane(chatField);
	
	

	/** scrollPane
	 * Default constructor - initiates GUI construction and connection.
	 * @param host
	 * @param port
	 */
	public void setFrame()
	{
		System.out.println("Set Frame");
		if(userName != null) {
			super.setTitle(userName);
				
		}else {
			super.setTitle("test");
			
		}
		this.setSize(400,450);
		
        this.addWindowListener(new WindowAdapter(){
        public void windowClosing(WindowEvent we){
            ClientService.exit();
            dispose();
        }
 });
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		setupGUI();
	}
	
	/**
	 * Sets up the GUI
	 */
	public void setupGUI()
	{
		Container cP = getContentPane();
		cP.add(boardPanel, BorderLayout.CENTER);
//		winlossPanel.add(winlossLabel);type filter text

		
		chatField.setSize(200,100);

		JScrollPane scrollPane = new JScrollPane(chatField);
		chatField.setEditable(false);
		chatField.setPreferredSize(chatField.getSize());
		sendButton.setSize(50,80);
		sendButton.setPreferredSize(sendButton.getSize());
		
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				String message = userName + ":";
				message += messageField.getText();
				messageField.setText("");
				Packet packet = new Packet(Packet.CHAT , message);
				try {
				os.writeObject(packet);

				chatField.append(packet.toString()+"\n");
					
				} catch (Exception e) {
					System.err.println(userName +" " +e.toString());
				}
			}
			
		});
		
//		winlossPanel.add(chatField);
		winlossPanel.add(scrollPane,BorderLayout.WEST);
		messageField.setSize(80,100);
		messageField.setPreferredSize(messageField.getSize());
		winlossPanel.add(messageField,BorderLayout.CENTER);
		winlossPanel.add(sendButton,BorderLayout.EAST);
//		
		String curDir = System.getProperty("user.dir");
		winlossPanel.add(winlossLabel,BorderLayout.CENTER);
		
		  red= new ImageIcon(curDir+"/src/red.png");
		 blue = new ImageIcon(curDir+"/src/blue.png");
		emptyIcon = new ImageIcon("/src/blank.png");
		
		
		hudPanel.add(winlossPanel, BorderLayout.WEST);
		cP.add(hudPanel, BorderLayout.SOUTH);
		boardPanel.setLayout(new GridLayout(8, 8));
	
		for (int i=0; i<8; i++)
		{
			for (int j=0; j<8; j++)
			{


				
				boardButtons[i][j] = new JButton("");
				boardButtons[i][j].addActionListener(this);
				if((i == 0) && (j % 2 == 0) ) boardButtons[i][j].setIcon(red);
				else if((i == 1 )&& (j % 2 == 1)) boardButtons[i][j].setIcon(red);
				else if ((i == 2 )&&( j % 2 == 0)) boardButtons[i][j].setIcon(red);
				else if ((i == 5 )&& (j % 2 == 1 )) boardButtons[i][j].setIcon(blue);
				else if((i == 6) &&( j % 2 == 0))  boardButtons[i][j].setIcon(blue);
				else if((i == 7) &&( j % 2 == 1)) boardButtons [i][j].setIcon(blue);
				else boardButtons[i][j].setIcon(emptyIcon);
				boardButtons[i][j].setActionCommand("" + i + "" + j);
				boardPanel.add(boardButtons[i][j]);
			}
		}
		
		if (turn == false) {
			boardDisable();
		}
		
		System.out.println(userName +" Set Gui completed");
	}
	
	public void sendMessageButton(MouseEvent evt){
		
	}
	
	private void boardDisable() {
		for (Component comp : boardPanel.getComponents()) {
			comp.setEnabled(false);
		}
	}
	private void boardEnable() {
		for (Component comp : boardPanel.getComponents()) {
			comp.setEnabled(true);
		}
	}
	   
	@Override
	public void actionPerformed(ActionEvent ae) {
		
			String command = ae.getActionCommand();
			Integer x = Integer.parseInt("" + command.charAt(0)); // row
			Integer y = Integer.parseInt("" + command.charAt(1)); // col
			boolean moveIsValid = false;
			
			if(firstSelected) {
				
				 moveMessage = "from "  + x + " "  + y ;
				firstSelected = false;
				
			}else {
				 moveMessage += " to " + x + " " + y;
					Packet move = new Packet(Packet.MOVE , moveMessage);
					try
					{
						moveIsValid = doMove(moveMessage,true,userName);

						
						if(moveIsValid) {
							os.writeObject(move);
							System.out.println(userName + " Move sent.");
						}
					}
					catch (IOException ioe)
					{
						JOptionPane.showMessageDialog(this, "\nConnection to Server Lost - Closing");
						System.exit(0);
					}
				 
				 moveMessage ="";
				 firstSelected = true;
				 if(moveIsValid) {
					 this.turn = false;
					 boardDisable();
				 }
			}

		
		
	}
    
    
    
    
    
    
    
    
    
    
    
    
    

    
    
    
    
    
    
    
    
    

    /**
     * Creates new form Game
     */
    public Game(Socket socket,String userName , boolean turn , String color) {
    	this.userName = userName;
    	this.turn = turn;
        this.socket = socket;
        
        
 
        
        setFrame();
        try{
            
            os = new ObjectOutputStream(socket.getOutputStream());
            Packet temp = new Packet(Packet.TEMP,userName);
            os.writeObject(temp);// 
            os.flush();
            is = new ObjectInputStream(socket.getInputStream());
            
            if(turn != true) {
	            JFrame frame = new JFrame();
	            String message = "Oyun oynamak istiyormusunuz ? ";
	            int answer = JOptionPane.showConfirmDialog(frame, message);
	            if (answer == JOptionPane.YES_OPTION) {
	                String msg = userName;
	                Packet accept = new Packet(Packet.ACCEPT,msg);
	                os.writeObject(accept);
	            } else if (answer == JOptionPane.NO_OPTION) {
	              String msg = "Kullanici oyun oynamak istemiyor";
	              Packet decline = new Packet(Packet.DECLINE,msg);
	              os.writeObject(decline);
	              ClientService.exit();
	              dispose();
	            }
            }
            
           
            
 
          System.out.println("Thread starting");

          
          if(color == "blue") this.myIcon = blue;
          else myIcon  = red;

           Thread t = new Thread(this);
           t.start();
            
        }catch(Exception ex){
            System.err.append(ex.toString());
        }
    }
    
    public Game(){
    	
    	setFrame();
    	boardEnable();
    	
    }
    
 

    





    public static void main(String args[]) {
        
        
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Game().setVisible(true);
            }
        });
    }


    @Override
    public void run() {
        int type ;
        String message;
        Packet responseLine;
        try{
            

            while( (responseLine = (Packet) is.readObject() ) != null ){
            	message = responseLine.toString();
            	type = responseLine.getType();
                
            	 if(type == Packet.TEMP){
            		winlossLabel.setText(  message );
            		otherPlayerName = message;
            		setTitle(userName + " vs " + otherPlayerName);
            		appendLog(userName + " - " + otherPlayerName + " start match");
                	
            	}
            	 
            	 else {
             		winlossLabel.setText("");
             	}
            	
            	
            	if(type ==  Packet.MOVE) {
//            		chatField.append(message+"\n");
            		if(this.turn== false) {
            			this.turn = true;
            			doMove(message, false,otherPlayerName);
            			boardEnable();
            		}
            	}
            	
            	if(type == Packet.DECLINE) {
            		
                    JFrame frame = new JFrame();
                    String showmsg = " Kullanici oyun oynamak istemiyor";
                    JOptionPane.showMessageDialog(frame, showmsg, ":(", 1 );
 
                      ClientService.exit();
                      dispose();
                    
                    
            	}

            	else if (type == Packet.ACCEPT) {
            		otherPlayerName = message;
            	}
            	
            	
            	else if(type == Packet.CHAT) {
            		chatField.append(message+ "\n");
            	}
            	
            	
            	

            }	
       }catch(Exception ex){
           System.err.append(ex.toString());
       }
        
    }
    
    public void start() {
        Thread t = new Thread(this);
        t.start();
    }
    
    private boolean doMove(String message , boolean mineMove , String user) {
    	
    	Integer fromX,fromY,toX,toY;
    	
    	String[] splitted =  message.split(" ");
    	fromX = Integer.parseInt(splitted[1]);
    	fromY = Integer.parseInt(splitted[2]);
    	
    	toX = Integer.parseInt(splitted[4]);
    	toY = Integer.parseInt(splitted[5]);
    	

    	Dimension from = new Dimension(fromX,fromY);
    	Dimension to = new Dimension(toX,toY);
    	
    	if(areaIsBlank(to) && moveIsValid(from,to,mineMove,user) ) {
    		
    		if(mineMove) { // control button is mine or not
    			if(!isMine(from)) return false;
    		}
    	
	    	JButton fromButton = boardButtons[fromX][fromY];
	    	JButton toButton = boardButtons[toX][toY];
	    	ImageIcon buttonIcon = (ImageIcon) fromButton.getIcon();
	    			
	    	fromButton.setIcon(emptyIcon);
	    	toButton.setIcon(buttonIcon);
	    	
	    	
	    	return true;
	    }else return false;
    }
    
    
    private void appendLog(String message) {
    	FileOutputStream fos = null; 
    	message += "\n";
    	try{
    		 fos = new FileOutputStream(userName+ "-" +otherPlayerName+".log",true);
    		 fos.write(message.getBytes());
    		 fos.close();
    	}catch(Exception e) {
    		System.err.println(e.toString());
    	}
    	finally{
    		
    	}
    }

    
    
    
    private boolean moveIsValid(Dimension from, Dimension to,boolean mineMove ,String user) {
    	
    	int yPath = to.width - from.width;
    	int xPath = to.height - from.height;
    	
    	System.out.println(userName + " xpath: "+xPath + " yPath:" + yPath);
    	
    	boolean xValid = false;
    	boolean yValid = false;
    	boolean isThereEat = false; 
    	
    	if(xPath == 1 || xPath == -1 ) xValid = true;
    	if((xPath == 2 || xPath == -2) && (yPath == 2 || yPath == -2) ) xValid =  true;
    	if(yPath == 1 || yPath == -1) yValid = true;
    	if((xPath == 2 || xPath == -2) && (yPath == 2 || yPath == -2)) isThereEat = (yValid = eatIsValid(from,to,mineMove,user));
    	
    	if (xValid && yValid && !isThereEat) {
    		String log = user + " - move <" + from.width+ "-" + from.height+"> to <" + to.width + "-" + to.height + ">" ;
    		System.out.println(log);
    		appendLog(log);
    		
    	}
    	
    	return xValid && yValid ;
    		
    	
    	
    }
    private boolean areaIsBlank(Dimension area) {
    	
    	
    	JButton target = boardButtons[(int)area.getWidth()][(int)area.getHeight()];
    	
    	if(target.getIcon() == null || target.getIcon() == this.emptyIcon) return true;
    	else return false;
    }
    
    private boolean isMine(Dimension area) {
    	JButton target = boardButtons[(int)area.getWidth()][(int)area.getHeight()];
    	if(target.getIcon() == myIcon) return true;
    	else return false;
    	
    	
    }
    private boolean eatIsValid(Dimension from , Dimension to , boolean mineMove, String user) {
    	
      	

    	Integer fromX ,fromY ;
    	
    	fromY = from.width ;
    	fromX  = from.height ;
    	
    	Integer toX, toY ;
    	toY = to.width;
    	toX = to.height; 
    	
    	Integer xPath, yPath;
    	xPath = fromX- toX;
    	yPath = fromY -toY ;
    	
    	Integer targetX , targetY ;
    	
    	if(xPath > 0) {
    		
    		targetX = fromX - 1;
    		
    	}else targetX = fromX + 1 ;
    	
    	if(yPath > 0) {
    		targetY = fromY - 1;
    	}else targetY = fromY + 1 ;
    	
    	
    	
    	
    	JButton target = boardButtons[targetY][targetX];
    	if(mineMove) {
    		if(target.getIcon() == myIcon){
    			return false;
    		}
    	}
    	
    	if(target.getIcon() ==  emptyIcon) {
    		return false;
    	}else {

        	target.setIcon(emptyIcon);
        	String log = user + " - move <" + from.width+ "-" + from.height+"> to <" + to.width + "-" + to.height + ">" ;
    		log += ", piece at <" + targetY +"-" + targetX  + "> is taken" ;
    		System.out.println(log);
    		appendLog(log);

        	return true ; 
    		
    	}

    	
    	
    
    	

    	
    	
    	
    	
    }
    
    
    


}

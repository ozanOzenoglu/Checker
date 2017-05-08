
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.net.*;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.SwingUtilities;


public class ClientService extends  javax.swing.JFrame implements Runnable {

        static Socket clientSocket = null;
        static PrintStream os = null;
        static DataInputStream is = null;
        static int MAXPLAYER = 10;
        
        static int port_number;
        static int myPort;
        static String host;
        static String userName;
        DefaultListModel model;
        static Player[] playerList = new Player[10];
        Socket playerSocket ;
        
    
    public ClientService() {
    }

    public ClientService(String SunucuAdresi, int PortAdresi, String KullaniciAdi , int myServerPort) {
        port_number=PortAdresi;
        host=SunucuAdresi;
        userName=KullaniciAdi;
        myPort = myServerPort;
        initComponents();
        setVisible(true);
        model  = (DefaultListModel) jList1.getModel();
         this.addWindowListener(new WindowAdapter(){
         public void windowClosing(WindowEvent we){
             exit();
             dispose();
         }
  });
        
        try {
			clientSocket = new Socket(host, port_number);
			
			os = new PrintStream(clientSocket.getOutputStream());
			is = new DataInputStream(clientSocket.getInputStream());
			} catch (UnknownHostException e) {
			System.err.println("Sunucu bulma hatasi");
			} catch (IOException e) {
			System.err.println("Sunucu baglanti hatasi");
			}
			         
			        /*
        	Thread thread1 = new Thread(this);
		 	thread1.start(); */
		 	
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Sohbet"));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton1.setText("Gonder/Oyuna Basla");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        jList1.setModel(new DefaultListModel());
        jScrollPane2.setViewportView(jList1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {                                      
                if (clientSocket != null && os != null && is != null) {
                        os.println(jTextField1.getText());  
                        String name = jList1.getSelectedValue().toString();
                        playerSocket = getPlayerByName(name).getSocket();
                       
                      new Game(playerSocket,userName,true,"blue"); // first move belongs client
                        	
                    
                        
                }
                
                
                System.out.println("Secilen Kullanici:" + jList1.getSelectedValue().toString());
    }                                     


    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new ClientService().setVisible(true);
            }
        });
    }
    
    
        private void ClientAyarlar() {
   


                        
    }
    
    
    // Variables declaration - do not modify                     
    private javax.swing.JButton jButton1;
    private javax.swing.JList jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration                   
 public void run() {
                        String responseLine;
                        os.println(userName);
                        os.println(Integer.toString(myPort));
                        try{ 
			while ((responseLine = is.readLine()) != null) {
                            if(responseLine.contains("AddPlayer")){
                            	String[] informationOfPlayer = responseLine.split(" ");
                            	this.AddPlayer(informationOfPlayer[1], informationOfPlayer[2], informationOfPlayer[3]);
                               model.addElement(informationOfPlayer[1]);                                 
                            }
                            else if(responseLine.contains("RemovePlayer")){
//                               jTextArea2.append(responseLine.substring(14));
                            	String[] informationOfPlayer = responseLine.split(" ");
                                String playerName = informationOfPlayer[1];
                                this.KullaniciSil(playerName);
                                this.removePlayerByName(playerName);
                                KullaniciSil(playerName);                                 
                            }
                            else{
                            jTextArea1.append(responseLine + "\n");
			}
                        }
                        jTextArea1.append(responseLine + "\n");
                        } catch (IOException e) {
                                System.err.println("Iletisim hatasi 2");
                        }   
    }

    private void  KullaniciSil(String CikanKullanici) {
        
           model.removeElement(CikanKullanici);
    }
    
    
    private void AddPlayer(String name , String address, String port) {
    	
    	for(int i = 0 ; i < MAXPLAYER ; i++) {
    		if(playerList[i] == null) {
    			playerList[i] = new Player(name,address,port);
    			System.out.println(this.userName+"(): eklenen player: " + name + " " + address + " " + port);
    			break; 
    		}
    	}
    	
    }
    
    private Player getPlayerByName(String name) {
    	for(int i = 0 ; i < MAXPLAYER ; i++) {
    		if(playerList[i]!= null && playerList[i].getName().contains(name)) return playerList[i];
    		
    		
    	}
    	System.out.println("Lan player bulunamad� ?" + name);
    	return null;
    }
    
    private int getIndexOfPlayerByName(String name){
    	for(int i = 0 ; i < MAXPLAYER ; i++){
    		if(playerList[i].getName().contains(name)) return i;
    	}
    	return -1;
    }
    
    private void removePlayerByName(String name){
    	
    	int indexOfPlayer = getIndexOfPlayerByName(name);
    	if(indexOfPlayer != -1) {
    		playerList[indexOfPlayer] = null;
    		System.out.println(name +" adli kullaniciyi listeden sildik ");
    	}
    	else {
    		System.err.append(name+" adli kullanici silinemedi error code:"+indexOfPlayer);
    		
    	}
    	
    }

    public static void exit() {
        os.println("/cik");
        
    }
    

}

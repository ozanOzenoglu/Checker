
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class client extends JFrame implements ActionListener{

	
        JLabel label1 = new JLabel("Sunucu Adresi : ");
        JLabel label2 = new JLabel("Port No : ");
        JLabel label3 = new JLabel("Oyuncu Adi : ");
        
        JTextField SunucuAdres = new JTextField("127.0.0.1" , 10);
        JTextField PortNo = new JTextField("1234" , 10);
        JTextField UserName = new JTextField("User" , 10);
        
        JButton button1 = new JButton("Oyun Oyna");
       
        public client(){
        	
        	
            
            setTitle("Giris");
            setLayout(null);
            
            add(label1);
            label1.setBounds(10, 10, 100, 20);
            
            add(SunucuAdres);
            SunucuAdres.setBounds(110, 10, 80, 20);
            
            add(label2);
            label2.setBounds(10, 40, 100, 20);

            add(label2);
            add(PortNo);
            PortNo.setBounds(110, 40, 80, 20);
            
            add(label3);
            label3.setBounds(10, 70, 100, 20);
            
            add(UserName);
            UserName.setBounds(110, 70, 80, 20);
            
            add(button1);
            button1.setBounds(10, 100, 180, 25);
            button1.addActionListener(this);
            
            setSize(230, 200);
            setVisible(true);    
            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        }

    public static void main(String[] args) {
    	
    	if(args.length>0 ){
    		
            Server serverInstance = new Server(0,args[2]);
            Thread server = new Thread(serverInstance); // 0 means a random port which avaiable
            server.start();
            
            Thread client = new Thread(new ClientService(args[0],Integer.parseInt(args[1]),args[2],serverInstance.getCurrentPort()));
            client.start();
    		
    	}
    	else {
    		new client();
    	}
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
     if (e.getSource() == button1) {
         // Button Tıklandı
        
        String SunucuAdresi = SunucuAdres.getText();
        int PortAdresi = Integer.parseInt(PortNo.getText());
        String KullaniciAdi =UserName.getText();
        dispose();
        
        Server serverInstance = new Server(0,KullaniciAdi);
        Thread server = new Thread(serverInstance); // 0 means a random port which avaiable
        server.start();
        
        Thread client = new Thread(new ClientService(SunucuAdresi,PortAdresi,KullaniciAdi,serverInstance.getCurrentPort()));
        client.start();
        

        dispose();   
     }

    }
}

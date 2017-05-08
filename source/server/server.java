
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class server extends JFrame implements ActionListener{
        
        JPanel panel1 = new JPanel();
        JLabel label1 = new JLabel("Port No : ");
        JTextField textfield1 = new JTextField("1234" , 5);
        JButton button1 = new JButton("Start Server");
       
        public server(){
        	
            add(panel1);
            panel1.add(label1);
            panel1.add(textfield1);
            panel1.add(button1);
            button1.addActionListener(this);
            
            setSize(200, 100);
            setVisible(true);    	
            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        }

    public static void main(String[] args) {
    	if(args.length > 0 ) {
    		try{
    			new ServerService(Integer.parseInt(args[0]));
    		}catch(Exception e){
    			System.err.println(e.toString());
    		}
    	}else {
    		
    	
        new server();
    	}
    	
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
     if (e.getSource() == button1) {
         int ServerPort = Integer.parseInt(textfield1.getText());
         
        try {
            new ServerService(ServerPort);
        } catch (IOException ex) {
        }
        dispose();   
     }

    }
}

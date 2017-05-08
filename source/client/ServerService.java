import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.net.*;
public class ServerService extends javax.swing.JFrame implements Runnable{
    
    static  Socket istemciSoket = null;
    static  ServerSocket sunucuSoket = null;
    static  servis t[] = new servis[10];
    static String KullaniciListesi[]=new String[10];
   
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    
    public ServerService() {
    }

    public ServerService(int GelenPortNo) throws IOException{
    initComponents();
    setVisible(true);

    sunucuSoket = new ServerSocket(GelenPortNo);

    ServerAyarlar();
    }
        
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new ServerService().setVisible(true);
            }
        });
    }
    
    
    private void ServerAyarlar() {

            Thread thread2 = new Thread(this);
            thread2.start();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables

    // End of variables declaration//GEN-END:variables
public void run(){
            jTextArea1.append("Sunucu Baslatiliyor\n");
            	while(true){
	    try {
                
		istemciSoket = sunucuSoket.accept();  // Burada Sunucumuz istemci gelmesini bekliyor.
                jTextArea1.append("Yeni Kullanici Baglandi\n");
		for(int i=0; i<=9; i++){
		    if(t[i]==null)
			{
			    (t[i] = new servis(istemciSoket,t,KullaniciListesi)).start(); // Yeni istemci gelince onun i�in istemciLif olu�turuluyor ve yine dinleme moduna geciyor.
			    break;
			}
		}
	    }
	    catch (IOException e) {
		System.out.println(e);}
	}
    }
}

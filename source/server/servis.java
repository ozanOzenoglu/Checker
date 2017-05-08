import java.io.*;
import java.net.*;
import java.util.*;
class servis extends Thread{
    String KullaniciListesi[];
    DataInputStream is = null;
    PrintStream os = null;
    Socket clientSocket = null;       
    servis t[];  // list of players
    String port;
   
    
    String adress;
    String isim;
    int KullaniciListeNumarasi;
    
    public servis(Socket clientSocket, servis[] t, String KullaniciListesi[]){
	this.clientSocket=clientSocket;
        this.t=t;
        this.KullaniciListesi=KullaniciListesi;
    }
    public void run() {
	String line;

	try{
	    is = new DataInputStream(clientSocket.getInputStream()); 
	    os = new PrintStream(clientSocket.getOutputStream());
	    isim = is.readLine();
	    adress = clientSocket.getInetAddress().getHostAddress();
	    port = is.readLine(); // read port
            os.println("isminiz : " + isim +"port numaniz"+port.toString());
	    os.println("Sohbetten cikmak icin /cik yaziniz"); 
	    	FileOutputStream fos = null;
			
	    	try {
				 fos = new FileOutputStream("server.log",true);
				fos.write(new String(adress + " " + port + " connected \n").getBytes());
				
				
			}catch(Exception e){
				System.err.println(e.toString());
			}
			finally {
				fos.close();
			}
			
			
            KullaniciListesiGonder();
            
	    for(int i=0; i<=9; i++)
		if (t[i]!=null && t[i]!=this)  {
		    t[i].os.println("Server: "+isim+" isimli kullanici Server'a baglandi" );
                   // t[i].os.println("AddPlayer "+isim+" "+adress+" " +port);
                    
                }
	    while (true) { // client'den ald���n bilgiyi b�t�n client'lere post et.
	    	
	    	line = is.readLine();
                if(line.startsWith("/cik")) break; //Determine clients exit or close socket!
                 
		for(int i=0; i<=9; i++)
		    if (t[i]!=null)  t[i].os.println("<"+isim+"> "+line); 
	   
	    
	    }
	    
		
    	try {
			 fos = new FileOutputStream("server.log",true);
			fos.write(new String(adress + " " + port + " disconnected \n").getBytes());
			
			
		}catch(Exception e){
			System.err.println(e.toString());
		}
		finally {
			fos.close();
		}
	    
	    for(int i=0; i<=9; i++) // diger clientlere bu clientin ciktigina dair bilgi mesaj� ver.
		if (t[i]!=null && t[i]!=this){  
		    t[i].os.println("Server:"+isim+" isimli kullanici servredan ayrildi" );
	
                    t[i].os.println("RemovePlayer "+isim); // Burda diger clientlere bu kullanciyi listeden cikarmasi i�in komut yolluyoruz.
                }
            
            for(int i=0; i<=9; i++)
		if (t[i]==this) t[i]=null;  	    
            
	    
            is.close();
	    os.close();
	    clientSocket.close();
	}
	catch(IOException e){};
    }

    private void KullaniciListesiGonder() { // hizmet verilen client'e serverdaki clientlerin bilgilerini g�nder.
           for(int i=0; i<10; i++){
            if(t[i]!=null){
                os.println("AddPlayer "+t[i].isim+" "+t[i].adress + " " + t[i].port);
               if(t[i]!= this) t[i].os.println("AddPlayer "+ this.isim + " "+ this.adress + " " +this.port);
            }
           }
    }

    /*private void KullaniciListesineEkle(String isim) { // Hizmet verilen client'in bilgisini listeye ekle.
        for(int i=0; i<10; i++){
            if(KullaniciListesi[i]==null){
                KullaniciListesi[i]=isim;
                KullaniciListeNumarasi=i;
                break;
            }
        }
    }*/
/*
    private void KullaniciListesindenCikar() { // Burda serverdaki kullanici listesinden cikariyoruz ki diger clientlere bu client varmis gibi bilgi gitmesin
        KullaniciListesi[KullaniciListeNumarasi]=null;
    }
    
    private void OyuncuListesineEkle(String name ,String[] oyuncuAdresi) {
    	oyuncular.put(name, oyuncuAdresi);
    	
    	
    }
    
    private void OyuncuListesindenCikar(String name) {
    	oyuncular.remove(name);
    } */
}

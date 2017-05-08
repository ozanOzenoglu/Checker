/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import java.io.Serializable;

/**
 *
 * @author Oz
 */
public class Packet implements Serializable {
    
    private  int header;
    private Object message;
    
    public static final int START_GAME = 1;
    public static final int GAME_BOARD = 2;
    public static final int GAME_END = 3;
    public static final int GAME_REQUEST = 4 ; 
    public static final int ACCEPT = 5;
    public static final int DECLINE = 6;
    public static final int MOVE = 7;
    public static final int CHAT = 8;
    public static final int TEMP = -1;
    
    
    public Packet(int header , Object message) {
        this.header = header;
        this.message = message;
    }
    
    @Override
    public String toString() {
        return (String) this.message;
    }
    
    public int getType() {
    	return this.header;
    }
    
    
}

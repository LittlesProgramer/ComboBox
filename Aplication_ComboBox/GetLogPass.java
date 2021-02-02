package Aplication_ComboBox;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

final public class GetLogPass { // Modu³ zwracaj¹cy Mapê Loginów-Hase³
	/*public static void main(String[] args){
		System.out.println("Jajca jak berety");
		Map<String,Integer> mp = getMapa();
		for(Map.Entry<String, Integer> el: mp.entrySet()){
			System.out.println("el: "+el);
		}
	}*/
	public Map<String,Integer> getMapa(){ //usun static
		
		  Map<String,Integer> mapa = new HashMap<String,Integer>();
	      byte tab[] = null;
	      try{
	        DataInputStream d = new DataInputStream(new FileInputStream("./zapisBiust.txt"));
	        DataInputStream n = new DataInputStream(new FileInputStream("./zapisNames.txt"));
	        
	        tab = new byte[d.available()];
	        
	        while(d.available() != 0){
	        	mapa.put(n.readUTF(),d.readInt());
	        }
	      }catch(FileNotFoundException e){
	    	  JOptionPane.showMessageDialog(null, e);
	      }catch(IOException e){
	    	  JOptionPane.showMessageDialog(null, e);
	      }/*catch(NumberFormatException e){
	    	  JOptionPane.showMessageDialog(null, "Proszê wprowadŸ cyfry");
	      }*/
	      return mapa;
	}
}

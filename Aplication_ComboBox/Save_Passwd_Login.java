package Aplication_ComboBox;
import java.util.Map;
import java.io.DataOutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JOptionPane;

final public class Save_Passwd_Login {
	static File fName = new File("./zapisNames.txt");
	static File fBiust = new File("./zapisBiust.txt");
	   
	public static void saveMap(Map<String,Integer> mapa){//Metoda dopisuj¹ca do pliku u¿ytkowników
		  int sizeMap = mapa.size();
	      String name[] = new String[sizeMap];
	      Integer val[] = new Integer[sizeMap];
	      int x = 0;
	      
	      try{
	    	  DataOutputStream mapName = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fName,true))); //true - dopisanie
	    	  DataOutputStream mapBiust = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fBiust,true))); // true - dopisanie
	    	  for(Map.Entry<String, Integer> el : mapa.entrySet()){
	        	  name[x] = el.getKey();
	        	  //System.out.println("name["+x+"] = "+name[x]);
	        	  mapName.writeUTF(name[x]);
	        	  val[x] = el.getValue();
	        	  //System.out.println("val["+x+"] = "+val[x]);
	        	  mapBiust.writeInt(val[x]);
	        	  x++;
	          }
	    	  mapName.close();
	    	  mapBiust.close();
	    	  
	      }catch(FileNotFoundException e){ 
	    	  JOptionPane.showMessageDialog(null, "Nie ma takiego folderu/pliku: "+e); 
	      }catch(IOException e){
	    	  JOptionPane.showMessageDialog(null, "B³¹d wejscia/wyjœæia");
	      }
	}
	
	public static void saveDelMap(Map<String,Integer> mapa){//Metoda usuwaj¹ca u¿ytkowników
		  int sizeMap = mapa.size();
	      String name[] = new String[sizeMap];
	      Integer val[] = new Integer[sizeMap];
	      int x = 0;
	      
	      try{
	    	  DataOutputStream mapName = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fName))); //true - dopisanie
	    	  DataOutputStream mapBiust = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fBiust))); // true - dopisanie
	    	  for(Map.Entry<String, Integer> el : mapa.entrySet()){
	        	  name[x] = el.getKey();
	        	  mapName.writeUTF(name[x]);
	        	  val[x] = el.getValue();
	        	  mapBiust.writeInt(val[x]);
	        	  x++;
	          }
	    	  mapName.close();
	    	  mapBiust.close();
	    	  
	      }catch(FileNotFoundException e){ 
	    	  JOptionPane.showMessageDialog(null, "Nie ma takiego folderu/pliku: "+e); 
	      }catch(IOException e){
	    	  JOptionPane.showMessageDialog(null, "B³¹d wejscia/wyjœæia");
	      }
	}
}

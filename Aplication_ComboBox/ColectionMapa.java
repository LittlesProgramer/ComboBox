package Aplication_ComboBox;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.EventHandler;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JScrollPane;
import java.util.Map;
import java.util.TreeMap;
import java.io.File;
import java.io.BufferedOutputStream;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class ColectionMapa extends JDialog{
	private static final long serialVersionUID = 1L;
	Map<NandS,PersonDate> map = new HashMap<NandS,PersonDate>();
	boolean cashLogika = false;
	
	JLabel tytul = new JLabel("<html><h1><i>Kolekcja Oparta o Mapê</i></h1><hr></html>");
	JLabel name = new JLabel("podaj imie: ");
	JTextField nameT = new JTextField(10);
	JLabel surname = new JLabel("podaj nazwisko: ");
	JTextField surnameT = new JTextField(10);
	JButton insert = new JButton("WprowadŸ dane do bazy");
	JLabel street = new JLabel("podaj ulicê: ");
	JTextField streetT = new JTextField(10);
	JLabel town = new JLabel("podaj miasto: ");
	JTextField townT = new JTextField(10);
	JLabel cash = new JLabel("podaj pensjê: ");
	JTextField cashT = new JTextField(10);
	JLabel dataUr = new JLabel("podaj date ur: ");
	JTextField dataUrT = new JTextField(10);
	JButton show = new JButton("Poka¿ wszystkie rekordy");
	JButton search = new JButton("ZnajdŸ rekord");
	JButton delete = new JButton("Usuñ rekord");
	JTextArea showAll = new JTextArea(10,20);
	JCheckBox sort = new JCheckBox("Posortowane rekordy bez powtórzeñ");
	
	public ColectionMapa(final PanelWithMenu pwm){
		super(pwm,"Lista Oparta o Mapê",false);
		GridBagLayout layout = new GridBagLayout();
		this.setLayout(layout);
		pwm.setExtendedState(JFrame.ICONIFIED);
		
		tytul.setForeground(Color.blue);
		this.add(tytul,new GBCm(0,0,8,1).setAnchor(GBCm.CENTER));
		this.add(name,new GBCm(0,1,1,1).setFill(GBCm.HORIZONTAL).setWeight(1, 0).setInsets(1));
		this.add(nameT,new GBCm(1,1,1,1).setFill(GBCm.HORIZONTAL).setWeight(100, 0).setInsets(1));
		this.add(surname,new GBCm(2,1,1,1).setFill(GBCm.HORIZONTAL).setWeight(1, 0).setInsets(1));
		this.add(surnameT,new GBCm(3,1,1,1).setFill(GBCm.HORIZONTAL).setWeight(100, 0).setInsets(1));
		this.add(insert,new GBCm(4,1,4,1).setFill(GBCm.HORIZONTAL).setWeight(10, 0).setInsets(1));
		
		this.add(street,new GBCm(0,2,1,1).setInsets(1));
		this.add(streetT,new GBCm(1,2,1,1).setFill(GBCm.HORIZONTAL).setInsets(1).setWeight(100, 0));
		this.add(town,new GBCm(2,2,1,1).setInsets(1));
		this.add(townT,new GBCm(3,2,1,1).setInsets(1).setFill(GBCm.HORIZONTAL).setWeight(100, 0));
		this.add(cash,new GBCm(4,2,1,1).setInsets(1));
		this.add(cashT,new GBCm(5,2,1,1).setInsets(1).setWeight(100, 0).setFill(GBCm.HORIZONTAL));
		this.add(dataUr,new GBCm(6,2,1,1).setInsets(1));
		this.add(dataUrT,new GBCm(7,2,1,1).setInsets(1).setFill(GBCm.HORIZONTAL).setWeight(100, 0));
		
		JScrollPane scroll = new JScrollPane(showAll);
		showAll.setBorder(BorderFactory.createLineBorder(Color.blue));
		showAll.setLineWrap(true);
		this.add(scroll,new GBCm(0,3,8,3).setInsets(1).setWeight(100, 100).setFill(GBCm.BOTH));
		
		//this.add(noRepeat,new GBCm(0,7,4,1).setInsets(1).setAnchor(GBCm.EAST));
		this.add(sort,new GBCm(5,7,4,1).setInsets(1).setAnchor(GBCm.WEST));
		this.add(show,new GBCm(0,8,8,1).setInsets(1).setWeight(100, 0).setFill(GBCm.HORIZONTAL));
		
		this.add(search,new GBCm(0,9,8,1).setInsets(1).setWeight(100, 0).setFill(GBCm.HORIZONTAL));
		this.add(delete,new GBCm(0,10,8,1).setInsets(1).setWeight(100, 0).setFill(GBCm.HORIZONTAL));
		
		ActionListener insertAction = EventHandler.create(ActionListener.class, this, "insertAction");
		insert.addActionListener(insertAction);
		ActionListener showAllRecord = EventHandler.create(ActionListener.class, this, "showAllRecord");
		show.addActionListener(showAllRecord);
		ActionListener searchMethod = EventHandler.create(ActionListener.class, this, "searchMethod");
		search.addActionListener(searchMethod);
		ActionListener remRecord = EventHandler.create(ActionListener.class, this, "remRecord");
		delete.addActionListener(remRecord);
		
		this.pack();
		this.setVisible(true);
		
		this.addComponentListener(new ComponentAdapter(){
			@Override
			public void componentHidden(ComponentEvent e){
				pwm.setExtendedState(JFrame.NORMAL);
			}
		});
	}
	
	public void remRecord(){
		NandS ns = null;
		try{
			ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File("Mapa.txt"))));
		    map = (HashMap<NandS,PersonDate>)in.readObject();
		    in.close();
		}catch(FileNotFoundException file){
			JOptionPane.showMessageDialog(null,"Nie mo¿na odnaleŸæ pliku Mapa.txt "+file);
		}catch(IOException io){
			JOptionPane.showMessageDialog(null, "Nie mogê odczytæ pliku Mapa.txt "+io);
		}catch(ClassNotFoundException clas){ JOptionPane.showMessageDialog(null, "Nie mogê odnaleŸæ klasy podczas próby odczytu "+clas); }
	         
		    for(Map.Entry<NandS, PersonDate> el : map.entrySet()){
		    	
		    	if(nameT.getText().equals(el.getKey().getName()) && surnameT.getText().equals(el.getKey().getSurname())
		    	&& Double.toString(el.getValue().getCash()).equals(cashT.getText())){
		    		ns = el.getKey();
		    		JOptionPane.showMessageDialog(null,"Usuniêto rekord");
		    		cashLogika = true;
		    	}
		    }
		    if(ns != null)map.remove(ns);
		    if(cashLogika == false)	JOptionPane.showMessageDialog(null, "Nie znaleziono rekordu dla imie && nazwisko && pensja !");
		    cashLogika = false;
		    
		 try{
			 ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File("Mapa.txt"))));
			 out.writeObject(map);
			 out.close();
		 }catch(FileNotFoundException file){
			 JOptionPane.showMessageDialog(null, "Nie mo¿na odnaleŸæ pliku Mapa.txt podczas próby zapisu zmiany rekordu !"+file);
		 }catch(IOException io){ JOptionPane.showMessageDialog(null, "B³¹d zapisu pliku Mapa.txt"+io); }
	}
	
	public void searchMethod(){
		showAll.setText("");
		
		try{
			ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File("Mapa.txt"))));
		    map = (HashMap<NandS,PersonDate>)in.readObject();
		    in.close();
		}catch(FileNotFoundException file){
			JOptionPane.showMessageDialog(null,"Nie mo¿na odnaleŸæ pliku Mapa.txt "+file);
		}catch(IOException io){
			JOptionPane.showMessageDialog(null, "Nie mogê odczytæ pliku Mapa.txt "+io);
		}catch(ClassNotFoundException clas){ JOptionPane.showMessageDialog(null, "Nie mogê odnaleŸæ klasy podczas próby odczytu "+clas); }
	         
		    for(Map.Entry<NandS, PersonDate> el : map.entrySet()){
		    	
		    	if(nameT.getText().equals(el.getKey().getName()) && surnameT.getText().equals(el.getKey().getSurname())){
		    		JOptionPane.showMessageDialog(null,"Znaleziono rekord");
		    		showAll.append(el.getKey().getName()+" "+el.getKey().getSurname()+"\n");
		    		cashLogika = true;
		    	}
		    }
		    if(cashLogika == false)	JOptionPane.showMessageDialog(null, "Nie znaleziono rekordu dla imie && nazwisko !");
		    cashLogika = false;
	}
	@SuppressWarnings("unchecked")
	public void showAllRecord(){
		
		try{
			ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream("Mapa.txt")));
			map = (HashMap)in.readObject();
			in.close();
		}catch(FileNotFoundException file){
			JOptionPane.showMessageDialog(null, file);
		}catch(IOException io){ 
			JOptionPane.showMessageDialog(null, io); 
		}catch(ClassNotFoundException clas){ JOptionPane.showMessageDialog(null, clas); } 
		
		showAll.setText("");
		
		if(sort.isSelected()){
			Map<NandS,PersonDate> maps = new TreeMap<NandS,PersonDate>();
			for(Map.Entry<NandS, PersonDate> el : map.entrySet()){ maps.put(el.getKey(), el.getValue()); }
			for(Map.Entry<NandS, PersonDate> el : maps.entrySet()){
				showAll.append("imie: "+el.getKey().getName()+" nazwisko: "+el.getKey().getSurname()+" ulica: "+el.getValue().getStreet()+
				" miasto: "+el.getValue().getTown()+" pensja = "+el.getValue().getCash()+" data_ur: "+el.getValue().getData()+"\n");
			}
		}else{
			for(Map.Entry<NandS, PersonDate> el : map.entrySet()){
				showAll.append("imie: "+el.getKey().getName()+" nazwisko: "+el.getKey().getSurname()+" ulica: "+el.getValue().getStreet()+
				" miasto: "+el.getValue().getTown()+" pensja = "+el.getValue().getCash()+" data_ur: "+el.getValue().getData()+"\n");
			}
		}
	}
	public void insertAction(){	
		Boolean name = false,sur = false,street = false,town = false,casch = false,data_ur = false;
		
		//Sprawdzenie poprawnoœci wprowadzonych danych
		Pattern patName = Pattern.compile("[a-zA-Z¹êœæŸ¿³óñ]+");
		Matcher matName = patName.matcher(nameT.getText());
		
		if(name = matName.matches()){
			System.out.println("imie to: "+nameT.getText());
		}else{ JOptionPane.showMessageDialog(null, "Proszê wprowadŸiæ poprawnie imie "); }
		
		Pattern patSurname = Pattern.compile("[a-zA-Z¹êœæŸ¿³óñ]+");
		Matcher matchSurname = patSurname.matcher(surnameT.getText());
		
		if(sur = matchSurname.matches()){
			
		}else{ JOptionPane.showMessageDialog(null, "Proszê wprowadŸ poprawne nazwisko "); }
		
		Pattern patStreet = Pattern.compile("[[¹êœæŸ¿³óñ0-9]+\\D]+");
		Matcher matchStreet = patStreet.matcher(streetT.getText());
		
		if(street = matchStreet.matches()){
			
		}else{ JOptionPane.showMessageDialog(null, "Proszê wprowadŸiæ poprawn¹ ulicê"); }
		
		Pattern patTown = Pattern.compile("[[¹êœæŸ¿³óñ]\\D]+");
		Matcher matchTown = patTown.matcher(townT.getText());
		
		if(town = matchTown.matches()){
			
		}else{ JOptionPane.showMessageDialog(null, "Proszê wprowadziæ poprawnie miasto"); }
		
		Pattern patCash = Pattern.compile("[\\d.]+");
		Matcher matchCash = patCash.matcher(cashT.getText());
		
		if(casch = matchCash.matches()){
			
		}else{ JOptionPane.showMessageDialog(null, "WprowadŸ poprawnie wyp³atê"); }
		
		Pattern patDate = Pattern.compile("\\d{2}-\\d{2}-\\d{4}");
		Matcher matchDate = patDate.matcher(dataUrT.getText());
		System.out.println("name = "+name+" sur = "+sur+" street = "+street+" town = "+town+" casch = "+casch+" data_ur = "+data_ur);
		
		if(data_ur = matchDate.matches()){
			String tab[] = dataUrT.getText().split("-");
			int day = Integer.parseInt(tab[0]);
			int msc = Integer.parseInt(tab[1]);
			int year = Integer.parseInt(tab[2]);
			
			if((day <= 31 && day > 0) && (msc <= 12 && msc > 0) && (year >= 1900 && year <= 2019)){
				System.out.println("Ok");
				data_ur = true;
			}else{ JOptionPane.showMessageDialog(null, "Wprowadz date wed³ug formatu dd-mm-yyyy"); data_ur = false; }
			
		}else{ JOptionPane.showMessageDialog(null,"Proszê wprowadzic datê wed³ug formatu dd-mm-yyyy"); }
		
		if(name == true && sur == true && street == true && town == true && casch == true && data_ur == true){
			System.out.println("Poprawnie wprowadzone dane");
			NandS n = new NandS();
			n.setName(nameT.getText());
			n.setSurname(surnameT.getText());
			PersonDate pd = new PersonDate();
			pd.setStreet(streetT.getText());
			pd.setTown(townT.getText());
			pd.setCash(Double.parseDouble(cashT.getText()));
			String tab[] = dataUrT.getText().split("-");
			int day = Integer.parseInt(tab[0]);
			int msc = Integer.parseInt(tab[1]);
			int year = Integer.parseInt(tab[2]);
			pd.setDate(day, msc, year);
			//zapis danych do mapy TreeMap
			n.setPersonDate(pd);////////////////////////////////////////////////////////////
			zapisDanych(n,pd);
			
		}else{ System.out.println("Popraw dane wed³ug wczeœniejszych instrukcji"); }
	}
	public void zapisDanych(NandS n,PersonDate pd){
		File f = new File("Mapa.txt");
		
		try{
			FileInputStream in = new FileInputStream(f);
			BufferedInputStream buf = new BufferedInputStream(in);
			ObjectInputStream ObjectIn = new ObjectInputStream(buf);
			map = (HashMap)ObjectIn.readObject();
			ObjectIn.close();
		}catch(FileNotFoundException file){
			JOptionPane.showMessageDialog(null, file);
		}catch(IOException io){
			JOptionPane.showMessageDialog(null, io);
		}catch(ClassNotFoundException clas){ JOptionPane.showMessageDialog(null, clas); }
	
		  map.put(n,pd);

		try{
		  FileOutputStream fout = new FileOutputStream(f);
		  BufferedOutputStream out = new BufferedOutputStream(fout);
		  ObjectOutputStream ObjectOut = new ObjectOutputStream(out);
		  ObjectOut.writeObject(map);
		  ObjectOut.flush();
		  ObjectOut.close();
		}catch(FileNotFoundException file){
			JOptionPane.showMessageDialog(null, file);
		}catch(IOException io){ JOptionPane.showMessageDialog(null, io); }
		
	}
}

class NandS implements Comparable<NandS>,Serializable{
	private static final long serialVersionUID = 1L;
	private String name;
	private String surname;
	private PersonDate pd;
	public void setName(String name){ this.name = name; }
	public void setSurname(String surname){ this.surname = surname; }
	public void setPersonDate(PersonDate pd){ this.pd = pd; }
	public String getName(){ return name; }
	public String getSurname(){ return surname; }
	public PersonDate getPersonDate(){ return pd; }
	@Override
	public int compareTo(NandS other){
		int x;
		if((x = this.name.compareTo(other.name)) != 0){
		    return x;
		}else{
			if((x = surname.compareTo(other.surname)) != 0){
				return surname.compareTo(other.surname);
			}else{
				return Double.compare(this.pd.getCash(),other.pd.getCash());
			}
			
		}
	}
}
class PersonDate implements Serializable{
	private static final long serialVersionUID = 1L;
	private String street;
	private String town;
	private double cash;
	private Date dataUr;
	
	public void setStreet(String s){this.street = s;}
	public void setTown(String t){ this.town = t; }
	public void setCash(double c){ this.cash = c; }
	public void setDate(int d,int m,int y){
		GregorianCalendar data = new GregorianCalendar(y,m-1,d);
	    dataUr = data.getTime();
	}
	public String getStreet(){ return street; }
	public String getTown(){ return town; }
	public double getCash(){ return cash; }
	public Date getData(){ 
		return dataUr;
	}
}

class GBCm extends GridBagConstraints{
	private static final long serialVersionUID = 1L;
	public GBCm(int gridx,int gridy){ this.gridx = gridx; this.gridy = gridy; }
	public GBCm(int gridx,int gridy,int gridwidth,int gridheight){
		this.gridx = gridx; this.gridy = gridy; this.gridwidth = gridwidth; this.gridheight = gridheight;
	}
	public GBCm setFill(int fill){
		this.fill = fill; return this;
	}
	public GBCm setWeight(int weightx,int weighty){
		this.weightx = weightx; this.weighty = weighty; return this;
	}
	public GBCm setAnchor(int anchor){
		this.anchor = anchor; return this;
	}
	public GBCm setInsets(int insets){
		this.insets = new Insets(insets,insets,insets,insets); return this;
	}
	public GBCm setInsets(int top,int left,int bottom,int right){
		this.insets = new Insets(top,left,bottom,right); return this;
	}
}
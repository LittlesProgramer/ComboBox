package Aplication_ComboBox;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import javax.swing.JButton;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.beans.EventHandler;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedInputStream;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ColectionList extends JDialog{
	private static final long serialVersionUID = 1L;
	ArrayList<BazaDanych> lista = new ArrayList<BazaDanych>();
	WyborPlikow wp = new WyborPlikow();
	private File nameFile = null;
	
	JMenuBar pasek = new JMenuBar();
	JMenu menu = new JMenu("Plik");
	JMenuItem open = new JMenuItem("Wska¿ plik z danymi");
	
	JLabel tytul = new JLabel("<html><h1><i>Obs³uga Danych za Pomoc¹ Listy</i></h1><hr></html>");
	
	JLabel labImie = new JLabel("podaj imie: ");
	JLabel labNazwisko = new JLabel("podaj nazwisko: ");
	JLabel labWiek = new JLabel("podaj wiek: ");
	JLabel labPensja = new JLabel("podaj pensja: ");
	JLabel labDataUr = new JLabel("podaj datê ur: ");
	JLabel labUlica = new JLabel("podaj ulicê: ");
	
	JTextField textImie = new JTextField(7);
	JTextField textNazwisko = new JTextField(7);
	JTextField textWiek = new JTextField(7);
	JTextField textPensja = new JTextField(7);
	JTextField textDataUr = new JTextField(7);
	JTextField textUlica = new JTextField(7);
	
	JTextArea ekran = new JTextArea(10,30);
	
	JButton butDodaj = new JButton("Dodaj wiersz danych");
	JButton butSearch = new JButton("Szukaj wiersza danych");
	JButton butShow = new JButton("Wyœwietl wszystkie wiersze");
	JButton butRem = new JButton("Usuñ wyszukany wiersz danych");
	
	public ColectionList(final PanelWithMenu pwm){
		super(pwm,"Obs³uga Danych za Pomoc¹ Listy",false);
		pwm.setExtendedState(JFrame.ICONIFIED);
		this.setLayout(new GridBagLayout());
		
		//dodanie menu
		this.setJMenuBar(pasek);
		menu.setToolTipText("Menu pozwalaj¹ce na zapis i odczyt plików");
		pasek.add(menu); menu.add(open);
		
		//ustawienie architektury menu
		this.add(tytul,new GBCf(0,0,12,1).setInsets(1));
		tytul.setForeground(Color.blue);
		
		ActionListener actionAdd = EventHandler.create(ActionListener.class, this, "dodaj");
		ActionListener openMenu = EventHandler.create(ActionListener.class, this, "openMenu");
		ActionListener showAll = EventHandler.create(ActionListener.class, this, "pokazAllRecord");
		ActionListener searchRecord = EventHandler.create(ActionListener.class, this, "searchRecord");
		ActionListener remRecord = EventHandler.create(ActionListener.class, this, "remRecord");
		
		this.add(labImie,new GBCf(0,1,1,1).setInsets(1));
		this.add(textImie,new GBCf(1,1,1,1).setInsets(1).setFill(GBCf.HORIZONTAL).setWeight(1, 0));
		this.add(labNazwisko,new GBCf(2,1,1,1).setInsets(1));
		this.add(textNazwisko,new GBCf(3,1,1,1).setInsets(1).setFill(GBCf.HORIZONTAL).setWeight(1, 0));
		this.add(labWiek,new GBCf(4,1,1,1).setInsets(1));
		this.add(textWiek,new GBCf(5,1,1,1).setInsets(1).setFill(GBCf.HORIZONTAL).setWeight(1, 0));
		this.add(labPensja,new GBCf(6,1,1,1).setInsets(1));
		this.add(textPensja,new GBCf(7,1,1,1).setInsets(1).setFill(GBCf.HORIZONTAL).setWeight(1, 0));
		this.add(labDataUr,new GBCf(8,1,1,1).setInsets(1));
		this.add(textDataUr,new GBCf(9,1,1,1).setInsets(1).setFill(GBCf.HORIZONTAL).setWeight(1, 0));
		this.add(labUlica,new GBCf(10,1,1,1).setInsets(1));
		this.add(textUlica,new GBCf(11,1,1,1).setInsets(1).setFill(GBCf.HORIZONTAL).setWeight(1, 0));
		
		this.add(butDodaj,new GBCf(0,2,3,1).setInsets(1).setFill(GBCf.HORIZONTAL).setWeight(1, 0));
		butDodaj.setToolTipText("Przycisk pozwalaj¹cy dodaæ rekord do bazy po wczeœnijszej weryfikacji");
		this.add(butSearch,new GBCf(3,2,3,1).setInsets(1).setFill(GBCf.HORIZONTAL).setWeight(1, 0));
		butSearch.setToolTipText("Przycisk pozwalaj¹cy odnaleŸæ interesuj¹cy nas rekord po Nazwisku i Ulicy");
		this.add(butShow,new GBCf(6,2,3,1).setInsets(1).setFill(GBCf.HORIZONTAL).setWeight(1, 0));
		butShow.setToolTipText("Przycisk pozwalaj¹cy wyœwietliæ wszystkie rekordy znajduj¹ce siê w bazie");
		this.add(butRem,new GBCf(9,2,3,1).setInsets(1).setFill(GBCf.HORIZONTAL).setWeight(1, 0));
		butRem.setToolTipText("Przycisk pozwalaj¹cy usun¹æ z bazy, wczeœniej znaleziony rekord przez opcje \"Wyœwietl Wszystkie Wiersze\"");
		
		JScrollPane scroll = new JScrollPane(ekran);
		ekran.setBorder(BorderFactory.createLineBorder(new Color(10,10,250)));
		ekran.setWrapStyleWord(true);
		this.add(scroll,new GBCf(0,3,12,3).setInsets(1).setFill(GBCf.BOTH).setWeight(1, 1));
		
		//dodanie s³uchacza do menu open
		open.addActionListener(openMenu);
		//dodanie s³uchacza do przycisku dodaj
		butDodaj.addActionListener(actionAdd);
		//dodanie s³uchacza do przycisku poka¿
		butShow.addActionListener(showAll);
		//dodanie s³uchacza do przycisku znajdz
		butSearch.addActionListener(searchRecord);
		//dodanie s³uchacza do przycisku usuñ
		butRem.addActionListener(remRecord);
		
		this.setVisible(true);
		this.pack();

		this.addComponentListener(new ComponentAdapter(){
			@Override
			public void componentHidden(ComponentEvent e){
				pwm.setExtendedState(JFrame.NORMAL);
			}
		});
	}
	
	public void remRecord(){
		int index = 0;
		if(nameFile != null && nameFile.length() > 0){
			try{
			  ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(nameFile.getAbsoluteFile().toString())));
			  lista = (ArrayList<BazaDanych>)in.readObject();
			  in.close();
			  boolean warunek = false;
			  ekran.setForeground(Color.black);
			  
			  for(BazaDanych el : lista){
				  if(el.getNazwisko().equals(textNazwisko.getText()) && ( el.getUlica().equals(textUlica.getText()))){
					  
					  lista.remove(el);
					  ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(nameFile.getAbsoluteFile().toString())));
					  out.writeObject(lista);
					  out.flush();
					  out.close(); 
					  JOptionPane.showMessageDialog(null, "Usuniêto rekord z bazy");
					  warunek = true;
					  
				  }else if((index == (lista.size() - 1)) && (warunek == false)){ 
					  ekran.setForeground(Color.red); ekran.setText("Brak rekordu w bazie"); 
				  }
				  index++;
			  }
			          
			}catch(FileNotFoundException a){
				JOptionPane.showMessageDialog(null, "Nie mo¿na odnaleŸæ pliku listaDanych.txt, podczas wyœwietlenia wszystkich rekordów");
			}catch(IOException b){
				JOptionPane.showMessageDialog(null, "B³¹d otwarcia pliku potrzebnego podczas wyœwietlenia wszystkich rekordów");
			}catch(ClassNotFoundException c){ JOptionPane.showMessageDialog(null,"B³¹d dopasowania klasy"+c);}
		   
		}else{ JOptionPane.showMessageDialog(null, "Proszê wybierz plik listaDanych.txt, aby odczytaæ istniej¹c¹ listê danych"); }
	}
	
	@SuppressWarnings("unchecked")
	public void searchRecord(){
		int index = 0;
		if(nameFile != null && nameFile.length() > 0){
			try{
			  ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(nameFile.getAbsoluteFile().toString())));
			  lista = (ArrayList<BazaDanych>)in.readObject();
			  in.close();
			  boolean warunek = false;
			  
			          ekran.setCaretColor(Color.black);
			          ekran.setText("");
			  for(BazaDanych el : lista){
				  if(el.getNazwisko().equals(textNazwisko.getText()) && ( el.getUlica().equals(textUlica.getText()))){
					  ekran.setForeground(Color.green);
					  ekran.setText("znaleziono rekord"+"\n");
					  ekran.append(el.getDane());
					  warunek = true;
				  }else if((index == (lista.size() - 1)) && (warunek == false)){ 
					  ekran.setForeground(Color.red); ekran.setText("Brak rekordu w bazie"); 
				  }
				  index++;
			  }
			          
			}catch(FileNotFoundException a){
				JOptionPane.showMessageDialog(null, "Nie mo¿na odnaleŸæ pliku listaDanych.txt, podczas wyœwietlenia wszystkich rekordów");
			}catch(IOException b){
				JOptionPane.showMessageDialog(null, "B³¹d otwarcia pliku potrzebnego podczas wyœwietlenia wszystkich rekordów");
			}catch(ClassNotFoundException c){ JOptionPane.showMessageDialog(null,"B³¹d dopasowania klasy"+c);}
		   
		}else{ JOptionPane.showMessageDialog(null, "Proszê wybierz plik listaDanych.txt, aby odczytaæ istniej¹c¹ listê danych"); }
	}
	
	@SuppressWarnings("unchecked")
	public void pokazAllRecord(){
		ekran.setForeground(Color.black);
		if(nameFile != null && nameFile.length() > 0){
			try{
			  ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(nameFile.getAbsoluteFile().toString())));
			  lista = (ArrayList<BazaDanych>)in.readObject();
			  in.close();
			}catch(FileNotFoundException a){
				JOptionPane.showMessageDialog(null, "Nie mo¿na odnaleŸæ pliku listaDanych.txt, podczas wyœwietlenia wszystkich rekordów "+a);
			}catch(IOException b){
				JOptionPane.showMessageDialog(null, "B³¹d otwarcia pliku potrzebnego podczas wyœwietlenia wszystkich rekordów "+b);
			}catch(ClassNotFoundException c){ JOptionPane.showMessageDialog(null,"B³¹d dopasowania klasy"+c);}
		    ekran.setText("");
			for(BazaDanych el : lista){
		    	ekran.append(el.getDane()+"\n");
		    }
		}else{ JOptionPane.showMessageDialog(null, "Proszê wybierz plik listaDanych.txt, aby odczytaæ istniej¹c¹ listê danych"); }
	}
	
	@SuppressWarnings("unchecked")
	public void dodaj(){
		BazaDanych db = new BazaDanych(textImie.getText(),textNazwisko.getText(),textWiek.getText(),textPensja.getText(),
		textDataUr.getText(),textUlica.getText());
		boolean spr = db.checkData();

		Path path = Paths.get(".").resolve("listaDanych.txt").toAbsolutePath().normalize();
		
		if(!path.toFile().exists()){
			try{
			  Files.createFile(path);
			}catch(IOException a){ JOptionPane.showMessageDialog(null, a); }
		}else if(path.toFile().length() > 0){
			 try{
				 //Na przysz³oœæ nale¿y pamiêtaæ ¿e dane nie powinny byæ przy ka¿dym dodaniu do listy odczytywane z pliku
				 // to zmienjsza wydajnoœæ aplikacji tak mi siê wydaje
				 ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File(path.toFile().toString()))));
				 lista = (ArrayList<BazaDanych>)in.readObject();
				 in.close();
			   }catch(FileNotFoundException e){ 
				  JOptionPane.showMessageDialog(null,"Nie mogê znaleŸæ pliku do odczytu listy listaDanych.txt "+e); 
			   }catch(IOException v){ 
				  JOptionPane.showMessageDialog(null,"Nie mogê odczytaæ pliku do odczytu listaDanych.txt "+v); 
			   }catch(ClassNotFoundException g){ JOptionPane.showMessageDialog(null, "Nie mogê dopasowaæ klasy podczas odczytu pliku listaDanych.txt"); }
		}
		
		if(nameFile != null){
			if(spr){
				lista.add(db);
				JOptionPane.showMessageDialog(null, "Dadano rekord do bazy");
				try{
				   ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File(nameFile.toString()))));
				   out.writeObject(lista);
			       out.flush();
				   out.close();
				}catch(FileNotFoundException e){
				   JOptionPane.showMessageDialog(null, "Nie znaleziono pliku do zapisu listyDanych.txt +"+"\n"+"(musisz utworzyc ten plik samodzielnie w katalogu g³ównym aplikacji)");
				}catch(IOException v){ JOptionPane.showMessageDialog(null, "B³¹d przy zapisie listaDanych.txt "+v); }
			}else{ return; }
		}else{ JOptionPane.showMessageDialog(null, "Wybierz plik do zapisu"); return; }
		
	}
	public void openMenu(){
		//Wyœwietlenie okna wyboru plików
		int result = wp.showOpenDialog(null);
		
		if(result == JFileChooser.APPROVE_OPTION){
			File f = wp.getSelectedFile();
			nameFile = wp.getSelectedFile();
			
		}
	}
}
class WyborPlikow extends JFileChooser{
	private static final long serialVersionUID = 1L;
	private JLabel akcesorium = new JLabel();
	public WyborPlikow(){
		this.setCurrentDirectory(new File("."));
		this.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		this.setSelectedFile(new File("domyœlny.txt"));
		FileNameExtensionFilter filtrJPG = new FileNameExtensionFilter("JPEG&&JPG&&GIF","jpg","jpeg","gif");
		//this.setFileFilter(filtrJPG);
		FileNameExtensionFilter filtrTXT = new FileNameExtensionFilter("Texteki","txt");
		this.addChoosableFileFilter(filtrTXT);
		this.addChoosableFileFilter(filtrJPG);
		
		this.setAccessory(akcesorium);
		akcesorium.setBorder(BorderFactory.createLineBorder(Color.green));
		Toolkit tool = Toolkit.getDefaultToolkit();
		Dimension d = tool.getScreenSize(); d.width = 50; d.height = 97;
		akcesorium.setPreferredSize(d);
		
		this.addPropertyChangeListener(new PropertyChangeListener(){
			@Override
			public void propertyChange(PropertyChangeEvent e){
				if(e.getPropertyName() == JFileChooser.SELECTED_FILE_CHANGED_PROPERTY){
					File f = (File)e.getNewValue();
					
					if(f == null){
						akcesorium.setIcon(null); return;
					}
					
					ImageIcon icon = new ImageIcon(getSelectedFile().toPath().toAbsolutePath().normalize().toString());
					
					if(icon.getIconHeight() >= akcesorium.getHeight()){
						icon = new ImageIcon(icon.getImage().getScaledInstance(akcesorium.getHeight(), -1, Image.SCALE_DEFAULT));
					}
					akcesorium.setIcon(icon);
				}
			}
		});
	}
}

final class BazaDanych implements Serializable{
	private String imie = null;
	private String nazwisko = null;
	private String wiek = null;
	private String pensja = null;
	private String dataUr = null;
	private String ulica = null;
	public BazaDanych(String i,String n,String w,String p,String d,String u){
		this.imie = i; this.nazwisko = n; this.wiek = w; this.pensja = p; this.dataUr = d; this.ulica = u;
	}
	public boolean checkData(){
		boolean name = checkImie();
		boolean nazwisko = checkNazwisko();
		boolean wiek = checkWiek();
		boolean pensja = checkPensja();
		boolean dataUr = checkDataUr();
		boolean ulica = checkUlica();
		
		if(name == true  && nazwisko == true && wiek == true && pensja == true && dataUr == true && ulica == true){
			return true;
		}else{
			JOptionPane.showMessageDialog(null, "Proszê poprawiæ wprowadzane dane zgodnie z wczeœniejszymi wskazówkami"); 
			return false;
		}
	}
	//Funkcje zwracaj¹ce nazwisko i ulice 
	public String getNazwisko(){
		return nazwisko;
	}
	public String getUlica(){
		return ulica;
	}
	//Funkcja zwracaj¹ca dane zawarte w rekordach
	public String getDane(){
		return "imie: "+imie+" nazwisko: "+nazwisko+" wiek: "+wiek+" pensja: "+pensja+" dataUr: "+dataUr+" ulica: "+ulica;
	}
	//Funkcje sprawdzaj¹ce podane dane
	public Boolean checkImie(){
		Pattern pat = Pattern.compile("[\\D¹êæœŸó³ñ¿]+");
		Matcher match = pat.matcher(imie);
		
		if(match.matches()){
			return true;
		}else{ JOptionPane.showMessageDialog(null, "Imiê sk³ada siê z liter"); return false; }
	}
	public boolean checkNazwisko(){
		Pattern pat = Pattern.compile("[\\D¹êæœ¿Ÿó³ñ]+");
		Matcher match = pat.matcher(nazwisko);
		
		if(match.matches()){
			return true;
		}else{ JOptionPane.showMessageDialog(null, "Nazwisko sk³ada siê z liter"); return false;}
	}
	public boolean checkWiek(){
		Pattern pat = Pattern.compile("[\\d]+");
		Matcher match = pat.matcher(wiek);
		
		if(match.matches()){
			return true;
		}else{ JOptionPane.showMessageDialog(null, "Wiek sk³ada siê z samych cyfr"); return false;}
	}
	public boolean checkPensja(){
		Pattern pat = Pattern.compile("\\d+\\.\\d{1,3}");
		Matcher match = pat.matcher(pensja);
		
		if(match.matches()){
			return true;
		}else{ JOptionPane.showMessageDialog(null, "Pensja sk³ada siê z cyfr i od 1 do 3 miejsc po przecinku np: 2370.55"); return false; }
	}
	public boolean checkDataUr(){
		Pattern pat = Pattern.compile("\\d{2}-\\d{2}-\\d{4}");
		Matcher match = pat.matcher(dataUr);
		
		if(match.matches()){
			return true;
		}else{ JOptionPane.showMessageDialog(null, "Data ur ma nastêpuj¹cy format dd-mm-rrrr"); return false; }
	}
	public boolean checkUlica(){
		Pattern pat = Pattern.compile("[\\D\\d¹êó³œ¹¿Ÿæñ]+");
		Matcher match = pat.matcher(ulica);
		
		if(match.matches()){
			return true;
		}else{ JOptionPane.showMessageDialog(null, "Nazwa ulicy sk³ada siê z liter i liczb"); return false; }
	}
}
//Klasa pomocnicza Layoutu dla GridBagLayout
final class GBCf extends GridBagConstraints{
	public GBCf(int gridx,int gridy){ this.gridx = gridx; this.gridy = gridy; }
	public GBCf(int gridx,int gridy,int gridwidth,int gridheight){
		this.gridx = gridx; this.gridy = gridy; this.gridwidth = gridwidth; this.gridheight = gridheight;
	}
	public GBCf setFill(int fill){ this.fill = fill; return this;}
	public GBCf setAnchor(int anchor){ this.anchor = anchor;return this; }
	public GBCf setWeight(int weightx,int weighty){ this.weightx = weightx; this.weighty = weighty; return this; }
	public GBCf setInsets(int x){ this.insets = new Insets(x,x,x,x); return this; }
	public GBCf setInsets(int top,int left,int bottom,int right){ this.insets = new Insets(top,left,bottom,right); return this;}
}

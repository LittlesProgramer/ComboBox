package Aplication_ComboBox;
import javax.swing.*;
import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.EventHandler;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import javax.swing.JScrollPane;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

final public class Serializacja extends JDialog implements Serializable{
	private static final long serialVersionUID = 1l;
	
	ActionListener listenerUpdateArea = EventHandler.create(ActionListener.class, this,"upDateArea");
	
    List<String> listaName = new ArrayList<String>();
	List<Integer> listaWiek = new ArrayList<Integer>();
	List<Double> listaPens = new ArrayList<Double>();
	List<Date> listaData = new ArrayList<Date>();
	
	transient JPanel panel1 = new JPanel();
	transient JPanel panel2 = new JPanel();
	transient JPanel panel3 = new JPanel();
	transient JPanel panel4 = new JPanel();
	
	transient JLabel labTytOne = new JLabel("PANEL ZAPISU DANYCH");
	transient JLabel labNazwisko = new JLabel("Podaj nazwisko: ");
	transient JLabel labWiek = new JLabel("Podaj wiek: ");
	transient JLabel labPens = new JLabel("Podaj pensje: ");
	transient JLabel labDataUr = new JLabel("Podaj datê ur wg. formatu (dd-mm-yyy): ");
	transient JLabel labNazwiskoM = new JLabel("Podaj nazwisko: ");
	transient JLabel labWiekM = new JLabel("Podaj wiek: ");
	transient JLabel labPensM = new JLabel("Podaj pensje: ");
	transient JLabel labDataUrM = new JLabel("Podaj datê ur wg. formatu (dd-mm-yyy): ");
	
	transient JTextField textZapNazw = new JTextField(10);
	transient JTextField textZapWiek = new JTextField(10);
	transient JTextField textZapPens = new JTextField(10);
	transient JTextField textZapDataUr = new JTextField(10);
	transient JButton butWrite = new JButton("Zapis\nDanych");
	
	transient JLabel labTytTwo = new JLabel("PANEL KONFIGURACYJNY ODCZYTU DANYCH");
	transient JComboBox<String> boxCzcionka = new JComboBox<String>(new String[]{"Serif","SensSerif","Arial","Monospaced","Dialog","DialogInput"});
	transient JComboBox<Integer> boxSize = new JComboBox<Integer>(new Integer[]{8,10,12,15,18,22,24,28,32,26});
	transient JCheckBox italic = new JCheckBox("Kursywa");
	transient JCheckBox bold = new JCheckBox("Pogrubiona");
	transient JLabel labTytThree = new JLabel("<html><b>PANEL ODCZYTU DANYCH</b></html>");
	transient JTextArea area = new JTextArea(5,22);
	transient JButton butRead = new JButton("Odczyt\nDanych");
	
	transient JLabel labTytFour = new JLabel("PANEL MODYFIKACJI DANYCH");
	transient JTextField textModNazw = new JTextField(10);
	transient JTextField textModWiek = new JTextField(10);
	transient JTextField textModPens = new JTextField(10);
	transient JTextField textModDataUr = new JTextField(10);
	transient JLabel labModId = new JLabel("Numer Id w bazie");
	transient JTextField textModId = new JTextField(3);
	transient JButton butModifi = new JButton("Modyfikuj\nDane");
	
	transient JLabel labTytFive = new JLabel("PANEL USUWANIA DANYCH");
	transient JLabel labDelId = new JLabel("Numer Id w bazie");
	transient JTextField textDelId = new JTextField(3);
	transient JButton butDel = new JButton("Usuñ/nRekord");
	
	public Serializacja(final PanelWithMenu pwm){
		super(pwm,"Serializacja",false);
		//Zminimalizowanie g³ównego okna
		pwm.setExtendedState(JFrame.ICONIFIED);
		//ustawienia s³uchacza zdarzeñ na zdarzenia generowane przez panel konfiguracyjny
		boxCzcionka.addActionListener(listenerUpdateArea);
		boxSize.addActionListener(listenerUpdateArea);
		italic.addActionListener(listenerUpdateArea);
		bold.addActionListener(listenerUpdateArea);
		
		GridLayout gr = new GridLayout(4,1);
		GridBagLayout grid = new GridBagLayout();
		this.setLayout(gr);
		panel1.setLayout(grid);
		panel2.setLayout(grid);
		panel3.setLayout(grid);
		panel4.setLayout(grid);
		this.add(panel1); this.add(panel2); this.add(panel3); this.add(panel4);
		
		panel1.add(labTytOne,new GBCa(0,0,3,1).setInsets(1).setAnchor(GBCa.CENTER));
		panel1.add(labNazwisko,new GBCa(0,1,1,1).setInsets(1).setAnchor(GBCa.WEST));
		panel1.add(labWiek,new GBCa(0,2,1,1).setInsets(1).setAnchor(GBCa.WEST));
		panel1.add(labPens,new GBCa(0,3,1,1).setInsets(1).setAnchor(GBCa.WEST));
		panel1.add(labDataUr,new GBCa(0,4,1,1).setInsets(1));
		panel1.add(textZapNazw,new GBCa(1,1,1,1).setInsets(1).setWeight(1,0).setFill(GBCa.HORIZONTAL));
		panel1.add(textZapWiek,new GBCa(1,2,1,1).setInsets(1).setWeight(1,0).setFill(GBCa.HORIZONTAL));
		panel1.add(textZapPens,new GBCa(1,3,1,1).setInsets(1).setWeight(1,0).setFill(GBCa.HORIZONTAL));
		panel1.add(textZapDataUr,new GBCa(1,4,1,1).setInsets(1).setWeight(1,0).setFill(GBCa.HORIZONTAL));
		panel1.add(butWrite,new GBCa(3,1,1,4).setInsets(1).setFill(GBCa.VERTICAL));
		panel1.setBorder(BorderFactory.createLineBorder(Color.black));
		
		panel2.add(labTytTwo,new GBCa(0,0,1,1).setInsets(1));
		panel2.add(boxCzcionka,new GBCa(0,1,1,1).setInsets(1).setFill(GBCa.HORIZONTAL));
		panel2.add(boxSize,new GBCa(0,2,1,1).setInsets(1).setFill(GBCa.HORIZONTAL));
		panel2.add(italic,new GBCa(1,1,1,1).setInsets(1).setAnchor(GBCa.WEST).setFill(GBCa.HORIZONTAL));
		panel2.add(bold,new GBCa(1,2,1,1).setInsets(1).setAnchor(GBCa.WEST).setFill(GBCa.HORIZONTAL));
		panel2.add(labTytThree,new GBC(0,3,2,1).setInsets(1).setAnchor(GBCa.CENTER));
		JScrollPane scroll = new JScrollPane(area);
		area.setBorder(BorderFactory.createLineBorder(Color.blue));
		panel2.add(scroll,new GBC(0,4,2,1).setInsets(1).setWeight(1, 1).setFill(GBCa.BOTH));
		panel2.add(butRead,new GBC(2,0,1,5).setInsets(1).setFill(GBCa.BOTH));
		panel2.setBorder(BorderFactory.createLineBorder(Color.black));
		
		panel3.add(labTytFour,new GBCa(0,0,3,1).setInsets(1));
		panel3.add(labNazwiskoM,new GBC(0,1,1,1).setInsets(1).setAnchor(GBCa.WEST));
		panel3.add(labWiekM,new GBCa(0,2,1,1).setInsets(1).setAnchor(GBCa.WEST));
		panel3.add(labPensM,new GBCa(0,3,1,1).setInsets(1).setAnchor(GBCa.WEST));
		panel3.add(labDataUrM,new GBCa(0,4,1,1).setInsets(1).setAnchor(GBCa.WEST));
		panel3.add(labModId,new GBCa(0,5,1,1).setInsets(1).setAnchor(GBCa.WEST));
		panel3.add(textModNazw,new GBCa(1,1,1,1).setInsets(1).setFill(GBCa.HORIZONTAL).setWeight(1, 0));
		panel3.add(textModWiek,new GBC(1,2,1,1).setInsets(1).setFill(GBCa.HORIZONTAL).setWeight(1, 0));
		panel3.add(textModPens,new GBCa(1,3,1,1).setInsets(1).setFill(GBCa.HORIZONTAL).setWeight(1, 0));
		panel3.add(textModDataUr,new GBCa(1,4,1,1).setInsets(1).setFill(GBCa.HORIZONTAL).setWeight(1, 0));
		panel3.add(textModId,new GBCa(1,5,1,1).setInsets(1).setFill(GBCa.HORIZONTAL).setWeight(1, 0));
		panel3.add(butModifi,new GBCa(2,1,1,5).setInsets(1).setFill(GBCa.BOTH));
		panel3.setBorder(BorderFactory.createLineBorder(Color.black));
		
		panel4.add(labTytFive,new GBCa(0,0,3,1).setInsets(1));
		panel4.add(labDelId,new GBCa(0,1,1,1).setInsets(1));
		panel4.add(textDelId,new GBCa(1,1,1,1).setInsets(1));
		panel4.add(butDel,new GBCa(2,1,1,1).setInsets(1));
		panel4.setBorder(BorderFactory.createLineBorder(Color.red));
		panel4.setBounds(getX(), getY(), getWidth()-100,getHeight());
		
		pwm.setExtendedState(JFrame.ICONIFIED);
		this.pack();
		this.setVisible(true);
		
		//Przywracania okna g³ównego po zamkniêciu okna dialogowego 
		this.addComponentListener(new ComponentAdapter(){
			@Override
			public void componentHidden(ComponentEvent e){
				pwm.setExtendedState(JFrame.NORMAL);
			}
		});
		
		//Przycisk zapisu danych
		butWrite.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
			    //Odczyt danych z pliku SerializacjaDanych.txt za pomoc¹ metody OdczytDanychZPlikuDoList()
			    OdczytDanychZPlikuDoList();
			    //Weryfikacja poprawnoœci i zapis danych do pliku SerializacjaDanych.txt 
				String name = checkNazwisko(textZapNazw);
				Integer wiek = checkWiek(textZapWiek);
				Double pens = checkPensja(textZapPens);
				Date data = checkDataUr(textZapDataUr);
				
				if((name == null) || (wiek == null) || (pens == null) || (data == null)){
					JOptionPane.showMessageDialog(null, "Proszê wprowadziæ dane wed³ug wczeœniejszych wskazówek"); return;
				}
				
				listaName.add(name); listaWiek.add(wiek); listaPens.add(pens); listaData.add(data);
				//Zapis zweryfikowanych danych z List do pliku SerializacjaDanych.txt i wyczyszczenie List
				
				ZapisDanychZListDoPliku();
			}
		});
		
		butRead.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				OdczytDanychZPlikuDoList();
				GregorianCalendar greg = new GregorianCalendar();
				area.setText("");
				for(int x = 0 ; x < listaName.size() ; x++){
					greg.setTime(listaData.get(x));
					area.append("id."+x+" imie: "+listaName.get(x)+" wiek: "+listaWiek.get(x)+" pensja: "+listaPens.get(x)
					+" dataUr: "+greg.get(Calendar.DAY_OF_MONTH)+"-"+greg.get(Calendar.MONTH)+"-"+greg.get(Calendar.YEAR)+"\n");
				}
				listaName.clear();
				listaWiek.clear();
				listaPens.clear();
				listaData.clear();
			}
		});
		
		butModifi.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				//Check that the name change;
				OdczytDanychZPlikuDoList();
				//Modyfication Name
				if(checkIndex(textModId.getText(),listaName)){	
					//check name
					String name = checkModNazwisko(textModNazw);
					if(name != null){
						Pattern pat = Pattern.compile("");
						Matcher match = pat.matcher(textModNazw.getText());
						
						if(match.matches()){
							System.out.println("to jest znak \"\" czyli nie zmieniamy tego imienia w rekordzie");
						}else{
							System.out.println("to mo¿e byæ tylko poprawne imie");
							listaName.set(Integer.parseInt(textModId.getText()),textModNazw.getText());
						}
						
					}
					//check wiek
					String wiek = this.checkModHowOld(textModWiek);
					if(wiek != null){
						Pattern pat = Pattern.compile("");
						Matcher match = pat.matcher(textModWiek.getText());
						
						if(match.matches()){
							System.out.println("to jest znak \"\" czyli nie zmieniamy wieku w rekordzie");
						}else{
							System.out.println("to mo¿e byæ tylko poprawny wiek");
							listaWiek.set(Integer.parseInt(textModId.getText()),Integer.parseInt(textModWiek.getText()));
						}
						
					}
					//check pens
					String pens = this.checkModPensja(textModPens);
					if(pens != null){
						Pattern pat = Pattern.compile("");
						Matcher match = pat.matcher(textModPens.getText());
						
						if(match.matches()){
							System.out.println("to jest znak \"\" czyli nie zmieniamy pola pensji w rekordzie");
						}else{
							System.out.println("to mo¿e byæ tylko poprawna pensja");
							listaPens.set(Integer.parseInt(textModId.getText()),Double.parseDouble(textModPens.getText()));
						}
						
					}
					//check data
				    String data = this.checkModDataUr(textModDataUr);
				    System.out.println("co zwraca data w Stringu = "+data);
				    
					if(data != null){
						Pattern pat = Pattern.compile("");
						Matcher match = pat.matcher(textModDataUr.getText());
						
						if(match.matches()){
							System.out.println("to jest znak \"\" czyli nie zmieniamy tego pola daty w rekordzie");
						}else{
							System.out.println("to mo¿e byæ tylko poprawna data");
							
				            String tabData[] = textModDataUr.getText().split("-");
					        int day = Integer.parseInt(tabData[0]);
					        int msc = Integer.parseInt(tabData[1]);
					        int year = Integer.parseInt(tabData[2]);
					
					        if((day > 0 && day <= 31) && (msc > 0 && msc <= 12) && (year >= 1900 && year <= 2019)){
					           GregorianCalendar greg = new GregorianCalendar(year,msc,day);
					           Date dat = greg.getTime();
					           listaData.set(Integer.parseInt(textModId.getText()),dat);
					        }else{
						       JOptionPane.showMessageDialog(null, "Co ty jaja sobie robisz, to ma byæ data ?");
						       return;
					        }
						}
						ZapisDanychZListDoPliku();
						
						listaName.clear();
						listaWiek.clear();
						listaPens.clear();
						listaData.clear();
					}else{ return; }
					
					
				}else{ return; }
				
			}
			
			public boolean checkIndex(String textModId,List<String> lista){
				int size = lista.size();
				Pattern pattern = Pattern.compile("[\\d-\"\"]+");
				Matcher match = pattern.matcher(textModId);
				
				if(match.matches()){
					if(!(Integer.parseInt(textModId) >= size || Integer.parseInt(textModId) < 0)){
						return true;
					}else{ JOptionPane.showMessageDialog(null, "Proszê poprawiæ index"); return false; }
				}else{
					JOptionPane.showMessageDialog(null, "Index sk³ada siê z cyfr(znajduje siê w Panelu Odczytu Danych)");
					return false;
				}
			}
			
			public String checkModNazwisko(JTextField text){
				Pattern pattern = Pattern.compile("[a-zA-Z¹ê¿Ÿœæñó³£ÓŒÆ¯£]+");
				Matcher match = pattern.matcher(text.getText());
				Pattern pattern1 = Pattern.compile("");
				Matcher match1 = pattern1.matcher(text.getText());
				
				if(match.matches() || match1.matches()){
					return text.getText();
				}else{
					JOptionPane.showMessageDialog(null, "WprowadŸ poprawnie imiê i nazwisko");
					return null;
				}
			}
			
			public String checkModHowOld(JTextField modWiek){
				Pattern pattern = Pattern.compile("[0-9]+");
				Matcher match = pattern.matcher(modWiek.getText());
				Pattern pattern1 = Pattern.compile("");
				Matcher match1 = pattern1.matcher(modWiek.getText());
				
				if(match.matches() || match1.matches()){
					return modWiek.getText();
				}else{
					JOptionPane.showMessageDialog(null, "Wiek powinieñ siê sk³adaæ z samych cyfr");
					return null;
				}
			}
			
			public String checkModPensja(JTextField modPens){
				Pattern pattern = Pattern.compile("[0-9]+");
				Matcher match = pattern.matcher(modPens.getText());
				Pattern pattern1 = Pattern.compile("");
				Matcher match1 = pattern1.matcher(modPens.getText());
				
				if(match.matches() || match1.matches()){
					return modPens.getText();
				}else{
					JOptionPane.showMessageDialog(null, "Wiek powinieñ siê sk³adaæ z samych cyfr");
					return null;
				}
			}
			
			public String checkModDataUr(JTextField modDataUr){
				Pattern pat = Pattern.compile("\\d{2}-\\d{2}-\\d{4}");
				Matcher match = pat.matcher(modDataUr.getText());
				Pattern pattern1 = Pattern.compile("");
				Matcher match1 = pattern1.matcher(modDataUr.getText());
			
				if(match.matches() || match1.matches()){
					return modDataUr.getText();
				}else{ 
					JOptionPane.showMessageDialog(null, "Proszê wpisaæ datê wed³ug wzoru: dd-mm-yyyy");
					return null;
				}
			}
		});
		
	}
	
	public void ZapisDanychZListDoPliku(){
		try{
			ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File("SerializacjaDanych.txt"))));
			out.writeObject(listaName);
			out.writeObject(listaWiek);
			out.writeObject(listaPens);
			out.writeObject(listaData);
			out.close();
			
			listaName.clear();
			listaWiek.clear();
			listaPens.clear();
			listaData.clear();
		}catch(FileNotFoundException a){
			JOptionPane.showMessageDialog(null, a);
		}catch(IOException b){
			JOptionPane.showMessageDialog(null, b);
		}
	}
	
	public void OdczytDanychZPlikuDoList(){
		File f = new File("SerializacjaDanych.txt");
		//Odczyt wczesniej zapisanych danych z pliku potrzebnych do póŸniejszego zapisu
		if(f.exists()){
		  try{	
			ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File("SerializacjaDanych.txt"))));
			listaName = (List<String>)in.readObject();
			listaWiek = (List<Integer>)in.readObject();
			listaPens = (List<Double>)in.readObject();
			listaData = (List<Date>)in.readObject();
		  }catch(FileNotFoundException a){
			  JOptionPane.showMessageDialog(null, "Nie znaleziono pliku do odczytu SerializacjaDanych.txt");
		  }catch(IOException b){ 
			  JOptionPane.showMessageDialog(null, "Nie mo¿na otworzyæ pliku SerializacjaDanych.txt"+b);
		  }catch(ClassNotFoundException c){ JOptionPane.showMessageDialog(null, "B³¹d rzutowania typów klas"+c); }
		}else{
			try{
			  Files.createFile(f.toPath());
			}catch(IOException v){JOptionPane.showMessageDialog(null, "Dont't create file SerializacjaDanych.txt"+v); }
		}
	}
	
	public String checkNazwisko(JTextField text){
		Pattern pattern = Pattern.compile("[a-zA-Z¹ê¿Ÿœæñó³£ÓŒÆ¯£]+");
		Matcher match = pattern.matcher(text.getText());
		Pattern pattern1 = Pattern.compile("");
		Matcher match1 = pattern1.matcher(text.getText());
		
		if(match.matches()){
			return text.getText();
		}else{
			JOptionPane.showMessageDialog(null, "WprowadŸ poprawnie imiê i nazwisko");
			return null;
		}
	}
	
	public Integer checkWiek(JTextField text){
		Pattern pattern = Pattern.compile("[0-9]+");
		Matcher match = pattern.matcher(text.getText());
		Pattern pattern1 = Pattern.compile("");
		Matcher match1 = pattern1.matcher(text.getText());
		
		if(match.matches()){
			return Integer.parseInt(text.getText());
		}else{
			JOptionPane.showMessageDialog(null, "Wiek powinieñ siê sk³adaæ z samych cyfr");
			return null;
		}
	}
	
	public Double checkPensja(JTextField text){
		Pattern pattern = Pattern.compile("\\d+\\.\\d{1,3}");
		Matcher match = pattern.matcher(text.getText());
		Pattern pattern1 = Pattern.compile("");
		Matcher match1 = pattern1.matcher(text.getText());
		
		if(match.matches()){
			return Double.parseDouble(text.getText());
		}else{
			JOptionPane.showMessageDialog(null, "Pensja sk³ada siê z cyfr i miejsc po przecinku (od 1 do 3) np: 58800.80!");
			return null;
		}
	}
	
	public Date checkDataUr(JTextField text){
		Pattern pat = Pattern.compile("\\d{2}-\\d{2}-\\d{4}");
		Matcher match = pat.matcher(text.getText());
		
		if(match.matches()){
			String tabData[] = text.getText().split("-");
			int day = Integer.parseInt(tabData[0]);
			int msc = Integer.parseInt(tabData[1]);
			int year = Integer.parseInt(tabData[2]);
			
			if((day > 0 && day <= 31) && (msc > 0 && msc <= 12) && (year >= 1900 && year <= 2019)){
			    GregorianCalendar greg = new GregorianCalendar(year,msc,day);
			    Date dat = greg.getTime();
			    return dat;
			}else{
				JOptionPane.showMessageDialog(null, "Co ty jaja sobie robisz, to ma byæ data ?");
				return null;
			}
		}else{ 
			JOptionPane.showMessageDialog(null, "Proszê wpisaæ datê wed³ug wzoru: dd-mm-yyyy");
			return null;
		}
	}
	
	public void upDateArea(){
		String fontFace = (String)boxCzcionka.getSelectedItem();
		int fontStyle = (italic.isSelected() ? Font.ITALIC : 0) + (bold.isSelected() ? Font.BOLD : 0);
		int fontSize = boxSize.getItemAt(boxSize.getSelectedIndex());
		Font font = new Font(fontFace,fontStyle,fontSize);
		area.setFont(font);
		area.repaint();
	}
}

final class GBCa extends GridBagConstraints{
	private static final long serialVersionUID = 1L;
	public GBCa(int gridx,int gridy){
		this.gridx = gridx; this.gridy = gridy;
	}
	public GBCa(int gridx,int gridy,int gridwidth,int gridheight){
		this.gridx = gridx; this.gridy = gridy; this.gridwidth = gridwidth; this.gridheight = gridheight;
	}
	public GBCa setFill(int fill){
		this.fill = fill; 
		return this;
	}
	public GBCa setAnchor(int anchor){
		this.anchor = anchor;
		return this;
	}
	public GBCa setWeight(int weightx,int weighty){
		this.weightx = weightx; this.weighty = weighty;
		return this;
	}
	public GBCa setInsets(int x){
		this.insets = new Insets(x,x,x,x);
		return this;
	}
	public GBCa setInsets(int top,int left,int bottom,int right){
		this.insets = new Insets(top,left,bottom,right);
		return this;
	}
}
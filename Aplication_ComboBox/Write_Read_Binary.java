package Aplication_ComboBox;
import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.BorderLayout;
import java.awt.Insets;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentAdapter;

final public class Write_Read_Binary extends JDialog{
	private static final long serialVersionUID = 1L;
	
	JLabel panWprDan = new JLabel("Panel Wprowadzania Danych");
	JLabel panOdczDan = new JLabel("Panel Odczytu Danych");
	JLabel panDelDan = new JLabel("Panel Kasowania Danych");
	JLabel labImie = new JLabel("Wprowadz Imie: ");
	JLabel labNazwisko = new JLabel("Wprowadz Nazwisko: ");
	JLabel labUlica = new JLabel("Wprowadz Ulicê: ");
	JLabel labWiek = new JLabel("Wprowadz Wiek: ");
	JLabel labPensja = new JLabel("Wprowadz Pensjê: ");
	JLabel labData = new JLabel("Wprowadz Datê Ur: ");
	JLabel labId = new JLabel("Wprowadz Id: ");
	
	
	JTextField textImie = new JTextField(5);
	JTextField textNazwisko = new JTextField(10);
	JTextField textUlica = new JTextField(10);
	JTextField textWiek = new JTextField(5);
	JTextField textPensja = new JTextField(10);
	JTextField textData = new JTextField(10);
	JTextField textId = new JTextField(5);
	
	JTextArea area = new JTextArea(20,77);
	
	JButton butWrite = new JButton("Zapis/n danych");
	JButton butRead = new JButton("Odczyt/n danych");
	JButton butDelete = new JButton("Usuñ/n rekord");
	
	public Write_Read_Binary(final PanelWithMenu pwm){
		super(pwm,"Panel Zapisu/Odczytu Danych Metod¹ Binarn¹",false);
		pwm.setExtendedState(JFrame.ICONIFIED);
		JPanel panOne = new JPanel();
		GridBagLayout grid = new GridBagLayout();
		panOne.setLayout(grid);
		this.add(panOne,BorderLayout.NORTH);
		panOne.setBorder(BorderFactory.createLineBorder(Color.black));
		
		panOne.add(panWprDan,new GBC(0,0,7,1).setInsets(1));
		panOne.add(labImie,new GBC(0,1,1,1).setInsets(1));
		panOne.add(textImie,new GBC(1,1,1,1).setInsets(1));
		panOne.add(labNazwisko,new GBC(2,1,1,1).setInsets(1));
		panOne.add(textNazwisko,new GBC(3,1,1,1).setInsets(1));
		panOne.add(labUlica,new GBC(4,1,1,1).setInsets(1));
		panOne.add(textUlica,new GBC(5,1,1,1).setInsets(1));
		
		panOne.add(labWiek,new GBC(0,2,1,1).setInsets(1));
		panOne.add(textWiek,new GBC(1,2,1,1).setInsets(1));
		panOne.add(labPensja,new GBC(2,2,1,1).setInsets(1));
		panOne.add(textPensja,new GBC(3,2,1,1).setInsets(1));
		panOne.add(labData,new GBC(4,2,1,1).setInsets(1));
		panOne.add(textData,new GBC(5,2,1,1).setInsets(1));
		panOne.add(butWrite,new GBC(6,1,1,4).setInsets(1).setFill(GBC.VERTICAL));
		
		JPanel panTwo = new JPanel();
		panTwo.setLayout(grid);
		this.add(panTwo,BorderLayout.CENTER);
		panTwo.setBorder(BorderFactory.createLineBorder(Color.blue,1));
			
		panTwo.add(panOdczDan,new GBC(0,3,7,1).setInsets(1));
		JScrollPane scroll = new JScrollPane(area);
		panTwo.add(scroll,new GBC(0,4,6,1).setInsets(1).setWeight(1, 1).setFill(GBC.VERTICAL).setAnchor(GBC.CENTER));
		panTwo.add(butRead,new GBC(0,5,6,1).setInsets(1).setWeight(0,0).setFill(GBC.HORIZONTAL));
		
		JPanel panThree = new JPanel();
		panThree.setLayout(grid);
		this.add(panThree,BorderLayout.SOUTH);
		panThree.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		
		panThree.add(panDelDan,new GBC(0,5,7,1).setInsets(1));
		panThree.add(labId,new GBC(2,6,1,1).setInsets(1));
		panThree.add(textId,new GBC(3,6,1,1).setInsets(1));
		panThree.add(butDelete,new GBC(6,6,1,1).setInsets(1));
		
		this.setResizable(false);
		this.setVisible(true);
		this.pack();
		
		//Przywracania okna g³ównego po zamkniêciu okna dialogowego 
		this.addComponentListener(new ComponentAdapter(){
			@Override
			public void componentHidden(ComponentEvent e){
				pwm.setExtendedState(JFrame.NORMAL);
			}
		});
		
		//Obs³uga przycisku do zapisu danych
		butWrite.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				try{
				 DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File("zapisBinarny.txt"),true)));
				 GregorianCalendar greg = new GregorianCalendar();
				 
				 int sizeName = 0;
				 String imie = checkImie();
				 int sizeNazwisko = 0;
				 String nazwisko = checkNazwisko();
				 int sizeUlica = 0;
				 String ulica = checkUlica();
				 Integer wiek = checkWiek();
				 Double pensja = checkPensja();
				 Date data = checkDate();
					
				  if(imie == null || nazwisko == null || ulica == null || wiek == null || pensja == null || data == null){
					  JOptionPane.showMessageDialog(null, "Proszê wprowadziæ dane zgodnie z wczeœniejszymi instrukcjami !"); return;
				  }

				  out.writeInt(imie.length());
				  out.writeChars(imie);//writeBytes(imie);
				  out.writeInt(nazwisko.length());
				  out.writeChars(nazwisko);//writeBytes(nazwisko);
				  out.writeInt(ulica.length());
				  out.writeChars(ulica);//writeBytes(ulica)
				  out.writeInt(wiek);
				  out.writeDouble(pensja);
				  //zapis Daty
				  greg.setTime(data);
				  out.writeInt(greg.get(Calendar.DAY_OF_MONTH));
				  out.writeInt(greg.get(Calendar.MONTH));
				  out.writeInt(greg.get(Calendar.YEAR));
				  
				  out.flush();
				  out.close();
				}catch(FileNotFoundException v){ 
					JOptionPane.showMessageDialog(null, "Nie mo¿na znaleŸæ pliku zapisBinarny.txt"); 
				}catch(IOException v){
					JOptionPane.showMessageDialog(null, "Nie mo¿na zapisaæ danych do pliku zapisBinarny.txt");
				}
			}
		});
		
		butRead.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
			  int index = 0;
			  try{
				DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(new File("zapisBinarny.txt"))));
				GregorianCalendar greg = null;
				area.setText("");
				while(in.available() != 0){

					int sizeName = in.readInt();
					char tabName[] = new char[sizeName];
					for(int x = 0 ; x < sizeName ; x++){
						tabName[x] = (char)in.readChar();//readByte()
					}
					String name = String.valueOf(tabName);
					
					
					int sizeNazwisko = in.readInt();
					char tabNazwisko[] = new char[sizeNazwisko];
					for(int x = 0 ; x < sizeNazwisko ; x++){
						tabNazwisko[x] = (char)in.readChar();//readByte()
					}
					String nazwisko = String.valueOf(tabNazwisko);
					
					int sizeUlica = in.readInt();
					char tabUlica[] = new char[sizeUlica];
					for(int x = 0 ; x < sizeUlica ; x++){
						tabUlica[x] = (char)in.readChar();//readByte()
					}
					String ulica = String.valueOf(tabUlica);
					
					int wiek = in.readInt();
					
					double pensja = in.readDouble();
					
					int day = in.readInt();
					int msc = in.readInt();
					int year = in.readInt();
					
					greg = new GregorianCalendar(year,msc,day);
					Date data = greg.getTime();
					
					area.append("id."+index+" imie: "+name+" nazwisko: "+nazwisko+" ulica: "+ulica+" wiek: "+wiek+" pensja: "+pensja+" data: "+data+"\n");
					index++;
				}
				    in.close();
			  }catch(FileNotFoundException v){
				  JOptionPane.showMessageDialog(null, "Nie mo¿na odnaleŸæ pliku do odczytu zapisBinarny.bin");
			  }catch(IOException z){
				  JOptionPane.showMessageDialog(null, "B³¹d odczytu pliku zapisBinarny.bin");
			  }
			}
		});
		
		butDelete.addActionListener(new ActionListener(){
			List<String> listaName = new LinkedList<String>();
			List<String> listaNazwisko = new LinkedList<String>();
			List<String> listaUlica = new LinkedList<String>();
			List<Integer> listaWiek = new LinkedList<Integer>();
			List<Double> listaPensja = new LinkedList<Double>();
			List<Date> listaData = new LinkedList<Date>();
			
			@Override
			public void actionPerformed(ActionEvent e){
				//Odczyt danch zawartych w pliku zapisBinarny.bin i przypisanie do poszczególnych LinkedList<>();
				 try{
						DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(new File("zapisBinarny.bin"))));
						GregorianCalendar greg = null;
						int index = 0;
						while(in.available() != 0){

							int sizeName = in.readInt();
							char tabName[] = new char[sizeName];
							for(int x = 0 ; x < sizeName ; x++){
								tabName[x] = (char)in.readChar();//readByte()
							}
							String name = String.valueOf(tabName);
							listaName.add(name);
							
							int sizeNazwisko = in.readInt();
							char tabNazwisko[] = new char[sizeNazwisko];
							for(int x = 0 ; x < sizeNazwisko ; x++){
								tabNazwisko[x] = (char)in.readChar();//readByte()
							}
							String nazwisko = String.valueOf(tabNazwisko);
							listaNazwisko.add(nazwisko);
							
							int sizeUlica = in.readInt();
							char tabUlica[] = new char[sizeUlica];
							for(int x = 0 ; x < sizeUlica ; x++){
								tabUlica[x] = (char)in.readChar();//readByte()
							}
							String ulica = String.valueOf(tabUlica);
							listaUlica.add(ulica);
							
							int wiek = in.readInt();
							listaWiek.add(wiek);
							
							double pensja = in.readDouble();
							listaPensja.add(pensja);
							
							int day = in.readInt();
							int msc = in.readInt();
							int year = in.readInt();
							
							greg = new GregorianCalendar(year,msc,day);
							Date data = greg.getTime();
							listaData.add(data);
							index++;
						}
						in.close();
						//Usuniêcie z poszczególnych LinkedList wskazanego rekordu z danymi
						if(Integer.parseInt(textId.getText()) > index || Integer.parseInt(textId.getText()) < 0){
							JOptionPane.showMessageDialog(null, "Proszê wprowadziæ prawid³owy index !!!"); return;
						}
						listaName.remove(Integer.parseInt(textId.getText()));
						for(String el : listaName){
							System.out.println("po usuniêciu = "+el);
						}
						listaNazwisko.remove(Integer.parseInt(textId.getText()));
						listaUlica.remove(Integer.parseInt(textId.getText()));
						listaWiek.remove(Integer.parseInt(textId.getText()));
						listaPensja.remove(Integer.parseInt(textId.getText()));
						listaData.remove(Integer.parseInt(textId.getText()));
						
						//Zapis po usuñieciu wskazanego rekordu do pliku zapisBinarny.txt
						DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File("zapisBinarny.bin"))));
						for(int x = 0 ; x < index-1; x++){
							out.writeInt(listaName.get(x).length());
							out.writeChars(listaName.get(x));//writeBytes(imie);
							out.writeInt(listaNazwisko.get(x).length());
							out.writeChars(listaNazwisko.get(x));//writeBytes(nazwisko);
							out.writeInt(listaUlica.get(x).length());
							out.writeChars(listaUlica.get(x));//writeBytes(ulica)
							out.writeInt(listaWiek.get(x));
							out.writeDouble(listaPensja.get(x));
							//zapis Daty
							greg.setTime(listaData.get(x));
							out.writeInt(greg.get(Calendar.DAY_OF_MONTH));
							out.writeInt(greg.get(Calendar.MONTH));
							out.writeInt(greg.get(Calendar.YEAR));
						}
						     
						    out.flush();
						    out.close();
						
					  }catch(FileNotFoundException v){
						  JOptionPane.showMessageDialog(null, "Nie mo¿na odnaleŸæ pliku do odczytu zapisBinarny.bin");
					  }catch(IOException z){
						  JOptionPane.showMessageDialog(null, "B³¹d odczytu pliku zapisBinarny.bin");
					  }
					  listaName.clear();
					  listaNazwisko.clear();
					  listaUlica.clear();
					  listaWiek.clear();
					  listaPensja.clear();
					  listaData.clear();
			}
		});
		
		
	}
	
	public String checkImie(){
		Pattern pattern = Pattern.compile("[a-zA-Z¹êó³æœŸ¿ñ]+");
		Matcher match = pattern.matcher(textImie.getText());
		
		if(match.matches()){
			return textImie.getText();
		}else{
			JOptionPane.showMessageDialog(null, "wprowadz poprawnie imie"); 
			return null;
		}
	}
	
	public String checkNazwisko(){
		Pattern pattern = Pattern.compile("[a-zA-Z¹êó³æœŸ¿ñ]+");
		Matcher match = pattern.matcher(textNazwisko.getText());
		
		if(match.matches()){
			return textNazwisko.getText();
		}else{
			JOptionPane.showMessageDialog(null, "wprowadz poprawnie nazwisko"); 
			return null;
		}
	}
	
	public String checkUlica(){
		Pattern pattern = Pattern.compile("[0-9a-zA-Z¹êó³æœŸ¿ñ\\s]+");
		Matcher match = pattern.matcher(textUlica.getText());
		
		if(match.matches()){
			return textUlica.getText();
		}else{
			JOptionPane.showMessageDialog(null, "wprowadz poprawnie ulicê"); 
			return null;
		}
	}
	
	public Integer checkWiek(){
		Pattern pattern = Pattern.compile("\\d+");
		Matcher match = pattern.matcher(textWiek.getText());
		
		if(match.matches()){
			return Integer.parseInt(textWiek.getText());
		}else{
			JOptionPane.showMessageDialog(null,"Wiek powinien sk³adaæ siê z samych cyfr, proszê poprawiæ dane !");
			return null;
		}
	}
	
	public Double checkPensja(){
		Pattern pattern = Pattern.compile("\\d+\\.\\d{1,3}");
		Matcher match = pattern.matcher(textPensja.getText());
		
		if(match.matches()){
			return Double.parseDouble(textPensja.getText());
		}else{
			JOptionPane.showMessageDialog(null, "Pensja sk³ada siê z cyfr i miejsc po przecinku (od 1 do 3) np: 58800.80!");
			return null;
		}
	}
	
	public Date checkDate(){
		Pattern pat = Pattern.compile("\\d{2}-\\d{2}-\\d{4}");
		Matcher match = pat.matcher(textData.getText());
		
		if(match.matches()){
			String tabData[] = textData.getText().split("-");
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
}

class GBC extends GridBagConstraints{
	private static final long serialVersionUID = 1L;
	
	public GBC(int gridx,int gridy){ this.gridx = gridx; this.gridy = gridy; }
	public GBC(int gridx,int gridy,int gridwidth,int gridheight){ 
		this.gridx = gridx; this.gridy = gridy; this.gridwidth = gridwidth; this.gridheight = gridheight;
	}
	public GBC setWeight(int weightx,int weighty){
		this.weightx = weightx; this.weighty = weighty; 
		return this;
	}
	public GBC setFill(int fill){
		this.fill = fill; 
		return this;
	}
	public GBC setAnchor(int anchor){
		this.anchor = anchor;
		return this;
	}
	public GBC setInsets(int x){
		this.insets = new Insets(x,x,x,x);
		return this;
	}
	public GBC setInsets(int top,int left,int bottom,int right){
		this.insets = new Insets(top,left,bottom,right);
		return this;
	}
}


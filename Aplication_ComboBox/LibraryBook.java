package Aplication_ComboBox;
import javax.swing.*;
import java.io.FilePermission;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Toolkit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.io.ObjectOutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.DataOutputStream;
import java.io.Serializable;

public class LibraryBook extends JDialog{
	private static final long serialVersionUID = 1L;
	private static GregorianCalendar aktualTime = null;
	private static GregorianCalendar setTime = null;
	
	JPanel panel1 = new JPanel();
	JLabel title1 = new JLabel("Wypo¿yczalnia ksi¹¿ek");
	JLabel name = new JLabel("imie: ");
	JTextField nameT = new JTextField(5);
	JLabel surName = new JLabel("nazwisko: ");
	JTextField surNameT = new JTextField(5);
	JLabel dateLendBook = new JLabel("data wypo¿yczenia ksi¹¿ki: ");
	JTextField dateLendBookT = new JTextField(10);
	JLabel titleBook = new JLabel("tytu³ ksi¹¿ki");
	JTextField titleBookT = new JTextField(10);
	JButton saveDate = new JButton("Zapisz dane");
	JButton showDate = new JButton("Poka¿ wypo¿yczone ksi¹¿ki");
	
	JPanel panel2 = new JPanel();
	JLabel title2 = new JLabel("Termin oddania ksi¹¿ki");
	JLabel actualTime = new JLabel("aktualny czas: ");
	static JTextField actualTimeT = new JTextField(10);
	JLabel giveBackDateBook = new JLabel("Data oddania ksi¹¿ki: ");
	static JTextField giveBackDateBookT = new JTextField(10);
	JButton startTimer = new JButton("Start Timer");
	JButton stopTimer = new JButton("Stop Timer");
	
	public LibraryBook(final PanelWithMenu pwm){
		super(pwm,"Wypo¿yczalnia Ksi¹¿ek",false);
		pwm.setExtendedState(JFrame.ICONIFIED);
		//giveBackDateBookT.setText("0000-00-00 00:00:00");
		
		this.setLayout(new GridLayout(2,1));
		GridBagLayout grid = new GridBagLayout();
		panel1.setLayout(grid); panel2.setLayout(grid);
		this.add(panel1); this.add(panel2);
		
		panel1.setBorder(BorderFactory.createLineBorder(Color.blue));
		panel1.add(title1,new GBCy(0,0,4,1).setInsets(1));
		panel1.add(surName,new GBCy(0,1,1,1).setInsets(1));
		panel1.add(surNameT,new GBCy(1,1,1,1).setInsets(1).setWeight(10, 0).setFill(GBCy.HORIZONTAL));
		panel1.add(name,new GBCy(2,1,1,1).setInsets(1));
		panel1.add(nameT,new GBCy(3,1,1,1).setInsets(1).setWeight(10, 0).setFill(GBCy.HORIZONTAL));
		
		panel1.add(dateLendBook,new GBCy(0,2,2,1).setInsets(1));
		panel1.add(dateLendBookT,new GBCy(2,2,2,1).setInsets(1).setWeight(10, 0).setFill(GBCy.HORIZONTAL));
		
		panel1.add(titleBook,new GBCy(0,3,2,1).setInsets(1));
		panel1.add(titleBookT,new GBCy(2,3,2,1).setInsets(1).setWeight(10, 0).setFill(GBCy.HORIZONTAL));
		
		panel1.add(saveDate,new GBCy(0,4,4,1).setInsets(1).setWeight(10, 0).setFill(GBCy.HORIZONTAL));
		panel1.add(showDate,new GBCy(0,5,4,1).setInsets(1).setWeight(10, 0).setFill(GBCy.HORIZONTAL));
		
		panel2.setBorder(BorderFactory.createLineBorder(Color.green));
		
		panel2.add(title2,new GBCy(0,0,4,1).setInsets(1));
		panel2.add(actualTime,new GBCy(0,1,2,1).setInsets(1));
		panel2.add(actualTimeT,new GBCy(2,1,2,1).setInsets(1).setWeight(10, 0).setFill(GBCy.HORIZONTAL));
		panel2.add(giveBackDateBook,new GBCy(0,2,2,1).setInsets(1));
		panel2.add(giveBackDateBookT,new GBCy(2,2,2,1).setInsets(1).setWeight(10, 0).setFill(GBCy.HORIZONTAL));
		panel2.add(startTimer,new GBCy(0,3,2,1).setInsets(1).setWeight(10, 0).setFill(GBCy.HORIZONTAL));
		panel2.add(stopTimer,new GBCy(2,3,2,1).setInsets(1).setWeight(10, 0).setFill(GBCy.HORIZONTAL));
		
		
		actualTimeT.setText(new Date().toLocaleString());
		
		saveDate.addActionListener(new ActionListener(){
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent e){
				File f = new File("LibraryBook.txt");
				List<Books> lista = new ArrayList<Books>();
				Books book = new Books();
				//Odczyt danych
				try{
				  if(f.exists()){
					 
					ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(f)));
					lista = (List<Books>)in.readObject();
					in.close();
				  }else{f.createNewFile();}
				}catch(FileNotFoundException exc){
					JOptionPane.showMessageDialog(null,"Nie mo¿na znaleŸæ pliku LibraryBook.txt do odczytu "+exc);
				}catch(IOException exc){
					JOptionPane.showMessageDialog(null, "B³¹d IOException "+exc);
				}catch(ClassNotFoundException exc){ JOptionPane.showMessageDialog(null, "Nie znaleziono pasuj¹cej KLASY "+exc); }
				
				//zapis Danych
				book.setName(nameT.getText());
				book.setsurName(surName.getText());
				book.setDateLand(dateLendBookT.getText());
				book.setDateGive(giveBackDateBookT.getText());
				book.setTitle(titleBookT.getText());
				
				lista.add(book);
				
				try{
					ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(f)));
					out.writeObject(lista);
					out.flush();
					out.close();
				}catch(FileNotFoundException exc){ 
					JOptionPane.showMessageDialog(null, "Nie mogê znaleŸæ pliku LibraryBook.txt"+exc); 
				}catch(IOException exc){ JOptionPane.showMessageDialog(null, "B³¹d IO pliku LibraryBook.txt "+exc); }
				
			}
		});
		
		showDate.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				try{
				   Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler "+"WWWLibraryBookShow.html");
				}catch(IOException exc){ JOptionPane.showMessageDialog(null, "B³¹d próby odczytu pliku WWWLibraryBookShow.html "+exc); }
			}
		});
		   Sluchacz s = new Sluchacz(new Date());
		   final Czasomierz czas = new Czasomierz(1000,s);
		   
		startTimer.addActionListener(new ActionListener(){   
			Date d = null;
			@Override
			public void actionPerformed(ActionEvent e){
					
				if(!giveBackDateBookT.getText().equals("")){
					if((d = CheckDate.CheckGiveDate(giveBackDateBookT.getText())) != null){
						GregorianCalendar setDate = new GregorianCalendar();
						setDate.setTime(d);
						GregorianCalendar actDate = new GregorianCalendar();
						actDate.setTime(new Date());
						
						if((setDate.compareTo(actDate)) == -1){
				              JOptionPane.showMessageDialog(null, "Czas oddania ksi¹¿ki nie mo¿e byæ mniejszy ni¿ aktualny czas !"); return; }
						czas.start();
					}else{ JOptionPane.showMessageDialog(null,"Ustaw prawid³owy format daty wed³ug wzoru: yyyy-mm-dd hh:mm:00"); return; }
					
				}else{JOptionPane.showMessageDialog(null,"Aby w³¹czyæ Timer musisz ustawiæ czas zwrotu ksi¹¿ki"); return; }
			}
		});
		
		stopTimer.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				czas.stop();
			}
		});
		
		aktualTime = new GregorianCalendar();
		setTime = new GregorianCalendar();
		
		this.addComponentListener(new ComponentAdapter(){
			@Override
			public void componentHidden(ComponentEvent e){
				pwm.setExtendedState(JFrame.NORMAL);
			}
		});
		this.pack();
		this.setVisible(true);
	}
	
	public static void metoda(Date date){
		aktualTime.setTime(date);
		Date actTime = CheckDate.CheckGiveDate(actualTimeT.getText());
		Date setTime = CheckDate.CheckGiveDate(giveBackDateBookT.getText());
		if(setTime != null){
		  if(actTime.getYear() == setTime.getYear() && actTime.getMonth() == setTime.getMonth() && actTime.getDay() == setTime.getDay() 
		  && actTime.getHours() == setTime.getHours() && actTime.getMinutes() == setTime.getMinutes()){
			  Toolkit.getDefaultToolkit().beep();
		  }else{  }
		}else{ JOptionPane.showMessageDialog(null,"Uruchamiaj¹æ stoper musisz okresliæ czas zwrotu ksi¹¿ki"); return; }
		actualTimeT.setText(new Date().toLocaleString());
	}
	
	public static Object getAllDateinLibrary(){
		File f = new File("LibraryBook.txt");
		Object o = null;
		if(f.exists()){
			try{
			   ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(f)));
			   o = (List<Books>)in.readObject();
			   in.close();
			}catch(FileNotFoundException fileExc){
				JOptionPane.showMessageDialog(null, "Nie znaleziono pliku LibraryBook.txt");
			}catch(IOException ioExc){ 
				JOptionPane.showMessageDialog(null, "B³¹d IO odczytu danych z List<Books>"+ioExc);
			}catch(ClassNotFoundException classExc){ JOptionPane.showMessageDialog(null, "Nie znaleziono Klasy"); }
			return o;
		}else{JOptionPane.showMessageDialog(null, "Nie ma jeszcze ¿adnych danych w bazie"); return null; }
	}
}

class Books implements Serializable{
	private String name = null;
	private String surName = null;
	private Date dataLandBook = null;
	private static Date dataGiveBackBook = new Date();
	private String titleBook = null;
	
	public void setName(String name){
		if(CheckDate.checkName(name)){
		  this.name = name; 
		}else{ JOptionPane.showMessageDialog(null, "WprowadŸ prawid³owo imiê"); }
	}
	public void setsurName(String surName){
		if(CheckDate.checkSurName(surName)){
		  this.surName = surName; 
		}else{ JOptionPane.showMessageDialog(null, "WprowadŸ prwid³owo nazwisko"); }
	}
	public void setDateLand(String dateLand){ 
		System.out.println("DATA to: "+dateLand);
		Date d = null;
		
		if((d = CheckDate.checkLandDate(dateLand)) != null){
		  this.dataLandBook = d;
		}else{ JOptionPane.showMessageDialog(null, "Proszê wprowadziæ prawid³ow¹ datê: yyyy-mm-dd"); return; }
	}
	public static void setDateGive(String dateGive){ 
		System.out.println("setDateGive = "+dateGive);
		Date d = null;
		System.out.println("d_NULL = "+d.toLocaleString());
		if((d = CheckDate.CheckGiveDate(dateGive)) != null){
		  dataGiveBackBook = d;
		}else{
			JOptionPane.showMessageDialog(null, "Proszê wprowadziæ prawid³ow¹ datê: yyyy-mm-dd hh:mm:00"); return; 
		}
	}
	public void setTitle(String title){ 
		if(CheckDate.checkTitle(title)){
		  this.titleBook = title;
		}else{ JOptionPane.showMessageDialog(null, "Proszê wprowadŸiæ prawid³owo tytu³ ksi¹¿ki"); }
	}
	
	public String getName(){ return name; }
	public String getsurName(){ return surName; }
	public Date getDateLand(){ return dataLandBook; }
	public static Date getDateGive(){ return dataGiveBackBook; }
	public String getTitleBook(){ return titleBook; }
}

class CheckDate{
	
	protected static boolean checkName(String name){
		Pattern p = Pattern.compile("\\D+");
		Matcher matcher = p.matcher(name);
		return matcher.matches();
	}
	
	protected static boolean checkSurName(String surName){
		Pattern p = Pattern.compile("\\D+");
		Matcher matcher = p.matcher(surName);
		return matcher.matches();
	}
	
	protected static Date checkLandDate(String dateLand){
		Pattern pat = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
		Matcher matcher = pat.matcher(dateLand);
		
		if(matcher.matches()){
		  String tab[] = dateLand.split("\\-"); //sprawdzenie// sprawdzenie napisaæ do klasy CheckDate.checkDate();
		
		  int year = Integer.parseInt(tab[0]);
		  int msc = Integer.parseInt(tab[1]);
		  int day = Integer.parseInt(tab[2]);
		  Date d = new Date();
		  int hour = d.getHours();
		  int minute = d.getMinutes();
		  GregorianCalendar cal = new GregorianCalendar();
		  cal.set(Calendar.YEAR, year); cal.set(Calendar.MONTH, msc); cal.set(Calendar.DAY_OF_MONTH, day);
		  cal.set(Calendar.HOUR_OF_DAY, hour); cal.set(Calendar.MINUTE, minute);
		  return cal.getTime();
		}else{ return null; }
	}
	
	protected static Date CheckGiveDate(String dateGive){  
		Pattern pat = Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}");
		Matcher matcher = pat.matcher(dateGive);
		GregorianCalendar greg = new GregorianCalendar();	
		
		if(matcher.matches()){
			String dateT = dateGive.substring(0,10);
			String timeT = dateGive.substring(11,19);
			String date[] = dateT.split("\\-");
			String time[] = timeT.split("\\:");
			
			int year = Integer.parseInt(date[0]);
			int msc = Integer.parseInt(date[1]);
			int day = Integer.parseInt(date[2]);
			
			int hour = Integer.parseInt(time[0]);
			int min = Integer.parseInt(time[1]);
			
			greg.set(Calendar.YEAR, year); greg.set(Calendar.MONTH, msc-1); greg.set(Calendar.DAY_OF_MONTH, day);
			greg.set(Calendar.HOUR_OF_DAY,hour); greg.set(Calendar.MINUTE, min); greg.set(Calendar.SECOND, 00);
			
			return greg.getTime();
			
		}else{ return null; }
	}
	
	protected static boolean checkTitle(String title){
		Pattern p = Pattern.compile("[\\D\\d]*");
		Matcher matcher = p.matcher(title);
		return matcher.matches(); 
	}
}

class Sluchacz implements ActionListener{
	private Date date;
	Date d = Books.getDateGive();
	public Sluchacz(Date actualTime){
		this.date = actualTime;
	}
	@Override
	public void actionPerformed(ActionEvent e){ LibraryBook.metoda(date); }
}
class Czasomierz extends Timer{
	private static final long serialVersionUID = 1L;
	
	public Czasomierz(int repeet,ActionListener listener){
		super(1000,listener);
	}
}

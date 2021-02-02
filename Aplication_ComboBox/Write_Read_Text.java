package Aplication_ComboBox;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.io.PrintWriter;
import java.io.File;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.io.StringWriter;
import java.util.Date;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.ArrayList;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

final public class Write_Read_Text extends JDialog{
	private static final long serialVersionUID = 1L;
	
	private Write_Read_Text wrt = this;
	private JPanel panZapis = new JPanel();
	private JLabel labTytulZapis = new JLabel("   Zapis danyc tekstowych");
	private JPanel konfPaneli = new JPanel();
	private JLabel imie = new JLabel("imie: ");
	private JTextField imieT = new JTextField(10);
	private JLabel wiek = new JLabel("wiek: ");
	private JTextField wiekT = new JTextField(5);
	private JLabel pensja = new JLabel("pensja: ");
	private JTextField pensjaT = new JTextField(5);
	private JLabel dataUr = new JLabel("data.ur:(d-mm-r) ");
	private JTextField dataUrT = new JTextField(7);
	private JButton zapis = new JButton("Zapisz dane");
	
	private JPanel panOdczyt = new JPanel();
	private JPanel helpPanelTytul = new JPanel();
	private JLabel labTytulOdczyt = new JLabel("Odczyt danych tekstowych");
	private JTextArea area = new JTextArea(20,1);
	private JButton odczyt = new JButton("Odczyt danych");
	
	private JMenuBar pasek = new JMenuBar();
	private JMenu menu = new JMenu("Menu");
	private JMenuItem rem = new JMenuItem("Usuñ Rekord");
	
	public Write_Read_Text(final JFrame f){
		super(f,"Odczyt/Zapis Plików Tekstowych",false);
		f.setExtendedState(JFrame.ICONIFIED);
		this.setJMenuBar(pasek);pasek.add(menu);menu.add(rem);
		this.setVisible(true);
		this.setLayout(new BorderLayout());
		this.add(panZapis,BorderLayout.NORTH); panZapis.setLayout(new BorderLayout());
		panZapis.add(labTytulZapis,BorderLayout.NORTH);
		panZapis.add(konfPaneli);
		konfPaneli.add(imie); konfPaneli.add(imieT);konfPaneli.add(wiek);konfPaneli.add(wiekT);
		konfPaneli.add(pensja);konfPaneli.add(pensjaT);konfPaneli.add(dataUr);konfPaneli.add(dataUrT);
		konfPaneli.add(zapis);
		
		this.add(panOdczyt,BorderLayout.CENTER);
		panOdczyt.setLayout(new BorderLayout());panOdczyt.add(helpPanelTytul,BorderLayout.NORTH);
		helpPanelTytul.setLayout(new FlowLayout(FlowLayout.CENTER));
		helpPanelTytul.add(labTytulOdczyt);
		area.setBorder(BorderFactory.createLineBorder(Color.blue));
		panOdczyt.add(area,BorderLayout.CENTER);
		panOdczyt.add(odczyt,BorderLayout.SOUTH);
		
		this.pack();
		
		zapis.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				String imie = checkImie();
				Integer wiek = checkWiek();
				Double pensja = checkPensja();
			    Date data = checkDate();
				
				if(imie != null && wiek != null && pensja != null && data != null){
					GregorianCalendar kalendarz = new GregorianCalendar();
					kalendarz.setTime(data);
				  try{
				    PrintWriter print = new PrintWriter(new FileOutputStream(new File("zapisDanych.txt"),true));
				    print.println(imie+"|"+wiek+"|"+pensja+"|"+kalendarz.get(Calendar.DAY_OF_MONTH)+"|"
				    +kalendarz.get(Calendar.MONTH)+"|"+kalendarz.get(Calendar.YEAR));
				    print.close();
				  }catch(FileNotFoundException v){ JOptionPane.showMessageDialog(null, "B³¹d zapisu pliku zapisDanych.txt"); }
				}else{ JOptionPane.showMessageDialog(null, "Proszê o popawne wprowadzenie b³êdnie wskazanej wartoœci!!!"); }
			}
		});
		
		odczyt.addActionListener(new ActionListener(){
			List<String> lista = new ArrayList<String>();
			Scanner scan = null;
			@Override
			public void actionPerformed(ActionEvent e){
				try{
				   scan = new Scanner(new File("zapisDanych.txt"));
				}catch(FileNotFoundException v){ JOptionPane.showMessageDialog(null, "Error read file zapisDanych.txt"); }
				while(scan.hasNext()){
					String line = scan.nextLine();
					String tab[] = line.split("\\|");
					String imie = tab[0];
					int wiek = Integer.parseInt(tab[1]);
					double pensja = Double.parseDouble(tab[2]);
					int year = Integer.parseInt(tab[3]);
					int msc = Integer.parseInt(tab[4]);
					int day = Integer.parseInt(tab[5]);
					StringBuffer buf = new StringBuffer();
					buf.append("imie: "+imie);buf.append(" wiek "+wiek);buf.append(" pensja "+pensja);
					buf.append(" dataUr: "+day+"-"); buf.append(msc+"-");buf.append(year);
					lista.add(buf.toString());
				}
				 
				area.setText("");
				for(String el : lista){
					area.append("id."+lista.indexOf(el)+" "+el+"\n");
				}
				lista.clear();
				scan.close();
			}
		});
		rem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				new RemRecord(wrt);
			}
		});
		//Przywracania okna g³ównego po zamkniêciu okna dialogowego 
		this.addComponentListener(new ComponentAdapter(){
			@Override
			public void componentHidden(ComponentEvent e){
				f.setExtendedState(JFrame.NORMAL);
			}
		});
	}
	public String checkImie(){
		Pattern pattern = Pattern.compile("[a-zA-Z¹êó³æœŸ¿]+");
		Matcher match = pattern.matcher(imieT.getText());
		
		if(match.matches()){
			return imieT.getText();
		}else{
			JOptionPane.showMessageDialog(null, "wprowadz poprawnie imie"); 
			return null;
		}
	}
	
	public Integer checkWiek(){
		Pattern pattern = Pattern.compile("\\d+");
		Matcher match = pattern.matcher(wiekT.getText());
		
		if(match.matches()){
			return Integer.parseInt(wiekT.getText());
		}else{
			JOptionPane.showMessageDialog(null,"Wiek powinien sk³adaæ siê z samych cyfr, proszê poprawiæ dane !");
			return null;
		}
	}
	
	public Double checkPensja(){
		Pattern pattern = Pattern.compile("\\d+\\.\\d{1,3}");
		Matcher match = pattern.matcher(pensjaT.getText());
		
		if(match.matches()){
			return Double.parseDouble(pensjaT.getText());
		}else{
			JOptionPane.showMessageDialog(null, "Pensja sk³ada siê z cyfr i miejsc po przecinku (od 1 do 3) np: 58800.80!");
			return null;
		}
	}
	
	public Date checkDate(){
		Pattern pat = Pattern.compile("\\d{2}-\\d{2}-\\d{4}");
		Matcher match = pat.matcher(dataUrT.getText());
		
		if(match.matches()){
			String tabData[] = dataUrT.getText().split("-");
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

final class RemRecord extends JDialog implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	private List<String> lista = new ArrayList<String>();
	private Scanner scan = null;

	private JLabel tytul = new JLabel("  Usuwanie Rekordów z Bazy");
	private JPanel middlePanel = new JPanel();
	private JLabel info = new JLabel("Wprowadz index do usuniêcia z bazy: ");
	private JTextField idText = new JTextField(5);
	private JButton rem = new JButton("Usuñ Rekord");
	
	public RemRecord(JDialog dial){
		super(dial,"Usuwanie Indexu z Bazy",false);
		this.add(tytul,BorderLayout.NORTH);
		this.add(middlePanel,BorderLayout.CENTER);middlePanel.add(info);middlePanel.add(idText);
		this.add(rem,BorderLayout.SOUTH);
		this.setVisible(true);
		this.pack();
		rem.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e){
		try{
			  scan = new Scanner(new File("zapisDanych.txt"));
			  while(scan.hasNext()){
				  String line = scan.nextLine();
				  lista.add(line.toString());
			  }
			  scan.close();
			}catch(FileNotFoundException v){ JOptionPane.showMessageDialog(null,"Nie mo¿na odnaleŸæ pliku zapisDanych.txt"); }
			
			lista.remove(Integer.parseInt(idText.getText()));
			
			try{
			   PrintWriter print = new PrintWriter("zapisDanych.txt");
			   for(String v : lista){
				   print.println(v);
			   }
			   //print.flush();//
			   print.close();
			}catch(FileNotFoundException v){ JOptionPane.showMessageDialog(null, e); }
			
			lista.clear();
	}
}

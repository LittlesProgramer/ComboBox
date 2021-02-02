package Aplication_ComboBox;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.*;
import java.util.Map;
import java.util.LinkedHashMap;
import java.io.EOFException;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

final public class Write_Read_RAF extends JDialog{
	private static final long serialVersionUID = 1L;
	
	private JPanel panelTop = new JPanel();
	private JPanel panelBottom = new JPanel();
	
	private JLabel labNM = new JLabel("Podaj imie i nazwisko: ");
	private JLabel labHowold = new JLabel("Podaj wiek: ");
	private JLabel labDataUr = new JLabel("Podaj datê ur: ");
	
	private JTextField textNM = new JTextField(10);
	private JTextField textHowOld = new JTextField(5);
	private JTextField textDataUr = new JTextField(5);
	private JButton butWrite = new JButton("Zapis");
	
	private JTextArea area = new JTextArea(10,23);
	private JButton butRead = new JButton("Odczyt");
	
	public Write_Read_RAF(final PanelWithMenu pwm){
		super(pwm,"Random Access File",false);
		pwm.setExtendedState(JFrame.ICONIFIED);
		this.setLayout(new GridLayout(2,1));
		this.add(panelTop); this.add(panelBottom);
		GridBagLayout grid = new GridBagLayout();
		
		panelTop.setLayout(grid); panelBottom.setLayout(grid);
		
		panelTop.add(labNM,new GBCs(0,0,1,1).setInsets(1).setAnchor(GBCs.EAST));
		panelTop.add(textNM,new GBCs(1,0,1,1).setInsets(1).setAnchor(GBCs.WEST));
		panelTop.add(labHowold,new GBCs(0,1,1,1).setInsets(1).setAnchor(GBCs.WEST));
		panelTop.add(textHowOld,new GBCs(1,1,1,1).setInsets(1).setAnchor(GBCs.WEST).setFill(GBCs.HORIZONTAL));
		panelTop.add(labDataUr,new GBCs(0,2,1,1).setInsets(1).setAnchor(GBCs.WEST));
		panelTop.add(textDataUr,new GBCs(1,2,1,1).setInsets(1).setAnchor(GBCs.WEST).setFill(GBCs.HORIZONTAL));
		panelTop.add(butWrite,new GBCs(2,0,1,3).setInsets(1).setFill(GBCs.VERTICAL));
		
		JScrollPane scroll = new JScrollPane(area);
		scroll.setBorder(BorderFactory.createLineBorder(Color.green));
		panelBottom.add(scroll,new GBCs(0,0,2,3).setInsets(1).setFill(GBCs.BOTH).setWeight(1,1));
		panelBottom.add(butRead,new GBCs(2,0,1,3).setFill(GBCs.BOTH).setInsets(1));
		
		this.pack();
		this.setVisible(true);
		
		butWrite.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String NM = checkNM();
				Integer howOld = checkHowOld();
				Date dataUr = checkDataUr();
				GregorianCalendar greg = new GregorianCalendar();
				greg.setTime(dataUr);
				int day = greg.get(Calendar.DAY_OF_MONTH);
				int msc = greg.get(Calendar.MONTH);
				int year = greg.get(Calendar.YEAR);
				
				//Sprawdzenie poprawnoœci wprowadzanych danych
				if(NM == null || howOld == null || dataUr == null){
					JOptionPane.showMessageDialog(null, "Proszê poprawiæ dane zgodnie z wczeœnijszymi instrukcjami !");
					return;
				}
				//zapis danych
				try{
				  RandomAccessFile access = new RandomAccessFile(new File("AccessText.txt"),"rws");
				  access.seek(access.length());
				  access.writeInt(howOld);
				  access.writeInt(day);
				  access.writeInt(msc);
				  access.writeInt(year);
				  access.writeChars(NM+"\n");
				  access.close();
				  //access.seek(0);
				}catch(FileNotFoundException v){
					JOptionPane.showMessageDialog(null, "Nie mo¿na znaleŸæ pliku do zapisu AccessText.txt");
				}catch(IOException z){
					JOptionPane.showMessageDialog(null, "B³¹d zapisu pliku AccessText.txt");
				}
			}
		});
		
		butRead.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				Map<String,Integer> mapa = new LinkedHashMap<String,Integer>();
				area.setText("");
				//odczyt
				try{
				  RandomAccessFile access = new RandomAccessFile(new File("AccessText.txt"),"r");
				  String line = null;
				  while((line = access.readLine()) != null){
					  mapa.put(line, line.length());
				  }
				  access.seek(0);
				  for(Map.Entry<String, Integer> el : mapa.entrySet()){      
					  int wiek = access.readInt();
				      int day = access.readInt();
				      int msc = access.readInt();
				      int year = access.readInt();
				     
				      char tab[] = new char[(el.getValue()-16)/2];
				      for(int x = 0 ; x < ((el.getValue()-16)/2); x++){
				    	  tab[x] = access.readChar();
				    	  System.out.println("tab["+x+"] = "+tab[x]);
				      }
				      String name = String.valueOf(tab);
				      area.append("imie: "+name+" wiek: "+wiek+" dataUr: "+day+"-"+msc+"-"+year);
				      area.append("\n");
				      access.readChar();
				  }
				  access.close();
				  mapa.clear();
				  
				 
				}catch(FileNotFoundException v){
					JOptionPane.showMessageDialog(null,"Nie mogê znaleŸæ pliku do odczytu AccessText.txt");
				}catch(IOException z){
					JOptionPane.showMessageDialog(null, "Nie mogê otworzyæ pliku AccessText.txt: "+z);
				}
			}
		});
		//Przywracania okna g³ównego po zamkniêciu okna dialogowego 
		this.addComponentListener(new ComponentAdapter(){
			@Override
			public void componentHidden(ComponentEvent e){
				pwm.setExtendedState(JFrame.NORMAL);
			}
		});
	}
	
	public String checkNM(){
		Pattern pattern = Pattern.compile("[a-zA-Z¹ê¿Ÿœæñó³£ÓŒÆ¯£\\s]+");
		Matcher match = pattern.matcher(textNM.getText());
		
		if(match.matches()){
			return textNM.getText();
		}else{
			JOptionPane.showMessageDialog(null, "WprowadŸ poprawnie imiê i nazwisko");
			return null;
		}
	}
	
	public Integer checkHowOld(){
		Pattern pattern = Pattern.compile("[0-9]+");
		Matcher match = pattern.matcher(textHowOld.getText());
		
		if(match.matches()){
			return Integer.parseInt(textHowOld.getText());
		}else{
			JOptionPane.showMessageDialog(null, "Wiek powinieñ siê sk³adaæ z samych cyfr");
			return null;
		}
	}
	
	public Date checkDataUr(){
		Pattern pat = Pattern.compile("\\d{2}-\\d{2}-\\d{4}");
		Matcher match = pat.matcher(textDataUr.getText());
		
		if(match.matches()){
			String tabData[] = textDataUr.getText().split("-");
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

final class GBCs extends GridBagConstraints{
	private static final long serialVersionUID = 1L;
	public GBCs(int gridx,int gridy){ 
		this.gridx = gridx; this.gridy = gridy;
	}
	public GBCs(int gridx,int gridy,int gridwidth,int gridheight){
		this.gridx = gridx; this.gridy = gridy; this.gridwidth = gridwidth; this.gridheight = gridheight;
	}
	public GBCs setWeight(int weightx,int weighty){
		this.weightx = weightx; this.weighty = weighty;
		return this;
	}
	public GBCs setFill(int fill){
		this.fill = fill;
		return this;
	}
	public GBCs setAnchor(int anchor){
		this.anchor = anchor;
		return this;
	}
	public GBCs setInsets(int x){
		this.insets = new Insets(x,x,x,x);
		return this;
	}
	public GBCs setInsets(int top,int left,int bottom,int right){
		this.insets = new Insets(top,left,bottom,right);
		return this;
	}
}

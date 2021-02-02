package Aplication_ComboBox;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.GridBagLayout;
import javax.swing.JScrollPane;
import java.beans.EventHandler;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class SortData extends JDialog{
	private static final long serialVersionUID = 1L;
	
	ArrayList<DaneOsobowe> lista = new ArrayList<DaneOsobowe>();
	
	JLabel tytul = new JLabel("<html><h1><i>Sortowanie Danych</i></h1><hr></html>");
	JLabel imie = new JLabel("Podaj imie: ");
	JLabel wiek = new JLabel("Podaj wiek: ");
	
	JTextField textImie = new JTextField(7);
	JTextField textWiek = new JTextField(7);

	JTextArea ekran = new JTextArea(10,7);
	JScrollPane scroll = new JScrollPane(ekran);
	
	JButton dodaj = new JButton("Dodaj");
	JButton sort = new JButton("Sortuj");
	
	public SortData(final PanelWithMenu pwm){
		super(pwm,"Sortowanie Danych",false);
		pwm.setExtendedState(JFrame.ICONIFIED);
		GridBagLayout grid = new GridBagLayout();
		this.setLayout(grid);
		
		ActionListener akcjaAdd = EventHandler.create(ActionListener.class, this, "akcjaAdd");
		ActionListener akcjaSort = EventHandler.create(ActionListener.class, this, "akcjaSort");
		
		this.add(tytul,new GBCe(0,0,5,1).setInsets(1));
		tytul.setForeground(Color.blue);
		this.add(imie,new GBCe(0,1,1,1).setInsets(1));
		this.add(textImie,new GBCe(1,1,1,1).setInsets(1).setWeight(1, 0).setFill(GBCe.HORIZONTAL));
		this.add(wiek,new GBCe(2,1,1,1).setInsets(1));
		this.add(textWiek,new GBCe(3,1,1,1).setInsets(1).setWeight(1, 0).setFill(GBCe.HORIZONTAL));
		this.add(dodaj,new GBCe(4,1,1,1).setInsets(1));
		this.add(ekran,new GBCe(0,2,5,3).setInsets(1).setWeight(1, 1).setFill(GBCe.BOTH));
		ekran.setBorder(BorderFactory.createLineBorder(Color.blue));
		this.add(sort,new GBCe(0,5,5,1).setInsets(1).setWeight(1, 0).setFill(GBCe.HORIZONTAL));
		dodaj.addActionListener(akcjaAdd);
		sort.addActionListener(akcjaSort);
		
		this.setVisible(true);
		this.pack();
		
		//Przywracania okna g³ównego po zamkniêciu okna dialogowego
		this.addComponentListener(new ComponentAdapter(){
			@Override
			public void componentHidden(ComponentEvent e){
				pwm.setExtendedState(JFrame.NORMAL);
			}
		});
	}
	@SuppressWarnings("unchecked")
	public void akcjaSort(){
		Path path = Paths.get(".").resolve("SortowaneDane.txt").normalize().toAbsolutePath();
		if(!(path.toFile().exists())){
			JOptionPane.showMessageDialog(null, "Nie utworzono pliku SortowaneDane.txt," +
			" wiêc aplikacja nie posiada danych które mog³a by posortowaæ");
			return;
		}
		lista.clear();
		try{
		  ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File("SortowaneDane.txt"))));
		  lista = (ArrayList<DaneOsobowe>)in.readObject();
		  in.close();
		}catch(ClassNotFoundException klasa){
			JOptionPane.showMessageDialog(null, "Brak typu klasy odpowiedniego dla odczytywanego obiektu"+klasa);
		}catch(FileNotFoundException file){
			JOptionPane.showMessageDialog(null, "Nie mogê odnaleŸæ pliku SortowaneDane.txt");
		}catch(IOException io){ JOptionPane.showMessageDialog(null,"B³¹d otwarcia pliku SortowaneDane.txt"); }
		DaneOsobowe tab[] = new DaneOsobowe[lista.size()];
		lista.toArray(tab);
		Arrays.sort(tab);
		lista.clear();
		for(DaneOsobowe el : tab){
			lista.add(el);
		}
		ekran.setText("");
		for(DaneOsobowe el : lista){
			ekran.append(el.getDane()+"\n");
		}
	    
	}
	@SuppressWarnings("unchecked")
	public void akcjaAdd(){		   
		try{
		 Path path = Paths.get(".").resolve("SortowaneDane.txt").normalize().toAbsolutePath();
		 if(!(path.toFile().exists())){
		      @SuppressWarnings("unused")
		      Path pat = Files.createFile(Paths.get(".").resolve("SortowaneDane.txt"));
		 }else{
		  ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File("SortowaneDane.txt"))));
		  lista = (ArrayList<DaneOsobowe>)in.readObject();
		  in.close();
		 }
		}catch(ClassNotFoundException klasa){
			JOptionPane.showMessageDialog(null, "Nie moge dopasowaæ klasy w funkcji sortowania Danych "+klasa);
		}catch(FileNotFoundException file){ 
			JOptionPane.showMessageDialog(null, "Nie mo¿na znaleŸæ pliku SortowaneDane.txt "+file); 
		}catch(IOException io){ JOptionPane.showMessageDialog(null, "B³¹d otwarcia pliku SortowaneDane.txt "+io);}
		
		lista.add(new DaneOsobowe(textImie.getText(),Integer.parseInt(textWiek.getText())));
		ekran.setText("");
		for(DaneOsobowe el: lista){
			ekran.append(el.getDane()+"\n");
		}
		
		try{
		   ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File("SortowaneDane.txt"))));
		   out.writeObject(lista);
		   out.close();
		}catch(FileNotFoundException f){
			JOptionPane.showMessageDialog(null, "Podczas Odczytu danych, nie mogê znaleŸæ pliku SortowaneDane.txt "+f);
		}catch(IOException io){ JOptionPane.showMessageDialog(null, "B³¹d zapisu pliku SortowaneDane.txt "+io); }
	}
}
class DaneOsobowe implements Serializable,Comparable<DaneOsobowe>{
	private static final long serialVersionUID = 1L;
	private String name;
	private int wiek = 0;
	public DaneOsobowe(String n,int w){ this.name = n; this.wiek = w; }
	public String getDane(){ return "imie = "+name+" wiek = "+wiek; }
	@Override
	public int compareTo(DaneOsobowe other){
		if(name.compareTo(other.name) == 0){
			return Integer.compare(wiek, other.wiek);
		}else{ return name.compareTo(other.name); }
	}
}
class GBCe extends GridBagConstraints{
	private static final long serialVersionUID = 1L;
	public GBCe(int gridx,int gridy){this.gridx = gridx; this.gridy = gridy; }
	public GBCe(int gridx,int gridy,int gridwidth,int gridheight){
		this.gridx = gridx; this.gridy = gridy; this.gridwidth = gridwidth; this.gridheight = gridheight;
	}
	public GBCe setFill(int fill){
		this.fill = fill;
		return this;
	}
	public GBCe setAnchor(int anchor){
		this.anchor =  anchor;
		return this;
	}
	public GBCe setWeight(int weightx,int weighty){
		this.weightx = weightx; this.weighty = weighty;
		return this;
	}
	public GBCe setInsets(int x){
		this.insets = new Insets(x,x,x,x);
		return this;
	}
	public GBCe setInsets(int top,int left,int bottom,int right){
		this.insets = new Insets(top,left,bottom,right);
		return this;
	}
}

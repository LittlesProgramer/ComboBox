package Aplication_ComboBox;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.InputStream;
import java.util.Scanner;
import java.applet.*;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.File;
import java.io.PrintWriter;
import java.nio.file.*;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.GregorianCalendar;


public class WWWInfoAplication extends JApplet{
	private static final long serialVersionUID = 1L;
	
	JLabel tytul = new JLabel();
	JLabel autorAp = new JLabel("autor aplikacji: ");
	JLabel showAutor = new JLabel();
	JLabel wersionAp = new JLabel("wersja aplikacji: ");
	JLabel showWersion = new JLabel("");
	JLabel termAp = new JLabel("data wydania: ");
	JLabel showTerm = new JLabel();
	
	@Override
	public void init(){
		EventQueue.invokeLater(new Runnable(){
			@Override
			public void run(){
				GridBagLayout layout = new GridBagLayout();
				setLayout(layout);
				
				add(tytul,new GBCy(0,0,3,1).setInsets(3));
				add(autorAp,new GBCy(0,1,1,1).setInsets(3).setAnchor(GBCy.WEST));
				add(showAutor,new GBCy(1,1,1,1).setInsets(3));
				add(wersionAp,new GBCy(0,2,1,1).setInsets(3).setAnchor(GBCy.WEST));
				add(showWersion,new GBCy(1,2,1,1).setInsets(3));
				add(termAp,new GBCy(0,3,1,1).setInsets(3).setAnchor(GBCy.WEST));
				add(showTerm,new GBCy(1,3,1,1).setInsets(3));
				
				InputStream inAu = WWWInfoAplication.class.getResourceAsStream("../ApWWWInfo/author.txt");
				Scanner scanAu = new Scanner(inAu);
				String autor = scanAu.nextLine();
				showAutor.setText(autor);
				
				InputStream inWer = WWWInfoAplication.class.getResourceAsStream("../ApWWWInfo/wersion.txt");
				Scanner scanWer = new Scanner(inWer);
				String wersion = scanWer.nextLine();
				showWersion.setText(wersion);
				
				InputStream inTerm = WWWInfoAplication.class.getResourceAsStream("../ApWWWInfo/term.txt");
				Scanner scanTerm = new Scanner(inTerm);
				String termin = scanTerm.nextLine();
				showTerm.setText(termin);
				
				int fontSize = Integer.parseInt(getParameter("size"));
				String fontStyle = getParameter("font");
				Font f = new Font(fontStyle,Font.BOLD,fontSize);
				showAutor.setFont(f); showWersion.setFont(f); showTerm.setFont(f);
				
				String color = getParameter("color");
				
				if(color.equals("red")){
					showAutor.setForeground(Color.red);
					showWersion.setForeground(Color.red);
					showTerm.setForeground(Color.red);
				}else if(color.equals("yellow")){
					showAutor.setForeground(Color.yellow);
					showWersion.setForeground(Color.yellow);
					showTerm.setForeground(Color.yellow);
				}else if(color.equals("blue")){
					showAutor.setForeground(Color.blue);
					showWersion.setForeground(Color.blue);
					showTerm.setForeground(Color.blue);
				}else if(color.equals("green")){
					showAutor.setForeground(Color.green);
					showWersion.setForeground(Color.green);
					showTerm.setForeground(Color.green);
				}else{
					showAutor.setForeground(Color.magenta);
					showWersion.setForeground(Color.magenta);
					showTerm.setForeground(Color.magenta);
				}
			}
		});
	}	
}

class GBCy extends GridBagConstraints{
	public GBCy(int gridx,int gridy){
		this.gridx = gridx; this.gridy = gridy;
	}
	
	public GBCy(int gridx,int gridy,int gridwidth,int gridheight){
		this.gridx = gridx; this.gridy = gridy; this.gridwidth = gridwidth; this.gridheight = gridheight;
	}
	
	public GBCy setInsets(int insets){
		this.insets = new Insets(insets,insets,insets,insets); return this;
	}
	public GBCy setInsets(int top,int left,int bottom,int right){
		this.insets = new Insets(top,left,bottom,right); return this;
	}
	public GBCy setWeight(int weightx,int weighty){
		this.weightx = weightx; this.weighty = weighty; return this;
	}
	public GBCy setFill(int fill){
		this.fill = fill; return this;
	}
	public GBCy setAnchor(int anchor){
		this.anchor = anchor; return this;
	}
}

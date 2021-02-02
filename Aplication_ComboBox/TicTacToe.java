package Aplication_ComboBox;
import java.awt.event.*;
import java.awt.EventQueue;
import java.awt.Toolkit;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.EventHandler;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

class TicTacToe extends JDialog{
	protected static PolaGry tabPolaGry[] = new PolaGry[9]; // tablica przechowujaca referencje do obiektw klasy PolaGry
	public TicTacToe(){}
	public TicTacToe(final PanelWithMenu pwm){
		super(pwm,"Kó³ko i Krzy¿yk",true);
		//System.out.println("Konstruktor TicTac Toe = "+this);
		//zminimalizowanie okna g³ównego
		pwm.setExtendedState(JFrame.ICONIFIED);
		this.setLayout(new GridLayout(3,3));
		this.putPola();
		this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		this.setSize(300, 300);
		this.getContentPane().setBackground(Color.cyan);
		this.setLocation(880, 10);
		this.setVisible(true);
		//Przywracania okna g³ównego po zamkniêciu okna dialogowego
		this.addComponentListener(new ComponentAdapter(){
			@Override
			public void componentHidden(ComponentEvent e){
				Action.resetAll();
				pwm.setExtendedState(JFrame.NORMAL);
			}
		});
	} 
	
	public void putPola(){
		for(int i = 0; i < 9; i++){
			tabPolaGry[i] = new PolaGry();
			add(tabPolaGry[i]);
			tabPolaGry[i].setBorder(BorderFactory.createLineBorder(Color.green));
			tabPolaGry[i].addMouseListener(new Action());
			tabPolaGry[i].setName(String.valueOf(i));
		}
	}
	public PolaGry getReference(int index){ // metoda oddajaca referencje do obiektu klasy PolaGry
		return tabPolaGry[index];
	}
}

class PolaGry extends JLabel{
	private final int sizeRect = 10;
	private Rectangle2D rect = new Rectangle2D.Double(0, 0, 15, 15);;
	private Ellipse2D circle = new Ellipse2D.Double(0, 0, 15, 15);;
	private String figura = "";
	//{ System.out.println("Klasa PolaGry = "+this); }
	
	public void paintComponent(Graphics g){// w zaleznosci od figury rysowane kó³ko lub krzy¿yk
		Graphics2D g2 = (Graphics2D)g;
		if(figura.equals("rect")){
			if(rect == null) return;
			g2.draw(rect);
		}else if(figura.equals("kolo")){ if(circle == null)  return; g2.draw(circle); }
	}
	public void rysuj(String figure){// metoda przyjmuj¹ca jako argument figure(kó³ko lub krzy¿yk)
		this.figura = figure;
		this.repaint();
	}
}

class Action extends MouseAdapter{	
	private static String figure = "rect";
	private static TablicaRuchow tabRuchow[];// = new TablicaRuchow[9]; // tablica przyjmuj¹ca ruchy usera i compa// argumenty to: (figura i nrPola)
	//{System.out.println("TABLICA RUCHÓW = "+ tabRuchow +" KLASA ACTION = "+this); }
	
	private static AnalizaRuchowUseraAndCompa analiza;
	private static String statusGry = "";// remis,computer,user
	private static int iloscRuchow = 0;
	
	public Action(){ /*!!!!!!!*/tabRuchow = new TablicaRuchow[9]; /*!!!!!!!*//*System.out.println(" Konstruktor ACTION = "+this + " TablicaRuchow = "+tabRuchow);*/ }
	public void mousePressed(MouseEvent event){
		PolaGry pola = (PolaGry)event.getSource();
		if(figure.equals("rect")){
		  if(iloscRuchow == 8){ JOptionPane.showMessageDialog(null,"Remis"); }
		  pola.rysuj(figure);
		  iloscRuchow++;
		  tabRuchow[Integer.parseInt(pola.getName())] = new TablicaRuchow("rect",Integer.parseInt(pola.getName()));
		  analiza = new AnalizaRuchowUseraAndCompa(tabRuchow,iloscRuchow,statusGry);
	
		  //System.out.println("kolejny ruch = "+analiza.getKolejnyRuch());
		  
		  this.figure = "kolo";  
		  TicTacToe.tabPolaGry[analiza.getKolejnyRuch()].rysuj(figure);
		  this.figure = "rect"; 
		  iloscRuchow++;
	   }
	}
	//Resetuje wszystkie pola
	public static void resetAll(){
		TicTacToe tic = new TicTacToe();
		TicTacToe.tabPolaGry = new PolaGry[9];
		tic.putPola();
		iloscRuchow = 0;
		statusGry = "";
		figure = "rect";
		tabRuchow = new TablicaRuchow[9];
		analiza = new AnalizaRuchowUseraAndCompa(tabRuchow,0,"");
	}
}

class TablicaRuchow{
	//{ System.out.println("klasa TablicaRuchów = "+this); }
	private String XorO; // if krzyz draw rect if kolo draw circle
	private int nrPola;
	public TablicaRuchow(String XorO,int nrPola){ this.XorO = XorO; this.nrPola = nrPola; }
	public String getXorO(){ return this.XorO; } 
	public int getNrPola(){ return nrPola; }
}

class AnalizaRuchowUseraAndCompa{
	private static TablicaRuchow tabRuchow[] = new TablicaRuchow[9];
	private int iloscRuchow;
	private PolaGry polaGry;
	private String statusGry;
	private int kolejnyRuch;
	
	public AnalizaRuchowUseraAndCompa(TablicaRuchow tab[],int iloscRuchow,String statusGry){
		
		this.tabRuchow = tab; this.iloscRuchow = iloscRuchow; this.statusGry = statusGry;
		if((statusGry = this.analizujUsera()).equals("user")){
			JOptionPane.showMessageDialog(null, "Wygra³eœ");  return; //System.exit(0);
		}else if(statusGry.equals("kolejny ruch")){
			this.analizujCompa();
			/*PolaGry*/ polaGry = TicTacToe.tabPolaGry[kolejnyRuch];
			tabRuchow[Integer.parseInt(polaGry.getName())] = new TablicaRuchow("kolo",Integer.parseInt(polaGry.getName()));
		}
	}
	
	public String analizujUsera(){
	  for(TablicaRuchow el : tabRuchow){
		for(TablicaRuchow ele : tabRuchow){
		  for(TablicaRuchow elem : tabRuchow){
			if(el != null && ele != null && elem != null){
			  if((el.getNrPola() == 0 && el.getXorO().equals("rect")) && (ele.getNrPola() == 1 && ele.getXorO().equals("rect")) && (elem.getNrPola() == 2 && elem.getXorO().equals("rect"))){ return "user"; }
			  if((el.getNrPola() == 3 && el.getXorO().equals("rect")) && (ele.getNrPola() == 4 && ele.getXorO().equals("rect")) && (elem.getNrPola() == 5 && elem.getXorO().equals("rect"))){ return "user"; }
			  if((el.getNrPola() == 6 && el.getXorO().equals("rect")) && (ele.getNrPola() == 7 && ele.getXorO().equals("rect")) && (elem.getNrPola() == 8 && elem.getXorO().equals("rect"))){ return "user"; }
			  if((el.getNrPola() == 0 && el.getXorO().equals("rect")) && (ele.getNrPola() == 3 && ele.getXorO().equals("rect")) && (elem.getNrPola() == 6 && elem.getXorO().equals("rect"))){ return "user"; }
			  if((el.getNrPola() == 1 && el.getXorO().equals("rect")) && (ele.getNrPola() == 4 && ele.getXorO().equals("rect")) && (elem.getNrPola() == 7 && elem.getXorO().equals("rect"))){ return "user"; }
			  if((el.getNrPola() == 2 && el.getXorO().equals("rect")) && (ele.getNrPola() == 5 && ele.getXorO().equals("rect")) && (elem.getNrPola() == 8 && elem.getXorO().equals("rect"))){ return "user"; }
			  if((el.getNrPola() == 6 && el.getXorO().equals("rect")) && (ele.getNrPola() == 4 && ele.getXorO().equals("rect")) && (elem.getNrPola() == 2 && elem.getXorO().equals("rect"))){ return "user"; }
			  if((el.getNrPola() == 0 && el.getXorO().equals("rect")) && (ele.getNrPola() == 4 && ele.getXorO().equals("rect")) && (elem.getNrPola() == 8 && elem.getXorO().equals("rect"))){ return "user"; }
			}
		  }  
		}  
	  }
	  return "kolejny ruch";
	}
	
	public void analizujCompa(){
	 	
	  for(TablicaRuchow el : tabRuchow){
		for(TablicaRuchow ele : tabRuchow){
		  for(TablicaRuchow elem : tabRuchow){
			 if(el != null  && ele != null && elem != null){
				 
				 //Wygrana komputera
				 if((el.getNrPola() == 0 && el.getXorO().equals("kolo")) && (ele.getNrPola() == 1 && ele.getXorO().equals("kolo"))){ if(tabRuchow[2] != null){}else{ this.kolejnyRuch = 2; JOptionPane.showMessageDialog(null, "wygra³ komputer"); return;} }
				 if((el.getNrPola() == 0 && el.getXorO().equals("kolo")) && (ele.getNrPola() == 2 && ele.getXorO().equals("kolo"))){ if(tabRuchow[1] != null){}else{ this.kolejnyRuch = 1; JOptionPane.showMessageDialog(null, "wygra³ komputer"); return;} }				
				 if((el.getNrPola() == 1 && el.getXorO().equals("kolo")) && (ele.getNrPola() == 2 && ele.getXorO().equals("kolo"))){ if(tabRuchow[0] != null){}else{ this.kolejnyRuch = 0; JOptionPane.showMessageDialog(null, "wygra³ komputer"); return;}  }								 
				 if((el.getNrPola() == 3 && el.getXorO().equals("kolo")) && (ele.getNrPola() == 4 && ele.getXorO().equals("kolo"))){ if(tabRuchow[5] != null){}else{ this.kolejnyRuch = 5; JOptionPane.showMessageDialog(null, "wygra³ komputer"); return;} }
				 if((el.getNrPola() == 3 && el.getXorO().equals("kolo")) && (ele.getNrPola() == 5 && ele.getXorO().equals("kolo"))){ if(tabRuchow[4] != null){}else{ this.kolejnyRuch = 4; JOptionPane.showMessageDialog(null, "wygra³ komputer"); return;}  }
				 if((el.getNrPola() == 4 && el.getXorO().equals("kolo")) && (ele.getNrPola() == 5 && ele.getXorO().equals("kolo"))){ if(tabRuchow[3] != null){}else{ this.kolejnyRuch = 3; JOptionPane.showMessageDialog(null, "wygra³ komputer"); return;}  }
				 if((el.getNrPola() == 6 && el.getXorO().equals("kolo")) && (ele.getNrPola() == 7 && ele.getXorO().equals("kolo"))){ if(tabRuchow[8] != null){}else{ this.kolejnyRuch = 8; JOptionPane.showMessageDialog(null, "wygra³ komputer"); return;}  }
				 if((el.getNrPola() == 6 && el.getXorO().equals("kolo")) && (ele.getNrPola() == 8 && ele.getXorO().equals("kolo"))){ if(tabRuchow[7] != null){}else{ this.kolejnyRuch = 7; JOptionPane.showMessageDialog(null, "wygra³ komputer"); return;} }
				 if((el.getNrPola() == 7 && el.getXorO().equals("kolo")) && (ele.getNrPola() == 8 && ele.getXorO().equals("kolo"))){ if(tabRuchow[6] != null){}else{ this.kolejnyRuch = 6; JOptionPane.showMessageDialog(null, "wygra³ komputer"); return;}  }
				 if((el.getNrPola() == 0 && el.getXorO().equals("kolo")) && (ele.getNrPola() == 3 && ele.getXorO().equals("kolo"))){ if(tabRuchow[6] != null){}else{ this.kolejnyRuch = 6; JOptionPane.showMessageDialog(null, "wygra³ komputer"); return;}  }
				 if((el.getNrPola() == 0 && el.getXorO().equals("kolo")) && (ele.getNrPola() == 6 && ele.getXorO().equals("kolo"))){ if(tabRuchow[3] != null){}else{ this.kolejnyRuch = 3; JOptionPane.showMessageDialog(null, "wygra³ komputer"); return;}  }
				 if((el.getNrPola() == 3 && el.getXorO().equals("kolo")) && (ele.getNrPola() == 6 && ele.getXorO().equals("kolo"))){ if(tabRuchow[0] != null){}else{ this.kolejnyRuch = 0; JOptionPane.showMessageDialog(null, "wygra³ komputer"); return;}  }
				 if((el.getNrPola() == 1 && el.getXorO().equals("kolo")) && (ele.getNrPola() == 4 && ele.getXorO().equals("kolo"))){ if(tabRuchow[7] != null){}else{ this.kolejnyRuch = 7; JOptionPane.showMessageDialog(null, "wygra³ komputer"); return;}  }
				 if((el.getNrPola() == 1 && el.getXorO().equals("kolo")) && (ele.getNrPola() == 7 && ele.getXorO().equals("kolo"))){ if(tabRuchow[4] != null){}else{ this.kolejnyRuch = 4; JOptionPane.showMessageDialog(null, "wygra³ komputer"); return;}  }
				 if((el.getNrPola() == 4 && el.getXorO().equals("kolo")) && (ele.getNrPola() == 7 && ele.getXorO().equals("kolo"))){ if(tabRuchow[1] != null){}else{ this.kolejnyRuch = 1; JOptionPane.showMessageDialog(null, "wygra³ komputer"); return;}  }
				 if((el.getNrPola() == 2 && el.getXorO().equals("kolo")) && (ele.getNrPola() == 5 && ele.getXorO().equals("kolo"))){ if(tabRuchow[8] != null){}else{ this.kolejnyRuch = 8; JOptionPane.showMessageDialog(null, "wygra³ komputer"); return;}  }
				 if((el.getNrPola() == 2 && el.getXorO().equals("kolo")) && (ele.getNrPola() == 8 && ele.getXorO().equals("kolo"))){ if(tabRuchow[5] != null){}else{ this.kolejnyRuch = 5; JOptionPane.showMessageDialog(null, "wygra³ komputer"); return;} }
				 if((el.getNrPola() == 5 && el.getXorO().equals("kolo")) && (ele.getNrPola() == 8 && ele.getXorO().equals("kolo"))){ if(tabRuchow[2] != null){}else{ this.kolejnyRuch = 2; JOptionPane.showMessageDialog(null, "wygra³ komputer"); return;}  }
				 if((el.getNrPola() == 2 && el.getXorO().equals("kolo")) && (ele.getNrPola() == 4 && ele.getXorO().equals("kolo"))){ if(tabRuchow[6] != null){}else{ this.kolejnyRuch = 6; JOptionPane.showMessageDialog(null, "wygra³ komputer"); return;}  }
				 if((el.getNrPola() == 2 && el.getXorO().equals("kolo")) && (ele.getNrPola() == 6 && ele.getXorO().equals("kolo"))){ if(tabRuchow[4] != null){}else{ this.kolejnyRuch = 4; JOptionPane.showMessageDialog(null, "wygra³ komputer"); return;}  }
				 if((el.getNrPola() == 4 && el.getXorO().equals("kolo")) && (ele.getNrPola() == 6 && ele.getXorO().equals("kolo"))){ if(tabRuchow[2] != null){}else{ this.kolejnyRuch = 2; JOptionPane.showMessageDialog(null, "wygra³ komputer"); return;}  }
				 if((el.getNrPola() == 0 && el.getXorO().equals("kolo")) && (ele.getNrPola() == 4 && ele.getXorO().equals("kolo"))){ if(tabRuchow[8] != null){}else{ this.kolejnyRuch = 8; JOptionPane.showMessageDialog(null, "wygra³ komputer"); return;}  }
				 if((el.getNrPola() == 0 && el.getXorO().equals("kolo")) && (ele.getNrPola() == 8 && ele.getXorO().equals("kolo"))){ if(tabRuchow[4] != null){}else{ this.kolejnyRuch = 4; JOptionPane.showMessageDialog(null, "wygra³ komputer"); return;}  }
				 if((el.getNrPola() == 4 && el.getXorO().equals("kolo")) && (ele.getNrPola() == 8 && ele.getXorO().equals("kolo"))){ if(tabRuchow[2] != null){}else{ this.kolejnyRuch = 0; JOptionPane.showMessageDialog(null, "wygra³ komputer"); return;}  }
				 
				 
				 // blokowanie przeciwnika przy 2 takich zamych znakach
				 
				 
				 if((el.getNrPola() == 0 && el.getXorO().equals("rect")) && (ele.getNrPola() == 1 && ele.getXorO().equals("rect"))){ if(tabRuchow[2] != null){  }else{ this.kolejnyRuch = 2; return; } } 
				 if((el.getNrPola() == 1 && el.getXorO().equals("rect")) && (ele.getNrPola() == 0 && ele.getXorO().equals("rect"))){ if(tabRuchow[2] != null){  }else{ this.kolejnyRuch = 2; return; } } 
				 if((el.getNrPola() == 0 && el.getXorO().equals("rect")) && (ele.getNrPola() == 2 && ele.getXorO().equals("rect"))){ if(tabRuchow[1] != null){  }else{ this.kolejnyRuch = 1; return; } } 
				 if((el.getNrPola() == 2 && el.getXorO().equals("rect")) && (ele.getNrPola() == 0 && ele.getXorO().equals("rect"))){ if(tabRuchow[1] != null){  }else{ this.kolejnyRuch = 1; return; } } 
				 if((el.getNrPola() == 1 && el.getXorO().equals("rect")) && (ele.getNrPola() == 2 && ele.getXorO().equals("rect"))){ if(tabRuchow[0] != null){  }else{ this.kolejnyRuch = 0; return; } } 
				 if((el.getNrPola() == 2 && el.getXorO().equals("rect")) && (ele.getNrPola() == 1 && ele.getXorO().equals("rect"))){ if(tabRuchow[0] != null){  }else{ this.kolejnyRuch = 0; return; } } 
				 
				 if((el.getNrPola() == 3 && el.getXorO().equals("rect")) && (ele.getNrPola() == 4 && ele.getXorO().equals("rect"))){ if(tabRuchow[5] != null){  }else{ this.kolejnyRuch = 5; return; } }
				 if((el.getNrPola() == 4 && el.getXorO().equals("rect")) && (ele.getNrPola() == 3 && ele.getXorO().equals("rect"))){ if(tabRuchow[5] != null){  }else{ this.kolejnyRuch = 5; return; } } 
				 if((el.getNrPola() == 3 && el.getXorO().equals("rect")) && (ele.getNrPola() == 5 && ele.getXorO().equals("rect"))){ if(tabRuchow[4] != null){  }else{ this.kolejnyRuch = 4; return; } }
				 if((el.getNrPola() == 5 && el.getXorO().equals("rect")) && (ele.getNrPola() == 3 && ele.getXorO().equals("rect"))){ if(tabRuchow[4] != null){  }else{ this.kolejnyRuch = 4; return; } } 
				 if((el.getNrPola() == 4 && el.getXorO().equals("rect")) && (ele.getNrPola() == 5 && ele.getXorO().equals("rect"))){ if(tabRuchow[3] != null){  }else{ this.kolejnyRuch = 3; return; } }
				 if((el.getNrPola() == 5 && el.getXorO().equals("rect")) && (ele.getNrPola() == 4 && ele.getXorO().equals("rect"))){ if(tabRuchow[3] != null){  }else{ this.kolejnyRuch = 3; return; } } 
				 
				 if((el.getNrPola() == 6 && el.getXorO().equals("rect")) && (ele.getNrPola() == 7 && ele.getXorO().equals("rect"))){ if(tabRuchow[8] != null){  }else{ this.kolejnyRuch = 8; return; } }
				 if((el.getNrPola() == 7 && el.getXorO().equals("rect")) && (ele.getNrPola() == 6 && ele.getXorO().equals("rect"))){ if(tabRuchow[8] != null){  }else{ this.kolejnyRuch = 8; return; } } 
				 if((el.getNrPola() == 6 && el.getXorO().equals("rect")) && (ele.getNrPola() == 8 && ele.getXorO().equals("rect"))){ if(tabRuchow[7] != null){  }else{ this.kolejnyRuch = 7; return; } }
				 if((el.getNrPola() == 8 && el.getXorO().equals("rect")) && (ele.getNrPola() == 6 && ele.getXorO().equals("rect"))){ if(tabRuchow[7] != null){  }else{ this.kolejnyRuch = 7; return; } } 
				 if((el.getNrPola() == 7 && el.getXorO().equals("rect")) && (ele.getNrPola() == 8 && ele.getXorO().equals("rect"))){ if(tabRuchow[6] != null){  }else{ this.kolejnyRuch = 6; return; } }
				 if((el.getNrPola() == 8 && el.getXorO().equals("rect")) && (ele.getNrPola() == 7 && ele.getXorO().equals("rect"))){ if(tabRuchow[6] != null){  }else{ this.kolejnyRuch = 6; return; } } 
				 
				 if((el.getNrPola() == 0 && el.getXorO().equals("rect")) && (ele.getNrPola() == 3 && ele.getXorO().equals("rect"))){ if(tabRuchow[6] != null){  }else{ this.kolejnyRuch = 6; return; } }
				 if((el.getNrPola() == 3 && el.getXorO().equals("rect")) && (ele.getNrPola() == 0 && ele.getXorO().equals("rect"))){ if(tabRuchow[6] != null){  }else{ this.kolejnyRuch = 6; return; } } 
				 if((el.getNrPola() == 0 && el.getXorO().equals("rect")) && (ele.getNrPola() == 6 && ele.getXorO().equals("rect"))){ if(tabRuchow[3] != null){  }else{ this.kolejnyRuch = 3; return; } }
				 if((el.getNrPola() == 6 && el.getXorO().equals("rect")) && (ele.getNrPola() == 0 && ele.getXorO().equals("rect"))){ if(tabRuchow[6] != null){  }else{ this.kolejnyRuch = 3; return; } } 
				 if((el.getNrPola() == 3 && el.getXorO().equals("rect")) && (ele.getNrPola() == 6 && ele.getXorO().equals("rect"))){ if(tabRuchow[0] != null){  }else{ this.kolejnyRuch = 0; return; } }
				 if((el.getNrPola() == 6 && el.getXorO().equals("rect")) && (ele.getNrPola() == 3 && ele.getXorO().equals("rect"))){ if(tabRuchow[0] != null){  }else{ this.kolejnyRuch = 0; return; } } 
				 
				 if((el.getNrPola() == 1 && el.getXorO().equals("rect")) && (ele.getNrPola() == 4 && ele.getXorO().equals("rect"))){ if(tabRuchow[7] != null){  }else{ this.kolejnyRuch = 7; return; } }
				 if((el.getNrPola() == 4 && el.getXorO().equals("rect")) && (ele.getNrPola() == 1 && ele.getXorO().equals("rect"))){ if(tabRuchow[7] != null){  }else{ this.kolejnyRuch = 7; return; } } 
				 if((el.getNrPola() == 1 && el.getXorO().equals("rect")) && (ele.getNrPola() == 7 && ele.getXorO().equals("rect"))){ if(tabRuchow[4] != null){  }else{ this.kolejnyRuch = 4; return; } } 
				 if((el.getNrPola() == 7 && el.getXorO().equals("rect")) && (ele.getNrPola() == 1 && ele.getXorO().equals("rect"))){ if(tabRuchow[4] != null){  }else{ this.kolejnyRuch = 4; return; } } 
				 if((el.getNrPola() == 4 && el.getXorO().equals("rect")) && (ele.getNrPola() == 7 && ele.getXorO().equals("rect"))){ if(tabRuchow[1] != null){  }else{ this.kolejnyRuch = 1; return; } }
				 if((el.getNrPola() == 7 && el.getXorO().equals("rect")) && (ele.getNrPola() == 4 && ele.getXorO().equals("rect"))){ if(tabRuchow[1] != null){  }else{ this.kolejnyRuch = 1; return; } } 
				 
				 if((el.getNrPola() == 2 && el.getXorO().equals("rect")) && (ele.getNrPola() == 5 && ele.getXorO().equals("rect"))){ if(tabRuchow[8] != null){  }else{ this.kolejnyRuch = 8; return; } }
				 if((el.getNrPola() == 5 && el.getXorO().equals("rect")) && (ele.getNrPola() == 2 && ele.getXorO().equals("rect"))){ if(tabRuchow[8] != null){  }else{ this.kolejnyRuch = 8; return; } } 
				 if((el.getNrPola() == 2 && el.getXorO().equals("rect")) && (ele.getNrPola() == 8 && ele.getXorO().equals("rect"))){ if(tabRuchow[5] != null){  }else{ this.kolejnyRuch = 5; return; } }
				 if((el.getNrPola() == 8 && el.getXorO().equals("rect")) && (ele.getNrPola() == 2 && ele.getXorO().equals("rect"))){ if(tabRuchow[5] != null){  }else{ this.kolejnyRuch = 5; return; } } 
				 if((el.getNrPola() == 5 && el.getXorO().equals("rect")) && (ele.getNrPola() == 8 && ele.getXorO().equals("rect"))){ if(tabRuchow[2] != null){  }else{ this.kolejnyRuch = 2; return; } }
				 if((el.getNrPola() == 8 && el.getXorO().equals("rect")) && (ele.getNrPola() == 5 && ele.getXorO().equals("rect"))){ if(tabRuchow[2] != null){  }else{ this.kolejnyRuch = 2; return; } } 
				 
				 if((el.getNrPola() == 2 && el.getXorO().equals("rect")) && (ele.getNrPola() == 4 && ele.getXorO().equals("kolo"))){ if(tabRuchow[6] != null){  }else{ this.kolejnyRuch = 6; return; } }
				 if((el.getNrPola() == 4 && el.getXorO().equals("rect")) && (ele.getNrPola() == 2 && ele.getXorO().equals("rect"))){ if(tabRuchow[6] != null){  }else{ this.kolejnyRuch = 6; return; } } 
				 if((el.getNrPola() == 2 && el.getXorO().equals("rect")) && (ele.getNrPola() == 6 && ele.getXorO().equals("kolo"))){ if(tabRuchow[4] != null){  }else{ this.kolejnyRuch = 4; return; } }
				 if((el.getNrPola() == 6 && el.getXorO().equals("rect")) && (ele.getNrPola() == 2 && ele.getXorO().equals("rect"))){ if(tabRuchow[4] != null){  }else{ this.kolejnyRuch = 4; return; } } 
				 if((el.getNrPola() == 4 && el.getXorO().equals("rect")) && (ele.getNrPola() == 6 && ele.getXorO().equals("kolo"))){ if(tabRuchow[2] != null){  }else{ this.kolejnyRuch = 2; return; } }
				 if((el.getNrPola() == 6 && el.getXorO().equals("rect")) && (ele.getNrPola() == 4 && ele.getXorO().equals("rect"))){ if(tabRuchow[2] != null){  }else{ this.kolejnyRuch = 2; return; } } 
				 
				 if((el.getNrPola() == 0 && el.getXorO().equals("rect")) && (ele.getNrPola() == 4 && ele.getXorO().equals("kolo"))){ if(tabRuchow[8] != null){  }else{ this.kolejnyRuch = 8; return; } }
				 if((el.getNrPola() == 4 && el.getXorO().equals("rect")) && (ele.getNrPola() == 0 && ele.getXorO().equals("rect"))){ if(tabRuchow[8] != null){  }else{ this.kolejnyRuch = 8; return; } } 
				 if((el.getNrPola() == 0 && el.getXorO().equals("rect")) && (ele.getNrPola() == 8 && ele.getXorO().equals("kolo"))){ if(tabRuchow[4] != null){  }else{ this.kolejnyRuch = 4; return; } }
				 if((el.getNrPola() == 8 && el.getXorO().equals("rect")) && (ele.getNrPola() == 0 && ele.getXorO().equals("rect"))){ if(tabRuchow[4] != null){  }else{ this.kolejnyRuch = 4; return; } } 
				 if((el.getNrPola() == 4 && el.getXorO().equals("rect")) && (ele.getNrPola() == 8 && ele.getXorO().equals("kolo"))){ if(tabRuchow[0] != null){  }else{ this.kolejnyRuch = 0; return; } }
				 if((el.getNrPola() == 8 && el.getXorO().equals("rect")) && (ele.getNrPola() == 4 && ele.getXorO().equals("rect"))){ if(tabRuchow[0] != null){  }else{ this.kolejnyRuch = 0; return; } } 
				 
				 
				 //Wybór liczby losowej dla wolnego pola
				 int liczba = (int)(Math.random() * 8);
				 if(this.getTabRuchowIndex(liczba) == null){
					 //System.out.println("kolejny ruch = "+liczba); 
					 this.kolejnyRuch = Integer.parseInt(TicTacToe.tabPolaGry[liczba].getName());
				 }
				 
			 } 
			}
		  }	
		}	
	}
	
	public int getKolejnyRuch(){
		return kolejnyRuch;
	}
	public TablicaRuchow getTabRuchowIndex(int index){
		return tabRuchow[index];
	}
}

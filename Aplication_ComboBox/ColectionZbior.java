package Aplication_ComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.*;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import javax.swing.JScrollPane;

final public class ColectionZbior extends JDialog{
	private static final long serialVersionUID = 1L;
	
	JMenuBar pasek = new JMenuBar();
	JMenu awans = new JMenu("Advance Menu");
	JMenuItem awansI = new JMenuItem("Menu Zaawansowane");
	JMenu exit = new JMenu("Exit");
	JMenuItem exitI = new JMenuItem("Wyjscie z podprogramu");
	
	JLabel tytul = new JLabel("<html><h1><i>Obs³uga Danych za Pomoc¹ Zbioru</i></h1><hr></html>");
	JPanel panel = new JPanel();
	JLabel name = new JLabel("podaj imie: ");
	JTextField nameT = new JTextField(7);
	JButton _add = new JButton("dodaj");
	JButton showAll = new JButton("poka¿ dane");
	JCheckBox createZbior = new JCheckBox("tworzy niepowtarzaj¹cy siê zbiór elementów");
	JTextArea ekran = new JTextArea(10,10);
	JScrollPane scroll = new JScrollPane(ekran);
	
	@SuppressWarnings("unused")
	public ColectionZbior(final PanelWithMenu pwm){
		super(pwm,"Dane Obs³ugiwane przez Zbiór",false);
		this.setDefaultCloseOperation(JFrame.NORMAL);
		pwm.setExtendedState(JFrame.ICONIFIED);
		this.setJMenuBar(pasek);
		pasek.add(awans); pasek.add(exit);
		awans.add(awansI);
		awansI.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				
			}
		});
		exit.add(exitI);
		exitI.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				pwm.setExtendedState(JFrame.NORMAL);
				setVisible(false);
			}
		});
		
		GridBagLayout grid = new GridBagLayout();
		this.setLayout(grid);
		panel.setLayout(grid);
		
		tytul.setForeground(Color.blue);
		this.add(tytul, new GBCg(0,0,3,1).setInsets(1, 5, 1, 5));
		this.add(name,new GBCg(0,1,1,1).setInsets(3));
		this.add(nameT,new GBCg(1,1,1,1).setFill(GBCg.HORIZONTAL).setWeight(1, 0).setInsets(1));
		this.add(_add,new GBCg(2,1,1,1).setFill(GBCg.HORIZONTAL).setInsets(3));
		this.add(panel,new GBCg(0,2,3,1).setInsets(2).setFill(GBCg.HORIZONTAL).setWeight(1, 0));
		panel.add(showAll,new GBCg(0,0,1,1).setInsets(1).setAnchor(GBCg.WEST).setWeight(1, 0).setFill(GBCg.HORIZONTAL));
		panel.add(createZbior,new GBCg(1,0,1,1));
		ekran.setBorder(BorderFactory.createLineBorder(Color.blue));
		this.add(scroll,new GBCg(0,3,3,3).setWeight(1, 1).setFill(GBCg.BOTH).setInsets(1));
		
		_add.addActionListener(new ActionListener(){
			
			private List<String> lista = new ArrayList<String>();
			
			@Override
			public void actionPerformed(ActionEvent e){
				//dodaje imie do listy i zapisuje w pliku
				addName(nameT.getText());
			}
			
			public void addName(String name){
				
				try{
					ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File("ZbiorDanych.txt"))));
					lista = (List<String>)in.readObject();
					in.close();
					
					ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File("ZbiorDanych.txt"))));
				    lista.add(name);
				    out.writeObject(lista);
				    out.flush();
				    out.close();
				}catch(FileNotFoundException v){
					JOptionPane.showMessageDialog(null, v);
				}catch(IOException e){ 
					JOptionPane.showMessageDialog(null, e); 
				}catch(ClassNotFoundException c){ JOptionPane.showMessageDialog(null, c); }
			}
		});
		
		showAll.addActionListener(new ActionListener(){
			private List<String> lista = new ArrayList<String>();
			private Set<String> zbior = new HashSet<String>();
			
			@Override
			public void actionPerformed(ActionEvent event){
				try{
				  ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File("ZbiorDanych.txt"))));
				  lista = (List<String>)in.readObject();
				  in.close();
				  
				  if(createZbior.isSelected()){
					  Iterator i = lista.iterator();
					  while(i.hasNext()){
						  zbior.add((String)i.next());
					  }
					  ekran.setText("");
					  for(String el : zbior){
						  ekran.append(el + "\n");
					  }
					  
				  }else{
					  ekran.setText("");
					  for(String el : lista){
						  ekran.append(el + "\n");
					  }
				  }
				  
				}catch(FileNotFoundException a){
					JOptionPane.showMessageDialog(null, a);
				}catch(IOException b){ 
					JOptionPane.showMessageDialog(null, b); 
				}catch(ClassNotFoundException c){
					JOptionPane.showMessageDialog(null, c);
				}
			}
		});
		
		this.addComponentListener(new ComponentAdapter(){
			public void ComponentHidden(ComponentEvent e){
				pwm.setExtendedState(JFrame.NORMAL);
			}
		});
		pack();
		this.setVisible(true);
	}
}

class GBCg extends GridBagConstraints{
	private static final long serialVersionUID = 1L;
	public GBCg(int gridx,int gridy){ this.gridx = gridx; this.gridy = gridy; }
	public GBCg(int gridx,int gridy,int gridwidth,int gridheight){
		this.gridx = gridx; this.gridy = gridy; 
		this.gridwidth = gridwidth; this.gridheight = gridheight;
	}
	public GBCg setWeight(int weightx, int weighty){
		this.weightx = weightx; this.weighty = weighty;
		return this;
	}
	public GBCg setFill(int fill){
		this.fill = fill; return this;
	}
	public GBCg setAnchor(int anchor){
		this.anchor = anchor; return this;
	}
	public GBCg setInsets(int x){
		this.insets = new Insets(x,x,x,x); return this;
	}
	public GBCg setInsets(int top,int left,int bottom,int right){
		this.insets = new Insets(top,left,bottom,right); return this;
	}
}

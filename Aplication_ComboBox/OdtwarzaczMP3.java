package Aplication_ComboBox;
import java.awt.EventQueue;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.applet.*;
import java.io.*;
import java.net.*;

public class OdtwarzaczMP3 extends JApplet{
	private static final long serialVersionUID = 1L;
	AudioClip audio = null;
	JLabel tytul = new JLabel("<html><h1><i> Odtwarzacz MP3 </i></h1></hr></html>");
	JPanel pan1 = new JPanel();
	JPanel pan2 = new JPanel();
	
	JButton search = new JButton("Search");
	JButton play = new JButton("Play");
	JButton stop = new JButton("Stop");
	
	@Override
	public void init(){
		EventQueue.invokeLater(new Runnable(){
			@Override
			public void run(){
				setLayout(new GridLayout(2,1));
				add(pan1); add(pan2);
				pan1.setLayout(new FlowLayout()); pan2.setLayout(new BorderLayout());
				pan1.add(tytul);
				setName("kokon");
				tytul.setToolTipText("Odtwarzacz bez certyfikatu nie zadzia³a za ma³e uprawnienia");
				
				int sizeFont = Integer.parseInt(getParameter("size"));
				String fontStyle = getParameter("font");
				String color = getParameter("color");
				
				Font f = new Font(fontStyle,Font.BOLD,sizeFont);
				tytul.setFont(f);
				
				if(color.equals("red")){
				  tytul.setForeground(Color.red);
				}else if(color.equals("green")){
				  tytul.setForeground(Color.green);
				}else if(color.equals("blue")){
				  tytul.setForeground(Color.blue);
				}else if(color.equals("yellow")){
				  tytul.setForeground(Color.yellow);
				}else{ tytul.setForeground(Color.black); } 
				
				pan2.add(play,BorderLayout.WEST); pan2.add(stop,BorderLayout.CENTER); pan2.add(search,BorderLayout.EAST); 
				pan2.setBorder(BorderFactory.createLineBorder(Color.green));
				
				play.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						if(audio != null){
							audio.play();
						}else{ JOptionPane.showMessageDialog(null, "Najpierw wyszukaj piosenkê do odtwarzania z rozszezeniem wav !"); }
					}
				});
				
				stop.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						if(audio != null){
							audio.stop();
						}else{ JOptionPane.showMessageDialog(null, "Najpierw wyszukaj piosenkê do odtwarzania z rozszezeniem wav !"); }
					}
				});
				
				search.addActionListener(new ActionListener(){
					JFileChooser chooser = new JFileChooser();
					public void actionPerformed(ActionEvent e){
						chooser.setCurrentDirectory(new File("."));
						int wynik = chooser.showOpenDialog(null);
						
						if(wynik == JFileChooser.APPROVE_OPTION){
							String filePath = "file:"+chooser.getSelectedFile().getPath();
							try{
							  audio = Applet.newAudioClip(new URL(filePath));
							}catch(MalformedURLException exc){ JOptionPane.showMessageDialog(null, "Malformed"); }
						}
					}
				});
			}
		});
	}
}

package Aplication_ComboBox;
import javax.sound.sampled.*;
import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.applet.*;

public class MusicSymfony extends JDialog{
	private static final long serialVersionUID = 1L;
	Process process = null;
	ButtonGroup group = new ButtonGroup();
	JRadioButton train = new JRadioButton("train");
	JRadioButton whistle = new JRadioButton("whistle");
	JButton play = new JButton("Play");
	JButton appletMusic = new JButton("Odtwarzacz Muzyki WWW");
	
	public MusicSymfony(final PanelWithMenu pwm){
		super(pwm,"Obs³uga Muzyki",false);
		pwm.setExtendedState(JFrame.ICONIFIED);
		this.setLayout(new FlowLayout());
		group.add(train); group.add(whistle);
		this.add(train); this.add(whistle); this.add(play); this.add(appletMusic);
		
		final Runtime runMusic = Runtime.getRuntime();
		
		play.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				
				File trainMusic = new File("Music/train.wav");
				File whistleMusic = new File("Music/whistle.wav");
				
			    try{
					if(train.isSelected()){
						Clip clip = AudioSystem.getClip();
						clip.open(AudioSystem.getAudioInputStream(trainMusic));
						clip.start();
						//process = runMusic.exec("rundll32 url.dll, FileProtocolHandler "+"Music/train.wav");
					}else{
						Clip clip = AudioSystem.getClip();
						clip.open(AudioSystem.getAudioInputStream(whistleMusic));
						clip.start();
					}
				  
				}catch(LineUnavailableException excLine) {
					JOptionPane.showMessageDialog(null, "Music Line Unavailable ");
				}catch(UnsupportedAudioFileException audioExc) {
					JOptionPane.showMessageDialog(null, "Audio File Exception");
				}catch(IOException v){ JOptionPane.showMessageDialog(null, v); }
			}
		});
		
		appletMusic.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				try{
				  Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler "+"OdtwarzaczMp3.html");
				}catch(IOException exc){ JOptionPane.showMessageDialog(null, exc); } 
			}
		});
		
		this.addComponentListener(new ComponentAdapter(){
			@Override
			public void componentHidden(ComponentEvent e){
				pwm.setExtendedState(JFrame.NORMAL);
			}
		});
		
		this.setSize(300, 300);
		this.setVisible(true);
	}
}

package Aplication_ComboBox;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Image;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import java.awt.Panel;
import java.awt.Label;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import java.awt.TextField;
import java.awt.Button;
import javax.swing.ImageIcon;
import javax.swing.Icon;
import java.awt.event.*;

final public class Open_File_Menner2 extends Frame implements ActionListener,WindowListener{
	private static final long serialVersionUID = 1L;
	FileDialog fd = null;
	Panel pan1 = new Panel();
	Panel pan2 = new Panel();
	Panel pan3 = new Panel();
	JLabel filtr = new JLabel("Wprowadz Filtr np: (*.jpeg;*.jpg)");
	TextField filtrText = new TextField(12);
	JLabel show = new JLabel("podgl¹d \n obrazu");
	Button button = new Button("Wybierz plik");
	JLabel labelek = null;
	PanelWithMenu pwm = null;
	
	public Open_File_Menner2(final PanelWithMenu pwm,JLabel labelek){
		pwm.setExtendedState(JFrame.ICONIFIED);
		this.labelek = labelek;
		this.pwm = pwm;
		this.setSize(500, 400);
		this.addWindowListener(this);
		this.setVisible(true);
		
		fd = new FileDialog(this);
		
		this.add(pan1,BorderLayout.NORTH); pan1.add(filtr); pan1.add(filtrText);
		
		this.add(pan2,BorderLayout.CENTER); pan2.add(show);
		show.setBorder(BorderFactory.createLineBorder(Color.black));
		show.setMaximumSize(this.getMaximumSize());
		
		this.add(pan3,BorderLayout.SOUTH); pan3.add(button);
		button.setBounds(10,0,200,75);
		button.addActionListener(this);
		
		this.addComponentListener(new ComponentAdapter(){
			@Override
			public void componentHidden(ComponentEvent e){
				pwm.setExtendedState(JFrame.NORMAL);
			}
		});
	}
	@Override
	public void actionPerformed(ActionEvent e){
		fd.setFile(filtrText.getText());
		pwm.setExtendedState(JFrame.ICONIFIED);
		fd.setVisible(true);
		ImageIcon ikona = new ImageIcon(fd.getDirectory()+fd.getFile());
		if(ikona.getIconWidth() > show.getWidth()){
			ikona = new ImageIcon(ikona.getImage().getScaledInstance(getWidth(), -1, Image.SCALE_DEFAULT));
		}
		show.setIcon(ikona);
		pack();
		labelek.setIcon(new ImageIcon(fd.getDirectory()+fd.getFile()));
		
	}
	@Override
	public void windowOpened(WindowEvent e){ }
	@Override
	public void windowClosing(WindowEvent e){ this.setVisible(false); pwm.setExtendedState(JFrame.NORMAL); }
	@Override
	public void windowIconified(WindowEvent e){ }
	@Override
	public void windowDeiconified(WindowEvent e){}
	@Override
	public void windowDeactivated(WindowEvent e){}
	@Override
	public void windowClosed(WindowEvent e){}
	@Override
	public void windowActivated(WindowEvent e){}
}

final class SelectFile extends FileDialog{
	public SelectFile(JFrame f){
		super(f,"Okno Wyboru Plików",FileDialog.LOAD);
		
	}
}


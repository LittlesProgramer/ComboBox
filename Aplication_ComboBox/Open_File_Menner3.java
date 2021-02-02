package Aplication_ComboBox;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import javax.swing.JDialog;
import java.nio.file.Files;
import java.nio.file.DirectoryStream;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.io.File;
import javax.swing.JTextArea;
import java.nio.file.Path;
import java.io.IOException;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

final public class Open_File_Menner3 extends JDialog implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	JPanel panGora = new JPanel();
	JLabel path = new JLabel("Scie¿ka: ");
	JTextField text = new JTextField(30);
	JButton downPath = new JButton("Poziom ni¿ej");
	
	JPanel panDol = new JPanel();
	JTextArea listFile = new JTextArea(20,20);
	JButton butListFile = new JButton("Lista Plików");
	
	File f = new File("");
	
	public Open_File_Menner3(final PanelWithMenu pwm){
		pwm.setExtendedState(JFrame.ICONIFIED);
		this.setPreferredSize(new Dimension(400,200));
		this.setSize(500,250);
		this.setVisible(true);
		
		this.add(panGora,BorderLayout.NORTH);
		panGora.setLayout(new BorderLayout());
		panGora.add(text,BorderLayout.EAST);
		text.setText(f.getAbsolutePath());
		panGora.add(path);
		panGora.add(downPath,BorderLayout.WEST);
		downPath.addActionListener(this);
		
		listFile.setBorder(BorderFactory.createLineBorder(Color.red));
		JScrollPane rolka = new JScrollPane(listFile);
		rolka.setBorder(BorderFactory.createLineBorder(Color.red));
		this.add(rolka);
		
		this.add(panDol,BorderLayout.SOUTH);
		panDol.add(butListFile);
		butListFile.addActionListener(this);
		
		//Przywracania okna g³ównego po zamkniêciu okna dialogowego 
		this.addComponentListener(new ComponentAdapter(){
			@Override
			public void componentHidden(ComponentEvent e){
				pwm.setExtendedState(JFrame.NORMAL);
			}
		});
	}
	public void actionPerformed(ActionEvent e){
		String name = e.getActionCommand();
		if(name.equals("Lista Plików")){
			System.out.println("Lista Plików = "+f.getAbsolutePath());
			try{
			  DirectoryStream<Path> dir = Files.newDirectoryStream(f.toPath());
			  for(Path d : dir){
				  listFile.append(d+"\n");
			  }
			}catch(IOException v){ JOptionPane.showMessageDialog(null, "Bl¹d wejœcia/wyjœcia"); }
		}else if(name.equals("Poziom ni¿ej")){
			File s = f.getAbsoluteFile().toPath().resolve("..").normalize().toFile();
			text.setText(s.toString());
			f = s;
		}
	}
}

package Aplication_ComboBox;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.attribute.BasicFileAttributes;
import java.io.File;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

final public class Open_File_Menner4 extends JDialog implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	File f = new File("");
	JPanel panGora = new JPanel();
	JLabel labGora = new JLabel("Scie¿ka: ");
	JTextField textGora = new JTextField(30);
	JButton butGora = new JButton("Poziom ni¿ej");
	
	JPanel panCenter = new JPanel();
	JLabel labCenter = new JLabel("Filtr np: *|jpg|txt|java|..itd");
	JTextField textFiltr =new JTextField(34);
	
	JPanel panDol = new JPanel();
	JTextArea listFile = new JTextArea(20,20);
	JScrollPane rolka = new JScrollPane(listFile);
	JButton butListFile = new JButton("Lista Plików");
	
	public Open_File_Menner4(final PanelWithMenu pwm){
		pwm.setExtendedState(JFrame.ICONIFIED);
		this.setSize(new Dimension(600,400));
		this.setVisible(true);
		
		this.add(panGora,BorderLayout.NORTH);
		textGora.setText(f.getAbsolutePath());
		panGora.add(labGora,BorderLayout.EAST); panGora.add(textGora,BorderLayout.CENTER); panGora.add(butGora,BorderLayout.WEST);
		butGora.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				File temp = f.getAbsoluteFile().toPath().resolve("..").normalize().toFile();
				textGora.setText(temp.toString());
				f = temp;
			}
		});
		
		this.add(panCenter,BorderLayout.CENTER);
		panCenter.add(labCenter); panCenter.add(textFiltr); 
		
		this.add(panDol,BorderLayout.SOUTH);
		panDol.setLayout(new BorderLayout());
		rolka.setBorder(BorderFactory.createLineBorder(Color.green));
		panDol.add(rolka,BorderLayout.CENTER); panDol.add(butListFile,BorderLayout.WEST);
		butListFile.addActionListener(this);
		this.pack();
		
		//Przywracania okna g³ównego po zamkniêciu okna dialogowego 
		this.addComponentListener(new ComponentAdapter(){
			@Override
			public void componentHidden(ComponentEvent e){
				pwm.setExtendedState(JFrame.NORMAL);
			}
		});
	}
	@Override
	public void actionPerformed(ActionEvent e){
			try{
				  Files.walkFileTree(f.toPath(), new SimpleFileVisitor<Path>(){
					@Override
					public FileVisitResult visitFile(Path file,BasicFileAttributes attrs) throws IOException {
						Pattern pat = Pattern.compile(".+\\."+textFiltr.getText());
						Matcher match = pat.matcher(file.toString());
						System.out.println("f = "+file);
						if(match.matches()){
						  listFile.append(file.toString()+"\n");
						  // TODO Auto-generated method stub
						}
						return super.visitFile(file, attrs);
					}
				  });
				}catch(IOException v){ System.out.println("e = "+v); }
	}
}

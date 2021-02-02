package Aplication_ComboBox;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.NoSuchFileException;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentAdapter;

final public class FilesAndPaths extends JDialog{
	private static final long serialVersionUID = 1L;
	
	JLabel createPathFolder = new JLabel("Nazwa tworzonego folderu to: ");
	JLabel createFolderNameIs = new JLabel("Nazwa tworzonego pliku to: ");
	JLabel pathCreateFolderIs = new JLabel("Scie¿ka tworzonego pliku to: ");
	JLabel copyFileWith = new JLabel("Kopiuj plik z ");
	JLabel moveFileWith = new JLabel("Przenieœ plik z ");
	JLabel intoCopy = new JLabel("do");
	JLabel intoMove = new JLabel("do");
	
	JTextField textPath = new JTextField(5);
	JTextField textFolderName = new JTextField(5);
	JTextField textFolderPath = new JTextField(5);
	JTextField textFileName = new JTextField(5);
	JTextField textPathName = new JTextField("Scie¿ka tworzonego pliku to:");
	JTextField textCopyFrom = new JTextField(30);
	JTextField textCopyInto = new JTextField(30);
	JTextField textMoveFrom = new JTextField(30);
	JTextField textMoveInto = new JTextField(30);
	JTextArea area = new JTextArea(10,20);
	
	JButton one = new JButton("Twórz folder");
	JButton two = new JButton("Twórz plik");
	JButton three = new JButton("Kopiuj");
	JButton four = new JButton("Przenieœ");
	JButton five = new JButton("Wyœwietl");
	JButton six = new JButton("Œcie¿ka dla folderu");
	JButton seven = new JButton("Œcie¿ka dla pliku");
	
	public FilesAndPaths(final PanelWithMenu pwm){
		super(pwm,"Panel zapisu nowymi metodami files and paths",false);
		pwm.setExtendedState(JFrame.ICONIFIED);
		this.setLayout(new GridBagLayout());
		
		this.add(createPathFolder,new GBCC(0,0,1,1).setInsets(1).setWeight(0, 1));
		this.add(textFolderName,new GBCC(1,0,1,1).setInsets(1).setWeight(1, 0).setFill(GBCC.HORIZONTAL));
		this.add(one,new GBCC(2,0,1,1).setInsets(1));
		this.add(textFolderPath,new GBCC(3,0,1,1).setInsets(1).setWeight(1, 0).setFill(GBCC.HORIZONTAL));
		this.add(six,new GBCC(4,0,1,1).setInsets(1));
		
		this.add(createFolderNameIs,new GBCC(0,1,1,1).setInsets(1).setAnchor(GBCC.WEST).setWeight(0, 1));
		this.add(textFileName,new GBCC(1,1,1,1).setInsets(1).setFill(GBCC.HORIZONTAL).setWeight(1, 0));
		this.add(textPathName,new GBCC(2,1,1,1).setInsets(1).setFill(GBCC.HORIZONTAL).setWeight(1, 0));
		this.add(seven,new GBCC(3,1,1,1).setInsets(1).setWeight(1, 0).setFill(GBCC.HORIZONTAL));//
		this.add(two,new GBCC(4,1,1,1).setInsets(1).setFill(GBCC.HORIZONTAL));
		
		this.add(copyFileWith,new GBCC(0,2,1,1).setInsets(1).setWeight(0, 1));
		this.add(textCopyFrom,new GBCC(1,2,1,1).setInsets(1).setFill(GBCC.HORIZONTAL).setWeight(1, 0));
		this.add(intoCopy,new GBCC(2,2,1,1).setInsets(1));
		this.add(textCopyInto,new GBCC(3,2,1,1).setInsets(1).setFill(GBCC.HORIZONTAL).setWeight(1, 0));
		this.add(three,new GBCC(4,2,1,1).setInsets(1).setFill(GBCC.HORIZONTAL));
		
		this.add(moveFileWith,new GBCC(0,3,1,1).setInsets(1).setWeight(0, 1));
		this.add(textMoveFrom,new GBCC(1,3,1,1).setInsets(1).setFill(GBCC.HORIZONTAL).setWeight(1, 0));
		this.add(intoMove,new GBCC(2,3,1,1).setInsets(1));
		this.add(textMoveInto,new GBCC(3,3,1,1).setInsets(1).setFill(GBCC.HORIZONTAL).setWeight(1, 0));
		this.add(four,new GBCC(4,3,1,1).setInsets(1).setFill(GBCC.HORIZONTAL));
		
		this.setVisible(true);
		this.pack();
		
		//Przywracania okna g³ównego po zamkniêciu okna dialogowego 
		this.addComponentListener(new ComponentAdapter(){
			@Override
			public void componentHidden(ComponentEvent e){
				pwm.setExtendedState(JFrame.NORMAL);
			}
		});
		
		one.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				try{
				  @SuppressWarnings("unused")
				  Path path = Files.createDirectory(Paths.get(".").resolve(textFolderName.getText()).normalize().toAbsolutePath());  
				}catch(IOException v){ JOptionPane.showMessageDialog(null, v); }
			}
		});
		
		six.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				Path path = Paths.get(".").toAbsolutePath();
				Path resolve = path.resolve(textFolderName.getText()).normalize();
				textFolderPath.setText(resolve.toString());
			}
		});
		
		seven.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				Path path = Paths.get(".");
				Path resolve = path.resolve(textFolderName.getText());
				textPathName.setText(resolve.toAbsolutePath().normalize().toString());
			}
		});
		
		two.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				try{
				  Files.createFile(Paths.get(textPathName.getText()).resolve(textFileName.getText()).toAbsolutePath().normalize());
				}catch(IOException v){ JOptionPane.showMessageDialog(null, v); }
			}
		});
		
		three.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				Path path = Paths.get("./Zród³o danych").resolve(textFileName.getText()).toAbsolutePath().normalize();
				textCopyFrom.setText(path.toString());
				textCopyInto.setText(Paths.get("./Skopiowano do/").resolve(textFileName.getText()).normalize().toAbsolutePath().toString());
				
				try{
				  Files.copy(Paths.get(textCopyFrom.getText()), Paths.get(textCopyInto.getText()));
				}catch(NoSuchFileException b){
					JOptionPane.showMessageDialog(null, "Brak pliku który mam skopiowaæ !!");
				}catch(FileAlreadyExistsException a){
					JOptionPane.showMessageDialog(null, "taki plik znajduje siê ju¿ w miejscu docelowym");
				}catch(IOException v){ 
					JOptionPane.showMessageDialog(null, "Nie mo¿na skopiowaæ pliku b³¹d wejœcia/wyjœcia"); 
				}
			}
		});
		
		four.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				Path path = Paths.get("./Zród³o danych").resolve(textFileName.getText()).toAbsolutePath().normalize();
				textMoveFrom.setText(path.toString());
				textMoveInto.setText(Paths.get("./Przeniesiono do/").resolve(textFileName.getText())
				.normalize().toAbsolutePath().toString());
				
				try{
					Files.move(Paths.get(textMoveFrom.getText()), Paths.get(textMoveInto.getText()));
				}catch(NoSuchFileException a){
					JOptionPane.showMessageDialog(null, "Nie mogê znale¿æ pliku do przeniesienia");
				}catch(IOException b){
					JOptionPane.showMessageDialog(null, "B³¹d Wejœcia/Wyjscia podczas przenoszenia pliku");
				}
			}
		});
	}
}

final class GBCC extends GridBagConstraints{
	public static final long serialVersionUID = 1L;
	public GBCC(int gridx,int gridy){
		this.gridx = gridx; this.gridy = gridy;
	}
	public GBCC(int gridx,int gridy,int gridwidth,int gridheight){
		this.gridx = gridx; this.gridy = gridy; this.gridwidth = gridwidth; this.gridheight = gridheight;
	}
	public GBCC setFill(int fill){
		this.fill = fill;
		return this;
	}
	public GBCC setWeight(int weightx,int weighty){
		this.weightx = weightx; this.weighty = weighty;
		return this;
	}
	public GBCC setAnchor(int anchor){
		this.anchor = anchor;
		return this;
	}
	public GBCC setInsets(int x){
		this.insets = new Insets(x,x,x,x);
		return this;
	}
	public GBCC setInsets(int top,int left,int bottom,int right){
		this.insets = new Insets(top,left,bottom,right);
		return this;
	}
}

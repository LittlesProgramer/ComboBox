package Aplication_ComboBox;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileView;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileFilter;
import java.awt.Color;
import java.awt.Image;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

final public class Open_File_Menner1 {
	
	public Open_File_Menner1(PanelWithMenu pwm,JLabel label){
		pwm.setExtendedState(JFrame.ICONIFIED);
		ChooserFile chooser = new ChooserFile();
		chooser.setCurrentDirectory(new File("."));
		chooser.setSelectedFile(new File("domuœlny.txt"));
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		FileNameExtensionFilter filtr1 = new FileNameExtensionFilter("Obrazy","jpeg","jpg","gif");
		FileNameExtensionFilter filtr2 = new FileNameExtensionFilter("Texteki","txt");
		chooser.setFileFilter(filtr1);
		chooser.addChoosableFileFilter(filtr2);
		chooser.setFileView(new Widok(new ImageIcon("./ikony/palette.gif"),filtr1));
		Akcesorium akc = new Akcesorium(chooser,pwm,label); akc.setBorder(BorderFactory.createLineBorder(Color.green));
		chooser.setAccessory(akc);
		int result = chooser.showOpenDialog(null);
		
		if(result == JFileChooser.APPROVE_OPTION || result == JFileChooser.CANCEL_OPTION){ 
			if(result == JFileChooser.APPROVE_OPTION){
			  String path = chooser.getSelectedFile().getPath();
			  pwm.setExtendedState(JFrame.NORMAL);
			  label.setIcon(new ImageIcon(path));
			}else if(result == JFileChooser.CANCEL_OPTION){
				 pwm.setExtendedState(JFrame.NORMAL);
			}
		}
		
		
	}
}

/*class MojFiltr extends FileFilter{
	
}*/

final class ChooserFile extends JFileChooser{
	private static final long serialVersionUID = 1L;
	public ChooserFile(){	 }
}

final class Widok extends FileView{
	private ImageIcon ikona;
	private FileFilter filtr;
	public Widok(ImageIcon ikona,FileFilter filtr){
		this.ikona = ikona; this.filtr = filtr;
	}
	public Icon getIcon(File f){
		if(!f.isDirectory() && filtr.accept(f))
			return ikona;
		else return null;
	}
}

final class Akcesorium extends JLabel{
	private static final long serialVersionUID = 1L;
	public Akcesorium(final JFileChooser chooser,final PanelWithMenu pwm,final JLabel label){
		this.setPreferredSize(new Dimension(100,100));
		
		chooser.addPropertyChangeListener(new PropertyChangeListener(){
			@Override
			public void propertyChange(PropertyChangeEvent e){
				
				if(e.getPropertyName() == JFileChooser.SELECTED_FILE_CHANGED_PROPERTY){
				   File f = (File)e.getNewValue();
				   if(f == null){
					   setIcon(null); return;
				   }
				   ImageIcon ikona = new ImageIcon(f.getPath());
				   if(ikona.getIconWidth() > getWidth()){
					   ikona = new ImageIcon(ikona.getImage().getScaledInstance(getWidth(),-1,Image.SCALE_DEFAULT));			
				   }
				   setIcon(ikona);
				}	
			}
		});
	}
}

package Aplication_ComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.io.IOException;
import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionListener;//interfejs
import java.awt.event.ActionEvent;//Klasa
import javax.swing.JButton;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;

public class AplicationInfo extends JDialog{
	private static final long serialVersionUID = 1L;
	private PanelWithMenu pwm;
	private JButton info = new JButton("Info o Aplikacji na Stronie WWW");
	public AplicationInfo(){}
	public AplicationInfo(final PanelWithMenu pwm){
		super(pwm,"Informacje o aplikacji",false);
		this.pwm = pwm;
		pwm.setExtendedState(JFrame.ICONIFIED);
		JLabel lab = new JLabel("<html><h1><i> Informacje o aplikacji dostêpne na stronie WWW </i></h1><hr> zapraszamy </html>");
		this.add(lab);
		this.pack();
		this.setVisible(true);
		
		this.addComponentListener(new Akcja());
		
		info.setToolTipText("Przycisk prze³¹czajacy do Strony WWW zwi¹zanej z Informacjami o aplikacji");
		this.add(info,BorderLayout.SOUTH);
		info.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				try{
				  Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " + "C:\\java\\1.7.2\\JavaPodstawy\\!Piotrek\\Debug\\AplicationInfo.html");
				  
				}catch(IOException e){ JOptionPane.showMessageDialog(null, e); }
			}
		});
	}
	protected void writeAppletAutor(String newAutor) throws FileNotFoundException{
		File f = new File("C:\\java\\1.7.2\\JavaPodstawy\\!Piotrek\\Debug\\ApWWWInfo\\author.txt");
		PrintWriter writeAutor = new PrintWriter(f);
		writeAutor.write(newAutor);
		writeAutor.flush();
		writeAutor.close();
	}
	
	class Akcja extends ComponentAdapter{
		@Override
		public void componentHidden(ComponentEvent event){
			pwm.setExtendedState(JFrame.NORMAL);
		}
	}
}

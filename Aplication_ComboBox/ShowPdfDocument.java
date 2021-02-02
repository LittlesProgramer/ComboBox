package Aplication_ComboBox;
import java.awt.EventQueue;
import java.awt.*;
import javax.swing.*;
import java.net.*;

public class ShowPdfDocument extends JApplet{
	private static final long serialVersionUID = 1L;
	JLabel lab = new JLabel("Przegl¹darka PDF'ów");
	String urlText = null;
	
	@Override
	public void init(){
		EventQueue.invokeLater(new Runnable(){
			@Override
			public void run(){
				setLayout(new FlowLayout());
				add(lab);
				try{
				   getAppletContext().showDocument(new URL("file:DokumentyPdf/JC2_laboratorium_2.pdf"));
				}catch(MalformedURLException e){ JOptionPane.showMessageDialog(null, e); }
			}
		});
	}
}

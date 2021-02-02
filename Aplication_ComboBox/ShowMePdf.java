package Aplication_ComboBox;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class ShowMePdf extends JDialog{
	private static final long serialVersionUID = 1L;
	JLabel title = new JLabel("<html><h1><i> Przegl¹darka dokumentów Pdf </i></h1></hr></html>");
	JPanel panel1 = new JPanel();
	JPanel panel2 = new JPanel();
	JButton pdfApplet = new JButton("PDF w IExplorer");
	JButton pdfAcrobat = new JButton("PDF w Acrobat Reader");
	public ShowMePdf(PanelWithMenu pwm){
		super(pwm,"Przegl¹darka Dokumentów PDF",false);
		this.setLayout(new GridLayout(2,1));
		this.add(panel1); this.add(panel2);
		
		panel1.setLayout(new FlowLayout());
		panel1.add(title);
		panel1.setBorder(BorderFactory.createLineBorder(Color.green));
		
		panel2.setLayout(new FlowLayout());
		panel2.add(pdfApplet); panel2.add(pdfAcrobat);
		
		pdfApplet.setToolTipText("pokazuje dokument pdf w IExplorer");
		pdfApplet.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				try{
				  Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " + "ShowDocumentPDF.html");
				}catch(IOException exc){ JOptionPane.showMessageDialog(null, exc); }
			}
		});
		
		pdfAcrobat.setToolTipText("wyœwietla dokument pdf poprzez program skojarzony z rozrze¿eniem pdf");
		pdfAcrobat.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				File file = null;
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File("."));
				int wynik = chooser.showOpenDialog(null);
				
				if(wynik == JFileChooser.APPROVE_OPTION){
					file = chooser.getSelectedFile();
					System.out.println("1");
				}
				
				try{
				   if(file != null) Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " + file.getAbsolutePath());
				}catch(IOException exc){ JOptionPane.showMessageDialog(null, exc); }
			}
		});
		
		this.pack();
		this.setVisible(true);
	}
}

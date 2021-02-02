package Aplication_ComboBox;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JTextField;
import javax.swing.Timer;
import java.awt.event.*;

import javax.swing.JApplet;

public class ServiceApplet extends JApplet{
	private static final long serialVersionUID = 1L;
	GregorianCalendar aktualTime = null;
	GregorianCalendar changeTime = null;
	Date d = new Date();
	JTextField fi = new JTextField(15);
	
	@Override
	public void init(){
		EventQueue.invokeLater(new Runnable(){
			@Override
			public void run(){
				setLayout(new FlowLayout(FlowLayout.LEFT));
				add(fi);
				
				//fi.setText(Integer.toString(d.getMinutes()));
				fi.setText(d.toLocaleString());
				ActionListener listener = new ActionListener(){
					public void actionPerformed(ActionEvent e){
						metoda(fi.getText());
					}
				};
				
				Timer t = new Timer(1000,listener);
				t.start();
				d = new Date();
				aktualTime = new GregorianCalendar();
				changeTime = new GregorianCalendar();
				changeTime.set(Calendar.MINUTE,47);
			}
		});
	}
    public void metoda(String text){
		
		aktualTime.set(Calendar.MINUTE, Integer.parseInt(text));
		
		if(aktualTime.equals(changeTime)){
		     Toolkit.getDefaultToolkit().beep();
		     System.out.println("ok");
		}else{
			System.out.println("aktualTime = "+aktualTime.get(Calendar.MINUTE)+" changeTime = "+changeTime.get(Calendar.MINUTE));
		}
		fi.setText(Integer.toString(new Date().getMinutes()));
	}
}

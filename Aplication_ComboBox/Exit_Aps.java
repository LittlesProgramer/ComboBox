package Aplication_ComboBox;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

final public class Exit_Aps {
	public Exit_Aps(final PanelWithMenu pwm){
		ActionListener action = new TimePrinter(pwm);
		Timer t = new Timer(1500,action);
		t.start();
		
		pwm.getContentPane().add(new JLabel("Dlaczego siê nie wyœwietla")); // Dlaczego siê nie wyœwietla
	}
}
class TimePrinter implements ActionListener{
	private PanelWithMenu pwm;
	public TimePrinter(final PanelWithMenu pwm){ this.pwm = pwm; }
	@Override
	public void actionPerformed(ActionEvent e){
		pwm.getContentPane().add(new JLabel("Closing for 2 secunds")); // Dlaczego siê nie wyœwietla
		Toolkit.getDefaultToolkit().beep();
		System.exit(0);
	}
}

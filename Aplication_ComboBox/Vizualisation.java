package Aplication_ComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Vizualisation extends JDialog{
	private static final long serialVersionUID = 1L;
	private Vizualisation vi = this;
	public Vizualisation(final PanelWithMenu pwm){
		super(pwm,"Wizualizacja Aplikacji",false);
		pwm.setExtendedState(JFrame.ICONIFIED);
		this.setLayout(new FlowLayout());
		UIManager.LookAndFeelInfo tab[] = UIManager.getInstalledLookAndFeels();
		for(UIManager.LookAndFeelInfo el: tab){
			Vizualisation(el.getName(), el.getClassName(), pwm);
		}
		
		this.pack();
		this.setVisible(true);
		
		this.addComponentListener(new ComponentAdapter(){
			@Override
			public void componentHidden(ComponentEvent event){
				pwm.setExtendedState(JFrame.NORMAL);
			}
		});
	}
	public void Vizualisation(String name,final String nameClass,final PanelWithMenu pwm){
		JButton but = new JButton(name);
		this.add(but);
		
		but.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				try{
				  UIManager.setLookAndFeel(nameClass);
				}catch(Exception exc){JOptionPane.showMessageDialog(null, "Problemy ze zmian¹ stylu: "+exc); }
				SwingUtilities.updateComponentTreeUI(pwm);
				SwingUtilities.updateComponentTreeUI(vi);
			}
		});
	}
}

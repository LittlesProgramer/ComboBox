package Aplication_ComboBox;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Map;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

final public class Show_All_Count extends JDialog{
	private static final long serialVersionUID = 1L;
	GetLogPass glp = new GetLogPass();
	Map<String,Integer> mapa = null;
	JPanel panel = new JPanel();
	JLabel label = new JLabel("Wyœwietla wszystkie dostêpne loginy");
	JTextArea allLoginsT = new JTextArea(10,35);
	JButton allLoginsB = new JButton("Wszystkie Loginy");
	
	public Show_All_Count(final PanelWithMenu f){
		super(f,"Wszytskie Dostêpne Konta",false);
		mapa = glp.getMapa();
		this.add(panel); panel.setLayout(new BorderLayout());
		JScrollPane rolka = new JScrollPane(allLoginsT);
		rolka.setBorder(BorderFactory.createLineBorder(Color.green));
		panel.add(label,BorderLayout.NORTH); panel.add(rolka,BorderLayout.EAST); panel.add(allLoginsB,BorderLayout.WEST);
		allLoginsB.setForeground(Color.blue);
		f.setExtendedState(JFrame.ICONIFIED);
		this.setVisible(true);
		this.pack();
		
		allLoginsB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				showAllLogins();
			}
		});
		
		this.addComponentListener(new ComponentAdapter(){
			@Override
			public void componentHidden(ComponentEvent e){
				f.setExtendedState(JFrame.NORMAL);
			}
		});
	}
	
	public void showAllLogins(){
		for(Map.Entry<String, Integer> el : mapa.entrySet()){
			allLoginsT.append(el.getKey()+"\n");
		}
	}
}

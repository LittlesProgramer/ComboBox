package Aplication_ComboBox;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.FlowLayout;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Map;
import java.util.HashMap;
import javax.swing.JDialog;

final public class Panel_Logowania extends JFrame{ //Modu³ wprowadzania/przypominania hase³
	JPanel panel = new JPanel();
	JLabel login = new JLabel("Login: ");
	JTextField logText = new JTextField(10);
	JLabel password = new JLabel("Has³o: ");
	JTextField passText = new JTextField(10);
	
	JPanel panelDolny = new JPanel();
	JPanel panelDolnyLewy = new JPanel();
	JButton button = new JButton("ok");
	
	JPanel panelDolnyPrawy = new JPanel();
	JLabel create_cont = new JLabel("Zapomnia³ eœ/aœ has³o ?");
	JCheckBox remember_pass = new JCheckBox();
	
	
	public Panel_Logowania(){
		this.add(panel,BorderLayout.NORTH);
		panel.setOpaque(false);
		panel.add(login);
		panel.add(logText);
		panel.add(password);
		panel.add(passText);
		
		this.add(panelDolny,BorderLayout.SOUTH);
		panelDolny.setLayout(new BorderLayout());
		panelDolny.add(panelDolnyLewy,BorderLayout.WEST);
		panelDolnyLewy.add(button);
		panelDolny.add(panelDolnyPrawy,BorderLayout.EAST);
		panelDolnyPrawy.add(create_cont);
		panelDolnyPrawy.add(remember_pass);
		
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				Compare_Log_Pass clp = new Compare_Log_Pass(logText,passText,Panel_Logowania.this);
			}
		});
		
		remember_pass.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				
				if(remember_pass.isSelected()){
					Remember_Pass rp = new Remember_Pass(Panel_Logowania.this,remember_pass);
				}
			}
		});
	}
}

final class Remember_Pass extends JDialog{
	private static final long serialVersionUID = 1L;
	private Map<String,Integer> mapa = null;
	private String LabText = null;
	private JCheckBox rem_pass = null;
	
	GetLogPass glp = new GetLogPass();
	JPanel panel_gora = new JPanel();
	JPanel panel_dol = new JPanel();
	
	JLabel give_log = new JLabel("Podaj Login: ");
	JTextField login = new JTextField(10);
	
	JButton but = new JButton("Akceptuj");
	JLabel your_pass_is = new JLabel("twoje has³o to: ");
	
	JMenuBar pasek = new JMenuBar();
	JMenu menu = new JMenu("Menu");
	JMenuItem close = new JMenuItem("Zamknij");
	
	public Remember_Pass(JFrame f,final JCheckBox rem_pass){
		super(f,"Przypominacz has³a",false);
		this.rem_pass = rem_pass;
		LabText = your_pass_is.getText();
		Toolkit tool = Toolkit.getDefaultToolkit();
		Dimension d = tool.getScreenSize(); d.width = 200; d.height = 125;
		//this.setLayout(new BorderLayout());
		this.add(panel_gora,BorderLayout.NORTH);
		this.add(panel_dol,BorderLayout.SOUTH);
		panel_dol.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel_gora.add(give_log);
		panel_gora.add(login);
		panel_dol.add(but);
		panel_dol.add(your_pass_is);
		this.setSize(d);
		//this.setPreferredSize(d);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(true);
		
		this.setJMenuBar(pasek);
		pasek.add(menu);
		menu.add(close);
		
		this.pack();
		
		but.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				mapa = glp.getMapa();
				int pass = 0;
				
				if(mapa.containsKey(login.getText())){
					pass = mapa.get(login.getText());
					your_pass_is.setText(LabText+String.valueOf(pass));
					Remember_Pass.this.pack();
				}else{
					your_pass_is.setText(LabText+" podano blêdny login");
					Remember_Pass.this.pack();
				}
			}
		});
		
		close.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				rem_pass.setSelected(false);
				Remember_Pass.this.setVisible(false);
			}
		});
	}
}

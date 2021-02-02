package Aplication_ComboBox;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Map;
import java.util.HashMap;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Set;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

final public class Change_Password_Class extends JDialog{
	private Map<String,Integer> mapa = null;
	GetLogPass glp = new GetLogPass();
	private Change_Password_Class cpc = this;
	JPanel panelTitle = new JPanel();
	JLabel title = new JLabel("Panel Zmiany Has³a");
	
	JPanel panelGiveLogin = new JPanel();
	JLabel labelGiveLogin = new JLabel("Podaj Login: ");
	JTextField textLogin = new JTextField(5);
	JLabel labelGivePasswd = new JLabel("Podaj Has³o: ");
	JTextField textPasswd = new JTextField(5);
	JButton change_passwd = new JButton("Zmieñ has³o");
	
	JPanel panelStatus = new JPanel();
	JLabel labelStatusConst = new JLabel("Status przetwarzania: ");
	JLabel labelStatusChange = new JLabel();
	
	JMenuBar pasek = new JMenuBar();
	JMenu menu = new JMenu("Menu");
	JMenuItem close = new JMenuItem("Zamknij Panel");
	
	public Change_Password_Class(final PanelWithMenu pwm){
		//Pasek menu
		this.setJMenuBar(pasek);
		pasek.add(menu); 
		menu.add(close);
		close.setForeground(Color.blue);
		close.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				cpc.setVisible(false);
			}
		});
		
		//Panel tytu³u
		this.add(panelTitle,BorderLayout.NORTH);
		Font czcionkaTytulu = new Font("SensSerif",Font.BOLD,15);
		title.setFont(czcionkaTytulu);
		panelTitle.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelTitle.add(title);
		title.setForeground(Color.blue);
		
		//Panel wprowadzania danych
		this.add(panelGiveLogin,BorderLayout.CENTER);
		panelGiveLogin.add(labelGiveLogin); panelGiveLogin.add(textLogin); 
		panelGiveLogin.add(labelGivePasswd); panelGiveLogin.add(textPasswd);
		panelGiveLogin.add(change_passwd);
		
		//Panel Status
		this.add(panelStatus,BorderLayout.SOUTH);
		panelStatus.setLayout(new FlowLayout(FlowLayout.LEFT));
		Font czcionkaStatus = new Font("SensSerif",Font.BOLD+Font.ITALIC,12);
		panelStatus.add(labelStatusConst,BorderLayout.EAST);
		panelStatus.add(labelStatusChange,BorderLayout.WEST);
		
		this.getContentPane().setBackground(new Color(120,120,120));
		this.setDefaultCloseOperation(JFrame.NORMAL);
		pwm.setExtendedState(JFrame.ICONIFIED);
		this.setVisible(true);
		this.pack();
		
		//Obs³uga zmiany has³a
		change_passwd.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				changePassword();
			}
		});
		
		//Przywracania okna g³ównego po zamkniêciu okna dialogowego 
		this.addComponentListener(new ComponentAdapter(){
			@Override
			public void componentHidden(ComponentEvent e){
				pwm.setExtendedState(JFrame.NORMAL);
			}
		});
	}
	
	public void changePassword(){
		mapa = glp.getMapa();
		String login = textLogin.getText();
		boolean logic = false;
		
		for(Map.Entry<String, Integer> el: mapa.entrySet()){
			if(el.getKey().equals(login)){
				logic = true;
				el.setValue(Integer.parseInt(textPasswd.getText()));
			}else{
				String hasNotLogin = "Takiego loginu nie ma w bazie";
				labelStatusChange.setForeground(Color.red);
				labelStatusChange.setText(hasNotLogin);
			}
		}
		
		if(logic){
			String statusActualized = "Has³o dla "+login+" zosta³o zaktualizowane";
			mapa.put(login, Integer.parseInt(textPasswd.getText()));
			Save_Passwd_Login.saveDelMap(mapa);
			labelStatusChange.setForeground(Color.green);
			labelStatusChange.setText(statusActualized);
		}
		
	}
}

package Aplication_ComboBox;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Map;
import java.util.HashMap;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

final public class Create_Delete_Count extends JDialog{
	private static final long serialVersionUID = 1L;
	GetLogPass glp = new GetLogPass();
	private Map<String,Integer> mapa = null;
	JPanel panelGorny = new JPanel();
	JLabel log = new JLabel("Login: ");
	JTextField logT = new JTextField(7);
	JLabel pass = new JLabel("Password: ");
	JTextField passT = new JTextField(7);
	JButton create = new JButton("Create Count");
	
	JPanel panelCenter = new JPanel();
	JPanel centralLeft = new JPanel();
	JPanel centralRight = new JPanel();
	JLabel logDel = new JLabel("Login: ");
	JTextField logTD = new JTextField(7);
	JButton delete = new JButton("Delete Count");
	
	JPanel panelDolny = new JPanel();
	JLabel status = new JLabel("Status: ");
	
	public Create_Delete_Count(final JFrame f){
		super(f,"Panel tworzenia/usuwania konta",false);
		//Parametryzacja Panelu
		this.add(panelGorny,BorderLayout.NORTH);
		panelGorny.add(log); panelGorny.add(logT); panelGorny.add(pass); panelGorny.add(passT); panelGorny.add(create);
		panelGorny.setBackground(new Color(170,170,170));
		panelGorny.setBorder(BorderFactory.createLineBorder(Color.black));
		
		this.add(panelCenter,BorderLayout.CENTER);
		panelCenter.setLayout(new BorderLayout());
		panelCenter.add(centralLeft,BorderLayout.WEST); panelCenter.add(centralRight,BorderLayout.EAST);
		panelCenter.setBorder(BorderFactory.createLineBorder(Color.black));
		centralLeft.setLayout(new FlowLayout(FlowLayout.LEFT)); centralRight.setLayout(new FlowLayout(FlowLayout.RIGHT));
	    centralLeft.add(logDel); centralLeft.add(logTD);   centralRight.add(delete);
		
		this.add(panelDolny,BorderLayout.SOUTH);
		panelDolny.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelDolny.add(status);
		panelDolny.setBorder(BorderFactory.createLineBorder(Color.black));
		//Przywracania okna g³ównego po zamkniêciu okna dialogowego 
		this.addComponentListener(new ComponentAdapter(){
			public void componentHidden(ComponentEvent e){
				f.setExtendedState(JFrame.NORMAL);
			}
		});
		
		f.setExtendedState(JFrame.ICONIFIED);
		this.setVisible(true);
		this.pack();
		
		
		create.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				createCount();
			}
		});
		
		delete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				deleteCount();
			}
		});
		System.out.println("Create Delete Count");
	}
	
	public void deleteCount(){//funkcja odpowiedzialna z usuwanie u¿ytkowników
		mapa = glp.getMapa();
		boolean check = true;
		String login = logTD.getText();
		
		if(!mapa.containsKey(login)){
			JOptionPane.showMessageDialog(null,"Login nie istnieje w bazie");
			check = false;
		}	
		
		if(check){
			//usuniecie z mapy loginu
			for(Map.Entry<String,Integer> el : mapa.entrySet()){
				System.out.println("przed usunieciem = "+el.getKey());
			}
			
			mapa.remove(login);
			System.out.println("");
			
            for(Map.Entry<String,Integer> el : mapa.entrySet()){
            	System.out.println("po usunieciu = "+el.getKey());
			}

			status.setForeground(Color.red);
			status.setText(status.getText()+" Usuniêto konto");
			//zapis zmodyfikowanej mapy do pliku
			Save_Passwd_Login.saveDelMap(mapa);
		}
	}
	
	public void createCount(){//funcka odpowiedzialna za tworzenie mapy kont - na podstawie odczytu z Pliku
		boolean check = true;
		mapa = glp.getMapa();
		String newLogin = logT.getText();
		int newPasswd = 0;
		
		for(Map.Entry<String, Integer> el : mapa.entrySet()){
			System.out.println("ok");
			if(el.getKey().equals(newLogin)){
				JOptionPane.showMessageDialog(null,"Ten Login ju¿ istnieje");
				check = false;
			}
		}
		try{
			newPasswd = Integer.parseInt(passT.getText());
			System.out.println("newLogin = "+newLogin+" newPasswd = "+newPasswd);
		}catch(NumberFormatException e){ JOptionPane.showMessageDialog(null, "Has³o musi siê sk³adaæ z cyfr"); check = false; }
		
		if(check){
			//dopisanie loginu i has³a do mapy
			mapa.put(newLogin, newPasswd);
			status.setForeground(Color.green);
			status.setText(status.getText()+" Utworzono Konto");
			//zapis danych do pliku
			Save_Passwd_Login.saveMap(mapa);
		}
	}
	
}

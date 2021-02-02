package Aplication_ComboBox;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

final public class Compare_Log_Pass {//Modu³ do weryfikacji mo¿liwoœci logowania
	private JTextField log,pass;
	private Map<String,Integer> map = null;
	
	public Compare_Log_Pass(JTextField log,JTextField pass,Panel_Logowania pan_Log){
		this.log = log; this.pass = pass;
		GetLogPass glp = new GetLogPass();
		map = glp.getMapa();
		
		int z;
		if( map.containsKey(log.getText()) ){
			int value = map.get(log.getText());
			
		  try{
			if(value == Integer.parseInt(pass.getText())){
				//Wejœcie do programu
				PanelWithMenu pwm = new PanelWithMenu(pan_Log);
			}else{
				JOptionPane.showMessageDialog(null, "B³êdne has³o !");
			}
		  }catch(NumberFormatException e){ JOptionPane.showMessageDialog(null,"Has³o musi siê sk³adaæ z cyfr"); }
			
		}else{
			JOptionPane.showMessageDialog(null, "B³êdny login");
		}
	}
}

